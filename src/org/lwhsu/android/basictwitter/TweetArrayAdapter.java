package org.lwhsu.android.basictwitter;

import java.util.Date;
import java.util.List;

import org.lwhsu.android.basictwitter.models.Tweet;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
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
        final TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
        ivProfileImage.setImageResource(android.R.color.transparent);
        final ImageLoader imageLoader = ImageLoader.getInstance();
        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(getRelativeCreatedAt(Tweet.getTwitterDate(tweet.getCreatedAt())));
        return v;
    }

    private CharSequence getRelativeCreatedAt(final Date createAtDate) {
        final String s = (String) DateUtils.getRelativeDateTimeString(getContext(), createAtDate.getTime(),
                DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
        Log.d("debug", s);
        return s;
    }

}
