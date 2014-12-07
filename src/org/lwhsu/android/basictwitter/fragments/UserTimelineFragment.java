package org.lwhsu.android.basictwitter.fragments;

import org.json.JSONArray;
import org.lwhsu.android.basictwitter.TwitterApplication;
import org.lwhsu.android.basictwitter.models.Tweet;

import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray array) {
                getAdapter().addAll(Tweet.fromJSONArray(array));
            }
        });
    }

}
