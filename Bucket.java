//Raymond Valdez
public class Bucket<K,V>{
	
	K keys;
	V values;
	
	
	Bucket(K key, V val){
		
		this.keys = key;
		this.values = val;
		
	}
	
	
	K getKey(){
		
		return keys;
	}
	
	
	V getValue(){
		
		return values;
	}

	public String BucketString(){
		
		
		StringBuilder Builder = new StringBuilder(keys + " " + values);
		
		
		return Builder.toString();
		
	}

}
