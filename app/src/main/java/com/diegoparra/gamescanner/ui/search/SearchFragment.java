package com.diegoparra.gamescanner.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.FragmentSearchBinding;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.ui.home.HomeFragmentDirections;
import com.diegoparra.gamescanner.utils.ErrorUtils;
import com.diegoparra.gamescanner.utils.EventObserver;
import com.diegoparra.gamescanner.utils.Resource;
import com.diegoparra.gamescanner.utils.SystemUtils;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private SearchGamesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToolbar();
        setupSearchResultsList();
        subscribeObservers();
    }

    private void setupToolbar() {
        binding.icNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.setQuery(s);
                return true;
            }
        });
        binding.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    SystemUtils.hideKeyboard(view);
                }
            }
        });
        binding.searchResults.setFocusable(true);
        binding.searchResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                binding.searchResults.requestFocus();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setupSearchResultsList() {
        adapter = new SearchGamesAdapter(dealId -> viewModel.navigateDetails(dealId));
        binding.searchResults.setHasFixedSize(true);
        binding.searchResults.setAdapter(adapter);
        binding.searchResults.addItemDecoration(new DividerItemDecoration(binding.searchResults.getContext(), DividerItemDecoration.VERTICAL));
    }

    private void subscribeObservers() {
        viewModel.getResults().observe(getViewLifecycleOwner(), new Observer<Resource<List<Game>>>() {
            @Override
            public void onChanged(Resource<List<Game>> listResource) {
                Timber.i("onChanged getResults called! - listResource = %s", listResource);
                ViewUtils.isVisible(binding.progressCircular, listResource.getStatus() == Resource.Status.LOADING);
                ViewUtils.isVisible(binding.searchResults, listResource.getStatus() == Resource.Status.SUCCESS);
                ViewUtils.isVisible(binding.message, listResource.getStatus() == Resource.Status.ERROR);

                switch (listResource.getStatus()) {
                    case LOADING:
                        break;
                    case SUCCESS: {
                        List<Game> data = listResource.getData();
                        Timber.d("data = %s", data);
                        adapter.submitList(data);
                        if (data != null && data.isEmpty()) {
                            if (binding.searchView.getQuery().length() == 0) {
                                binding.message.setText(R.string.search_empty_query);
                            } else {
                                binding.message.setText(R.string.no_search_results_found);
                            }
                            ViewUtils.isVisible(binding.message, true);
                        }
                        break;
                    }
                    case ERROR: {
                        adapter.submitList(Collections.emptyList());
                        Throwable error = listResource.getError();
                        Objects.requireNonNull(error);
                        String errorMessage = ErrorUtils.getMessage(error, binding.getRoot().getContext());
                        binding.message.setText(errorMessage);
                        break;
                    }
                }
            }
        });
        viewModel.getNavigateDetails().observe(getViewLifecycleOwner(), new EventObserver<>(dealId -> {
            NavDirections navDirections = HomeFragmentDirections.actionGlobalGameDetailsFragment(dealId);
            NavHostFragment.findNavController(SearchFragment.this).navigate(navDirections);
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}