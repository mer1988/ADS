import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Routing {

	public static void main(String Args[]){
		
		Graph 					graph = new Graph();
		Map<Integer, String>	ips = new HashMap<Integer, String>();
		try{
			graph.load("test/input_graphsmall_part2.txt");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		try{
			ips = loadIps("test/input_ipsmall_part2.txt");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		//Create Routing table for every node... is this O(n^2)??? 
		for(int i=0;i<graph.getNumVetices();i++){	
			Map.Entry<Integer[], Integer[]> e = SSP.Dijkstra(graph, i+"");
			Integer[] dist = e.getKey();
			Integer[] prev = e.getValue();
 			for(int j=0;j<graph.getNumVetices();j++){	
	 			if(i != j){	
	 				Integer Inx = j;
	 				while(!prev[Inx].equals(graph.getSource())){
	 					Inx = prev[Inx];
	 				}
	 				
	 				System.out.println(j+" - "+Inx);
	 				
	 				try{
	 					graph.addEntryRoutingTable(i, ips.get(j), ips.get(Inx));
	 				}catch(Exception ex){
	 					//System.out.println(ex.getMessage());
	 					ex.printStackTrace();
	 					System.exit(1);
	 				}
	 			}
			}
 			graph.traverseNode(i);
		}
		
	}
	
	
	
	
	
	
	public static String normalizeIp(String ip){
		while(ip.length() < 32){
			ip = "0"+ip;
		}

		return ip;
	}
	
	public static Map<Integer, String> loadIps(String path) throws Exception{
		
		FileInputStream fstream = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		Map<Integer, String> ips = new HashMap<Integer, String>();
		String strLine;
	
		int label = 0;
		while ((strLine = br.readLine()) != null)   {
			String binary = normalizeIp(ipToBinary(strLine));
			ips.put(label, binary);
			//System.out.println(binary);
			label += 1;	
			br.readLine();
		}
		//Close the input stream
		br.close();
		return ips;
	}
	
	public static String ipToBinary(String ip) throws Exception{
		InetAddress ipAdr = InetAddress.getByName(ip);
		byte[] bytes = ipAdr.getAddress();	
		String data_out_string = new BigInteger(1, bytes).toString(2);
		return data_out_string;
	}
	
	
}
