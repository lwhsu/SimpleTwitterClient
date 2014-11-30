package org.lwhsu.android.basictwitter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tweet {
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public static Tweet fromJSON(final JSONObject json) {
        final Tweet tweet = new Tweet();
        try {
            tweet.body = json.getString("text");
            tweet.uid = json.getLong("id");
            tweet.createdAt = json.getString("created_at");
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        } catch (final JSONException e) {
            e.printStackTrace();
            Log.d("error", "error1");
            return null;
        }
        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public static ArrayList<Tweet> fromJSONArray(final JSONArray json) {
        final ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());

        for (int i = 0; i < json.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = json.getJSONObject(i);
            } catch (final Exception e) {
                e.printStackTrace();
                continue;
            }

            final Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

    public static Date getTwitterDate(final String date) {
        final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        final SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.US);
        sf.setLenient(true);
        try {
            return sf.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }

}
