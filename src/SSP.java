import java.util.Map;




public class SSP {
	
	public static void main(String Args[]){
		
		//Initialize Graph From File
		Graph graph = new Graph();
				
		try{
			graph.load("test/graph.txt");
		}catch(Exception ex){
			System.out.println("Problem loading file!");
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		
		try{
			graph.setSource(new Integer("0"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		try{
			graph.setDest(new Integer("2"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Dijkstra(graph);
		
		System.out.println(graph.toString());
		
		
		
	}
	
	
	public static float Dijkstra (Graph g){
		FibonacciHeap heap = new FibonacciHeap();
		Integer[] dist = new Integer[g.getNumVetices()];
		
		for(int i=0;i<g.getNumVetices();i++){
			Integer nodeLabel = new Integer(i);
			double distance = (nodeLabel != g.getSource())? Double.MAX_VALUE : 0.0;
			dist[i] = (int) distance;
			heap.enqueue(new Integer(i), distance);
		}
		
		while(!heap.isEmpty()){
			Integer u = heap.dequeueMin();
			for (Map.Entry<Integer, Integer> ady: g.getAdyacents(u)){
				Integer adyVertex = ady.getKey();
			    Integer distance = ady.getValue();
			    int alt = dist[u] + distance;
			    if(alt < dist[adyVertex]){
			    	dist[adyVertex] = alt;
			    	heap.decreaseKey(n, priority);
			    }
			}
			
		}
		return 0 ;
	}
	
	
	

}
