package com.diegoparra.gamescanner.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.diegoparra.gamescanner.databinding.LoadStateFooterDealsListBinding;
import com.diegoparra.gamescanner.utils.ErrorUtils;
import com.diegoparra.gamescanner.utils.ViewUtils;

public class DealsLoadStateAdapter extends LoadStateAdapter<DealsLoadStateAdapter.ViewHolder> {

    @NonNull
    private final RetryListener retryListener;

    public DealsLoadStateAdapter(@NonNull RetryListener retryListener) {
        this.retryListener = retryListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoadState loadState) {
        holder.bind(loadState);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        return ViewHolder.create(parent, retryListener);
    }

    public interface RetryListener {
        void onClick();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LoadStateFooterDealsListBinding binding;

        public ViewHolder(@NonNull LoadStateFooterDealsListBinding binding, @NonNull RetryListener retryListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.retryButton.setOnClickListener(view -> retryListener.onClick());
        }

        public void bind(@NonNull LoadState loadState) {
            if(loadState instanceof LoadState.Error) {
                String errorMessage = ErrorUtils.getMessage(((LoadState.Error) loadState).getError(),binding.errorMsg.getContext());
                binding.errorMsg.setText(errorMessage);
            }
            ViewUtils.isVisible(binding.progressBar, loadState instanceof LoadState.Loading);
            ViewUtils.isVisible(binding.retryButton, loadState instanceof LoadState.Error);
            ViewUtils.isVisible(binding.errorMsg, loadState instanceof LoadState.Error);
        }

        static ViewHolder create(@NonNull ViewGroup parent, @NonNull RetryListener retryListener) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(LoadStateFooterDealsListBinding.inflate(inflater, parent, false), retryListener);
        }
    }

}
