import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Routing {

	public static void main(String Args[]){
		
		Graph graph = new Graph();
		Trie  trie = new Trie();
		
		try{
			graph.load("test/graph.txt");
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
			graph.setDest(new Integer("2"));
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		try{
			trie.load("test/ip.txt");
		}catch(Exception e){
			e.getMessage();
			System.exit(1);
		}
	}
	
	
	
	
}
