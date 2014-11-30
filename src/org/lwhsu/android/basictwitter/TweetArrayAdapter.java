package org.lwhsu.android.basictwitter;

import java.util.List;

import org.lwhsu.android.basictwitter.models.Tweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(final Context context, final List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // Get the data item for position
        final Tweet tweet = getItem(position);
        // Find or inflate the template
        View v;
        if (convertView == null) {
            final LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }
        // Find the views within template
        final ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        final TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        final TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        ivProfileImage.setImageResource(android.R.color.transparent);
        final ImageLoader imageLoader = ImageLoader.getInstance();
        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        return v;
    }

}
