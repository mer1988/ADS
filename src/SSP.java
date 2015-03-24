import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;




public class SSP {
	
	public static void main(String Args[]){
		
		Graph graph = new Graph();
				
		try{
			graph.load("test/new_input_5000_1_part1.txt");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		try{
			graph.setSource(new Integer("0"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		try{
			graph.setDest(new Integer("4999"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		Map.Entry<Integer, Integer[]> e = Dijkstra(graph);
		
		Integer[] prev = e.getValue();
		Integer Inx = prev[graph.getDest()];
		String path = graph.getDest() + "";
		
		while(Inx != -1){
			path = Inx + " "+ path;
			Inx = prev[Inx];
		}
		
		System.out.println(e.getKey());
		System.out.print(path);

		
	}
	
	
	public static Map.Entry<Integer, Integer[]> Dijkstra (Graph g){		
		
		FibonacciHeap heap = new FibonacciHeap();
		Integer[] dist = new Integer[g.getNumVetices()];
		Integer[] prev = new Integer[g.getNumVetices()];
		
		
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
		
		Map.Entry<Integer,Integer[]> result = new AbstractMap.SimpleEntry<Integer, Integer[]>(dist[g.getDest()], prev);
		return result;
	}
	
	
	

}
