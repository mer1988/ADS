import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;


public class Graph{
	
	private int 						source;
	private int 						dest;
	private Map<Integer, Node> 	    	vertices;
	private int							numVert,numEdg;
 	
		
	public Graph (){
		vertices = new HashMap<Integer, Node>();
		source = -1;
		dest = -1;
		numVert = 0;
		numEdg = 0;		
	}
	
	public void load (String path) throws IOException{
		
		File file = new File(path);
	    Scanner sc = new Scanner(file);
	    
	    numVert = sc.nextInt();
	    numEdg = sc.nextInt();

        while (sc.hasNextInt()){
        	int v1lbl = sc.nextInt();
        	int v2lbl = sc.nextInt();
        	
            Node 	v1 = vertices.get(v1lbl);
			Node 	v2 = vertices.get(v2lbl);           
			if(v1 == null){
				v1 = new Node(v1lbl);
				vertices.put(v1lbl, v1);
			}
			if(v2 == null){
				v2 = new Node(v2lbl);
				vertices.put(v2lbl, v2);
			}
			
			int 	w = sc.nextInt();
			
			v1.addAdyasentVertex(v2lbl, w);
			v2.addAdyasentVertex(v1lbl, w);
			
        }	
        
        sc.close();
   
		
		
	}	
	
	public void traverseNode(Integer n){
		vertices.get(n).getRoutingTable().traverse();
		
	}
	
	public void addEntryRoutingTable(int node, int dest, int next) throws Exception{
		
		vertices.get(node).addPairRoutingTable(vertices.get(dest).getIp(), next);
		
	}
	
	public int getNumVetices(){
		return numVert;
	}
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) throws Exception{
		
		if(vertices.get(source) != null)
			this.source = source;
		else{
			throw new Exception("source vertex not found!");
		}
	}

	public Integer getDest() {
		return dest;
	}

	public void setDest(Integer dest) throws Exception {
		if(vertices.get(dest) != null)
			this.dest = dest;
		else{
			throw new Exception("destination vertex not found!");
		}
	}
		
	public Map<Integer, Node> getVertices() {
		return vertices;
	}

	public String toString(){
		String str = "";
		str += "Source: "+getSource()+"\n";
		str += "Destination: "+getDest()+"\n";
		for (Map.Entry<Integer, Node> entry : vertices.entrySet()) {
		    Integer key = entry.getKey();
		    Node value = entry.getValue();
		    str += "Vertex("+key+"):";
		    for(Map.Entry<Integer, Integer> e: value.getAdyasents()){
		    	str += "Ady("+e.getKey()+", "+e.getValue()+"), ";
		    }
		    str += "\n";
		}
		
		return str;
	}
	
	public Set<Entry<Integer, Integer>>  getAdyacents(Integer vertex){
		return vertices.get(vertex).getAdyasents();
	}
	
	
	public Map.Entry<Integer, String> getNextHub(int n, int dest){
		return vertices.get(n).getRoutingTable().longestPrefixMatch(vertices.get(dest).getIp());	
	}
	
	
	public Node getNode(int label){
		return vertices.get(label);
	}

}
