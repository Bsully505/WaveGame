/**********
 * Stack
 * Original Author: Christian Duncan
 * Modified by:  ... team names ...
 *
 * A very simple array-based stack implementation
 * that does NOT grow.  [That part should be changed.]
 **********/
import java.util.NoSuchElementException;

public class Stack<T> {
    static class StackFullException extends Exception {
	public StackFullException() { super("The Stack is Full."); }
	public StackFullException(String message) { super(message); }
    }
	T[] newArr; //temp array
    T[] arr;  // The array
    int n;    // The number of items in the stack (also, the top of stack)


    // Constructor methods
    public Stack() { this(10); }

    @SuppressWarnings("unchecked") // Generic array allocation
    public Stack(int initialSize) {
	arr = (T[]) new Object[initialSize];
	n = 0;
    }

    // Push a new element onto the stack
    // If the stack is FULL throw an exception
    //    THIS SHOULD BE FIXED so it does not need to do this but
    //    increases the size of the array
    public void push(T item) throws StackFullException {
	if (n < arr.length) {
	    // There is room
	    arr[n] = item;
	    n++;
	} else {
		
		
		
		
	    // Insufficient space - GROW THE ARRAY!
	    // But for now, we just throw an exception
		
	    //throw new StackFullException();
	}
    }
	//////////////////////////Array Methods//////////////////////////////
	public void arrayPlus1(){
		// expanding array by 1
		newArr = new T[arr.length +1];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
	}
	
	public void arrayPlus100(){
		//expanding array by 100
		newArr = new T[arr.length +100];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
	}
	
	public void arrayDouble(){
		//expanding array by * 2
		newArr = new T[arr.length*2];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
	}
	
	public void arrayPlusArrDiv2(){
		//expanding array by arr + (arr/2)
		newArr = new T[arr.length + (arr.length/2)];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
	}
	
	public void arrayMultiply4(){//possibly has to be changed to length*length
		//expanding array by arr * 4
		if (arr.length > 0){
		newArr = new T[arr.length*4];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
		}
		else arr.length;
	}
	
	public void arrayPlusFloorSqRoot(){
		//expanding array by the floor of the square root of some value
		newArr = new T[arr.length + (int)Math.floor(Math.sqrt(arr.length))];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		arr = newArr;
	}
	//////////////////////////Array Methods//////////////////////////////
	

    // Pop the top element off the stack
    // If the stack is empty, throw an exception
    public T pop() throws NoSuchElementException {
	if (isEmpty()) throw new NoSuchElementException();

	n--;
	return arr[n];
    }

    // Similar to pop but does not remove the element from the stack
    public T peek() throws NoSuchElementException {
	if (isEmpty()) throw new NoSuchElementException();
	return arr[n-1];
    }

    // Is the stack empty?
    public boolean isEmpty() {
	return n <= 0;
    }
}
