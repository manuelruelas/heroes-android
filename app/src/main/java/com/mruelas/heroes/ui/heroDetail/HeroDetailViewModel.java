package com.mruelas.heroes.ui.heroDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mruelas.heroes.data.model.Hero;
import com.mruelas.heroes.data.repository.HeroRepository;

public class HeroDetailViewModel extends ViewModel {
    private MutableLiveData<Hero> heroData;
    private HeroRepository heroRepository;

    public void init(){
        if(heroData != null){
            return;
        }
        heroRepository = HeroRepository.getInstance();
        heroData = new MutableLiveData<>();
    }

    void getHero(String id){
        heroRepository.getHero(id).subscribe(hero -> {
            heroData.setValue(hero);
        }, error->{
            System.out.println(error.getMessage());
        });
    }

    LiveData<Hero>getHeroInfo(){
        return heroData;
    }
}
