import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;



public class Trie {
	private Node root;
	
	public Trie(){
		root = (Node) new BranchNode();
		root.setRight(null);
		root.setLeft(null);
	}
	
	
	public boolean insert(String address, Integer hop){
		Node r = root;
		Node prev = null;
		int where = -1;
		int level = 0;
		
		while(r != null){
			prev = r;
			if(address.substring(level, level+1) == "0"){
				r = r.getLeft();
				where = 0;
			}
			else{
				r = r.getRight();
				where = 1;
			}
			level += 1;
		}
		
		if(prev.getKey() == null){ //Node is not element node
			Node newNode = new ElementNode(address, hop);
			if(where == 0){
				prev.setLeft(newNode);
			}
			if(where == 1){
				prev.setRight(newNode);
			}
		}else{ //node is element node
			
			
		}
		
		return false;
	}
	
	public boolean search(String Address){
		
		return false;
	}
	
	public String longestPrefixMatch(String Address){
		
		return "";
	}
	
	public String normalizeIp(String ip){
		while(ip.length() < 32){
			ip = "0"+ip;
		}

		return ip;
	}
	
	public void load(String path) throws Exception{
		
		FileInputStream fstream = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
	
		int label = 0;
		while ((strLine = br.readLine()) != null)   {
			String binary = normalizeIp(ipToBinary(strLine));
			System.out.println(binary);
			insert(binary, label);
			label += 1;			
		}

		//Close the input stream
		br.close();
		
	}
	
	public String ipToBinary(String ip) throws Exception{
		InetAddress ipAdr = InetAddress.getByName(ip);
		byte[] bytes = ipAdr.getAddress();	
		String data_out_string = new BigInteger(1, bytes).toString(2);
//		boolean[] data_out_binary = new boolean[data_out_string.length()];
//		for (int i = 0; i < data_out_string.length(); i++){
//		    char c = data_out_string.charAt(i); 
//		    if(c == '0')
//		    	data_out_binary[i] = false;
//		    else
//		    	data_out_binary[i] = true;
//		}
		return data_out_string;
	}
	
	/*
	 * 
	 * 			Node Classes
	 * 
	 * */
	
	private class Node{
		
		public Node getRight(){	return null;}
		
		public Node getLeft(){return null;} 
		
		public String getKey(){	return null;}
		
		public void setRight(Node r){ }
		
		public void setLeft(Node r){ }
	}
	
	private class BranchNode extends Node{
		private Node left;
		private Node right;
		
		public BranchNode(){
			left = null;
			right = null;
		}
		
		@Override
		public Node getRight(){
			return right;
		}
		
		@Override
		public Node getLeft(){
			return left;
		}
		
		public void setRight(Node r){
			right = r;
		}
		
		public void setLeft(Node r){
			left = r;
		}
	}
	
	private class ElementNode extends Node{
		private String key;
		private Integer value;
		
		public ElementNode(String key, Integer value){
			this.value = value;
			this.key = key;
		}
		
		public String getKey(){
			return key;
		}
		
	}
}
