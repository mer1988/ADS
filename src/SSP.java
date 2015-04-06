import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;




public class SSP {
	
	public static void main(String Args[]){
		
		Graph graph = new Graph();
				
		try{
			graph.load("test/input_1000_50_part1.txt");
		}catch(Exception ex){
			//ex.printStackTrace();
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		
		
		Map.Entry<Integer[], Integer[]> e = Dijkstra(graph, "0", "999");
		
		Integer[] prev = e.getValue();
		Integer Inx = prev[graph.getDest()];
		String path = graph.getDest() + "";
		
		while(Inx != -1){
			path = Inx + " "+ path;
			Inx = prev[Inx];
		}
		
		System.out.println(e.getKey()[graph.getDest()]);
		System.out.print(path);

		
	}
	
	
	public static Map.Entry<Integer[], Integer[]> Dijkstra (Graph g, String source, String dest){		
		
		FibonacciHeap heap = new FibonacciHeap();
		Integer[] dist = new Integer[g.getNumVetices()];
		Integer[] prev = new Integer[g.getNumVetices()];
		
		try{
			g.setSource(new Integer(source));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		try{
			g.setDest(new Integer(dest));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		for(int i=0;i<g.getNumVetices();i++){
			Integer nodeLabel = new Integer(i);
			if (!nodeLabel.equals(g.getSource())){
				dist[i] = Integer.MAX_VALUE;
				
			}else
				dist[i] = 0;
			prev[i] = -1;
			heap.enqueue(nodeLabel, dist[i]);
		}
		
		while(!heap.isEmpty()){
			Integer u = heap.extractMin();
			
			for (Map.Entry<Integer, Integer> ady: g.getAdyacents(u)){
				Integer adyVertex = ady.getKey();
			    Integer distance = ady.getValue();
			    int alt = dist[u] + distance;
			    if(alt < dist[adyVertex]){
			    	dist[adyVertex] = alt;
			    	prev[adyVertex] = u;
			    	heap.decreaseKey(adyVertex, alt);
			    }
			}
			
		}
		
		Map.Entry<Integer[],Integer[]> result = new AbstractMap.SimpleEntry<Integer[], Integer[]>(dist, prev);
		return result;
	}
	
	
	

}
