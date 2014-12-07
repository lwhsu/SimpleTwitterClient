package org.lwhsu.android.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.lwhsu.android.basictwitter.EndlessScrollListener;
import org.lwhsu.android.basictwitter.TwitterApplication;
import org.lwhsu.android.basictwitter.TwitterClient;
import org.lwhsu.android.basictwitter.models.Tweet;
import org.lwhsu.android.basictwitter.models.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Delete;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private long lastId;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();

        //if (isConnected()) {
            populateTimeline(Long.valueOf(1), null);
        /*
        } else {
            Toast.makeText(this, "No Network Connection!", Toast.LENGTH_LONG).show();
            populateTimelineOffline();
        }
        */
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = super.onCreateView(inflater, container, savedInstanceState);
        getListViewTweets().setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(final int page, final int totalItemsCount) {
                populateTimeline(null, Long.valueOf(lastId) - 1);
            }

        });
        return v;
    }

    public void populateTimeline(final Long sinceId, final Long maxId) {
        client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray json) {
                final ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);
                lastId = tweets.get(tweets.size() - 1).getUid();

                // update local db
                new Delete().from(Tweet.class).execute();
                new Delete().from(User.class).execute();
                for (final Tweet tweet : tweets) {
                    tweet.getUser().save();
                    tweet.save();
                }
            }

            @Override
            public void onFailure(final Throwable e, final String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    private void populateTimelineOffline() {
        addAll((ArrayList<Tweet>) Tweet.getAll());
    }

    public void prependTimeline(final Tweet tweet) {
        prependTimeLine(tweet);
    }

}
