import java.util.ArrayList;
import java.util.List;



public class FibonacciHeap {
	
    private Node min;
    private int size;

    public FibonacciHeap() {
		min = null;
		size = 0;
		
	}
    
    public int getSize(){
    	return size;
    }
    
    public void insert(Node n,  int priority) {
    	n.setParent(null);
    	n.setChild(null);
    	
    	n.setNext(n);
    	n.setPrev(n);
    	
    	n.setDegree(0);
    	n.setPriority(priority);
    	
        min = meld(min, n);
        size += 1;
        
    }
    
   

    public boolean isEmpty() {
        if (size != 0) return false;
        else return true;
    }

 

    


   
    public Integer extractMin() {
        size -= 1;
        Node minCopy = min;
       
        
        if (min.getNext() == min) { 
            min = null; 
        }
        else {
            min.getPrev().setNext(min.getNext());
            min.getNext().setPrev(min.getPrev());
            min = min.getNext(); 
        }
        
        if (minCopy.getChild() != null) {  
            Node n = minCopy.getChild().getNext();
            
            while(n != minCopy.getChild()){
                n.setParent(null);
                n = n.getNext();
            } 
            n.setParent(null);
        }

        min = meld(min, minCopy.getChild());

        if (min == null) return minCopy.getLabel();
        
        List<Node> mergeTable = new ArrayList<Node>();
        List<Node> toVisit = new ArrayList<Node>();

        for (Node n = min; toVisit.isEmpty() || toVisit.get(0) != n; n = n.getNext())
            toVisit.add(n);

        for (Node n: toVisit) {    
            while (true) {   
                while (n.getDegree() >= mergeTable.size())
                    mergeTable.add(null);

                if (mergeTable.get(n.getDegree()) == null) {
                    mergeTable.set(n.getDegree(), n);
                    break;
                }
                
                Node n2 = mergeTable.get(n.getDegree());
                mergeTable.set(n.getDegree(), null); 

                Node min;
                Node max;
                
                if(n2.getPriority() < n.getPriority()){
                	min = n2;
                	max = n;
                }
                else{
                	min = n;
                	max = n2;
        		}
                
                max.getNext().setPrev(max.getPrev());
                max.getPrev().setNext(max.getNext());

                max.setNext(max);
                max.setPrev(max);
                min.setChild(meld(min.getChild(), max));
                
                max.setParent( min );
                max.setChildCut(false);
                min.setDegree(min.getDegree()+1);               
                n = min;
            }

            if (n.getPriority() <= min.getPriority()) min = n;
        }
        return minCopy.getLabel();
    }

    public void delete(Node entry) throws Exception{  
        decreaseKey(entry, Integer.MIN_VALUE);
        extractMin();
    }
    
    private Node meld(Node n1, Node n2) {
       
        if (n1 == null && n2 == null) { 
            return null;
        }
        else if (n1 != null && n2 == null) { 
            return n1;
        }
        else if (n1 == null && n2 != null) {
            return n2;
        }
        else { 
            Node nxt = n1.getNext();
            n1.setNext(n2.getNext());
            n1.getNext().setPrev(n1);
            n2.setNext(nxt);
            n2.getNext().setPrev(n2);   
        }
        if(n1.getPriority() < n2.getPriority())
        	return n1;
        else
        	return n2;
        
    }
    
    public void decreaseKey(Node n, int priority) {
    	
    	if(n != null){
    		n.setPriority(priority);
    	       
            if (n.getParent() != null && n.getPriority() <= n.getParent().getPriority())
                cut(n);
          
            if (n.getPriority() <= min.getPriority())
                min = n;    		
    	}
        
    }

   
    private void cut(Node n) {
        
        n.setChildCut(false);
        
        if (n.getParent() == null) return;

        if (n.getNext() != n) { 
            n.getNext().setPrev( n.getPrev() );
            n.getPrev().setNext( n.getNext() );
        }
        
        if (n.getParent().getChild() == n) {
            
            if (n.getNext() != n) {
                n.getParent().setChild(n.getNext());
            }
            else {
                n.getParent().setChild( null );
            }
        }

       
        n.getParent().setDegree(n.getParent().getDegree() - 1);

        n.setPrev(n);
        n.setNext(n);
        
        min = meld(min, n);
        
        if (n.getParent().getChildCut())
            cut(n.getParent());
        else
            n.getParent().setChildCut( true );
        
        n.setParent( null );
    }
   
}