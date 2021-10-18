package com.diegoparra.gamescanner.ui.game_details;

import android.content.Intent;
import android.net.Uri;
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
import com.diegoparra.gamescanner.utils.ErrorUtils;
import com.diegoparra.gamescanner.utils.ImageUtils;
import com.diegoparra.gamescanner.utils.NavigationUtils;
import com.diegoparra.gamescanner.utils.Resource;
import com.diegoparra.gamescanner.utils.ViewUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

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
        adapter = new DealWithStoreAdapter(this::openGoToDealLink);
        binding.dealsList.setAdapter(adapter);
    }

    private void subscribeObservers() {
        viewModel.getDealWithGameInfo().observe(getViewLifecycleOwner(), new Observer<Resource<DealWithGameAndStoreInfo>>() {
            @Override
            public void onChanged(Resource<DealWithGameAndStoreInfo> dealWithGameAndStoreInfo) {
                ViewUtils.isVisible(binding.content, dealWithGameAndStoreInfo.getStatus() == Resource.Status.SUCCESS);
                ViewUtils.isVisible(binding.errorMessage, dealWithGameAndStoreInfo.getStatus() == Resource.Status.ERROR);

                switch (dealWithGameAndStoreInfo.getStatus()) {
                    case SUCCESS: {
                        DealWithGameAndStoreInfo data = Objects.requireNonNull(dealWithGameAndStoreInfo.getData());
                        loadInfo(data);
                        break;
                    }
                    case ERROR: {
                        Throwable error = Objects.requireNonNull(dealWithGameAndStoreInfo.getError());
                        displayError(error);
                        break;
                    }
                }
            }

            private void loadInfo(@NonNull DealWithGameAndStoreInfo data) {
                loadGameInfo(data.getGame());
                loadDealAndStoreInfo(data.getDeal(), data.getStore());
            }

            private void loadGameInfo(@NonNull Game game) {
                binding.toolbar.setTitle(game.getTitle());
                binding.title.setText(game.getTitle());
                loadImage(game.getImageUrl());
                loadReleaseDate(game.getReleaseDate());
                loadSteamInfo(game.getSteamInfo());
                loadMetacriticInfo(game.getMetacriticInfo());
            }

            private void loadImage(@Nullable String imageUrl) {
                ImageUtils.loadImageWithPlaceholderAndError(binding.image, imageUrl);
            }

            private void loadReleaseDate(@Nullable LocalDate releaseDate) {
                ViewUtils.isVisible(binding.releaseDate, releaseDate != null);
                if (releaseDate != null) {
                    String dateFormatted = releaseDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
                    String dateStr = binding.getRoot().getContext().getString(R.string.release_date, dateFormatted);
                    binding.releaseDate.setText(dateStr);
                }
            }

            private void loadSteamInfo(@Nullable SteamInfo steamInfo) {
                boolean isVisible = steamInfo != null && steamInfo.getRatingPercent() != null && steamInfo.getRatingPercent() > 0;
                ViewUtils.isVisible(binding.steamScore, isVisible);
                if (isVisible) {
                    String score = String.valueOf(steamInfo.getRatingPercent()) + "%";
                    String ratingText = steamInfo.getRatingText();
                    String str = binding.getRoot().getContext().getString(R.string.steam_score_info, score, ratingText);
                    binding.steamScore.setText(str);
                }
            }

            private void loadMetacriticInfo(@Nullable MetacriticInfo metacriticInfo) {
                boolean isVisible = metacriticInfo != null && metacriticInfo.getRatingPercent() != null && metacriticInfo.getRatingPercent() > 0;
                ViewUtils.isVisible(binding.metacriticScore, isVisible);
                if (isVisible) {
                    String score = String.valueOf(metacriticInfo.getRatingPercent()) + "%";
                    String str = binding.getRoot().getContext().getString(R.string.metacritic_score_info, score);
                    binding.metacriticScore.setText(str);
                }
            }

            private void loadDealAndStoreInfo(@NonNull Deal deal, @NonNull Store store) {
                binding.cardSelectedDeal.setOnClickListener(view -> {
                    if (deal.getGoToDealLink() != null) {
                        openGoToDealLink(deal.getGoToDealLink());
                    }
                });

                ImageUtils.loadImageWithPlaceholderAndError(binding.selectedDealLogo, store.getBannerUrl());

                ViewUtils.isVisible(binding.selectedDealDiscount, deal.isOnSale());
                if (deal.isOnSale()) {
                    String discountPercentStr = String.valueOf((int) deal.getDiscountPercent()) + "%";
                    binding.selectedDealDiscount.setText(discountPercentStr);
                }

                String priceStr = NumberFormat.getCurrencyInstance(Locale.US).format(deal.getSalePrice());
                binding.selectedDealPrice.setText(priceStr);
            }
        });

        viewModel.getAdditionalDealsWithStoreInfo().observe(getViewLifecycleOwner(), new Observer<Resource<List<DealWithStore>>>() {
            @Override
            public void onChanged(Resource<List<DealWithStore>> dealWithStores) {
                ViewUtils.isVisible(binding.content, dealWithStores.getStatus() == Resource.Status.SUCCESS);
                ViewUtils.isVisible(binding.errorMessage, dealWithStores.getStatus() == Resource.Status.ERROR);

                switch (dealWithStores.getStatus()) {
                    case SUCCESS: {
                        List<DealWithStore> data = dealWithStores.getData();
                        Objects.requireNonNull(data);
                        adapter.submitList(data);
                        break;
                    }
                    case ERROR: {
                        adapter.submitList(Collections.emptyList());
                        Throwable error = Objects.requireNonNull(dealWithStores.getError());
                        displayError(error);
                        break;
                    }
                }
            }
        });
    }


    private void openGoToDealLink(@NonNull String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e("Couldn't open URL = " + url + ", exception = " + e);
        }
    }

    private void displayError(@NonNull Throwable error) {
        String errorMessage = ErrorUtils.getMessage(error, binding.getRoot().getContext());
        binding.errorMessage.setText(errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}