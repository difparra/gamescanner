package com.diegoparra.gamescanner.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.diegoparra.gamescanner.databinding.ListItemDealHomeBinding;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;

public class DealsAdapter extends ListAdapter<DealWithGameInfo, DealsAdapter.ViewHolder> {

    protected DealsAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemDealHomeBinding binding;

        public ViewHolder(ListItemDealHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DealWithGameInfo dealWithGameInfo) {
            Deal dealInfo = dealWithGameInfo.getDeal();
            Game gameInfo = dealWithGameInfo.getGame();
            binding.title.setText(gameInfo.getTitle());
        }

        static ViewHolder create(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(ListItemDealHomeBinding.inflate(inflater, parent, false));
        }

    }

    private static final DiffUtil.ItemCallback<DealWithGameInfo> diffCallback = new DiffUtil.ItemCallback<DealWithGameInfo>() {
        @Override
        public boolean areItemsTheSame(@NonNull DealWithGameInfo oldItem, @NonNull DealWithGameInfo newItem) {
            return oldItem.getDeal().getDealId().equals(newItem.getDeal().getDealId()) && oldItem.getGame().getGameId().equals(newItem.getGame().getGameId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DealWithGameInfo oldItem, @NonNull DealWithGameInfo newItem) {
            return oldItem.equals(newItem);
        }
    };

}
