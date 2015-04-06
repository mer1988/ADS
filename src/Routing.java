import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Routing {

	public static void main(String Args[]){
		
		Graph graph = new Graph();
		
		
		try{
			graph.load("test/graph.txt");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		
	}
	
	
	
	
}
