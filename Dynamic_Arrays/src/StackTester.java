/**********
 * StackTester
 * Original Author: Christian Duncan
 * Modified by:  ... team names ...
 *
 * A simple test of the Stack class
 **********/
import java.util.NoSuchElementException;

public class StackTester {
    public static void main(String[] args) {
	// Test some of the simple features
	Stack<Integer> s = new Stack<Integer>();
	try {
	    for (int i = 0; i < 10; i++) {
		// Fill the array
		s.push(i);
	    }
	} catch (Stack.StackFullException e) {
	    System.err.println("ERROR: Exceptions should not happen here.");
	    System.err.println(e.getMessage());
	}

	try {
	    s.push(10);
	    System.err.println("ERROR: This should be an error. Stack does not grow!");
	} catch (Stack.StackFullException e) { /* Exception should be thrown here */ }

	// Now just print out the results
	try {
	    System.err.println("Should print 9, 8, 7, ... 0");
	    while (!s.isEmpty()) {
		int result = s.pop();
		System.out.println(result);
	    }
	} catch (NoSuchElementException e) {
	    System.err.println("ERROR: Exceptions should not happen here.");
	    System.err.println(e.getMessage());
	}

	try {
	    int res = s.pop();
	    System.err.println("ERROR: This should be an error. Stack should be empty!");
	} catch (NoSuchElementException e) { /* Exception should be thrown */ }

	// Could test more, but that is sufficient to demonstrate...
    }
}
