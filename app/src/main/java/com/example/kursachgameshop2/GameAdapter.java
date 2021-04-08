package com.example.kursachgameshop2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursachgameshop2.data.FavouriteGame;
import com.example.kursachgameshop2.data.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> implements Filterable {

    private List<Game> games;
    private List<Game> allGames;

    private List<FavouriteGame> favouriteGames;
     OnImageGameListener onImageGameListener;
     OnReachEndListener onReachEndListener;

    public GameAdapter(){
        games = new ArrayList<>();
        allGames = new ArrayList<>(games);
    }

    public void setOnImageGameListener(OnImageGameListener onImageGameListener) {
        this.onImageGameListener = onImageGameListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Game> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(allGames);
            }else{
                for(Game game : allGames){
                    if(game.getGameTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(game);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            games.clear();
            games.addAll((Collection<? extends Game>) results.values);
            notifyDataSetChanged();
        }
    };


    public interface OnImageGameListener {

        void onImageClick(int position);
    }

    public interface OnReachEndListener{

        void onReachEnd();

    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item,parent,false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {

        if(games.size() >= 20 && position > games.size() - 2 && onReachEndListener != null)
        {
            onReachEndListener.onReachEnd();
        }
        Game game = games.get(position);
        Picasso.get().load(game.getBoxartMedium()).into(holder.imageViewSmall);
        holder.textViewGameTitle.setText(game.getGameTitle());
        holder.textViewGamePrice.setText(game.getPrice());

    }

    public void clear(){
        this.games.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    class GameViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewSmall;
        private TextView textViewGameTitle;
        private TextView textViewGamePrice;


        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmall = itemView.findViewById(R.id.imageSmallImage);
            textViewGameTitle = itemView.findViewById(R.id.TextViewGameTitle);
            textViewGamePrice = itemView.findViewById(R.id.TextViewGamePriceTest);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onImageGameListener != null)
                    {
                        onImageGameListener.onImageClick(getAdapterPosition());
                    }
                }
            });
        }
    }


    public List<Game> getGames() {
        return games;
    }

    public void addGames(List<Game> games){

        this.games.addAll(games);
        notifyDataSetChanged();
    }


    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

}
