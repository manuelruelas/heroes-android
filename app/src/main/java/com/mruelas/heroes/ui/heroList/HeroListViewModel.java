package com.mruelas.heroes.ui.heroList;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mruelas.heroes.data.model.Hero;
import com.mruelas.heroes.data.repository.HeroRepository;

import java.util.List;


public class HeroListViewModel extends ViewModel {
    private MutableLiveData<List<Hero>> heroesLiveData;
    private HeroRepository heroRepository;
    private int currentPage = 0;
    private MutableLiveData<ViewModelState> state =new MutableLiveData<>(ViewModelState.loading);
    private boolean searchActive = false;

    public void  init(){
        if(heroesLiveData != null){
            return;
        }
        heroRepository = HeroRepository.getInstance();
        heroesLiveData = new MutableLiveData<>();
        getListFromObservable();
    }
    public void searchHero(String name){
        currentPage = 0;
        searchActive = true;
        state.setValue(ViewModelState.loading);
        heroRepository.searchHero(name).subscribe(search -> {
            heroesLiveData.setValue(search.getResults());
           state.setValue(ViewModelState.ready);
        },error->{
            state.setValue(ViewModelState.ready);
            System.out.println(error.getMessage());
        });
    }


    public void getListFromObservable(){
        if(!searchActive){
            state.setValue(ViewModelState.loading);
            heroRepository.getHeroes(currentPage).subscribe(heroes -> {
                heroesLiveData.setValue(heroes);
                currentPage++;
                state.setValue(ViewModelState.ready);
            },error->{
                state.setValue(ViewModelState.ready);
                System.out.println(error.getMessage());
            });
        }

    }
    public void cancelSearch(){
        searchActive = false;
    }
    public MutableLiveData<List<Hero>> getHero(){
        return heroesLiveData;
    }

    public MutableLiveData<ViewModelState> getViewModelState(){return state;}
}


enum ViewModelState{
    ready,
    loading,
}