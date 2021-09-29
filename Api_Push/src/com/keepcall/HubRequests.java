package com.keepcall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;

public class HubRequests {
	
	
	/**
	 * create a Contact List in hubspot from file name.
	 * @param name
	 * @return 
	 */
	protected static String postRequest(String request, String body) {
		// POST
//		System.out.println("request : "+request);
//		System.out.println("input : "+body);
		String output = "";
		try {
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			output = reader.readLine();
//			System.out.println(output);
			String tmp= "";
			while ((tmp = reader.readLine()) != null) {	
				output=output+tmp;
//				System.out.println(output);
			}

			conn.disconnect();
			os.close();
			reader.close();
			//return output;
			System.out.println("..................SUCCESS\n" );	

		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
		
	}
	
	
	
	public static String CreateListe(String nameFile, String hapikey) {
		
		String nameFileWithoutExtension = FilenameUtils.removeExtension(nameFile);
		
		String input =  "{\r\n" + 
				"	\"name\": \"" + nameFileWithoutExtension +"\"\r\n" +
				"}";
		String request = "https://api.hubapi.com/contacts/v1/lists?hapikey="+ hapikey;

		//System.out.println("generate request success");
		
		
		String output = postRequest(request,input);

		return output;
	}
	
	
	public static String Push_ContactInHubspot(String ref,String owner,String strhapikey) {

		String body = "{\r\n" + 
				"  \"properties\": [\r\n" + 
				"    {\r\n" + 
				"      \"property\": \"lastname\",\r\n" + 
				"      \"value\": \""+ ref + "\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"property\": \"hubspot_owner_id\",\r\n" + 
				"      \"value\": \""+  owner + "\"\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"  ]\r\n" + 
				"}";
		String request = "https://api.hubapi.com/contacts/v1/contact/?hapikey="+strhapikey;
		
		
		String output = postRequest(request,body);
		
		return output;
		
	}
	
	
	
	public static String Post_Task(String owner,
			Long echanceDate, String type, String contactId, 
			String body, String titre, String status,String strhapikey) {
		// POST
		
		String request = "https://api.hubapi.com/engagements/v1/engagements?hapikey="+strhapikey;

		
		String input = "{\r\n" + 
				"	\"engagement\": {\r\n" + 
				"		\"active\": true,\r\n" + 
				"		\"ownerId\": "+ owner +",\r\n" + 
				"		\"timestamp\": "+  echanceDate  +",\r\n" + 
				"		\"type\": \""+  type   +"\"\r\n" + 
				"	},\r\n" + 
				"	\"associations\": {\r\n" + 
				"		\"contactIds\": [\r\n" + 
				"			"+ contactId +"\r\n" + 
				"		],\r\n" + 
				"		\"dealIds\": [],\r\n" + 
				"		\"ownerIds\": [],\r\n" + 
				"		\"workflowIds\": []\r\n" + 
				"	},\r\n" + 
				"	\"attachments\": [],\r\n" + 
				"\r\n" + 
				"	\"metadata\": {\r\n" + 
				"		\"body\": \""+ body+"\",\r\n" + 
				"		\"subject\": \"" + titre + "\",\r\n" + 
				"		\"forObjectType\": \"CONTACT\",\r\n" + 
				"		\"taskType\":\"CALL\",\r\n" + 
				"		\"status\": \""+ status +"\",\r\n" + 
				"		\"reminders\":[ \r\n" + 
				"		   "+   echanceDate  + "\r\n" + 
				"		],\r\n" + 
				"		\"sendDefaultReminder\":true\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"}";
						
		String output = postRequest(request,input);

		return output;
	}
	
	
	
	
	
	
}
