package com.diegoparra.gamescanner.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.ListItemSearchGameResultBinding;
import com.diegoparra.gamescanner.models.Game;

import java.text.NumberFormat;
import java.util.Locale;

public class SearchGamesAdapter extends ListAdapter<Game, SearchGamesAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;

    protected SearchGamesAdapter(OnItemClickListener onItemClickListener) {
        super(diffCallback);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = getItem(position);
        if(game != null) {
            holder.bind(game);
        }
    }


    interface OnItemClickListener {
        void onItemClick(String dealId);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemSearchGameResultBinding binding;
        private Game game;

        public ViewHolder(ListItemSearchGameResultBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(game != null && game.getCheapestDealId() != null) {
                        onItemClickListener.onItemClick(game.getCheapestDealId());
                    }
                }
            });
        }

        public void bind(@NonNull Game game) {
            this.game = game;
            binding.title.setText(game.getTitle());
            if(game.getCheapestCurrentPrice() != null) {
                String priceStr = NumberFormat.getCurrencyInstance(Locale.US).format(game.getCheapestCurrentPrice());
                binding.price.setText(binding.getRoot().getContext().getString(R.string.price_from, priceStr));
            }
        }


        static ViewHolder create(ViewGroup parent, OnItemClickListener onItemClickListener) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(ListItemSearchGameResultBinding.inflate(inflater, parent, false), onItemClickListener);
        }

    }

    private static final DiffUtil.ItemCallback<Game> diffCallback = new DiffUtil.ItemCallback<Game>() {
        @Override
        public boolean areItemsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
            return oldItem.getGameId().equals(newItem.getGameId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
            return oldItem.equals(newItem);
        }
    };

}
