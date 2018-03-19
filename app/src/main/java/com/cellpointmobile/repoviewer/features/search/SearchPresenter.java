package com.cellpointmobile.repoviewer.features.search;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.repoviewer.SearchRepos;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.cellpointmobile.repoviewer.data.model.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *  Initialized model and view for presenter
 *  Initialized RxJava listener and fetched data for view
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

    @Override
    public void fetchData(String searchText) {
        disposables.add(Rx2Apollo.from(searchModel.getTasks(searchText))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<SearchRepos.Data>>() {
                    @Override
                    public void onSuccess(Response<SearchRepos.Data> dataResponse) {
                        final SearchRepos.Search entry = dataResponse.data().search();
                        if (entry.edges() != null) {
                            List<Repository> repositories = new ArrayList<>();
                            for (int i = 0; i < entry.edges().size(); i++) {
                                if (entry.edges().get(i).node().asRepository().primaryLanguage() != null) {
                                    repositories.add(new Repository(entry.edges().get(i).node().asRepository().name(), entry.edges().get(i).node().asRepository().description(), entry.edges().get(i).node().asRepository().primaryLanguage().name()));
                                }
                            }
                            view.showData(repositories);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.noData();
                    }
                }));
    }
}
