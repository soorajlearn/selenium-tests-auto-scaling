package selenium_tests_auto_scaling;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetQueueSize {
	
/* To send and receive the requests - using OK Http Client */
	
	
	/* Method to retrieve from the Selenium Grid using GraphQL */
	@Test
	public void getQueueSize() throws IOException, JSchException {
		
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		
		// Construct the GraphQL Body
		RequestBody requestBody = RequestBody.create(mediaType, "{\"query\": \" query Summary { grid { sessionQueueSize } } \"}");
		
		// Build the request to the Selenium Grid
		Builder builder = new Request.Builder();
		Request request = builder.url("http://localhost:4444/graphql").post(requestBody).build();
		
		// Execute the request
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		
		// Convert the String to JSON
		JSONObject json = new JSONObject(responseBody);
		int queueSize = json
				.getJSONObject("data")
				.getJSONObject("grid")
				.getInt("sessionQueueSize");
		System.out.println(queueSize);
		
		if(queueSize > 0) {
			// write an algorithm to scale up the pods
			System.out.println("Call Kubectl command through SSH");
			ScalePodsUsingSSH.scaleUp(queueSize);
		} else {
			System.out.println("There is no need to scale up as there is nothing waiting for you");
		}
		
		
		
		//return 0;
	}

}
