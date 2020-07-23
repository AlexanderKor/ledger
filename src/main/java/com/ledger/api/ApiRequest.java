package com.ledger.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiRequest {

	private static ThreadLocal<Response> response = new ThreadLocal<>();
	private static Cookies cookies = new Cookies();
	private static String USER_AGENT = "Mozilla/5.0 Chrome/80.0";

	public static void doGetAndSetResponse(String url) {
		url = url.contains("https://") ? url : "https://" + url;
		setResponse(given().header("User-Agent", USER_AGENT).cookies(cookies).when().get(url));
	}

	public static void doGetAndSetResponse(String username, String password, String url) {
		setResponse(given().auth().basic(username, password).header("User-Agent", USER_AGENT).when().get(url));
	}

	public static Response getApiResponse() {
		return response.get();
	}

	public static void setResponse(Response response) {
		ApiRequest.response.set(response);
	}
	public static void setUserAgent (String value) {
		USER_AGENT = value;
	}
}