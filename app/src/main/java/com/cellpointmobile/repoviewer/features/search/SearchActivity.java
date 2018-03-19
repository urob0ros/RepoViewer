package com.cellpointmobile.repoviewer.features.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cellpointmobile.repoviewer.R;
import com.cellpointmobile.repoviewer.data.model.Repository;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchMVP.View {

    private RecyclerView recyclerView;
    private SearchPresenter searchPresenter = new SearchPresenter(new SearchModel());
    private String searchText;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Repo Viewer");
        getSupportActionBar().setLogo(R.mipmap.ic_icon);

        //presenter setting view
        //presenter fetching data
        searchPresenter.setView(this);
        searchPresenter.fetchData("rxjava");

        //recyclerView of repositories
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchView.setQuery(searchText, false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchText = searchView.getQuery().toString();
                return true;
            }
        });

        //this is a listener to do a search when the user types
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery(query,false);
                searchPresenter.fetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.isEmpty()) {
                    if(searchText == null || searchText.isEmpty()) {
                        searchPresenter.fetchData("rxjava");
                    }
                } else {
                    searchPresenter.fetchData(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void showData(List<Repository> repository) {
        //showing received data from graphql, defining adapter of recyclerview
        SearchAdapter groupsAdapter = new SearchAdapter(repository);
        recyclerView.setAdapter(groupsAdapter);
        groupsAdapter.notifyDataSetChanged();
    }

    @Override
    public void noData() {

    }

}

