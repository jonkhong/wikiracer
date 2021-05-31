import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * WikiRacer.java
 * Author: Jonathan Khong
 * Description: This class is to solve the Wiki Racer game. It returns all the titles of sites in order
 * from the start page to the end page. It requires 2 arguments being a start page and an end page. For example,
 * Fruit
 * Strawberry
 * 
 */
public class WikiRacer {

	
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
		
	}

	/*
	 * This method finds the wiki ladder result as a list and returns it.
	 * It does this by using the max priority queue class and dequeing one ladder 
	 * at a time checking the links on its page.
	 */
	private static List<String> findWikiLadder(String start, String end) {
		Set<String> visited = new HashSet<String>(); // set to keep track of sites visited
		MaxPQ queue = new MaxPQ(end); // create priority queue
		List<String> ladder = new ArrayList<String>();
		ladder.add(start);
		queue.enqueue(ladder);
		
		while (queue.isEmpty() == false) {
				List<String> lst = queue.dequeue();
				Set<String> startLinks = WikiScraper.findWikiLinks(lst.get(lst.size() - 1));
				
				if (found(lst, end) == true) {
					//lst.add(end);
					return lst;
				} else {
					startLinks.parallelStream().forEach(link -> {
						WikiScraper.findWikiLinks(link);
						});
					for (String link : startLinks) {
						if (!visited.contains(link)) {
						List<String> cur = new ArrayList<String>(lst);
						cur.add(link);
						queue.enqueue(cur);
						visited.add(link);
						}
					}
		}
		
				
	}
		List<String> empty = new ArrayList<String>();
		return empty;
	}
	
	/*
	 * This is a helper method to check if the end page is found on the 
	 * current page.
	 * param: list of current page's links and String of the end page
	 * return: boolean
	 */
	private static boolean found(List<String> cur, String end) {
		return cur.contains(end);
	}


}

