package org.lwhsu.android.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

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
            tweet.createdAt = json.getString("created_at;");
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        } catch (final JSONException e) {
            e.printStackTrace();
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

}
