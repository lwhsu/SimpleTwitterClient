package org.lwhsu.android.basictwitter;

import org.json.JSONObject;
import org.lwhsu.android.basictwitter.fragments.UserTimelineFragment;
import org.lwhsu.android.basictwitter.models.User;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

    private String screenName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        screenName = getIntent().getStringExtra("screen_name");
        loadProfileInfo();
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentUserTimeline, UserTimelineFragment.newInstance(screenName));
        ft.commit();

    }

    private void loadProfileInfo() {
        if (screenName == null) {
            TwitterApplication.getRestClient().getMyInfo(
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(final JSONObject json) {
                            final User u = User.fromJSON(json);
                            getActionBar().setTitle("@" + u.getScreenName());
                            populateProfileHeader(u);
                        }

                    });
        } else {
            TwitterApplication.getRestClient().getUsersShow(screenName,
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(final JSONObject json) {
                            final User u = User.fromJSON(json);
                            getActionBar().setTitle("@" + u.getScreenName());
                            populateProfileHeader(u);
                        }

                    });
        }
    }

    private void populateProfileHeader(final User u) {
        final TextView tvName = (TextView) findViewById(R.id.tvName);
        final TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        final TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        final TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        final ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(u.getName());
        tvTagline.setText(u.getDescription());
        tvFollowers.setText(u.getFollowersCount() + " Followers");
        tvFollowing.setText(u.getFollowingCount() + " Following");
        ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
