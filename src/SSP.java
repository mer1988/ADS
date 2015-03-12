import java.util.Map;


public class SSP {
	
	private static FibonacciHeap heap;
	private static Graph graph;
	
	public static void main(String Args[]){
		//initialize Fib Heap
		heap = new FibonacciHeap();
		
		//Initialize Graph From File
		graph = new Graph();
				
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
		
		System.out.println(graph.toString());
		
		
		
	}
	
	
	public static float Dijkstra (){
		
		for(int i=0;i<graph.getNumVetices();i++){
				
		}
		
		return 0 ;
	}
	
	
	

}
