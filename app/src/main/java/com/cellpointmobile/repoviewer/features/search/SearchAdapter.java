package com.cellpointmobile.repoviewer.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cellpointmobile.repoviewer.R;
import com.cellpointmobile.repoviewer.data.model.Repository;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Github search adapter which binds data to each item in the recycler view
 */
public class SearchAdapter extends RealmRecyclerViewAdapter<Repository, SearchAdapter.ViewHolder> {

    public SearchAdapter(OrderedRealmCollection<Repository> repos) {
        super(repos, true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //repository model receives attributes using graphql query
        final Repository repository = getItem(position);
        holder.data = repository;
        holder.name.setText(repository.getName());
        holder.description.setText(repository.getDescription());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        public Repository data;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            description = view.findViewById(R.id.subtitle);
        }
    }
}

