package com.cellpointmobile.repoviewer.features.search;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.repoviewer.SearchRepos;
import com.cellpointmobile.repoviewer.data.model.Repository;

import java.util.List;

/**
 * Defined interfaces for model, view, and presenter
 */
public class SearchMVP {

    public interface Model {
        ApolloCall<SearchRepos.Data> getTasks(String searchText);
    }

    public interface View {
        void showData(List<Repository> repository);
        void noData();
    }

    public interface Presenter {
        void fetchData(String test);
    }
}
