package org.lwhsu.android.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.lwhsu.android.basictwitter.models.Tweet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        populateTimeline();
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
        lvTweets.setAdapter(aTweets);
    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray json) {
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(final Throwable e, final String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }
}
