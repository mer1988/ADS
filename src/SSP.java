import java.util.ArrayList;
import java.util.Map;




public class SSP {
	
	public static void main(String Args[]){
		
		Graph graph = new Graph();
				
		try{
			graph.load("test/input_1000_50_part1.txt");
		}catch(Exception ex){
			ex.printStackTrace();
//			System.out.println("Problem loading file!");
//			System.out.println(ex.getMessage());
//			System.exit(1);
		}
		
		try{
			graph.setSource(new Integer("0"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		try{
			graph.setDest(new Integer("999"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
				
		System.out.println(graph.toString());
		
		System.out.print("Shortest path cost: "+Dijkstra(graph));

		
	}
	
	
	public static float Dijkstra (Graph g){
		
		FibonacciHeap heap = new FibonacciHeap();
		Integer[] dist = new Integer[g.getNumVetices()];
		
		for(int i=0;i<g.getNumVetices();i++){
			Integer nodeLabel = new Integer(i);
			if (!nodeLabel.equals(g.getSource()))
				dist[i] = Integer.MAX_VALUE;
			else
				dist[i] = 0;
			heap.enqueue(nodeLabel, dist[i]);
		}
		
		//for(int i=0; i<g.getNumVetices();i++)			
			//System.out.println("dis("+i+")="+dist[i]);
		
		while(!heap.isEmpty()){
			Integer u = heap.dequeueMin();
			//System.out.println("Dequeue node: "+u);
			for (Map.Entry<Integer, Integer> ady: g.getAdyacents(u)){
				Integer adyVertex = ady.getKey();
			    Integer distance = ady.getValue();
			    int alt = dist[u] + distance;
			    if(alt < dist[adyVertex]){
			    	//System.out.println("Update dist from source to "+adyVertex);
			    	dist[adyVertex] = alt;
			    	heap.decreaseKey(adyVertex, alt);
			    }
			}
			
		}
		//for(int i=0; i<g.getNumVetices();i++)			
			//System.out.println("dis("+i+")="+dist[i]);
		return dist[g.getDest()] ;
	}
	
	
	

}
