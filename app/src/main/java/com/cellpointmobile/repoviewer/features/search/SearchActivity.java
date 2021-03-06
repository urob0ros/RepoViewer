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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cellpointmobile.repoviewer.R;
import com.cellpointmobile.repoviewer.data.model.Repository;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class SearchActivity extends AppCompatActivity implements SearchMVP.View {

    private RecyclerView recyclerView;
    private SearchPresenter searchPresenter = new SearchPresenter(new SearchModel());
    private String searchText;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Repo Viewer");
        getSupportActionBar().setLogo(R.mipmap.ic_icon);
        Realm.getDefaultInstance();

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
    public void showData(OrderedRealmCollection<Repository> repository) {
        //showing received data from graphql, defining adapter of recyclerview
        SearchAdapter searchAdapter = new SearchAdapter(repository);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noData() {
        Toast.makeText(getApplicationContext(),"No results found",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noNetwork() {
        Toast.makeText(getApplicationContext(),"No network",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }
}

