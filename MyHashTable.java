//Raymond Valdez
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class MyHashTable<K,V> {
	
	//PrintStream out3 = new PrintStream(new FileOutputStream("MyTrace.txt")); //displays my stats 
	
	int maxProbe = 0;
	int probecount = 0;
	double avgprobe = 0;
	int capacity;
	int[] probestat;
	int numEntries;
	K keys;
	V values;
	
	Bucket<K,V>[] Block; 
	
	//MyHashTable<String, Integer> wordFreq = new MyHashTable<>(100);
	//Bucket<K,V>[] Block = new Bucket[capacity];
	//MyHashTable<String, Integer> wordFreq;
	//ArrayList<Bucket<K,V>> Buckets = new ArrayList<>();
	//ArrayList<Node> nodeArr = new ArrayList();

	MyHashTable(int capacity){
		
		this.capacity = capacity;
		
		//this block can be changed to nodes
		Block = new Bucket[capacity];
		probestat = new int[capacity];
		
		//creates a hash table with capacity number of buckets (2^15 = 32768)
		//K is the type of keys
		//V is the type of values
		
	}
	
	void put(K searchKey, V newValue){
		
		int numProbe = 0;
		
		//get the hashcode of the Key
		int index = hash(searchKey);
		
		//Create the bucket thats holding the key and the value
		Bucket<K,V> newBuc = new Bucket<>(searchKey, newValue);
		
		while(true){
			
			if(Block[index] == null || Block[index].getKey().equals(searchKey)){
				
				if(Block[index] == null){
				probestat[numProbe]++;
				}
				
				Block[index] = newBuc;
				if(numProbe > maxProbe)
				maxProbe = numProbe;
				
				break;
			}
			
			else{
				index = (index + 1) % capacity;
				numProbe++;
				if(numProbe > maxProbe){
					maxProbe = numProbe;
				}
				
			}
		}
		
			
		//update or add the newValue to the bucket hash(SearchKey)
		//if has(key) is full, use linear probing to find the next available bucket
	}
		

	V get(K searchKey){
		
		int index = hash(searchKey);
		while (Block[index] != null){
			
			if(Block[index].getKey().equals(searchKey))
				return Block[index].getValue();
			else index++;
			
		}
	
		return null;
		
		//returns a value for the specified key from the bucket hash(searchKey)
		//if hash(searchKey) doesn't contain the value, use linear probing to find the appropriate value
	}
	
	boolean containsKey(K searchKey){
		if(get(searchKey) != null){
			return true;
		}
		
		return false;
	}
	
	void stats(){
		
		System.out.println("\nHash Table Stats \n=================");
		
		
		//displays the number of entries
		System.out.print("Number of Entries: ");
		for(int i = 0; i<capacity; i++){
			if(Block[i] != null){
				numEntries++;
			}
		}
		System.out.println(numEntries);
		
		//displays number of buckets
		System.out.println("Number of Buckets:" + capacity);
	
		
		//displays the histogram of probes
		System.out.print("Histogram of Probes:[" + probestat[0]); 
		 for(int i = 1; i<= maxProbe; i++){
		 System.out.print( "," + probestat[i]);
		 }
		 System.out.println("]");
		
		 
		 //displays fill percentage
		 System.out.println("Fill percentage ");  
		 System.out.printf("%.4f", (((double)numEntries/(double)capacity)*100));
		 System.out.println("%");
		 
		//displays the max probed
		System.out.println("Max Linear Prob: " + maxProbe);
		 
		
		//displays average probe
		for(int i = 0; i<probestat.length; i++){
			probecount = probecount + (probestat[i] * i);
		}
		
		avgprobe = (double)probecount/(double)numEntries;
		
		System.out.println("Average Linear Probe: " + avgprobe);
		
		
		//displays the statblock in the hashtable
		//this is incomplete
	}
	
	private int hash(K key){
		
		int hashCode = (Math.abs(key.hashCode())) % capacity;
		
		
		return hashCode;
		
	}
	
	public String toString(){
		
		StringBuilder Str = new StringBuilder();
		
		Str.append("[");
		
		
		for(int i = 0; i<Block.length; i++){
			if(Block[i] != null)
			Str.append("(" + Block[i].getKey().toString() + ", " + Block[i].getValue() + "), ");
		}
		
		Str.append("]");
		
		return Str.toString();
		
	}
	
	
	//Below is my implementation of this map but by using quadratic probing
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 for this assignment, quadratic probing isn't very good because 
	  
	  
	  
	 */
	
	void quadraticPut(K searchKey, V newValue){
		
		int numProbe = 0;
		
		//get the hashcode of the Key
		int index = hash(searchKey);
		int plus;
		
		//Create the bucket thats holding the key and the value
		Bucket<K,V> newBuc = new Bucket<>(searchKey, newValue);
		
		while(true){
			
			if(Block[index] == null || Block[index].getKey().equals(searchKey)){
				
				if(Block[index] == null){
				probestat[numProbe]++;
				}
				
				Block[index] = newBuc;
				if(numProbe > maxProbe)
				maxProbe = numProbe;
				
				break;
			}
			
			else{
				numProbe++;
				index = Math.abs(hash(searchKey) + ((int)Math.pow(2, numProbe))) % capacity;
				
				
				if(numProbe > maxProbe){
					maxProbe = numProbe;
				}
			}
		}	
	}
	
	
	V quadraticGet(K searchKey){
		
		int counter = 0;
		
		int index = hash(searchKey);
		while (Block[index] != null){
			
			if(Block[index].getKey().equals(searchKey)){
				return Block[index].getValue();
			}
				
			else{ 
				counter++;
				index = Math.abs(hash(searchKey) + ((int)Math.pow(2, counter))) % capacity;
				
			}
			
		}
	
		return null;
		
		//returns a value for the specified key from the bucket hash(searchKey)
		//if hash(searchKey) doesn't contain the value, use linear probing to find the appropriate value
	}
	
}	

