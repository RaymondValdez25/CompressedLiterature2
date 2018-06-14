//Raymond Valdez
import java.io.*;
import java.util.*;

public class Main {
	
	//the main method outputs the map of codes into 'MyCodes'
	//and outputs the compressed file into 'MyEncoded'
	public static void main(String []args) throws IOException{
		
		
		long startTime = System.nanoTime();	
		long endTime = System.nanoTime();
		
		
		PrintStream out = new PrintStream(new FileOutputStream("MyCodes.txt")); //displays my map
		PrintStream out2 = new PrintStream(new FileOutputStream("MyEncoded.txt")); //displays my Encoded version
		
		//gets the file and stores it within a string
		File file = new File("WarAndPeace.txt");
		
		//File file = new File("War.txt"); //testing file
		
		
		Scanner scan = new Scanner(file);
		String content = scan.useDelimiter("\\Z").next();
		
		double initialSize = file.length() /1024;
		
		CodingTree(content, out, out2); //coding tree
		
		File outfile = new File("myEncoded.txt");
		
		
		double finalSize = outfile.length() /1024;
		
		
		////////////////////////////////////
		//testCodingTree(out, out2); //tester method for coding tree with a short simple phrase
		//comment out the above CodingTree method for this to work
		
		//testMyHashTable(); //tester method for testing the hash table with a short simple phrase
		///////////////////////////////////
		
		
		//display statistics of the program
		endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double milliseconds = totalTime/(Math.pow(10,6));
		System.out.println("\ninitial size: " + (initialSize + 1)); //the file won't display the correct size unless I add a 1 to it
		System.out.println("output size: " + (finalSize + 1)); //the file won't display the correct size unless I add a 1 to it
		System.out.println("ratio: " + (finalSize+1)/(initialSize+1) * 100 + "%");
		System.out.println("the code takes " + milliseconds + " milliseconds to complete");
		
	}
	
	static void CodingTree(String content, PrintStream out, PrintStream out2){
		
		//this is the old coding tree
		//CodingTree testTree = new CodingTree(content,out, out2);
		
		CodingTree testTree = new CodingTree(content);
		
		out.print(testTree.codes.toString());
		out2.print(testTree.bits);
		
	}
	
	//this tester method will test a short string inputted
	static void testCodingTree(PrintStream out, PrintStream out2){
		
	
		String Sally = "She se'llsAB the the thCDe the a wh-ole) buncEFh of  sea sea sea sea shells by the sea the shore.";
	
		
		CodingTree testTree = new CodingTree(Sally);
		
	}
	
	static void testMyHashTable(){
		
		MyHashTable<String, Integer> test = new MyHashTable<>(10);
		
		//testing my push method
		test.put("123", 1);
		test.put("344", 2);
		test.put("11233", 3);
		test.put("12442", 4);
		test.put("112A333", 5);
		test.put("1A232", 6);
		test.put("125B6", 7);
		test.put("1237676", 8);
		test.put("1278989", 9);
		
		test.put("Hello", 1); //first place the value
		System.out.println(test.toString()); //print the table
		
		test.put("Hello", test.get("Hello") + 10); //update the value using the 'get' method to update the value
		System.out.println(test.toString()); //print the table
		
		System.out.println(test.containsKey("Hello")); //is this key inside of the hash table? 
		
		test.stats(); //print the stats 
		
		
	}
	

}
