package org.lwhsu.android.basictwitter.fragments;

import java.util.ArrayList;

import org.lwhsu.android.basictwitter.R;
import org.lwhsu.android.basictwitter.TweetArrayAdapter;
import org.lwhsu.android.basictwitter.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        // Inflate the layout
        final View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Assign our view references
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        // Return the layout view
        return v;
    }

    public void addAll(final ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void prependTimeLine(final Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();

        tweet.getUser().save();
        tweet.save();
    }
}
