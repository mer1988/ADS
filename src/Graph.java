import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Graph{
	
	private Vertex 						source;
	private Vertex 						dest;
	private Map<Integer, Vertex> 	    vertices;
	private int							numVert, numEdg;
 	
	//Empty Graph
	public Graph (){
		vertices = new HashMap<Integer, Vertex>();
		source = null;
		dest = null;
		numVert = 0;
		numEdg = 0;
	}
	
	//Load graph from file
	public void load (String path) throws IOException{
	
		FileInputStream fstream = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		
		//Read First Line
		strLine = br.readLine();
		String[] num = strLine.split(" ");
		numVert = Integer.parseInt(num[0]);
		numEdg = Integer.parseInt(num[1]);
		
		//Create all vertices
		for (int i = 0; i < numVert; i++) {
			vertices.put((Integer) i, new Vertex(i));
		}
		
		while ((strLine = br.readLine()) != null)   {
			String[] edge =  strLine.split(" ");
			Vertex v1 = vertices.get(new Integer(edge[0]));
			Vertex v2 = vertices.get(new Integer(edge[1]));
			int w = Integer.parseInt(edge[2]);
			
			v1.addAdyasentVertex(v2.label, w);
			v2.addAdyasentVertex(v1.label, w);
			
		}

		//Close the input stream
		br.close();
		
	}	
	
	public int getNumVetices(){
		return numVert;
	}
	
	public Integer getSource() {
		return source.getLabel();
	}

	public void setSource(Integer source) throws Exception{
		
		if(vertices.get(source) != null)
			this.source = vertices.get(source);
		else{
			throw new Exception("source vertex not found!");
		}
	}

	public Integer getDest() {
		return dest.getLabel();
	}

	public void setDest(Integer dest) throws Exception {
		if(vertices.get(dest) != null)
			this.dest = vertices.get(dest);
		else{
			throw new Exception("destination vertex not found!");
		}
	}
	
	
	
	public Map<Integer, Vertex> getVertices() {
		return vertices;
	}


	public String toString(){
		String str = "";
		str += "Source: "+getSource()+"\n";
		str += "Destination: "+getDest()+"\n";
		for (Map.Entry<Integer, Vertex> entry : vertices.entrySet()) {
		    Integer key = entry.getKey();
		    Vertex value = entry.getValue();
		    str += "Vertex("+key+"):";
		    for(Map.Entry<Integer, Integer> e: value.getAdyasents()){
		    	str += "Ady("+e.getKey()+", "+e.getValue()+"), ";
		    }
		    str += "\n";
		}
		
		return str;
	}
	
	
	
	private class Vertex{
		
		private Integer label;
		private Map<Integer, Integer> adyasent; 

		public Vertex(Integer label) {
			this.label = label;
			adyasent = new HashMap<Integer, Integer>();
		}

		public Integer getLabel() {
			return label;
		}

		public void setLabel(Integer label) {
			this.label = label;
		}
		
		public void addAdyasentVertex(Integer v2, Integer weight){
			adyasent.put(v2, weight);			
		}

		
		public Set<Entry<Integer, Integer>> getAdyasents(){
			return adyasent.entrySet();
		}
		
					
	}
	
	

}
