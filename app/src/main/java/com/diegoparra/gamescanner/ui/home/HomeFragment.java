package com.diegoparra.gamescanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.FragmentHomeBinding;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.ErrorUtils;
import com.diegoparra.gamescanner.utils.EventObserver;
import com.diegoparra.gamescanner.utils.NavigationUtils;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.net.UnknownHostException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private DealWithGameInfoPagedAdapter pagedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToolbar();
        setupDealsList();
        subscribeObservers();
    }


    private void setupToolbar() {
        NavigationUtils.setupToolbar(binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item_search) {
                    NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToSearchFragment();
                    NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);
                }
                return true;
            }
        });
    }

    private void setupDealsList() {
        binding.dealsList.setHasFixedSize(true);
        pagedAdapter = new DealWithGameInfoPagedAdapter(dealId -> viewModel.navigateDetails(dealId));
        ConcatAdapter pagedAdapterWithLoadStateHeaderAndFooter = pagedAdapter.withLoadStateHeaderAndFooter(
                new DealsLoadStateAdapter(pagedAdapter::retry),
                new DealsLoadStateAdapter(pagedAdapter::retry)
        );
        binding.dealsList.setAdapter(pagedAdapterWithLoadStateHeaderAndFooter);
        binding.dealsList.addItemDecoration(new DividerItemDecoration(binding.dealsList.getContext(), DividerItemDecoration.VERTICAL));

        binding.retryButton.setOnClickListener(view -> pagedAdapter.retry());
    }

    private void subscribeObservers() {
        viewModel.getDealWithGameInfoList().observe(getViewLifecycleOwner(), new Observer<PagingData<DealWithGameInfo>>() {
            @Override
            public void onChanged(PagingData<DealWithGameInfo> dealWithGameInfoPagingData) {
                pagedAdapter.submitData(getLifecycle(), dealWithGameInfoPagingData);
            }
        });
        pagedAdapter.addLoadStateListener(loadStates -> {
            ViewUtils.isVisible(binding.progressCircular, loadStates.getRefresh() instanceof LoadState.Loading);

            boolean isError = loadStates.getRefresh() instanceof LoadState.Error;
            ViewUtils.isVisible(binding.errorMessage, isError);
            ViewUtils.isVisible(binding.retryButton, isError);
            if (isError) {
                String errorMsg = ErrorUtils.getMessage(((LoadState.Error) loadStates.getRefresh()).getError(), binding.getRoot().getContext());
                binding.errorMessage.setText(errorMsg);
            }

            return null;
        });
        viewModel.getNavigateDetails().observe(getViewLifecycleOwner(), new EventObserver<>(dealId -> {
            NavDirections navDirections = HomeFragmentDirections.actionGlobalGameDetailsFragment(dealId);
            NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}