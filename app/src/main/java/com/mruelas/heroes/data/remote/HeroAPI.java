package com.mruelas.heroes.data.remote;

import com.mruelas.heroes.data.model.Hero;
import com.mruelas.heroes.data.model.Search;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HeroAPI {
    @GET("{id}")
    Observable<Hero> getHero(@Path("id") String heroId);


    @GET("search/{name}")
    Observable<Search>searchHero(@Path("name") String name);
}
