package org.lwhsu.android.basictwitter;

import org.lwhsu.android.basictwitter.fragments.HomeTimelineFragment;
import org.lwhsu.android.basictwitter.fragments.MentionsTimelineFragment;
import org.lwhsu.android.basictwitter.listeners.FragmentTabListener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TimelineActivity extends FragmentActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupTabs();
    }

    private void setupTabs() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        final Tab tab1 = actionBar
            .newTab()
            .setText("Home")
            .setIcon(R.drawable.ic_home)
            .setTag("HomeTimelineFragment")
            .setTabListener(
                new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        final Tab tab2 = actionBar
            .newTab()
            .setText("Mentions")
            .setTag("MentionsTimelineFragment")
            .setIcon(R.drawable.ic_mentions)
            .setTabListener(
                new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    private final int REQUEST_CODE_COMPOSE = 20;
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_compose) {
            final Intent i = new Intent(this, ComposeActivity.class);
            startActivityForResult(i, REQUEST_CODE_COMPOSE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
            final String status = data.getStringExtra("status");
            client.statusesUpdate(status, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(final JSONObject json) {
                    final Tweet tweet = Tweet.fromJSON(json);
                    prependTimeline(tweet);
                }

                @Override
                public void onFailure(final Throwable e, final String s) {
                    Log.d("debug", e.toString());
                    Log.d("debug", s.toString());
                }
            });
        }
    }
    */

    private boolean isConnected() {
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
