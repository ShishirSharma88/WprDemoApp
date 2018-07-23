package com.wipro.shishir.demoapp.api;

import com.wipro.shishir.demoapp.model.MainData;

import retrofit2.http.GET;
import rx.Observable;

public interface RetroInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<MainData> getTourData();
}
