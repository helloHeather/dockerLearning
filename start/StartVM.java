
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

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
		//http.sendingGetRequest();

		// Sending post request
		http.sendingPostRequest("/vms");
 
	 }
	 
	 // HTTP GET request
	 public void sendingGetRequest(String url) throws Exception {
	  
		URL obj = new URL(prismCentralIP + apiPrefix + url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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
	 
		URL obj = new URL(prismCentralIP + apiPrefix + url);

		System.out.println("sendingPostRequest: " + obj.toString());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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


	public String createJSONObject() {
		String jsonString = 
			"'spec': {'cluster_reference': {'kind': 'cluster', 'name': 'POC074', 'uuid': '00056b91-2968-462b-0000-00000000e606'}, 'description': 'My VM', 'resources': {'vnuma_config': {'num_vnuma_nodes': 0 }, 'nic_list': [], 'num_vcpus_per_socket': 1, 'num_sockets': 1, 'gpu_list': [], 'memory_size_mib': 1024, 'power_state': 'ON', 'hardware_clock_timezone': 'UTC', 'power_state_mechanism': {'mechanism': 'HARD'}, 'vga_console_enabled': true, 'disk_list': [] }, 'name': 'Test VM'}, 'api_version': '3.1', 'metadata': {'last_update_time': '2018-05-08T15:22:14Z', 'kind': 'vm', 'name': 'My VM', 'project_reference': {'kind': 'project', 'name': 'default', 'uuid': '10a25e69-007a-48fd-860b-08fa09a98c41'}, 'creation_time': '2018-05-08T15:15:34Z', 'spec_version': 1, 'owner_reference': {'kind': 'user', 'uuid': '00000000-0000-0000-0000-000000000000', 'name': 'admin'}, 'categories': {}, 'uuid': 'e7432835-9f81-4b02-beed-e99220aa3638'} 'spec': {'cluster_reference': {'kind': 'cluster', 'name': 'POC074', 'uuid': '00056b91-2968-462b-0000-00000000e606'}, 'description': 'test-vm-clone', 'resources': {'vnuma_config': {'num_vnuma_nodes': 0 }, 'nic_list': [{'nic_type': 'NORMAL_NIC', 'subnet_reference': {'kind': 'subnet', 'name': 'Primary', 'uuid': 'db2b8daa-7fd7-462b-b249-904be4240106'} } ], 'num_vcpus_per_socket': 1, 'num_sockets': 2, 'gpu_list': [], 'memory_size_mib': 4096, 'power_state': 'OFF', 'hardware_clock_timezone': 'Asia/Calcutta', 'power_state_mechanism': {'mechanism': 'HARD'}, }, 'name': 'Test VM'}, 'api_version': '3.1', 'metadata': {'kind': 'vm', 'spec_version': 0, 'uuid': 'faabffba-8234-49d5-9759-c811e90dc71b', 'categories': {} }";
	
		return jsonString;

	}

}
