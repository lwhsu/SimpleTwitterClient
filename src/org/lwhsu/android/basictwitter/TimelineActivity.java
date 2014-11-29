package org.lwhsu.android.basictwitter;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

    private TwitterClient client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray json) {
                Log.d("debug", json.toString());
            }

            @Override
            public void onFailure(final Throwable e, final String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }
}
