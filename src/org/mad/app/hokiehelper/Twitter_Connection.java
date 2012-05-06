package org.mad.app.hokiehelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class is used to connect to twitter's api. The commented out stuff are my attempts
 * at authentication.
 * @author karthik
 *
 */
public class Twitter_Connection {
	@SuppressWarnings("unused")
	private String url;
	private int status;
	private String result;
	private static byte[] sBuffer = new byte[512];

//	private String CALLBACKURL = "app://twitter";
//	private String consumerKey = "HNLPTt93WzEIBWnYccIdSw";
//	private String consumerSecret = "vtYhyRIvHYSpe8b3H514MK8wszsJDdbI1WFtq1cPOmw";
//
//	private OAuthProvider httpOauthprovider = new DefaultOAuthProvider("https://api.twitter.com/oauth/request_token", "https://api.twitter.com/oauth/access_token", "https://api.twitter.com/oauth/authorize");
//	private CommonsHttpOAuthConsumer httpOauthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

	/**
	 * Constructor. Sets up the URL to communicate with Twitter. Attempts to
	 * parse the URL, throws exception if unsuccessful.
	 * 
	 * @param orig
	 *            the starting point of the trip.
	 * @param dest
	 *            the ending point of the trip.
	 */
	public Twitter_Connection(String url) {
		this.url = url;

		try {
			parse(url);
		} catch (Exception e) {
			status = 400;
		}
	}

	/**
	 * Parses a url pointing to a Google JSON object to a Route object.
	 * 
	 * @throws Exception
	 */
	private void parse(String givenUrl) throws Exception {
		result = getUrlContent(givenUrl);

	}

	/**
	 * Uses a HTTP client to get the content from Google Maps
	 * 
	 * @param url
	 *            the link to get directions.
	 * @return String the route for this trip
	 * @throws ApiException
	 *             the exception thrown if any problem occurs.
	 */
	private String getUrlContent(String url) throws ApiException {

		// Create client and set our specific user-agent string
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		// request.setHeader("User-Agent", sUserAgent);

		try {
			HttpResponse response = client.execute(request);
			// Pull content stream from response

			HttpEntity entity = response.getEntity();
			status = response.getStatusLine().getStatusCode();
			InputStream inputStream = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
//			newConnection();

			// Return result from buffered stream
			return new String(content.toByteArray());
		} catch (IOException e) {
			throw new ApiException("Problem communicating with API", e);
		}
	}

	@SuppressWarnings("serial")
	public static class ApiException extends Exception {
		public ApiException(String detailMessage, Throwable throwable) {
			super(detailMessage, throwable);
		}

		public ApiException(String detailMessage) {
			super(detailMessage);
		}
	}

	/**
	 * Returns the status of the directions request.
	 * 
	 * @return String the status message.
	 */
	public int getStatus() {
		return status;
	}

	public String getResult() {
		return result;
	}

	//	private void authenticateUser() {
	//		try {
	//			// prepare a connection for the twitter API update method
	//			URL url = new URL(
	//					"https://api.twitter.com/1/statuses/home_timeline.json?include_entities=true");
	//			URLConnection conn = url.openConnection();
	//			conn.setAllowUserInteraction(false);
	//			conn.setDoOutput(true);
	//
	//			// encode users credentials
	//			byte[] credentialsBytes = ("kkumar9844" + ":" + "fallschurch")
	//					.getBytes();
	//			byte[] encodedBytes = Base64.encode(credentialsBytes,
	//					Base64.DEFAULT);
	//			String credentialString = new String(encodedBytes);
	//			conn.setRequestProperty("Authorization", "Basic "
	//					+ credentialString);
	//
	//			// set the request to POST and send
	//			conn.setRequestProperty("Content-Type",
	//					"application/x-www-form-urlencoded");
	//			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	//			// out.writeBytes("status=" + URLEncoder.encode(message, "UTF-8"));
	//			out.flush();
	//			out.close();
	//
	//			// get the response
	//			BufferedReader input = new BufferedReader(new InputStreamReader(
	//					conn.getInputStream()));
	//			StringBuilder builder = new StringBuilder();
	//			String s = null;
	//			while ((s = input.readLine()) != null) {
	//				builder.append(s);
	//			}
	//			input.close();
	//			String response = builder.toString();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	private void newConnection() {
	//
	//		try {
	//			
	//			URL url = new URL("https://api.twitter.com/1/statuses/home_timeline.json?include_entities=true");
	//			String encoding = Base64.encode("kkumar9844:fallschurch".getBytes(), Base64.URL_SAFE).toString();
	//
	//			HttpURLConnection connection = (HttpURLConnection) url
	//					.openConnection();
	//			connection.setRequestMethod("POST");
	//			connection.setDoOutput(true);
	//			connection.setRequestProperty("Authorization", "Basic "
	//					+ encoding);
	//			int sta = connection.getResponseCode();
	//				
	//			InputStream content = (InputStream) connection
	//					.getInputStream();
	//			BufferedReader in = new BufferedReader(
	//					new InputStreamReader(content));
	//			String line;
	//			while ((line = in.readLine()) != null) {
	//				System.out.println(line);
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//
	//	}
	
//	public void newConnection() {
//		try {
//			String authUrl = httpOauthprovider.retrieveRequestToken(httpOauthConsumer, CALLBACKURL);
//			Uri uri = Uri.parse(authUrl);
//			
//			if (uri != null && uri.toString().startsWith(CALLBACKURL)) {
//				 
//		        String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
//		 
//		        try {
//		            // this will populate token and token_secret in consumer
//		 
//		            httpOauthprovider.retrieveAccessToken(httpOauthConsumer, verifier);
//		            String userKey = httpOauthConsumer.getToken();
//		            String userSecret = httpOauthConsumer.getTokenSecret();
//		 
//		            // Save user_key and user_secret in user preferences and return
//		            SharedPreferences settings = getBaseContext().getSharedPreferences("your_app_prefs", 0);
//		            SharedPreferences.Editor editor = settings.edit();
//		            editor.putString("user_key", userKey);
//		            editor.putString("user_secret", userSecret);
//		            editor.commit();
//		            
//		            HttpGet get = new HttpGet("http://api.twitter.com/1/statuses/home_timeline.json");
//		            HttpParams params = new BasicHttpParams();
//		            HttpProtocolParams.setUseExpectContinue(params, false);
//		            get.setParams(params);
//		             
//		            try {
////		              SharedPreferences settings = getContext().getSharedPreferences("your_app_prefs", 0);
////		              String userKey = settings.getString("user_key", "");
////		              String userSecret = settings.getString("user_secret", "");
//		             
//		              httpOauthConsumer.setTokenWithSecret(userKey, userSecret);
//		              httpOauthConsumer.sign(get);
//		             
//		              DefaultHttpClient client = new DefaultHttpClient();
//		              String response = client.execute(get, new BasicResponseHandler());
//		              JSONArray array = new JSONArray(response);
//		            } catch (Exception e) { 
//		              // handle this somehow
//		            }
//		        } catch (Exception e) {
//		 
//		        }
//		    } else {
//		        // Do something if the callback comes from elsewhere
//		    }
//	
//		} catch (OAuthMessageSignerException e) {
//			e.printStackTrace();
//		} catch (OAuthNotAuthorizedException e) {
//			e.printStackTrace();
//		} catch (OAuthExpectationFailedException e) {
//			e.printStackTrace();
//		} catch (OAuthCommunicationException e) {
//			e.printStackTrace();
//		}
//	}


}
