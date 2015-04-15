import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class routing {

	public static void main(String Args[]){
		
		Graph 					graph = new Graph();
		
		try{
			graph.load(Args[0]);
		}catch(Exception ex){
			//System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
		
		try{
			loadIps(graph, Args[1]);
		}catch(Exception ex){
			ex.printStackTrace();
			//System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		int source = Integer.parseInt(Args[2]);
		int dest = Integer.parseInt(Args[3]);
		int minDist = -1;
		
		
		
		for(int i=0;i<graph.getNumVetices();i++){	
			Map.Entry<Integer[], Integer[]> e = ssp.Dijkstra(graph, i+"");
			Integer[] dist = e.getKey();
			Integer[] prev = e.getValue();
 			for(int j=0;j<graph.getNumVetices();j++){	
	 			if(i != j){	
	 				
	 				Integer Inx = j;
	 				while(!prev[Inx].equals(graph.getSource())){
	 					Inx = prev[Inx];
	 				}
	 				 				
	 				try{
	 					graph.addEntryRoutingTable(i, j, Inx);
	 				}catch(Exception ex){
	 					//System.out.println(ex.getMessage());
	 					ex.printStackTrace();
	 					System.exit(1);
	 				}
	 			}
	 			if(i ==  source && j == dest){
	 				minDist = dist[dest];
	 			}
			}
 			graph.traverseNode(i);
		}
		
		//simulate network sending packet from source to destination
		
		System.out.println(minDist);
		
		Integer node = source;
		while(node != dest){
			//System.out.print(node);
			Map.Entry<Integer, String> e  = graph.getNextHub(node, dest);
			System.out.print(e.getValue()+" ");
			node = e.getKey();
		}
		
	}
	
	
	
	
	
	
	public static String normalizeIp(String ip){
		while(ip.length() < 32){
			ip = "0"+ip;
		}

		return ip;
	}
	
	public static void loadIps(Graph g, String path) throws Exception{
		

		File file = new File(path);
	    Scanner sc = new Scanner(file);
	    int label = 0;
	    while (sc.hasNextLine()) {
	    	String binary = normalizeIp(ipToBinary(sc.next()));
			g.getNode(label).setIp(binary);
			label += 1;		
	    }
	    
	    sc.close();
	 
		
//		FileInputStream fstream = new FileInputStream(path);
//		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//		Map<Integer, String> ips = new HashMap<Integer, String>();
//		String strLine;
//	
//		int label = 0;
//		while ((strLine = br.readLine()) != null)   {
//			String binary = normalizeIp(ipToBinary(strLine));
//			ips.put(label, binary);
//			label += 1;	
//			br.readLine();
//		}
//		//Close the input stream
//		br.close();
//		return ips;
	}
	
	public static String ipToBinary(String ip) throws Exception{
		InetAddress ipAdr = InetAddress.getByName(ip);
		byte[] bytes = ipAdr.getAddress();	
		String data_out_string = new BigInteger(1, bytes).toString(2);
		return data_out_string;
	}
	
	
}
