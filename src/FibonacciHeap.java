import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FibonacciHeap {
	private Map<Integer, Node> nodes;
    private Node min;
    private int size;

    public FibonacciHeap() {
		min = null;
		size = 0;
		nodes = new HashMap<Integer, Node>();
	}
    
    
    public void enqueue(Integer value, int priority) {
    	
    	
        Node newNode = new Node(value, priority);
        
        nodes.put(value, newNode);
       
        
        min = mergeLists(min, newNode);
        size += 1;
        
    }
    
    public Integer getMin() {
       
        return min.getElem();
    }

    public boolean isEmpty() {
        return min == null;
    }

 
    public int getSize() {
        return size;
    }

    


   
    public Integer extractMin() {
        

        size -= 1;;

        Node minCopy = min;
       
        if (min.next == min) { 
            min = null; 
        }
        else {
            min.prev.next = min.next;
            min.next.prev = min.prev;
            min = min.next; 
        }
        
        if (minCopy.child != null) {
            
            Node n = minCopy.child;
            
            do {
                n.parent = null;
                n = n.next;
            } while (n != minCopy.child);
        }

        min = mergeLists(min, minCopy.child);

        if (min == null) return minCopy.getElem();
        
        List<Node> treeTable = new ArrayList<Node>();

        List<Node> toVisit = new ArrayList<Node>();

        for (Node n = min; toVisit.isEmpty() || toVisit.get(0) != n; n = n.next)
            toVisit.add(n);

        
        for (Node n: toVisit) {
            
            while (true) {
                
                while (n.degree >= treeTable.size())
                    treeTable.add(null);

                if (treeTable.get(n.degree) == null) {
                    treeTable.set(n.degree, n);
                    break;
                }
                
                Node n2 = treeTable.get(n.degree);
                treeTable.set(n.degree, null); 

                Node min = (n2.priority < n.priority)? n2 : n;
                Node max = (n2.priority < n.priority)? n  : n2;

                max.next.prev = max.prev;
                max.prev.next = max.next;

                max.next = max;
                max.prev = max;
                min.child = mergeLists(min.child, max);
                
                max.parent = min;
                max.childCut = false;
                min.degree += 1;               
                n = min;
            }

            if (n.priority <= min.priority) min = n;
        }
        return minCopy.getElem();
    }

    
  
    public void delete(Node entry) throws Exception{  
        decreaseKey(entry.getElem(), Integer.MIN_VALUE);
        extractMin();
    }

   
    private Node mergeLists(Node n1, Node n2) {
       
        if (n1 == null && n2 == null) { // Both null, resulting list is null.
            return null;
        }
        else if (n1 != null && n2 == null) { // Two is null, result is one.
            return n1;
        }
        else if (n1 == null && n2 != null) { // One is null, result is two.
            return n2;
        }
        else { 
            Node oneNext = n1.next;
            n1.next = n2.next;
            n1.next.prev = n1;
            n2.next = oneNext;
            n2.next.prev = n2;

          
            return n1.priority < n2.priority? n1 : n2;
        }
    }

    
    public void decreaseKey(Integer nodeLabel, int priority) {
    	Node n = nodes.get(nodeLabel);
    	if(n != null){
    		n.priority = priority;
    	       
            if (n.parent != null && n.priority <= n.parent.priority)
                cutNode(n);
          
            if (n.priority <= min.priority)
                min = n;    		
    	}
        
    }

   
    private void cutNode(Node n) {
        
        n.childCut = false;
        
        if (n.parent == null) return;

        if (n.next != n) { 
            n.next.prev = n.prev;
            n.prev.next = n.next;
        }

        
        if (n.parent.child == n) {
            
            if (n.next != n) {
                n.parent.child = n.next;
            }
          
            else {
                n.parent.child = null;
            }
        }

       
        n.parent.degree -= 1;

        n.prev = n.next = n;
        min = mergeLists(min, n);
        
        if (n.parent.childCut)
            cutNode(n.parent);
        else
            n.parent.childCut = true;
        
        n.parent = null;
    }
    
    
    /*
     * Fib Heap Node class
     * */
    
    private class Node {
        private int     	degree = 0;       
        private boolean 	childCut = false; 
        private Node 		next, prev, parent, child;  
        private Integer     elem;     
        private int 		priority; 

        private Node(Integer elem, int priority) {
            next = this;
            prev = this;
            this.elem = elem;
            this.priority = priority;
        }
        
        public Integer getElem() {
            return elem;
        }
       
        public void setElem(Integer elem) {
            this.elem = elem;
        }

        public double getPriority() {
            return priority;
        }

        
    }
    
    
}