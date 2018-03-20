package com.cellpointmobile.repoviewer.features.search;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.repoviewer.SearchRepos;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.cellpointmobile.repoviewer.MvpApplication;
import com.cellpointmobile.repoviewer.data.model.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Initialized model and view for presenter
 * Initialized RxJava listener and fetched data for view
 * Implemented getNetworkState method for checking network state
 */
public class SearchPresenter implements SearchMVP.Presenter {

    private SearchModel searchModel;
    private SearchMVP.View view;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchPresenter(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    public SearchMVP.View getView() {
        return view;
    }

    public void setView(SearchMVP.View view) {
        this.view = view;
    }

    /**
     * Fetches data via a graphql query using a search text
     * @param searchText - filters the search
     */
    @Override
    public void fetchData(String searchText) {
        view.showLoadingDialog();
        disposables.add(Rx2Apollo.from(searchModel.getTasks(searchText))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<SearchRepos.Data>>() {
                    @Override
                    public void onSuccess(Response<SearchRepos.Data> dataResponse) {

                        //clear repo table to get the latest data
                        try(Realm realmInstance = Realm.getDefaultInstance()) {
                            realmInstance.executeTransaction(realm -> realm.delete(Repository.class));
                        }

                        final SearchRepos.Search entry = dataResponse.data().search();
                        if (entry.edges() != null) {
                            List<Repository> repositories = new ArrayList<>();

                            //get graphql data to store in repositories list
                            for (int i = 0; i < entry.edges().size(); i++) {
                                if (entry.edges().get(i).node().asRepository().primaryLanguage() != null) {
                                    repositories.add(new Repository(entry.edges().get(i).node().asRepository().name(),
                                            entry.edges().get(i).node().asRepository().description(),
                                            entry.edges().get(i).node().asRepository().primaryLanguage().name(),
                                            entry.edges().get(i).node().asRepository().stargazers().totalCount(),
                                            entry.edges().get(i).node().asRepository().forks().totalCount()));
                                }
                            }

                            //save data in repos.realm
                            for (Repository repo : repositories) {
                                if(repositories.size() > 0) {
                                    try (Realm realmInstance = Realm.getDefaultInstance()) {
                                        realmInstance.executeTransaction((realm) -> realm.insertOrUpdate(repo));
                                    }
                                }
                            }

                            if(repositories.isEmpty()) {
                                view.noData();
                            }

                            showRealmRepos();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getNetworkState()) {
                            view.noData();
                        } else {
                            showRealmRepos();
                            view.noNetwork();
                        }
                    }
                }));
    }

    /**
     * Gets all data from repos.realm table and sends to view
     */
    private void showRealmRepos() {
        try(Realm realmInstance = Realm.getDefaultInstance()) {
            RealmResults<Repository> list = realmInstance.where(Repository.class).findAll();
            view.showData(list);
        }
    }

    /**
     * Returns true if connected to a network
     * @return
     */
    private boolean getNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MvpApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
}
