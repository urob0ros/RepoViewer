package com.cellpointmobile.repoviewer.features.search;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.cache.http.HttpCachePolicy;
import com.apollographql.apollo.repoviewer.SearchRepos;
import com.cellpointmobile.repoviewer.data.service.GraphqlService;

/**
 * Getting repositories list by searchText
 */
public class SearchModel implements SearchMVP.Model {

    @Override
    public ApolloCall<SearchRepos.Data> getTasks(String searchText) {
        return GraphqlService.getService()
                .query(new SearchRepos(searchText))
                .httpCachePolicy(HttpCachePolicy.NETWORK_FIRST);
    }

}
