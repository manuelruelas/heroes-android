package com.mruelas.heroes.ui.heroList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mruelas.heroes.R;
import com.mruelas.heroes.data.model.Hero;

import java.util.ArrayList;
import java.util.List;


public class HeroListActivity extends AppCompatActivity {
    private final ArrayList<Hero> mHeroesList = new ArrayList<>();

    HeroListViewModel heroListViewModel;
    RecyclerView mRecyclerView;
    HeroListAdapter heroAdapter;
    FloatingActionButton mFab;
    SearchView mSearch;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearch = findViewById(R.id.svSearch);
        mProgress = findViewById(R.id.progressBar);
        mRecyclerView =findViewById(R.id.rvHeroes);

        heroListViewModel =  new ViewModelProvider(this).get(HeroListViewModel.class);
        heroListViewModel.init();

        initObservers();
        setupRecyclerView();
        configureSearchBar();
    }

    void configureSearchBar(){

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mHeroesList.clear();
                heroListViewModel.searchHero(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()==0){
                    heroListViewModel.cancelSearch();
                    mHeroesList.clear();
                    heroAdapter.notifyDataSetChanged();
                    heroListViewModel.getListFromObservable();
                }
                return false;
            }

        });
    }

    void setupRecyclerView(){
        if (heroAdapter == null){
            heroAdapter = new HeroListAdapter(this,mHeroesList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(heroAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setNestedScrollingEnabled(true);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager){
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    heroListViewModel.getListFromObservable();
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else {
            heroAdapter.notifyDataSetChanged();
        }
    }

    void initObservers(){
        heroListViewModel.getViewModelState().observe(this,state->{
            switch (state){
                case loading:
                    mProgress.setVisibility(View.VISIBLE);
                    break;
                case ready:
                    mProgress.setVisibility(View.INVISIBLE);
                    break;
            }
        });
        heroListViewModel.getHero().observe(this, (List<Hero> response) -> {
            mHeroesList.addAll(response);
            heroAdapter.notifyDataSetChanged();

        });
    }

    void scrollRecycler(){
        mRecyclerView.postDelayed(() -> mRecyclerView.smoothScrollToPosition(mHeroesList.size()-10),1000);
    }

}
