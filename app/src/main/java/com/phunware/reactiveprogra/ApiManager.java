package com.phunware.reactiveprogra;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by vivek on 2/12/14.
 */
public class ApiManager {
    private static final String ENDPOINT="http://api.openweathermap.org/data/2.5";
    private static final RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
    private static final ApiManagerService apiManagerService=restAdapter.create(ApiManagerService.class);

    public static Observable<WeatherData> getWeatherData(final String city){
        return Observable.create(new Observable.OnSubscribeFunc<WeatherData>() {
            @Override
            public Subscription onSubscribe(Observer<? super WeatherData> observer) {
                try{
                    observer.onNext(apiManagerService.getWeather(city,"metric"));
                    observer.onCompleted();
                }catch(Exception e){
                    observer.onError(e);
                }
                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.io());
    }





    private interface ApiManagerService{
        @GET("/weather")
        WeatherData getWeather(@Query("q") String place,@Query("units")String units);
    }


}
