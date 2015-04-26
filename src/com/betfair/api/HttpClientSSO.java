package com.betfair.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.betfair.jsonrpc.JsonConverter;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class HttpClientSSO {

	private static int port = 443;

	public static String obtainSessionKey() throws Exception {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		LoginCredentials loginC = null;
		SSLContext ctx = SSLContext.getInstance("TLS");
		KeyManager[] keyManagers = getKeyManagers("pkcs12", new FileInputStream(new File("C:\\openssl\\bin\\client-2048.p12")), "password");
		ctx.init(keyManagers, null, new SecureRandom());
		SSLSocketFactory factory = new SSLSocketFactory(ctx);

		ClientConnectionManager manager = httpClient.getConnectionManager();
		manager.getSchemeRegistry().register(new Scheme("https", factory, port));
		HttpPost httpPost = new HttpPost("https://identitysso.betfair.com/api/certlogin");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "pjbenson"));
		nvps.add(new BasicNameValuePair("password", "iceplanet87"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		httpPost.setHeader("X-Application","XaBM5CwzIU1oLrgw");

//		System.out.println("executing request" + httpPost.getRequestLine());

		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String sessionKey = null;

//		System.out.println("----------------------------------------");
//		System.out.println(response.getStatusLine());
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			loginC = JsonConverter.convertFromJson(responseString, LoginCredentials.class);
			sessionKey = loginC.getSessionToken();
//			System.out.println("responseString" + responseString);
		}


		return sessionKey;
	}



	private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(keyStore, keyStorePassword.toCharArray());
		return kmf.getKeyManagers();
	}

	class LoginCredentials{
		private String sessionToken;
		private String loginStatus;
		public String getSessionToken() {
			return sessionToken;
		}
		public void setSessionToken(String sessionToken) {
			this.sessionToken = sessionToken;
		}
		public String getLoginStatus() {
			return loginStatus;
		}
		public void setLoginStatus(String loginStatus) {
			this.loginStatus = loginStatus;
		}
	}
}
