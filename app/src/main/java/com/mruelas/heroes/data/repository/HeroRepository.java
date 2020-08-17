package com.mruelas.heroes.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.mruelas.heroes.data.model.Hero;
import com.mruelas.heroes.data.model.Search;
import com.mruelas.heroes.data.remote.HeroAPI;
import com.mruelas.heroes.data.remote.RetrofitService;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeroRepository {
    private static HeroRepository heroRepository;
    private HeroAPI heroAPI;

    public static HeroRepository getInstance(){
        if(heroRepository== null){
            heroRepository = new HeroRepository();
        }
        return heroRepository;
    }

    public HeroRepository(){
        heroAPI = RetrofitService.createService(HeroAPI.class);
    }

    public Observable<Hero> getHero(String id){
        return heroAPI.getHero(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Hero>> getHeroes(int page){
        List<Observable<Hero>> requests = new ArrayList<>();
        int offset = page * 10;
        for (int i = 1; i <=10; i++) {
            requests.add(heroAPI.getHero(String.valueOf(i+offset)));
        }
        return Observable.merge(requests).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Search>searchHero(String name){
        return heroAPI.searchHero(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
