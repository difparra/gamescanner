package com.diegoparra.gamescanner.ui.home;

import android.content.res.Resources;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.ListItemHomeDealBinding;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.utils.ImageUtils;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DealWithGameInfoAdapter extends ListAdapter<DealWithGameInfo, DealWithGameInfoAdapter.ViewHolder> {

    private final OnItemClickListener listener;

    public DealWithGameInfoAdapter(OnItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface OnItemClickListener {
        void onItemClick(String dealId);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemHomeDealBinding binding;
        private DealWithGameInfo dealWithGameInfo;

        public ViewHolder(ListItemHomeDealBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dealWithGameInfo != null) {
                        listener.onItemClick(dealWithGameInfo.getDeal().getDealId());
                    }
                }
            });
        }

        public void bind(DealWithGameInfo dealWithGameInfo) {
            this.dealWithGameInfo = dealWithGameInfo;
            Deal dealInfo = dealWithGameInfo.getDeal();
            Game gameInfo = dealWithGameInfo.getGame();
            binding.title.setText(gameInfo.getTitle());
            loadImage(gameInfo.getImageUrl());
            loadReleaseDate(gameInfo.getReleaseDate());
            loadLastUpdated(dealInfo.getTimeSinceLastChange());
            loadPrices(dealInfo.getNormalPrice(), dealInfo.getSalePrice(), dealInfo.getDiscountPercent());
        }

        private void loadImage(String imageUrl) {
            ImageUtils.loadImageWithPlaceholderAndError(binding.image, imageUrl);
        }

        private void loadReleaseDate(LocalDate releaseDate) {
            ViewUtils.isVisible(binding.releaseDate, releaseDate != null);
            if (releaseDate != null) {
                String dateFormatted = releaseDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
                String dateStr = binding.getRoot().getContext().getString(R.string.release_date, dateFormatted);
                binding.releaseDate.setText(dateStr);
            }
        }

        private void loadLastUpdated(Duration timeSinceLastUpdated) {
            ViewUtils.isVisible(binding.lastUpdated, timeSinceLastUpdated != null);
            if (timeSinceLastUpdated != null) {
                long days = timeSinceLastUpdated.toDays();
                long hours = timeSinceLastUpdated.toHours();
                long minutes = timeSinceLastUpdated.toMinutes();
                String str = null;
                Resources resources = binding.getRoot().getResources();
                if (days > 0) {
                    str = resources.getQuantityString(R.plurals.updated_days_ago, (int) days, (int) days);
                } else if (hours > 0) {
                    str = resources.getQuantityString(R.plurals.updated_hours_ago, (int) hours, (int) hours);
                } else {
                    str = resources.getQuantityString(R.plurals.updated_minutes_ago, (int) minutes, (int) minutes);
                }
                binding.lastUpdated.setText(str);
            }
        }

        private void loadPrices(float normalPrice, float salePrice, float discountPercent) {
            String normalPriceStr = NumberFormat.getCurrencyInstance(Locale.US).format(normalPrice);
            SpannableString normalPriceStrStrike = strikeCompleteString(normalPriceStr);
            binding.normalPrice.setText(normalPriceStrStrike);

            String salePriceStr = NumberFormat.getCurrencyInstance(Locale.US).format(salePrice);
            binding.salePrice.setText(salePriceStr);

            String discountPercentStr = String.valueOf((int) discountPercent) + "%";
            binding.discount.setText(discountPercentStr);
        }

        private SpannableString strikeCompleteString(String text) {
            SpannableString strikedNormalPriceStr = new SpannableString(text);
            strikedNormalPriceStr.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return strikedNormalPriceStr;
        }


        static ViewHolder create(ViewGroup parent, OnItemClickListener listener) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(ListItemHomeDealBinding.inflate(inflater, parent, false), listener);
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
