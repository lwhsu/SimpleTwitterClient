package org.lwhsu.android.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = TwitterConfig.TWITTER_API_KEY;
	public static final String REST_CONSUMER_SECRET = TwitterConfig.TWITTER_API_SECRET;
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets";

	public TwitterClient(final Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(final Long sinceId, final Long maxId, final AsyncHttpResponseHandler handler) {
	    final String apiUrl = getApiUrl("statuses/home_timeline.json");
		final RequestParams params = new RequestParams();
        if (sinceId != null) {
            params.put("since_id", sinceId.toString());
        }
        if (maxId != null) {
            params.put("max_id", maxId.toString());
        }
	    client.get(apiUrl, params, handler);
	}

	public void statusesUpdate(final String status, final AsyncHttpResponseHandler handler) {
	    final String apiUrl = getApiUrl("statuses/update.json");
		final RequestParams params = new RequestParams();
		params.put("status", status);
		client.post(apiUrl, params, handler);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(final AsyncHttpResponseHandler handler) {
		final String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		final RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

    public void getMentions(final Long sinceId, final Long maxId, final JsonHttpResponseHandler handler) {
	    final String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		final RequestParams params = new RequestParams();
        if (sinceId != null) {
            params.put("since_id", sinceId.toString());
        }
        if (maxId != null) {
            params.put("max_id", maxId.toString());
        }
	    client.get(apiUrl, params, handler);
    }

    public void getMyInfo(final JsonHttpResponseHandler handler) {
	    final String apiUrl = getApiUrl("account/verify_credentials.json");
	    client.get(apiUrl, null, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}