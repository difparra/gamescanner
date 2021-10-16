package com.diegoparra.gamescanner.ui.game_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.FragmentDealDetailsBinding;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.MetacriticInfo;
import com.diegoparra.gamescanner.models.SteamInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ImageUtils;
import com.diegoparra.gamescanner.utils.NavigationUtils;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DealDetailsFragment extends Fragment {

    private DealDetailsViewModel viewModel;
    private FragmentDealDetailsBinding binding;
    private DealWithStoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DealDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpToolbar();
        setUpDealsList();
        subscribeObservers();
    }

    private void setUpToolbar() {
        NavigationUtils.setupToolbar(binding.toolbar);
    }

    private void setUpDealsList() {
        adapter = new DealWithStoreAdapter();
        binding.dealsList.setAdapter(adapter);
    }

    private void subscribeObservers() {
        viewModel.getDealWithGameInfo().observe(getViewLifecycleOwner(), new Observer<DealWithGameAndStoreInfo>() {
            @Override
            public void onChanged(DealWithGameAndStoreInfo dealWithGameAndStoreInfo) {
                loadGameInfo(dealWithGameAndStoreInfo.getGame());
                loadDealAndStoreInfo(dealWithGameAndStoreInfo.getDeal(), dealWithGameAndStoreInfo.getStore());
            }

            private void loadGameInfo(Game game) {
                if(game != null) {
                    binding.toolbar.setTitle(game.getTitle());
                    binding.title.setText(game.getTitle());
                    loadImage(game.getImageUrl());
                    loadReleaseDate(game.getReleaseDate());
                    loadSteamInfo(game.getSteamInfo());
                    loadMetacriticInfo(game.getMetacriticInfo());
                }
            }

            private void loadImage(String imageUrl) {
                ImageUtils.loadImageWithPlaceholderAndError(binding.image, imageUrl);
            }

            private void loadReleaseDate(LocalDate releaseDate) {
                ViewUtils.isVisible(binding.releaseDate, releaseDate != null);
                if (releaseDate != null) {
                    String dateFormatted = releaseDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
                    String dateStr = binding.getRoot().getContext().getString(R.string.release_date, dateFormatted);
                    binding.releaseDate.setText(dateStr);
                }
            }

            private void loadSteamInfo(SteamInfo steamInfo) {
                boolean isVisible = steamInfo != null && steamInfo.getRatingPercent() > 0;
                ViewUtils.isVisible(binding.steamScore, isVisible);
                if (isVisible) {
                    String score = String.valueOf(steamInfo.getRatingPercent()) + "%";
                    String ratingText = steamInfo.getRatingText();
                    String str = binding.getRoot().getContext().getString(R.string.steam_score_info, score, ratingText);
                    binding.steamScore.setText(str);
                }
            }

            private void loadMetacriticInfo(MetacriticInfo metacriticInfo) {
                boolean isVisible = metacriticInfo != null && metacriticInfo.getRatingPercent() > 0;
                ViewUtils.isVisible(binding.metacriticScore, isVisible);
                if (isVisible) {
                    String score = String.valueOf(metacriticInfo.getRatingPercent()) + "%";
                    String str = binding.getRoot().getContext().getString(R.string.metacritic_score_info, score);
                    binding.metacriticScore.setText(str);
                }
            }

            private void loadDealAndStoreInfo(Deal deal, Store store) {
                ImageUtils.loadImageWithPlaceholderAndError(binding.selectedDealLogo, store.getBannerUrl());
                ViewUtils.isVisible(binding.selectedDealDiscount, deal.isOnSale());
                if(deal.isOnSale()) {
                    String discountPercentStr = String.valueOf((int) deal.getDiscountPercent()) + "%";
                    binding.selectedDealDiscount.setText(discountPercentStr);
                }
                String priceStr = NumberFormat.getCurrencyInstance(Locale.US).format(deal.getSalePrice());
                binding.selectedDealPrice.setText(priceStr);
            }
        });

        viewModel.getAdditionalDealsWithStoreInfo().observe(getViewLifecycleOwner(), new Observer<List<DealWithStore>>() {
            @Override
            public void onChanged(List<DealWithStore> dealWithStores) {
                adapter.submitList(dealWithStores);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}