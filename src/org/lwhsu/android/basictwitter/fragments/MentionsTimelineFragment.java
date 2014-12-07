package org.lwhsu.android.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.lwhsu.android.basictwitter.TwitterApplication;
import org.lwhsu.android.basictwitter.TwitterClient;
import org.lwhsu.android.basictwitter.models.Tweet;
import org.lwhsu.android.basictwitter.models.User;

import android.os.Bundle;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(Long.valueOf(1), null);
    }

    public void populateTimeline(final Long sinceId, final Long maxId) {
        client.getMentions(sinceId, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray json) {
                final ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);

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

}
