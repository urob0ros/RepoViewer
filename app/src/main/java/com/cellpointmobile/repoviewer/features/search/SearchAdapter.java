package com.cellpointmobile.repoviewer.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.starCount.setText(Integer.toString(repository.getStarCount()));
        holder.forkCount.setText(Integer.toString(repository.getForkCount()));
        switch (repository.getLanguage()) {
            case "Java":
                holder.imageView.setImageResource(R.drawable.javaicon);
                break;
            case "JavaScript":
                holder.imageView.setImageResource(R.drawable.javascripticon);
                break;
            case "HTML":
                holder.imageView.setImageResource(R.drawable.htmlicon);
                break;
            case "Python":
                holder.imageView.setImageResource(R.drawable.phytonicon);
                break;
            case "PHP":
                holder.imageView.setImageResource(R.drawable.phpicon);
                break;
            case "Ruby":
                holder.imageView.setImageResource(R.drawable.rubyicon);
                break;
            case "C#":
                holder.imageView.setImageResource(R.drawable.csharp);
                break;
            case "CSS":
                holder.imageView.setImageResource(R.drawable.cssicon);
                break;
            case "C++":
                holder.imageView.setImageResource(R.drawable.cplusicon);
                break;
            case "C":
                holder.imageView.setImageResource(R.drawable.cplusicon);
                break;
            default:
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView starCount;
        TextView forkCount;
        ImageView imageView;
        public Repository data;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            description = view.findViewById(R.id.subtitle);
            starCount = view.findViewById(R.id.star_count);
            forkCount = view.findViewById(R.id.fork_count);
            imageView = view.findViewById(R.id.thumbnail);
        }
    }
}

