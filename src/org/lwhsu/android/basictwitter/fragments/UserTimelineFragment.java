package org.lwhsu.android.basictwitter.fragments;

import org.json.JSONArray;
import org.lwhsu.android.basictwitter.TwitterApplication;
import org.lwhsu.android.basictwitter.models.Tweet;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

    public static UserTimelineFragment newInstance(final String screenName) {
        final UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        final Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String screenName = getArguments().getString("screen_name");
        TwitterApplication.getRestClient().getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONArray array) {
                Log.d("debug", array.toString());
                getAdapter().addAll(Tweet.fromJSONArray(array));
            }
        });
    }

}
