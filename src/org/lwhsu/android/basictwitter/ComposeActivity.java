package org.lwhsu.android.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComposeActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void onTweet(final View v) {
        final Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }
}
