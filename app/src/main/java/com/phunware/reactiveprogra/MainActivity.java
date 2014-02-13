package com.phunware.reactiveprogra;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import rx.Observable;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

public class MainActivity extends ActionBarActivity {
    TextView theView;
    String[] cities={"Atlanta","Delhi","California"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thelayout);

        Observable.from(cities).mapMany(new Func1<String, Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call(String s) {
                return ApiManager.getWeatherData(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<WeatherData>() {
            @Override
            public void call(WeatherData weatherData) {
                int theindex=Arrays.asList(cities).indexOf(weatherData.name);
                TextView textView=null;
                switch(theindex){
                    case 0:{
                        textView=(TextView)findViewById(R.id.atltextView);
                        break;
                    }
                    case 1:{
                        textView=(TextView)findViewById(R.id.deltextView);
                        break;
                    }
                    case 2:{
                        textView=(TextView)findViewById(R.id.calitextView);
                        break;
                    }
                }
                double temp=weatherData.main.temp;
                textView.setText(temp+"");
                Log.v("weatherapp",weatherData.name+" "+  Arrays.asList(cities).indexOf(weatherData.name));

            }
        });



    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
