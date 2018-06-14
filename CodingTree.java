//Raymond Valdez
import java.util.*;
import java.io.*;

public class CodingTree {

	//the message is passed into the constructor,
	//the constructor parses the string and places the string inside 
		
		MyHashTable<String,String> codes;
		MyHashTable<String, Integer> wordFreq;
		Map<String,Integer> testmap = new HashMap<String, Integer>(); //tester map
		
		String bits;
		double probecount = 0;
		double avgProbe = 0;
		
		
	CodingTree(String message){ //constructor
		
		//change sized to initialize the HashTable with fewer buckets
		int sized = 32768; //this is the capacity of the HashTable
		
		
		wordFreq = new MyHashTable<>(sized); //adjust the parameter for a bigger size
		
		//create the codes map
		codes = new MyHashTable<>(sized);
		
		
		ArrayList<String> Arr = new ArrayList<String>();
		StringBuilder Build = new StringBuilder();
		
		char test;
		for(int i = 0; i<message.length(); i++){
			test = message.charAt(i);
			
				//place the word into an array of words if it is a valid word
				if((test >= 48 && test <= 57) || (test>=65 && test<=90) || (test>=97 && test<=122) || test == 39 || test == 45){
					Build.append(test);
				}
				else {
					
					if(Build.length()!=0){
					Arr.add(Build.toString());
					Build.setLength(0);
					}
					
					Arr.add(Character.toString(test));
					}
			
		}
		
		
		//implement the hash table
		HashTable(Arr);
		//HashTabletest(Arr); //tester
		
		wordFreq.stats(); 
		
		//create an arraylist of nodes
		ArrayList<Node> nodeArr = new ArrayList<>();
		
		
		for(int i =0; i<sized; i++){
		
		//extracts the keys and values from the Block array and adds it to the build tree method
		if(wordFreq.Block[i] != null){
		Node element = new Node(wordFreq.Block[i].getKey() , wordFreq.Block[i].getValue()); //creates the node
		nodeArr.add(element); //stores the node into the array
		}
		
		
		}
		
		Collections.sort(nodeArr);
		
		StringBuilder path = new StringBuilder();
		
		Node ROOT = new Node();
		
		ROOT = treebuilder(nodeArr);
				
		Treetraversal(ROOT, path); //Traverses the tree and assigns the path to their respective strings
		
		//out.print(wordFreq.toString()); //prints the wordFreq to String
		//out.print(codes.toString()); //prints the codes map to String
		
		//passing the array of words to be encoded
		encode(Arr);
	}
	
	
	public void HashTable(ArrayList<String> Arr){
		
		//for counting frequencies there will be many buckets,
		//but once I get to building the tree, I will cut the bucket size down
		
		
		
		for(int i = 0; i<Arr.size(); i++){
			
			
			//if the word is not in the map, place the word in the map
			if(!wordFreq.containsKey(Arr.get(i).toString())){
				wordFreq.put(Arr.get(i),1);
				
			}
			
			//else increment the value of the word within the map.
			else{
				wordFreq.put(Arr.get(i), wordFreq.get(Arr.get(i)) + 1);
			}
		}
		
		
		
		//////////////////////////////////////////////////////////////////////
		//if you want to implement this using quadratic probing,
		//comment out all the code above in the method HashTable and uncomment the code below
		
		//ANALYSIS
		/*for this assignment, quadratic probing isn't very good and the run time is very long.
		I believe it is because as we are searching for a position in the map to place our word,
		there isn't always an open position. Using this collision handling method
		we may need to perform a complete rehash. I came to this conclusion because this method
		works great if you increase the size of the map and the histogram of probes is more right skewed than
		if you do linear probing, meaning the hashmap is able to find the correct location a-lot sooner using quadratic hashing.
		But for this particular assignment, Linear probing is the better option because of the number of words and 
		the small size of the map.
		*/
		
		/*
		for(int i = 0; i<Arr.size(); i++){
			
			//if the word is not in the map, place the word in the map
			if(!wordFreq.containsKey(Arr.get(i).toString())){
				wordFreq.quadraticPut(Arr.get(i),1);
				
			}
			
			//else increment the value of the word within the map.
			else{
				wordFreq.quadraticPut(Arr.get(i), wordFreq.quadraticGet(Arr.get(i)) + 1);
			}
		}
		
		*/
		
	}
	
	public void HashTabletest(ArrayList<String> Arr){
		
		//for counting frequencies there will be many buckets,
		//but once I get to building the tree, I will cut the bucket size down
			
		for(int i = 0; i<Arr.size(); i++){
			
			//if the word is not in the map, place the word in the map
			if(!testmap.containsKey(Arr.get(i).toString())){
				testmap.put(Arr.get(i),1);
					
			}
			
			//else increment the value of the word within the map.
			else{
				testmap.put(Arr.get(i), testmap.get(Arr.get(i)) + 1);
			}
		}
		//count how many words are stored in the map
		//System.out.println(wordFreq.toString());
		
	}
	
	
	
	public Node treebuilder(ArrayList<Node> nodeArr){
		
		while(nodeArr.size()>2){
			Collections.sort(nodeArr);	
				
			Node element = new Node(nodeArr.remove(0), nodeArr.remove(1));
			
			nodeArr.add(element);
			}
			
			//the last two nodes do not get merged in the above while loop so I hard merge them 
			//and create the root;
			//if there is an error, it may be caused by this 
			Node root = new Node(nodeArr.remove(0), nodeArr.remove(0));
		
		return root;
	}
	
	
	public void Treetraversal(Node root, StringBuilder path){
		if(root == null){
			return;
		}
		
		else if(root.left == null && root.right == null){
			//root.NodeString(); //prints the node
			//System.out.println(path.toString()); //print the traversals
			root.path = path.toString(); //converts the path to a string
			
			//places the codes into the code map
			codes.put(root.getString(), root.path);
			
		}
		path.append('0');
		Treetraversal(root.left, path);
		path.deleteCharAt(path.length() - 1);
		
		path.append('1');
		Treetraversal(root.right, path);
		path.deleteCharAt(path.length() - 1);	
	}
	
	public void encode(ArrayList<String> Arr){
		
		StringBuilder Binary = new StringBuilder(); 
		
		for(int i =0; i<Arr.size(); i++){
			
			Binary.append(codes.get(Arr.get(i)));
		}
		
		this.bits = Binary.toString();
		
		StringBuilder str = new StringBuilder();
		
		
		 //this compresses the file
		for(int i =0; i< bits.length()/8; i++){
			
			int a = Integer.parseInt(bits.substring(8*i, (i+1)*8),2);
			str.append((char)a);
		}
		
		bits = str.toString();
		
		//out2.print(bits); //compresses the string into their equivalent character
		
		/*
		//the corresponding ones and zeros mapped will be appended to the string Binary, based on the character key
		//within the message.
		for(int i = 0; i< message.length(); i++){
			index = message.charAt(i);
			if(codes.get(index) != null){ //if there is no mapping for the key, there will be no appending
			Binary.append(codes.get(index));
			}
		}
		
		this.bits = Binary.toString();
		StringBuilder str = new StringBuilder();
		
		
		 //this compresses the file
		for(int i =0; i< bits.length()/8; i++){
			
			int a = Integer.parseInt(bits.substring(8*i, (i+1)*8),2);
			str.append((char)a);
		}
		
		bits = str.toString();
		
		out2.print(bits); //compresses the string into their equivalent character.
	
		*/
		
	}	
}
