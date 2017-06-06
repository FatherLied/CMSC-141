import java.lang.*;
import java.io.*;
import java.util.Scanner;


//For Clearing Screens [needs to be in Try-Catch]
class CLS {
    public static void main(String... arg) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}

class NonExistentFileException extends Exception{
	public NonExistentFileException(String fileName){
		super(("File: \"" + fileName + "\" DOES NOT EXIST."));
	}

	public NonExistentFileException(){
		super(("File: null"));
	}
}

class InputOutOfBounds extends Exception{
	public InputOutOfBounds(String type){
		super(("Input Error: INVALID " + type.toUpperCase() + "."));
	}
}

public class Mandreza2 {
	public static void main(String[] args) {
		FileSet file = new FileSet();
		file.process("mpa2.in");

		System.out.println("My Code has trouble with Sets...");
	}
}

class Tester {
	public static void main(String[] args) {
		try{
			CLS.main(args);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		// Node<Integer> nodal = new Node<Integer>(15,new Node<Integer>(15,new Node<Integer>(15,new Node<Integer>(15))));
		
		/*Node<Integer> nodal = new Node<Integer>(15);*/

		// Node cast = nodal;

		/*Set<Integer> setter = new Set<Integer>(nodal);*/
			
		// setter.checkClass();
		// setter.checkNodeClass();
		
		/*setter.insert(new Node<Integer>(5));*/

		// setter.insert(new Node<Integer>(5,new Node<Integer>(5,new Node<Integer>(5,new Node<Integer>(5)))));

		/*setter.insert(10);

		Node<Integer> nody = new Node<Integer>(1,new Node<Integer>(10,new Node<Integer>(3,new Node<Integer>(5))));
		Set<Integer> set2 = new Set<Integer>(nody);*/

		// Set.toggleDebug();

		// Set<Set<Integer>> bigSet = new Set<Set<Integer>>();
		// bigSet.insert(setter);
		// bigSet.insert(set2);

		// System.out.println(bigSet);

		// bigSet.remove(set2);

		// System.out.println(bigSet);

		// System.out.println( "Size: " + setter.size() + "\t\t" + setter );
		// System.out.println( "Size: " + set2.size() + "\t\t" + set2 );

		// System.out.println("Removed: " + setter.remove(nodal));
		// System.out.println("Removed: " + setter.remove(15));

		// System.out.println(setter);

		/*Set<Integer> newSet = setter.union(set2);

		System.out.println(newSet);
		System.out.println(newSet.createSub(1,5));*/

		// System.out.println(setter.intersection(set2));
		// System.out.println(setter.difference(set2));

		/*Object object = new Integer(5);
		System.out.println(object.getClass());

		Set setSet = new Set<Set<Integer>>();

		setSet.insert(new Set<Integer>("{1,2,3}",1));
		setSet.insert(new Set<Integer>("{4,5,6}",1));
		setSet.insert(new Set<Integer>("{7,8,9}",1));

		System.out.println(setSet);

		int i = 2;
		System.out.println("Index " + i + ": " + (setSet.get(i).item).getClass());

		setSet.remove(new Set<Integer>("{6,5,4}",1));

		System.out.println(setSet);

		System.out.println();
		System.out.println();

		Set<Integer> first = new Set<Integer>(new Node<Integer>(1,new Node<Integer>(2,new Node<Integer>(3))));
		Set<Integer> second = new Set<Integer>(new Node<Integer>(2,new Node<Integer>(1,new Node<Integer>(3))));

		System.out.println(first + " vs. " + second);
		System.out.println(first.equals(second));
*/
		/*Set<Integer> stringSet = new Set<Integer>("1 2 3 4 5", 1);
		System.out.println(stringSet);

		stringSet.remove(3);

		System.out.println(stringSet);

		stringSet.insert(3);
		stringSet.insert(5);
		stringSet.insert(6);

		System.out.println(stringSet);*/

		/*IndexTable indices = new IndexTable(4);

		while(indices.valid()){
			System.out.println(indices);
			indices.nextSet();
		}*/

		// Set<Integer> newSet = new Set<Integer>("{1,2,3,4}",1);

		/*Node<Integer> node = new Node<Integer>(1, new Node<Integer>(2, new Node<Integer>(3,  new Node<Integer>(4))));
		Set<Integer> newSet = new Set<Integer>(node);

		System.out.println("Set is: " + newSet);

		System.out.println(newSet.powerSet());*/

		FileSet.toggleDebug();

		FileSet file = new FileSet();

		file.process("mpa2.in");
		
		/*try{
			file.openFile("mpa2.in");
			file.parseFile();

			file.routine();
		}
		catch(NonExistentFileException e){
			System.out.println(e.getMessage());
		}
		catch(InputOutOfBounds e){
			System.out.println(e.getMessage());
		}*/
		
	}
}

class FileSet {
	private String fileContents;
	private String outContents;

