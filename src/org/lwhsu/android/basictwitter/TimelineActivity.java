package org.lwhsu.android.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lwhsu.android.basictwitter.models.Tweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    private long lastId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        populateTimeline(Long.valueOf(1), null);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(final int page, final int totalItemsCount) {
                populateTimeline(null, Long.valueOf(lastId) - 1);
            }

        });
    }

    public void populateTimeline(final Long sinceId, final Long maxId) {
        client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray json) {
                final ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                aTweets.addAll(tweets);
                lastId = tweets.get(tweets.size() - 1).getUid();
            }

            @Override
            public void onFailure(final Throwable e, final String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    public void prependTimeline(final Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    private final int REQUEST_CODE_COMPOSE = 20;
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_compose) {
            final Intent i = new Intent(this, ComposeActivity.class);
            startActivityForResult(i, REQUEST_CODE_COMPOSE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
            final String status = data.getStringExtra("status");
            client.statusesUpdate(status, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(final JSONObject json) {
                    final Tweet tweet = Tweet.fromJSON(json);
                    prependTimeline(tweet);
                }

                @Override
                public void onFailure(final Throwable e, final String s) {
                    Log.d("debug", e.toString());
                    Log.d("debug", s.toString());
                }
            });
        }
    }
}
