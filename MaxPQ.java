import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * MaxPQ.java
 * Author: Jonathan Khong
 * Description: This class is a priority queue implemented by an array backed by a binary max-heap. It uses an array
 * of Ladders which is a private class within this class. This priority queue is used in WikiRacer.java.
 */

/*
 * TODO: This file should contain your priority queue backed by a binary
 * max-heap.
 */
public class MaxPQ {
	
	// Ladder class
	private class Ladder {
		public List<String> lst;
		
		Set<String> endLinks = WikiScraper.findWikiLinks(end);
		Set<String> next;
		public int priority;
	
		// constructor
		public Ladder(List<String> lst) {
			
			this.lst = lst;
			next = WikiScraper.findWikiLinks(lst.get(lst.size() - 1));
			this.priority = findPriority();
			
		}
		public String toString() {
			return lst.toString();
		}
		// find priority using retain all and a deep copy of the set.
		public int findPriority() {
			Set<String> cur = new HashSet<String>(next);
			next.retainAll(endLinks);
			return next.size();
		}
	}
	
	//**************
	private Ladder[] arr; // array of Ladders
    private int size;
    private int cap = 8;
    private String end;
 // constructor
    public MaxPQ(String end) {
    	this.end = end;
        this.size = 0;
        this.arr = new Ladder[cap];
        this.arr[0] = null;

    }

    /*
     * This method adds a Ladder to the back of the queue and
     * reorganizes them so that the higher priority moves to the front.
     * It uses the name and priority from the parameter to create the Ladder.
     * params: string name and int priority.
     */
    public void enqueue(List<String> lst) {
        Ladder Ladder = new Ladder(lst);
        //
        if (this.size + 1 >= arr.length) {
            Ladder[] newArray = new Ladder[arr.length * 2];
            for (int i = 1; i < this.size; i++) {
                newArray[i] = arr[i];
            }
            this.arr = newArray;

        }
        // if the queue is already empty
        if (size == 0) {
            size++;
            arr[size] = Ladder;
        } else {
        size++;
        arr[size] = Ladder;
        bubbleUp(size);

    }

    // System.out.println(Arrays.toString(arr));
}

/*
 * This method adds a Ladder to the back of the queue.
 * param: Ladder
 */
public void enqueue(Ladder Ladder) {

        if (this.size >= arr.length) {
            Ladder[] newArray = new Ladder[arr.length * 2];
            for (int i = 1; i < this.size; i++) {
                newArray[i] = arr[i];
            }
            this.arr = newArray;

        }
        if (size == 0) {
            size++;
            arr[size] = Ladder;
        } else {
        size++;
            arr[size] = Ladder;
        bubbleUp(size);

        }

    }

    /*
     * This method removes the front of the queue and reorganizes it based on
     * the
     * new parents and children. It returns the Ladder removed.
     */
    public List<String> dequeue()  {
        List<String> front = arr[1].lst; // Storing the name being
                                               // removed.
        // if the queue is empty.
        if (this.size == 0) {
            System.out.println("empty");
        } else {
            arr[1] = arr[size];
            arr[size] = null;
            size--; // decrement the size.
            bubbleDown(1);
        }
        return front;

    }

    /*
     * This method returns the name of the Ladder
     * in the front of the queue
     */
    public String peek() throws Exception {
        if (this.size == 0) {
            throw new Exception();
        }
        return arr[1].lst.toString();
    }

    /*
     * This method returns the priority value of
     * the Ladder in the front of the queue
     */
    public int peekPriority() throws Exception {
        if (this.size == 0) {
            throw new Exception();
        }
        return arr[1].priority;

    }

    /*
     * This method changes the priority of the name of the Ladder
     * from the parameter.
     * param: String name and int newPriority
     */
    public void changePriority(List<String> name, int newPriority) {
        // iterates through the queue to find the Ladder
        // that matches the name.
        for (int i = 1; i <= size; i++) {
            if (arr[i].lst.toString() == name.toString()) {
                if (arr[i].priority > newPriority) {
                    arr[i].priority = newPriority;
                    bubbleUp(i); // re-bubble up based on new priority number.
                } else {
                arr[i].priority = newPriority; // changes the priority
                bubbleDown(i); // re-bubble down
            }
        }
    }
    }

    /*
     * This method returns a true or a false
     * depending on if the queue is empty or not.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
     * This method returns the size of the queue.
     */
    public int size() {
        return this.size;
    }

    /*
     * This method removes everything inside of the queue.
     */
    public void clear() {
        for (int i = 0; i <= size; i++) {
            arr[i] = null;
        }
        this.size = 0;
    }

    /*
     * This method returns the queue in a string form.
     */
    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        String result = "{" + arr[1];
        for (int i = 2; i <= size; i++) { // iterate through queue
            if (arr[i] == null) {
                result += ", ";
            } else {
            result += ", " + arr[i];
        }
    }
        result += "}";
        return result;

    }

    /*
     * This is a private helper method for enqueue.
     * It recurses through to check the newly added Ladder
     * of the queue and checks if it's parents are lower priority and if so
     * swaps places with them.
     * param: int index
     */
    private void bubbleUp(int index) {
        if (index != 1) {
            if (arr[index / 2] == null) {
                return;
            }
            if ((index/2) <= size) {
            // checks if priority of child is higher than parent
            if (arr[index].priority > arr[index / 2].priority) {
                Ladder child = arr[index]; // temporary placeholder
            Ladder parent = arr[index / 2];
                arr[index] = parent; // swap
            arr[index / 2] = child;
                bubbleUp(index / 2); // recursion

            } }else if (arr[index].priority == arr[index / 2].priority) { // check
                                                                         // if
                                                                         // priorities
                                                                         // equal
                if (arr[index].lst.toString()
                        .compareTo(arr[index / 2].lst.toString()) < 0) {
                    Ladder child = arr[index];
                    Ladder parent = arr[index / 2];
                    arr[index] = parent;
                    arr[index / 2] = child;
                    bubbleUp(index / 2);
                }
        }


    }
}
    /*
     * This is a private helper method for dequeue.
     * It recurses through to check the newly added Ladder
     * of the queue and checks if it's parents are higher priority and if so
     * swaps places with them.
     * param: int index
     */
private void bubbleDown(int index) {
	if ((index * 2 + 1) <= size) {
		int child1 = index * 2;
		int child2 = index * 2 + 1;
		int largestChild;
		// if there is not child2
		if (arr[child2] == null) {
			largestChild = child1;
		}
		else if (child2 <= size && arr[child1].priority > arr[child2].priority) {
			largestChild = child1;
			
			// THis is to check if the 2 children's priorities are the same and if so,
			// compares the lexicographical values of the strings.
		} else if (arr[child1].priority == arr[child2].priority) {
			if (arr[child1].toString().compareTo(arr[child2].toString()) > 0) {
				largestChild = child1;
			} else if (arr[child1].toString().compareTo(arr[child2].toString()) < 0) {
				largestChild = child2;
			} else {
				largestChild = child1;
			}
		}
		else {
			largestChild = child2;
		}
		if (arr[index].priority < arr[largestChild].priority) {
			Ladder temp = arr[index];
			arr[index] = arr[largestChild];
			arr[largestChild] = temp;
			bubbleDown(largestChild);
		}
	} 
}
    


}


