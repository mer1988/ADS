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
	
	private void visit(Node p, Node n, int d){
		if(n.getKey() == null){
			if(n.getLeft() != null && n.getRight() != null){
				if(n.getLeft().getValue() != null && n.getRight().getValue() != null){
					if(n.getLeft().getValue().equals(n.getRight().getValue())){
						if(d == 1){
							p.setRight(n.getLeft());
						}
						else{
							p.setLeft(n.getLeft());
						}
					}
				}
			}
		}		
	}
	
	private void postorder(Node p, Node n, int d){
		if (n != null){
			postorder(n, n.getLeft(), 0);
			postorder(n, n.getRight(), 1);
			visit(p, n, d);
		}
			
	}
	
	public void traverse(){
		postorder(null, root, -1);
	}
	
	public void insert (String key, String value) throws Exception{
		Node r = root;
		Node p = null;
		Node gp = null;
		int level = 0;
		
		while(r != null){
			gp = p;
			p = r;
			if(key.substring(level, level+1).equals("0")){
				r = r.getLeft();	
			}
			else{
				r = r.getRight();	
			}
			level += 1;
		}
		
		if(p.getKey() == null){ //Prev is not element node
			Node newNode = new ElementNode(key, value);
			if(key.substring(level-1, level).equals("0")){
				p.setLeft(newNode);
			}
			else{
				p.setRight(newNode);
			}
		}else{ //node is element node
			if(p.getKey().equals(key)){
				throw new Exception("Inserting duplicated IP key");
			}
			else{
				Node n = new BranchNode();
				
				if(key.substring(level-2, level-1).equals("0")){
					gp.setLeft(n);
				}
				else{
					gp.setRight(n);
				}
				level = level -1;
				while(p.getKey().substring(level, level+1).equals(key.substring(level, level+1))){
					Node n2 = new BranchNode();
					
					if(key.substring(level, level+1).equals("0"))
						n.setLeft(n2);
					else
						n.setRight(n2);
					
					n = n2;
					level = level + 1;
				}
				
				if(key.substring(level, level+1).equals("0")){
					n.setLeft(new ElementNode(key, value));
					n.setRight(p);
				}
				else{
					n.setRight(new ElementNode(key, value));
					n.setLeft(p);
				}
			}			
		}
	}
	
	public boolean search(String value){
		
		return false;
	}
	
	public String longestPrefixMatch(String key){
		
		return "";
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
		
		public String getValue() { return null;}
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
		private String value;
		
		public ElementNode(String key, String value){
			this.value = value;
			this.key = key;
		}
		
		public String getKey(){
			return key;
		}
		
		public String getValue(){
			return value;
		}
		
		
	}
}
