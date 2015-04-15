import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



public class Node {
    
	//For FibHeap
	private int     	degree = 0;       
	private boolean 	childCut = false; 
	private Node 		next, prev, parent, child;     
	private int 		priority; 
	
	//For Graph
	private Map<Integer, Integer> 	adyasent; 
	private int 					label;
	
	//For Routing
	private Trie					routingTable;
	private String					ip;

	
	public Node(int label) {
		adyasent = new HashMap<Integer, Integer>();
		routingTable = new Trie();
		next = this;
	    prev = this;
	    this.priority = 0;
	    this.label = label;
	}

	public int getLabel(){
		return label;
	}
	
	public void addAdyasentVertex(Integer v2, Integer weight){
		adyasent.put(v2, weight);			
	}

	
	public Set<Entry<Integer, Integer>> getAdyasents(){
		return adyasent.entrySet();
	}
	
	public void addPairRoutingTable(String dest, Integer next) throws Exception{ 
		routingTable.insert(dest, next);
	}
	
	public Trie getRoutingTable(){
		return routingTable;
	}
	

	public int getDegree() {
		return degree;
	}



	public void setDegree(int degree) {
		this.degree = degree;
	}



	public boolean getCut() {
		return childCut;
	}



	public void setChildCut(boolean childCut) {
		this.childCut = childCut;
	}



	public Node getNext() {
		return next;
	}



	public void setNext(Node next) {
		this.next = next;
	}



	public Node getPrev() {
		return prev;
	}



	public void setPrev(Node prev) {
		this.prev = prev;
	}



	public Node getParent() {
		return parent;
	}



	public void setParent(Node parent) {
		this.parent = parent;
	}



	public Node getChild() {
		return child;
	}



	public void setChild(Node child) {
		this.child = child;
	}



	



	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
    public boolean getChildCut(){
    	return childCut;
    }   
    
   
    public void setIp(String ip){
    	this.ip = ip;
    }
    
    public String getIp(){
    	return ip;
    }
        
}