	public File currentFile;				//File being parsed
	public String fileHandle;				//File name

	private String pattern = "  ";			//Pattern for splitting

	private static boolean debug = false;

	public FileSet(){
		fileContents = "";
		outContents = "";
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println("FileSet: Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println("FileSet: Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void process(String handle){
		try{
			openFile(handle);
			parseFile();

			routine();

			outFile();
		}
		catch(NonExistentFileException e){
			System.out.println(e.getMessage());
		}
		catch(InputOutOfBounds e){
			System.out.println(e.getMessage());
		}

	}

	public void routine() throws InputOutOfBounds{
		if(debug){
			System.out.println("\nSection - [Routine]");
		}

		Set set1 = null;			//Readies Set to a Generic.
		Set set2 = null;

		Scanner parser = new Scanner(fileContents);
		String cast = parser.nextLine();

		int cases = Integer.parseInt(cast);

		if(debug)
			System.out.println("\tNo. of Cases: " + cases + "\n");


		for(int i = 0 ;cases > i; i++){
			cast = parser.nextLine();			//finds type via Scanner [hopefully]

			Scanner typeParser = new Scanner(cast);
			int type = typeParser.nextInt();	//type stored as int
			int secondaryType = 0;

			if(debug)
				System.out.println("\tCase " + (i+1) +": Type " + type);

			//Intialize sets
			switch (type){
				case 1:		//Integer
					set1 = new Set<Integer>();
					set2 = new Set<Integer>();
					break;

				case 2:		//Double
					set1 = new Set<Double>();
					set2 = new Set<Double>();
					break;

				case 3:		//Character
					set1 = new Set<Character>();
					set2 = new Set<Character>();
					break;

				case 4:		//String
					set1 = new Set<String>();
					set2 = new Set<String>();
					break;

				case 5:		//Set
					secondaryType = typeParser.nextInt();

					switch(secondaryType){
						case 1:		//Set of Integer
							set1 = new Set<Set<Integer>>();
							set2 = new Set<Set<Integer>>();
							break;

						case 2:		//Set of Double
							set1 = new Set<Set<Double>>();
							set2 = new Set<Set<Double>>();
							break;

						case 3:		//Set of Character
							set1 = new Set<Set<Character>>();
							set2 = new Set<Set<Character>>();
							break;

						case 4:		//Set of String
							set1 = new Set<Set<String>>();
							set2 = new Set<Set<String>>();
							break;

						case 6:		//Set of Object
							set1 = new Set<Set<Object>>();
							set2 = new Set<Set<Object>>();
							break;

						default:
							throw new InputOutOfBounds("type");
					}
					break;

				case 6:		//Object
					set1 = new Set<Object>();
					set2 = new Set<Object>();
					break;

				default:
					throw new InputOutOfBounds("type");

			}

			typeParser.close();		//closes type parser

			/*Fill Sets*/

			//Set 1
			Scanner setParser = new Scanner(parser.nextLine());
			setParser.useDelimiter(" ");

			switch (type){	//For set 1
			case 1:		//Integer
				while(setParser.hasNextInt()){
					set1.insert(setParser.nextInt());
				}
				break;

			case 2:		//Double
				while(setParser.hasNextDouble()){
					set1.insert(setParser.nextDouble());
				}
				break;

			case 3:		//Character
				while(setParser.hasNext()){
					set1.insert(setParser.next().charAt(0));
				}
				break;

			case 4:		//String
				while(setParser.hasNext()){
					set1.insert(setParser.next());
				}
				break;

			case 5:		//Set
				while(setParser.hasNext()){
					cast = setParser.next();

					switch(secondaryType){
						case 1:		//Set of Integer
							set1.insert(new Set<Integer>(cast, secondaryType ) );
							break;

						case 2:		//Set of Double
							set1.insert(new Set<Double>(cast, secondaryType ) );
							break;

						case 3:		//Set of Character
							set1.insert(new Set<Character>(cast, secondaryType ) );
							break;

						case 4:		//Set of String
							set1.insert(new Set<String>(cast, secondaryType ) );
							break;

						case 6:		//Set of Object
							set1.insert(new Set<Object>(cast, secondaryType ) );
							break;

						default:
							throw new InputOutOfBounds("type");
					}
					// set1.insert(new Set<>(setParser.next()) );
				}
				break;

			case 6:		//Object

				while(setParser.hasNext()){
					set1.insert(setParser.next());
				}
				break;

			default:
				throw new InputOutOfBounds("type");

			}	//End of Switch [Set 1]

			setParser.close();

			//Set 2
			setParser = new Scanner(parser.nextLine());
			setParser.useDelimiter(" ");

			switch (type){	//For set 2
			case 1:		//Integer
				while(setParser.hasNextInt()){
					set2.insert(setParser.nextInt());
				}
				break;

			case 2:		//Double
				while(setParser.hasNextDouble()){
					set2.insert(setParser.nextDouble());
				}
				break;

			case 3:		//Character
				while(setParser.hasNext()){
					set2.insert(setParser.next().charAt(0));
				}
				break;

			case 4:		//String
				while(setParser.hasNext()){
					set2.insert(setParser.next());
				}
				break;

			case 5:		//Set
				while(setParser.hasNext()){
					switch(secondaryType){
						case 1:		//Set of Integer
							set2.insert(new Set<Integer>(setParser.next(), secondaryType ) );
							break;

						case 2:		//Set of Double
							set2.insert(new Set<Double>(setParser.next(), secondaryType ) );
							break;

						case 3:		//Set of Character
							set2.insert(new Set<Character>(setParser.next(), secondaryType ) );
							break;

						case 4:		//Set of String
							set2.insert(new Set<String>(setParser.next(), secondaryType ) );
							break;

						case 6:		//Set of Object
							set2.insert(new Set<Object>(setParser.next(), secondaryType ) );
							break;

						default:
							throw new InputOutOfBounds("type");
					}
					// set1.insert(new Set<>(setParser.next()) );
				}
				break;

			case 6:		//Object

				while(setParser.hasNext()){
					set2.insert(setParser.next());
				}
				break;

			default:
				throw new InputOutOfBounds("type");

			}	//End of Switch [Set 2]

			setParser.close();

			if(debug){
				System.out.println("\tSet 1: " + set1);
				System.out.println("\tSet 2: " + set2);
				// System.out.println();
			}

			int operations = Integer.parseInt(parser.nextLine());

			if(debug)
				System.out.println("\tNo. of Operations: " + operations + "\n");

			for(int j = 0; j < operations; j++){
				cast = parser.nextLine();

				if(debug){
					System.out.println("\t\tOperation " + (j + 1) + ":");
					System.out.println("\t\tOrder: " + cast);
					// System.out.println();
				}

				Scanner opParser = new Scanner(cast);
				opParser.useDelimiter(" ");

				int action = Integer.parseInt(opParser.next());
				int source;
				int destination;

				if(debug)
					System.out.println("\t\t\tAction Type: " + action);

				switch(action){
					case 1:		//insert
						source = Integer.parseInt(opParser.next());
						/*
					
						Insert code here

						*/

						switch(type){
							case 1:		//Integer
								if(source == 1){
									set1.insert(opParser.nextInt());
								}
								else{
									set2.insert(opParser.nextInt());
								}

								break;

							case 2:		//Double
								if(source == 1){
									set1.insert(opParser.nextDouble());
								}
								else{
									set2.insert(opParser.nextDouble());
								}

								break;

							case 3:		//Char
								if(source == 1){
									set1.insert(opParser.next().charAt(0));
								}
								else{
									set2.insert(opParser.next().charAt(0));
								}

								break;

							case 4:		//String
								if(source == 1){
									set1.insert(opParser.next());
								}
								else{
									set2.insert(opParser.next());
								}

								break;

							case 5:		//Set
								if(debug)
									Set.toggleDebug();

								switch(secondaryType){
									case 1:		//Set of Integer
										if(source == 1){
											set1.insert(new Node<Set<Integer>>(new Set<Integer>(opParser.next(),secondaryType)));
										}
										else{
											set2.insert(new Node<Set<Integer>>(new Set<Integer>(opParser.next(),secondaryType)));
										}
										break;

									case 2:		//Set of Double
										if(source == 1){
											set1.insert(new Node<Set<Double>>(new Set<Double>(opParser.next(),secondaryType)));
										}
										else{
											set2.insert(new Node<Set<Double>>(new Set<Double>(opParser.next(),secondaryType)));
										}
										break;

									case 3:		//Set of Character
										if(source == 1){
											set1.insert(new Node<Set<Character>>(new Set<Character>(opParser.next(),secondaryType)));
										}
										else{
											set2.insert(new Node<Set<Character>>(new Set<Character>(opParser.next(),secondaryType)));
										}
										break;

									case 4:		//Set of String
										if(source == 1){
											set1.insert(new Node<Set<String>>(new Set<String>(opParser.next(),secondaryType)));
										}
										else{
											set2.insert(new Node<Set<String>>(new Set<String>(opParser.next(),secondaryType)));
										}
										break;

									case 6:		//Set of Object
										if(source == 1){
											set1.insert(new Node<Set<Object>>(new Set<Object>(opParser.next(),secondaryType)));
										}
										else{
											set2.insert(new Node<Set<Object>>(new Set<Object>(opParser.next(),secondaryType)));
										}
										break;

											default:
									throw new InputOutOfBounds("type");
								}	//End of Secondary Switch

								/*if(source == 1){
									set1.insert(new Set<>(opParser.next(),secondaryType));
								}
								else{
									set2.insert(new Set<>(opParser.next(),secondaryType));
								}*/

								break;

							case 6:		//Object
								if(source == 1){
									set1.insert(opParser.next());
								}
								else{
									set2.insert(opParser.next());
								}

								break;

							default:
								throw new InputOutOfBounds("type");
						}

						if(source == 1)
							outContents += (set1 + pattern);
						else
							outContents += (set2 + pattern);

						break;

					case 2:		//remove
						source = Integer.parseInt(opParser.next());
						/*
					
						Remove code here

						*/

						switch(type){
							case 1:		//Integer
								if(source == 1){
									set1.remove(opParser.nextInt());
								}
								else{
									set2.remove(opParser.nextInt());
								}

								break;

							case 2:		//Double
								if(source == 1){
									set1.remove(opParser.nextDouble());
								}
								else{
									set2.remove(opParser.nextDouble());
								}

								break;

							case 3:		//Char
								if(source == 1){
									set1.remove(opParser.next().charAt(0));
								}
								else{
									set2.remove(opParser.next().charAt(0));
								}

								break;

							case 4:		//String
								if(source == 1){
									set1.remove(opParser.next());
								}
								else{
									set2.remove(opParser.next());
								}

								break;

							case 5:		//Set
								if(debug){
									Set.toggleDebug();
									Node.toggleDebug();
								}

								switch(secondaryType){
									case 1:		//Set of Integer
										if(source == 1){
											set1.remove(new Node<Set<Integer>>(new Set<Integer>(opParser.next(),secondaryType)));
										}
										else{
											set2.remove(new Node<Set<Integer>>(new Set<Integer>(opParser.next(),secondaryType)));
										}
										break;

									case 2:		//Set of Double
										if(source == 1){
											set1.remove(new Node<Set<Double>>(new Set<Double>(opParser.next(),secondaryType)));
										}
										else{
											set2.remove(new Node<Set<Double>>(new Set<Double>(opParser.next(),secondaryType)));
										}
										break;

									case 3:		//Set of Character
										if(source == 1){
											set1.remove(new Node<Set<Character>>(new Set<Character>(opParser.next(),secondaryType)));
										}
										else{
											set2.remove(new Node<Set<Character>>(new Set<Character>(opParser.next(),secondaryType)));
										}
										break;

									case 4:		//Set of String
										if(source == 1){
											set1.remove(new Node<Set<String>>(new Set<String>(opParser.next(),secondaryType)));
										}
										else{
											set2.remove(new Node<Set<String>>(new Set<String>(opParser.next(),secondaryType)));
										}
										break;

									case 6:		//Set of Object
										if(source == 1){
											set1.remove(new Node<Object>(new Set<Object>(opParser.next(),secondaryType)));
										}
										else{
											set2.remove(new Node<Object>(new Set<Object>(opParser.next(),secondaryType)));
										}
										break;

											default:
									throw new InputOutOfBounds("type");
								}	//End of Secondary Switch

								/*if(source == 1){
									set1.remove(new Set(opParser.next(),secondaryType));
								}
								else{
									set2.remove(new Set(opParser.next(),secondaryType));
								}*/

								break;

							case 6:		//Object
								if(source == 1){
									set1.remove(opParser.next());
								}
								else{
									set2.remove(opParser.next());
								}

								break;

							default:
								throw new InputOutOfBounds("type");
						}

						if(source == 1)
							outContents += (set1 + pattern);
						else
							outContents += (set2 + pattern);

						break;

					case 3:		//subset
						outContents += (set1.subset(set2) + pattern);
						break;

					case 4:		//union
						outContents += (set1.union(set2) + pattern);
						break;

					case 5:		//intersection
						outContents += (set1.intersection(set2) + pattern);
						break;

					case 6:		//difference
						outContents += (set1.difference(set2) + pattern);
						break;

					case 7:		//powerset
						source = Integer.parseInt(opParser.next());

						if(source == 1)
							outContents += (set1.powerSet() + pattern);
						else
							outContents += (set2.powerSet() + pattern);

						break;

					default:
						throw new InputOutOfBounds("action");

				}	//End of Action Switch

				if(debug){
					System.out.println();
				}	

			}

			if(debug)
				System.out.println();
		}	//End of Case Routine Loop

		parser.close();

	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean openFile(String handle) throws NonExistentFileException{
		fileHandle = null;
		currentFile = null;
		
		File file = new File(handle);

		if(!file.exists()){
			throw new NonExistentFileException(handle);
		}

		fileHandle = handle;
		currentFile = file;

		return true;
	}

	public void parseFile() throws NonExistentFileException{
		// boolean disable = true;

		if(debug){
			System.out.println("\nSection - [ParseFile]");
		}

		if(fileHandle == null)
			throw new NonExistentFileException();
		if(currentFile == null)
			throw new NonExistentFileException();

		fileContents = "";

		FileReader reader;
		try{
			reader = new FileReader(currentFile);
		}
		catch (Exception e){
			throw new NonExistentFileException(fileHandle);
		}

		Scanner parser  = new Scanner(reader);
		// parser.useDelimiter("\n");

		while(parser.hasNextLine()){
			fileContents += (parser.nextLine() + "\n");
		}

		parser.close();

		if(debug){
			System.out.println("File Content:\n" + fileContents);
		}

		/*

		Write parsing code here

		*/
	}

	public void outFile() throws NonExistentFileException{
		if(debug){
			System.out.println("\nSection - [OutFile]");
			System.out.println("\tHandle is: " + fileHandle);
		}
			

		if(fileHandle == null)
			throw new NonExistentFileException();
		if(currentFile == null)
			throw new NonExistentFileException();

		// Scanner handleParser = new Scanner(fileHandle);
		Scanner handleParser = new Scanner(fileHandle);
		handleParser.useDelimiter("\\.");

		File outFile;

		String outHandle = "";

		if(!handleParser.hasNext()){
			// outHandle = "output.out";

			outFile = new File("output.out");
		}
		else{
			String current = handleParser.next();
			outHandle += current;

			if(debug){
				System.out.println("\tCurrent is: " + current);
			}

			while(handleParser.hasNext()){
				current = handleParser.next();

				if(handleParser.hasNext())
					outHandle += current;

				if(debug){
					System.out.println("\tCurrent is: " + current);
				}
			}

			outFile = new File((outHandle + ".out"));

			if (outFile.exists()) {
				for(int i = 1; outFile.exists(); i++){
					outFile = new File((outHandle + "(" + i + ").out"));
				}
			}
		}

		handleParser.close();

		try{
			outFile.createNewFile();
		}
		catch (Exception e){

		}

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(outFile);
			bw = new BufferedWriter(fw);

			String[] contentLines = outContents.split(pattern);

			for (String line: contentLines) {
    		    bw.write(line);
    		    bw.newLine();
    		}

			// bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
}

class Node<T> {
	public Object item;
	public Node<T> next;

	private static boolean debug = false;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Node(Object item){
		this.item = item;
		this.next = null;
	}

	public Node(Object item, Object next){
		this.item = item;
		this.next = new Node<T>(next);
	}

	public Node(Object item, Node<T> next){
		this.item = item;
		this.next = next;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public static void toggleDebug(){
		if (debug){
			System.out.println("Node: Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println("Node: Debug-Mode Turned on.");
		debug = true;

		return;
	}

	public Class<?> itemClass(){	//remove later
		// System.out.println(item.getClass());
		return item.getClass();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean equals(Node<T> other){
		if(debug){
			System.out.println("\nSection - [Node || Equals]");
			System.out.println("\tThis: " + this.item.getClass());
			System.out.println("\tOther: " + other.item.getClass());
		}
		return (this.item.equals(other.item));
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	/*public Object item(){
		return item;
	}

	public Node<T> next(){
		return next;
	}*/

	public String toString(){		//lets Node be outputted as a String
		if(this != null)
			return item.toString();
		else
			return "null";
	}

	public void isolate(){			//gets rid of next, isolating this into a single node
		this.next = null;
	}

	public Node<T> copy(){
		return new Node<T>(this.item);
	}
}

class Set<T> {
	private Node<T> head;
	private Node<T> tail;

	private int size = 0;

	private static boolean debug = false;

	public Class<?> setType;		//remove later

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Set(){					//Instantiates an empty set
		this.head = this.tail = null;	//sets both head and tail to null
		size = 0;						//for (un)necessary security
	}

	public Set(Node<T> head){		//Instantiates a set w/ contents
		// this.head = head;
		// size++;
		this();

		Node<T> pointer = head;

		while(/*pointer.next*/pointer != null){	//loops until there is no next
			this.insert(pointer.copy());

			pointer = pointer.next;
			// size++;
		}

		// this.tail = pointer;			//pointer should be the last element after loop
	}

	public Set(Set<T> other){
		// head = other.head();
		// tail = other.tail();

		// size = other.size();
		
		this();

		Node<T> pointer = other.head;

		for(;pointer != null;pointer = pointer.next){
			this.insert(pointer.copy());
		}
	}

	public Set(String string, int secondaryType){
		this();

		boolean isClosed = (string.contains("{") || string.contains("}"));

		if(isClosed)
			string = string.substring(1,(string.length() - 1));

		Scanner parser = new Scanner(string);
		if(isClosed)
			parser.useDelimiter(",");
		else
			parser.useDelimiter(" ");

		while(parser.hasNext()){
			switch(secondaryType){
				case 1:		//Set of Integer
					insert(new Node<T>(parser.nextInt()));
					break;

				case 2:		//Set of Double
					insert(new Node<T>(parser.nextDouble()));
					break;

				case 3:		//Set of Character
					insert(new Node<T>(parser.next().charAt(0)));
					break;

				case 4:		//Set of String
					insert(new Node<T>(parser.next()));
					break;

				case 6:		//Set of Object
					insert(new Node<T>(parser.next()));
					break;

				default:
					return;
			}
		}

		System.out.println(this);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println("Set: Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println("Set: Debug-Mode Turned on.");
		debug = true;

		return;
	}

	public void checkClass(){
		if(head != null){
			setType = head.itemClass();
			System.out.println(setType);
		}
	}

	public void checkNodeClass(){
		head.itemClass();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	//Insert and Remove Functions

	public boolean insert(Node<T> item){
		if(item == null)
			return false;

		item.isolate();

		if(!contains(item)){
			size++;				//increments size

			if(size == 0 || head == null || tail == null){
				head = tail = item;

				return true;
			}

			tail.next = item;	//adds item to the last of the list
			tail = item;		//sets item as new tail

			return true;
		}
		
		return false;
	}

	public Node<T> remove(Node<T> item){
		Node<T> pointer = head;
		Node<T> last = head;

		if(debug){
			System.out.println("\nSection - [Set || Remove]");
		}

		do{
			if(debug){
				System.out.println("Item is: " + item);
				System.out.println("Pointer is: " + pointer);
				System.out.println("Last is: " + last);
				System.out.println("Pointer.equals(Item) = " + pointer.equals(item));
				System.out.println(item.getClass());
			}

			if(pointer.item.equals(item.item)){
				if(pointer == head){	//should occur when item matches the head of the list
					head = head.next;	
				}
				else{
					if(pointer == tail)
						tail = last;

					last.next = pointer.next;
				}

				pointer.isolate();	//isolates the removed node
				size--;				//decrements size
				break;
			}

			last = pointer;
			pointer = pointer.next;

		}while(pointer != null);

		return pointer; //returns null if item is not found
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	//Insert and Remove Functions [Non-nodal Version]

	public boolean insert(T item){		//A version for a non-nodal item
		if(item == null)
			return false;

		Node<T> cast = new Node<T>(item);

		if(!contains(cast)){
			size++;				//increments size
			
			if(size == 0 || head == null || tail == null){
				head = tail = cast;

				return true;
			}

			tail.next = cast;	//adds item to the last of the list
			tail = cast;		//sets item as new tail

			return true;
		}
		
		return false;
	}

	public Node<T> remove(T item){
		Node<T> pointer = head;
		Node<T> last = head;
		Node<T> cast = new Node<T>(item);

		do{
			if(pointer.equals(cast)){
				if(pointer == head){	//should occur when item matches the head of the list
					head = head.next;	
				}
				else{
					if(pointer == tail)
						tail = last;

					last.next = pointer.next;
				}

				pointer.isolate();	//isolates the removed node
				size--;				//decrements size
				break;
			}

			last = pointer;
			pointer = pointer.next;

		}while(pointer != null);

		return pointer; //returns null if item is not found
	}

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//


	public boolean subset(Set<T> other){
		Node<T> pointer = other.head;

		if(other.size() == 0)
			return true;

		while(pointer != null){
			if(!this.contains(pointer))		//returns false if an element of other is not found in this
				return false;

			pointer = pointer.next;
		}

		return true;
	}

	public Set<T> union(Set<T> other){
		Set<T> out = this.clone();

		Node<T> pointer = other.head;

		while(pointer != null){
			out.insert(pointer.copy());
			pointer = pointer.next;
		}

		return out;
	}

	public Set<T> intersection(Set<T> other){
		if(debug){		//indicates start of debugging section
			System.out.println("\nSection - [Set || Intersection]");
		}

		Set<T> out = new Set<T>();
		Node<T> pointer = this.head;

		while(pointer != null){
			if(debug){
				System.out.println("Current is: "  + pointer);
				System.out.println("In Set: " + this.contains(pointer));
			}

			if(other.contains(pointer)){
				out.insert(pointer.copy());
			}

			pointer = pointer.next;
		}

		// System.out.println("Size: " + out.size());

		return out;
	}

	public Set<T> difference(Set<T> other){
		Set<T> out = this.clone();

		Node<T> pointer = other.head;

		while(pointer != null){
			out.remove(pointer.copy());

			pointer = pointer.next;
		}

		return out;
	}

	public Set<Set<T>> powerSet(){
		IndexTable indices = new IndexTable(size);
		Set<Set<T>> out = new Set<Set<T>>();

		while(indices.valid()){
			Node<T> pointer = this.head;
			Set<T> node = new Set<T>();

			for(int i = 0; i < size; i++, pointer = pointer.next){
				if(indices.get(i))
					node.insert(pointer.copy());
			}

			out.insert(node);
			indices.nextSet();
		}
		/*

		Write code here.

		*/
		return out;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	private Set<T> createSub(int start, int end){
		Set<T> out = new Set<T>();

		if(start < 0 || end < 0 || start > end)
			return out;

		Node<T> pointer = head;
		boolean found = false;

		for(int i = 0; i < (end + 1); i++){
			if(i == start)
				found = true;

			if(pointer != null){
				if(found){
					out.insert(pointer.copy());
				}
				
				pointer = pointer.next;
			}
		}

		return out;
	}

	public Node<T> get(int index){
		Node<T> pointer = head;

		for(int i = 0; i < index ; i++){
			if(i < size)
				pointer = pointer.next;
		}

		return pointer;
	}

	//Checks if LinkedList already has a similar Node
	private boolean contains(Node<T> item){
		Node<T> pointer = head;

		while(pointer != null){		//Parses LinkedList individually
			if (pointer.equals(item))
				return true;

			pointer = pointer.next;
		}

		return false;
	}

	public String toString(){		//For Printing
		boolean debug = (this.debug && false);

		if(debug){		//indicates start of debugging section
			System.out.println("\nSection - [Set || ToString]");
		}

		if(size == 0)
			return "empty";

		String out = "{";

		Node<T> pointer = head;

		while(pointer != null){		//Parses LinkedList individually
			out += pointer;

			if(pointer.next != null)
				out += ",";

			if(debug){				//For debugging/tracking
				System.out.println("Current: " + pointer);
				System.out.print("Next: ");
				if(pointer.next == null)
					System.out.println("null");
				else
					System.out.println(pointer.next);
			}

			pointer = pointer.next;
		}

		out+="}";


		if(debug){		//adds a line to isolate debugging section
			System.out.println();
		}

		return out;
	}

	public Set<T> clone(){
		Set<T> out = new Set<T>();
		Node<T> pointer = this.head;

		while(pointer != null){
			out.insert(pointer.copy());
			pointer = pointer.next;
		}

		return out;
	}

	public boolean equals(Set<T> other){

		if(debug){
			System.out.println("\nSection - [Set || Equals]");
			System.out.println("\tAyy! Lmao!");
		}

		if(this.size() != other.size())
			return false;

		Node<T> pointer = other.head;

		while(pointer != null){
			if(!this.contains(pointer))
				return false;
			pointer = pointer.next;
		}

		return true;
	}

	public Node<T> head(){
		return head;
	}

	public Node<T> tail(){
		return tail;
	}

	public int size(){
		return size;
	}
}

class IndexTable{
	private boolean table[];
	private int size;
	private int max;
	private int count;

	private boolean reset;

	public IndexTable(int size){
		if(size <= 0)
			size = 1;

		this.size = size;

		table = new boolean[size];
		 
		count = 0;
		max = 1;

		for(int i = 0; i < size; i++){
			table[i] = false;
			max *= 2;
		}

	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void nextSet(){
		boolean carry = false;

		for(int i = 0; i < size; i++){
			if(i == 0){
				if(table[i]){
					carry = true;
					table[i] = false;
				}
				else
					table[i] = true;
			}
			else{
				if(carry){
					if(table [i]){
						carry = true;
						table[i] = false;
					}
					else{
						table[i] = true;
						carry = false;
					}

				}
				else
					break;
			}
		}	//End of For Loop

		count++;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean valid(){
		if(count < max)
			return true;
		else
			return false;
	}

	public boolean get(int index){
		return table[index];
	}

	public int size(){
		return size;
	}

	public String toString(){		//Represents table in binary
		String out = "";

		for(int i = 0; i < size; i++){
			if(table[i])
				out += "1";
			else
				out += "0";
		}

		return out;
	}
}