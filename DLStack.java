
public class DLStack<T> implements DLStackADT<T> {
	private DoubleLinkedNode<T> top;
	private int numItems;
	private int testNum;
	
	public DLStack() {
		top = null;
		numItems = 0;
	}
	
	public void push(T dataItem) {
		DoubleLinkedNode<T> aNode = new DoubleLinkedNode<T> (dataItem);
		DoubleLinkedNode<T> tempNode = top;
		DoubleLinkedNode<T> tempTop = top;
		
		if (numItems == 0) {
			top = aNode;
			tempNode = top;
			numItems ++;
		}
		else if (numItems == 1) {
			top.setNext(aNode);
			top = top.getNext();
			top.setPrevious(tempNode);
			numItems ++;
		}
		else {
			top.setNext(aNode);
			top = top.getNext();
			top.setPrevious(tempNode);
			numItems ++;;
		}
		tempTop = top;
	}
	
	public T pop() throws EmptyStackException {
		DoubleLinkedNode<T> tempTop = top;
		if (numItems == 0) {
		}
		if (numItems == 0) {
			throw new EmptyStackException("Stack is Empty");
		}
		T aNode = top.getElement();
		if (numItems == 1) {
			numItems --;
			aNode = top.getElement();
			top = null;
		}
		else {
			
		/*Removing the pointer of the top node in the stack as well as
		 * the pointer pointing to it. This is done by getting the top
		 * and previous element, making the top node point to nothing,
		 * and making no nodes point to it. The node is then returned.
		 */	
			aNode = top.getElement();
			tempTop = top.getPrevious();
			top.setPrevious(null);
			top = tempTop;
			top.setNext(null);
			numItems --;
		}
		return aNode;
	}
	
	/*
	 * Responsible for popping the nodes from the stack at said index and
	 * returning it. To do this, I split the method up into 3 different 
	 * cases: if k was 1 (indicating that the top of the stack was to be
	 * popped), if k was equal to the number of items in the stack (indicating 
	 * that the last item in the stack was to be popped) and everything
	 * in between. Count was just used keep track of the number of iterations
	 * needed to get "top" pointing back to the top of the stack after popping.
	 */
	public T pop(int k) throws InvalidItemException {
        DoubleLinkedNode<T> currentNode = top;
        DoubleLinkedNode<T> nextNode = top;
        DoubleLinkedNode<T> prevNode = top;
        DoubleLinkedNode<T> returnNode = null;
        int count = 0;
        
        if (k > numItems || k <= 0) {
        	throw new InvalidItemException("Error. Node at that location does not exist.");
        }
        
        if (k == 1) {
        	returnNode = currentNode;
            prevNode = currentNode.getPrevious();
        	top.setPrevious(null);
        	top = prevNode;
        	top.setNext(null);
        	numItems --;
        }
        else if (k == numItems) {
        	for (int i = 0; i < k - 1; i ++) {
        		top = top.getPrevious();
        		count ++;
        	}
        	nextNode = top.getNext();
        	returnNode = top;
        	top.setNext(null);
        	top = nextNode;
        	top.setPrevious(null);
        	numItems --;
        	for (int i = 0; i < count - 1; i ++) {
        		top = top.getNext();
        	}
        }
        else {
            for (int j = 0; j < k - 1; j ++) {
            	top = top.getPrevious();
            	count ++;
            }
            prevNode = top.getPrevious();
            nextNode = top.getNext();
            returnNode = top;
            top.setNext(null);
            top.setPrevious(null);
            top = nextNode;
            top.setPrevious(prevNode);
            top = prevNode;
            top.setNext(nextNode);
            numItems --;
            for (int a = 0; a < count; a ++) {
            	top = top.getNext();
            }
        }
        return returnNode.getElement();
        
    }
	
	public T peek() throws EmptyStackException {
		if (numItems == 0) {
			throw new EmptyStackException("Stack is empty");
		}
		else {
			return top.getElement();
		}
	}
	
	public boolean isEmpty() {
		if (numItems == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return numItems;
	}
	
	public DoubleLinkedNode<T> getTop() {
		return top;
	}
	
	public String toString() {
		String aString = "[";
		DoubleLinkedNode<T> aTop = top;
		
		while (aTop != null) {
			aString = aString + aTop.getElement() + " ";
			aTop = aTop.getPrevious();
		}
		aString = aString + "]";
		return aString;
	}
}
