import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Rest_api {
	
	public Rest_api(String filename) throws IOException, ParseException{
		
		try {
			Scanner scan = new Scanner(new File(filename));
			
			while (scan.hasNextLine()){
				
				get_url_info(scan.nextLine());
	
				
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
			
		}
		
	}
	
	public String get_url_info(String url) throws ParseException{
		
		//String url1 = "http://www.google.com/search?q=httpClient";

		//HttpClient client = HttpClientBuilder.create().build();
		//HttpGet request = new HttpGet(url1);
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		String result = null;
		try {
		        response = client.execute(request);         
		        HttpEntity entity = response.getEntity();

		        if (entity != null) {

		            // A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            result = convertStreamToString(instream);
		            // now you have the string representation of the HTML request
		            
		            JSONParser parser = new JSONParser();
		            JSONObject json = (JSONObject) parser.parse(result);
		            
		            System.out.println("RESPONSE: " + result);
		            System.out.println("RESPONSE: " + json);
		            
		            
		            instream.close();
		            
		          
		            

		        }
		        // Headers
		        org.apache.http.Header[] headers = response.getAllHeaders();
		        for (int i = 0; i < headers.length; i++) {
		            System.out.println(headers[i]);
		        }
		    } catch (ClientProtocolException e1) {
		       
		        e1.printStackTrace();
		    } catch (IOException e1) {
		       
		        e1.printStackTrace();
		    }
		    return result;

		  
		
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	

}
