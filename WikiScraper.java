import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * WikiScraper.java
 * Author: Jonathan Khong
 * Description: This class has methods used in WikiRacer such as finding the wiki links, fetching the html,
 * scraping the html and getting the url.
 */


public class WikiScraper {
		private static HashMap<String,Set<String>> map = new HashMap<String,Set<String>>();
	/*
	 * This method finds the wiki links on the page that is 
	 * passed in the parameter by using fetchHTMl and scrapeHTMl.
	 * param: String of link
	 * return set of links
	 */
	public static Set<String> findWikiLinks(String link) {
		// memoization to keep track of links that have been scraped
		if (map.containsKey(link)) {
			return map.get(link);
		} else {
		String html = fetchHTML(link);
		Set<String> links = scrapeHTML(html);
		map.put(link, links);
		return links;
		}


	}
	
	/*
	 * This method grabs the html source code of the link
	 * it does this by opening the link and creating the string of 
	 * html. The int ptr is a count variable to keep track of where 
	 * the source is being read and adding each character to the string buffer.
	 * Param: String of link
	 * return: String
	 */
	
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * This method is a getter for the url.
	 * Params: the link in string form
	 * return: returns the link preceeded by the wikipedia url.
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * This method takes a title of a wiki page and 
	 * gets all the titles of wiki links on that page and
	 * adds them into a set and returns it.
	 * param: String of HTML
	 * return Set of links
	 */

	private static Set<String> scrapeHTML(String html) {
		Set<String> set = new HashSet<>();
		String s = html;
		if (s.isEmpty()) {
			return set;
		} else {
			while (s.indexOf("<a href=\"/wiki/") != -1) {
				s = s.substring(s.indexOf("<a href=\"/wiki/") + 15, s.length());
				String title = s.substring(0, s.indexOf("\"")); 
				s = s.substring(s.indexOf("\""));
				if (!title.contains(":") && !title.contains("#")) {
					set.add(title);
				} 
			} return set;
		}
	}
	}
	

