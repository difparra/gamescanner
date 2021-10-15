package com.diegoparra.gamescanner.ui.game_details;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.ListItemDealDetailBinding;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class DealWithStoreAdapter extends ListAdapter<DealWithStore, DealWithStoreAdapter.ViewHolder> {

    protected DealWithStoreAdapter() {
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

        private final ListItemDealDetailBinding binding;

        public ViewHolder(ListItemDealDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DealWithStore dealWithStore) {
            Deal deal = dealWithStore.getDeal();
            loadImage(dealWithStore.getStore().getBannerUrl());
            loadDiscount(deal.isOnSale(), deal.getDiscountPercent());
            loadPrice(deal.isOnSale(), deal.getNormalPrice(), deal.getSalePrice());
        }

        private void loadImage(String imageUrl) {
            Glide.with(binding.storeLogo.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(binding.storeLogo);
        }

        private void loadDiscount(boolean isOnSale, float discount) {
            ViewUtils.isVisible(binding.discount, isOnSale);
            String discountStr = String.valueOf((int) discount) + "%";
            binding.discount.setText(discountStr);
        }

        private void loadPrice(boolean isOnSale, float normalPrice, float salePrice) {
            if(isOnSale) {
                String normalPriceStr = NumberFormat.getCurrencyInstance(Locale.US).format(normalPrice);
                String salePriceStr = NumberFormat.getCurrencyInstance(Locale.US).format(salePrice);
                SpannableString spannableString = new SpannableString(normalPriceStr + " " + salePriceStr);
                spannableString.setSpan(new StrikethroughSpan(), 0, normalPriceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                binding.price.setText(spannableString);
            }else {
                String priceStr = NumberFormat.getCurrencyInstance(Locale.US).format(normalPrice);
                binding.price.setText(priceStr);
            }
        }

        static ViewHolder create(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(ListItemDealDetailBinding.inflate(inflater, parent, false));
        }
    }

    private static final DiffUtil.ItemCallback<DealWithStore> diffCallback = new DiffUtil.ItemCallback<DealWithStore>() {
        @Override
        public boolean areItemsTheSame(@NonNull DealWithStore oldItem, @NonNull DealWithStore newItem) {
            return oldItem.getDeal().getDealId().equals(newItem.getDeal().getDealId()) &&
                    oldItem.getStore().getId().equals(newItem.getStore().getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DealWithStore oldItem, @NonNull DealWithStore newItem) {
            return oldItem.equals(newItem);
        }
    };

}
