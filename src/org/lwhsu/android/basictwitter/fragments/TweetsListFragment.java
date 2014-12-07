package org.lwhsu.android.basictwitter.fragments;

import org.lwhsu.android.basictwitter.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TweetsListFragment extends Fragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        // Inflate the layout
        final View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Assign our view references
        // Return the layout view
        return v;
    }

}
