package edu.cscc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.*;

/**
 * Find out who's in space JSON library from:
 * https://github.com/stleary/JSON-java
 * 
 * @author peter_hille, CSCI2469 Lab7, 3/4/23
 *
 */
public class Main {

	public static void main(String[] args) {
		
		final String theURLString = "http://api.open-notify.org/astros.json";
		
		try {
			URL url = new URL(theURLString);
			URLConnection connection = url.openConnection();
			try (BufferedReader brdr = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				//s stores current; json holds concatenation
				String s, json = "";
				//read input
				while ((s = brdr.readLine()) != null) {
					json = json + s + "\n";
				}
				System.out.println(json + "\n\n");

				JSONObject jo = new JSONObject(json);
				//invoke parseJSON to retrieve the names of the people in space and the vessel they are on
				String[] peopleInSpace = parseNames(jo);
				//invoke parseTotal and store int to find the total number of people in space
				Integer totalPeople = parseTotal(jo);
				System.out.println("There are currently " + totalPeople + " people in space.");
				if (peopleInSpace.length > 0) {
					for (String person : peopleInSpace) {
						System.out.println(person);
					}
				} else {
					System.out.println("No Astronauts in Space");
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		//parse JSON object to get the number of people in space
		public static Integer parseTotal(JSONObject jo) {
			Integer number = null;
			if (jo != null) {
				Integer parseNumber = jo.getInt("number");
				number = parseNumber;
			}
			return number;
		} 
		//parse JSON object and return the names of the people in space and the vessel they are on
		public static String[] parseNames(JSONObject jo) {
			ArrayList<String> peopleInSpace = new ArrayList<>();
			if (jo != null) {
				JSONArray people = jo.getJSONArray("people");
				if (people.length() > 0) {
					for (int i = 0; i < people.length(); ++i) {
						JSONObject vessel = people.getJSONObject(i);
						String craft = vessel.getString("craft");
						String name = vessel.getString("name");
						peopleInSpace.add(name + " is currently onboard: " + craft);
					}
				}
			}
			return peopleInSpace.toArray(new String[1]);
		}
	}