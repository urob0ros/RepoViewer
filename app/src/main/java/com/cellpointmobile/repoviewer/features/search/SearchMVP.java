package com.cellpointmobile.repoviewer.features.search;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.repoviewer.SearchRepos;
import com.cellpointmobile.repoviewer.data.model.Repository;

import io.realm.OrderedRealmCollection;

/**
 * Defined interfaces for model, view, and presenter
 */
public class SearchMVP {

    public interface Model {
        ApolloCall<SearchRepos.Data> getTasks(String searchText);
    }

    public interface View {
        void showData(OrderedRealmCollection<Repository> repository);
        void noData();
        void noNetwork();
    }

    public interface Presenter {
        void fetchData(String searchText);
    }
}
