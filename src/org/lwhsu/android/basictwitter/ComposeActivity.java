package org.lwhsu.android.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void onTweet(final View v) {
        final EditText etTweet = (EditText) findViewById(R.id.etTweet);
        final String status = etTweet.getText().toString();
        final Intent i = new Intent();
        i.putExtra("status", status);
        setResult(RESULT_OK, i);
        this.finish();
    }
}
