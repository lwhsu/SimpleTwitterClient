package org.lwhsu.android.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

    private TwitterClient client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
    }

    public void onTweet(final View v) {
        final EditText etTweet = (EditText) findViewById(R.id.etTweet);
        final String status = etTweet.getText().toString();
        final Intent i = new Intent();

        client.statusesUpdate(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final JSONObject json) {
                setResult(RESULT_OK, i);
            }

            @Override
            public void onFailure(final Throwable e, final String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });

        this.finish();
    }
}
