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
	
	
	public void insert (String address, Integer hop) throws Exception{
		Node r = root;
		Node p = null;
		Node gp = null;
		int level = 0;
		
		while(r != null){
			gp = p;
			p = r;
			if(address.substring(level, level+1) == "0"){
				r = r.getLeft();	
			}
			else{
				r = r.getRight();	
			}
			level += 1;
		}
		
		if(p.getKey() == null){ //Prev is not element node
			Node newNode = new ElementNode(address, hop);
			if(address.substring(level-1, level).equals("0")){
				p.setLeft(newNode);
			}
			else{
				p.setRight(newNode);
			}
		}else{ //node is element node
			if(p.getKey().equals(address)){
				throw new Exception("Inserting duplicated IP address");
			}
			else{
				Node n = new BranchNode();
				
				if(address.substring(level-2, level-1).equals("0")){
					gp.setLeft(n);
				}
				else{
					gp.setRight(n);
				}
				level = level -1;
				while(p.getKey().substring(level, level+1).equals(address.substring(level, level+1))){
					Node n2 = new BranchNode();
					
					if(address.substring(level, level+1).equals("0"))
						n.setLeft(n2);
					else
						n.setRight(n2);
					
					n = n2;
					level = level + 1;
				}
				
				if(address.substring(level, level+1).equals("0")){
					n.setLeft(new ElementNode(address, hop));
					n.setRight(p);
				}
				else{
					n.setRight(new ElementNode(address, hop));
					n.setLeft(p);
				}
			}			
		}
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
