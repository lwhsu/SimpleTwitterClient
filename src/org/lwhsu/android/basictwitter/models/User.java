package org.lwhsu.android.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {
    @Column(name = "uid")
    private long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "following_count")
    private int followingCount;

    public User() {
        super();
    }

    public User(final long uid, final String name, final String screenName,
            final String profileImageUrl, final String description,
            final int followersCount, final int followingCount) {
        super();
        this.uid = uid;
        this.name = name;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public static User fromJSON(final JSONObject json) {
        final User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.description = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.followingCount = json.getInt("friends_count");
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }
}
