package com.noththatspecial.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.simple.JSONObject; //json-simple-1.1.1.jar

/*
* Simple REST connectivity class
*/


public class HttpClientServiceUtil {
 
	private final String USER_AGENT = "NotThatSpecial";
	private String encoding = Base64.getEncoder().encodeToString(("admin:techX2018!").getBytes()); 
	private String prismCentralIP = "10.21.74.39:9440";
	private String apiPrefix = "/api/nutanix/v3/clusters/list";

 
	public static void main(String[] args) throws Exception {

		HttpURLConnectionExample http = new HttpURLConnectionExample();

		 // Sending get request
		http.sendingGetRequest();

		// Sending post request
		http.sendingPostRequest();

	 }
	 
	 // HTTP GET request
	 public void sendingGetRequest(String urlString) throws Exception {
	  
		URL url = new URL(urlString);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// By default it is GET request
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty  ("Authorization", "Basic " + encoding);

		int responseCode = con.getResponseCode();
		System.out.println("Sending get request : "+ url);
		System.out.println("Response code : "+ responseCode);

		// Reading response from input Stream
		BufferedReader in = new BufferedReader(
		      new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = in.readLine()) != null) {
		response.append(output);
		}
		in.close();

		//printing result from response
		System.out.println(response.toString());
	 
	 }
	 
	 // HTTP Post request
	 public void sendingPostRequest(String url) throws Exception {
	 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		    // Setting basic post request
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type","application/json");
		con.setRequestProperty  ("Authorization", "Basic " + encoding);

		String postJsonData;
		/*String representation of JSON data ex:
		postJsonData = "{"id":5,"countryName":"USA","population":8000}";
		*/
		
		postJsonData = "{'kind':'cluster', 'sort_attribute':'ASCENDING'}"

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Post Data : " + postJsonData);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		      new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();

		//printing result from response
		System.out.println(response.toString());
	}

	/* For reference
	public createJSONObject() {
		JSONObject obj = new JSONObject();

		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));

		StringWriter out = new StringWriter();
		obj.writeJSONString(out);

		String jsonText = out.toString();
		System.out.print(jsonText);
	}
	*/
		

}
