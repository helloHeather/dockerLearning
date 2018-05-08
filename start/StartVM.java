
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

//import org.json.simple.JSONObject; //json-simple-1.1.1.jar

/*
* Simple REST connectivity class
*/


public class StartVM {
 
	private final String USER_AGENT = "NotThatSpecial";
	private String encoding = Base64.getEncoder().encodeToString(("admin:techX2018!").getBytes()); 
	private String prismCentralIP = "https://10.21.74.39:9440";
	private String apiPrefix = "/api/nutanix/v3";

 
	public static void main(String[] args) throws Exception {
		System.out.println("welcome to StartVM");

		StartVM http = new StartVM();

		 // Sending get request
		http.sendingPostListRequest("/vms");

		// Sending post request
		//http.sendingPostRequest("/vms");
 
	 }
	 
	 // HTTP GET request
	 public void sendingGetRequest(String url) throws Exception{
	  


		URL obj = new URL(prismCentralIP + apiPrefix + url);

		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

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
	 public void sendingPostRequest(String url) throws Exception{
	 
		URL obj = new URL(prismCentralIP + apiPrefix + url);

		setCert();

		System.out.println("sendingPostRequest: " + obj.toString());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		    // Setting basic post request
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type","application/json");
		con.setRequestProperty  ("Authorization", "Basic " + encoding);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(createJSONObject());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
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

	// HTTP Post request
	 public void sendingPostListRequest(String url) throws Exception{

	 	String urlString = prismCentralIP + apiPrefix + url;
	 	System.out.println("urlString = " + urlString);
	 
		URL obj = new URL(urlString);

		setCert();

		//String payload = "{\"kind\": \"vm\", \"sort_order\": \"ASCENDING\", \"offset\": 0, \"length\": 10, \"sort_attribute\": \"name\"}";

		String payload = createJSONObject();

		System.out.println("sendingPostRequest: " + obj.toString());
		HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
		connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer jsonString = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
                jsonString.append(line);
        }
        br.close();
        connection.disconnect();
		//printing result from response
		System.out.println(jsonString);
	}



	private String createJSONObject() {
		String jsonString = 
			  "{\"spec\": {\"cluster_reference\": {\"kind\": \"cluster\", \"name\": \"POC074\", \"uuid\": \"00056b91-2968-462b-0000-00000000e606\"}, \"description\": \"test-vm-nic\", \"resources\": {\"power_state\": \"ON\", \"vnuma_config\": {\"num_vnuma_nodes\": 0 }, \"nic_list\": [{\"nic_type\": \"NORMAL_NIC\", \"subnet_reference\": {\"kind\": \"subnet\", \"name\": \"Primary\", \"uuid\": \"db2b8daa-7fd7-462b-b249-904be4240106\"} }], \"num_vcpus_per_socket\": 1, \"num_sockets\": 2, \"gpu_list\": [], \"memory_size_mib\": 4096, \"hardware_clock_timezone\": \"Asia/Calcutta\", \"power_state_mechanism\": {\"mechanism\": \"HARD\"}, \"vga_console_enabled\": true }, \"name\": \"TF2 VM\"}, \"api_version\": \"3.1\", \"metadata\": {\"kind\": \"vm\", \"last_update_time\": \"2018-05-08T15:22:14Z\", \"spec_version\": 0, \"creation_time\": \"2018-05-08T15:15:34Z\", \"owner_reference\": {\"kind\": \"user\", \"uuid\": \"00000000-0000-0000-0000-000000000000\", \"name\": \"admin\"} } }";
		return jsonString;

	}

	private void setCert() throws NoSuchAlgorithmException, KeyManagementException {
		// Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
 
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	}

}
