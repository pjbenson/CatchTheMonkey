package com.spring.json;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReadFromFile {
	
	private static final String filePath = " C:\\Users\\paulb\\Documents\\College\\CatchTheMonkey\\response.json";
	
	public static void readFile(String file) throws IOException, ParseException{
		FileReader reader = new FileReader(filePath);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		
		JSONArray lang= (JSONArray) jsonObject.get("result");
		
		for(int i=0; i<lang.size(); i++){
			System.out.println("The " + i + " element of the array: "+lang.get(i));
		}

		
//		JSONParser parser = new JSONParser();
//		
//		Object obj = null;
//		try {
//			obj = parser.parse(new FileReader(file));
//			JSONObject jsonObject = (JSONObject) obj;
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
