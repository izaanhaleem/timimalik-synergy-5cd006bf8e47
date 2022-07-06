package com.hisdu.ses.ZeroDoseVaccination;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.SESCluster.interfaces.onChildSaveClick;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.R;
import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChildModelData> movieList;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;
    onChildSaveClick onChildClick;
    private Boolean enableclick = true;

    public ChildListAdapter(Context context, List<ChildModelData> data, onChildSaveClick onChildClick, Boolean enableclick) {
        this.context = context;
        this.movieList = data;
        this.onChildClick = onChildClick;
        this.enableclick = enableclick;
    }

    public void setMovieList(List<ChildModelData> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.child_item, parent, false);
                viewHolder = new MovieViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChildModelData movie = movieList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.child_name.setText(movie.getChildName());
                movieViewHolder.father_name.setText(movie.getFatherName());
                movieViewHolder.age.setText(movie.getAge());
                movieViewHolder.house_no.setText(movie.getHouseNo());
                if(movie.getVaccinated()!=null){
                    movieViewHolder.isvacciated_layout.setVisibility(View.VISIBLE);
                    if(movie.getVaccinated()){
                        movieViewHolder.is_vaccinated.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.vaccinated));

                    }else {
                        movieViewHolder.is_vaccinated.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.not_vaccinated));

                    }
                }else {
                    movieViewHolder.isvacciated_layout.setVisibility(View.GONE);
                }

                break;

            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enableclick) {
                    Bundle args = new Bundle();
                    String personJsonString = Utils.getGsonParser().toJson(movie);
                    args.putString("child", personJsonString);
                    ChildDetailFragment fragment = new ChildDetailFragment(onChildClick);
                    fragment.setArguments(args);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.content_frame, fragment).addToBackStack(null).commit();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ChildModelData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieList.size() - 1;
        ChildModelData result = getItem(position);

        if (result != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(ChildModelData movie) {
//        if(movie.getChildName()!=null){
//
//        }
        movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

    public void addAll(List<ChildModelData> moveResults) {
        for (ChildModelData result : moveResults) {
            add(result);
        }
    }

    public ChildModelData getItem(int position) {
        return movieList.get(position);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView child_name, father_name, age, house_no;
        private ImageView movieImage,is_vaccinated;
        private LinearLayout isvacciated_layout;

        public MovieViewHolder(View itemView) {
            super(itemView);
            child_name = (TextView) itemView.findViewById(R.id.child_name);
            father_name = (TextView) itemView.findViewById(R.id.father_name);
            age = (TextView) itemView.findViewById(R.id.age);
            house_no = (TextView) itemView.findViewById(R.id.house_no);
            is_vaccinated= (ImageView) itemView.findViewById(R.id.is_vaccinated);
            isvacciated_layout= (LinearLayout) itemView.findViewById(R.id.isvacciated_layout);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);

        }
    }

}