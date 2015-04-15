import java.util.AbstractMap;
import java.util.Map;




public class ssp {
	
	
	
	public static void main(String Args[]){
		
		Graph graph = new Graph();
				
		try{
			graph.load("test/input_5000_1_part1.txt");
		}catch(Exception ex){
			ex.printStackTrace();
			//System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		try{
			graph.setDest(new Integer("4999"));
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		Map.Entry<Integer[], Integer[]> e = Dijkstra(graph, "0");
		
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
	
	
	public static Map.Entry<Integer[], Integer[]> Dijkstra (Graph g, String source){		
		
		FibonacciHeap heap = new FibonacciHeap();
		Integer[] dist = new Integer[g.getNumVetices()];
		Integer[] prev = new Integer[g.getNumVetices()];
		
		try{
			g.setSource(new Integer(source));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		for(int i=0;i<g.getNumVetices();i++){
			int nodeLabel = i;
			
			if (nodeLabel != g.getSource()){
				dist[i] = Integer.MAX_VALUE;
			}else
				dist[i] = 0;
			
			prev[i] = -1;
			
			heap.insert(g.getNode(nodeLabel), dist[i]);
		
		}
		
		while(!heap.isEmpty()){
			//System.out.println(heap.getSize());
			int u = heap.extractMin();
			
			for (Map.Entry<Integer, Integer> ady: g.getAdyacents(u)){
				int adyVertex = ady.getKey();
			    int distance = ady.getValue();
			    int alt = dist[u] + distance;
			    if(alt < dist[adyVertex]){
			    	dist[adyVertex] = alt;
			    	prev[adyVertex] = u;
			    	heap.decreaseKey(g.getNode(adyVertex), alt);
			    }
			}
			
		}
		
		Map.Entry<Integer[],Integer[]> result = new AbstractMap.SimpleEntry<Integer[], Integer[]>(dist, prev);
		return result;
	}
	
	
	

}
