import java.lang.*;
import java.util.*;
import java.io.*;

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

class Test{
	public static void main(String[] args) {
		CodeValidator validator = new CodeValidator();

		try{
			CLS.main(args);

			validator.toggleDebug();

			validator.openFile("mpa1.in");

			// int i = 0 + 9;

			// ArrayList<String> reservedWords = new ArrayList<String>(Arrays.asList( "int" , "double" , "char" , "float"));
			// String test = "int";

			// System.out.println(reservedWords.contains(test));		

			// String trial = "aha";

			// System.out.println(validator.machine.varChecker("man123"));
			// System.out.println(validator.machine.constChecker("123a"));
			// System.out.println(validator.machine.clean("int x++ +,y,z=10;"));

			/*Scanner tester = new Scanner("int x, y, z,a = 10;");
			tester.useDelimiter("(,?\\s|,)");*/

			// Scanner tester = new Scanner(validator.machine.clean("int x, y, z = 10;"));
			// tester.useDelimiter("\\s+");

			// while(tester.hasNext())
			// 	System.out.println(tester.next());

			validator.parseFile();

			validator.outFile();

			// System.out.println(validator.openFile("mpa2.in"));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}

class MainMenu{
	public static void main(String[] args) {
		CodeValidator validator = new CodeValidator();

		boolean exit = false;

		while(exit == false){
			System.out.print("\nFileName: ");
			Scanner inputStream = new Scanner(System.in);
			String input = inputStream.nextLine();

			try{
				CLS.main(args);

				switch (input){
					case "debug()":
						validator.toggleDebug();
						break;

					default:
						validator.openFile(input);

						validator.parseFile();
						validator.outFile();

						exit = true;
						break;
				}

			}
			catch( Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
}

public class CodeValidator {
	public String[] response = {		//reponse based on mode, should follow { identity [0/2/4] + validity [0/1] }
		"valid variable declaration",		//response[0]
		"invalid variable declaration",		//response[1]
		"valid function declaration",		//response[2]
		"invalid function declaration",		//response[3]
		"valid function definition",		//response[4]
		"invalid function definition"		//response[5]
	};

	public StateMachine machine;

	public File currentFile;				//File being parsed
	public String fileHandle;				//File name

	// public ArrayList<String> variableList;		//Variable list for the whole code [includes function identifiers]
	// public ArrayList<String> tempList;			//Variable list within function definition



	private boolean debug = false;		//enables debug mode

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void main(String[] args) {
		MainMenu.main(args);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public CodeValidator(){
		/*
		
		Constructor code here

		*/

		// variableList = new ArrayList<String>();
		// tempList = new ArrayList<String>();

		machine = new StateMachine();
	}

	public void toggleDebug(){
		if (debug){
			System.out.println("Debug-Mode Turned off.");
			debug = false;
		}
		else{
			System.out.println("Debug-Mode Turned on.");
			debug = true;
		}

		machine.toggleDebug();

		return;
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

		if(debug){
			System.out.println("\nSection - [ParseFile]");
		}

		if(fileHandle == null)
			throw new NonExistentFileException();
		if(currentFile == null)
			throw new NonExistentFileException();

		String fileContents = "";

		FileReader reader;
		try{
			reader = new FileReader(currentFile);
		}
		catch (Exception e){
			throw new NonExistentFileException(fileHandle);
		}


		ArrayList<String> lines = new ArrayList<String>();

		// Scanner lineSplitter = new Scanner(fileContents);
		Scanner lineSplitter = new Scanner(reader);
		lineSplitter.useDelimiter("");

		String currentLine = "";
		boolean semiIgnore = false;

		while(lineSplitter.hasNext()){
			String character = lineSplitter.next();

			currentLine += character;

			if(character.equals("{"))
				semiIgnore = true;

			if(!semiIgnore && character.equals(";")){
				lines.add(currentLine);
				currentLine = new String("");
			}
			else if (character.equals("}")){
				lines.add(currentLine);
				currentLine = new String("");

				semiIgnore = false;
			}
		}

		if (debug){										//For Debugging/Tracking purposes
			System.out.println("\nInside ArrayList 'Lines':");

			for(int i = 0; i < lines.size(); i++){
				System.out.println("\tLine " + (i + 1) + ": " + lines.get(i));
			}
		}

		lineSplitter.close();

		machine.setInput(lines);
		machine.evaluate();

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

			ArrayList<Integer> outResponse = machine.respond();
			String content = "";

			for(int i = 0; i < outResponse.size(); i++){
				content += (response[outResponse.get(i)] + "  ");
			}			

			fw = new FileWriter(outFile);
			bw = new BufferedWriter(fw);

			String[] contentLines = content.split("  ");

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


		if(debug){
			System.out.println();
		}
	}

}

class StateMachine {
	public ArrayList<String> variableList;		//Variable list for the whole code [includes function identifiers]
	public ArrayList<String> tempList;			//Variable list within function definition

	public ArrayList<String> input;				//Contents from file parsing
	public ArrayList<Integer> response;			//

	private ArrayList<String> reservedWords = new ArrayList<String>(Arrays.asList( "int" , "double" , "char" , "float"));
	private ArrayList<String> operators = new ArrayList<String>(Arrays.asList( "+" , "-" , "/" , "*" , "%"));


	public boolean debug = false;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public StateMachine(){
		response = new ArrayList<Integer>();
		variableList = new ArrayList<String>();
		tempList = new ArrayList<String>();
	}

	public void setInput(ArrayList<String> content){
		input = content;
	}

	public void toggleDebug(){
		if (debug){
			// System.out.println("Debug-Mode Turned off.");
			debug = false;

			return;
		}

		// System.out.println("Debug-Mode Turned on.");
		debug = true;

		return;
	}

	public void evaluate(){
		for(int i = 0; i < input.size(); i++){
			String current = input.get(i);

			if(current.contains("{")){
				functionDef(current);
			}
			else if(current.contains("(")){
				prototypeDef(current);
			}
			else
				variableDef(current);
		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean constChecker(String constant){
		String numeric = "0123456789";

		Scanner parser = new Scanner(constant);
		parser.useDelimiter("");

		while(parser.hasNext()){
			String current = parser.next();

			if(!numeric.contains(current))
				return false;
		}

		parser.close();

		return true;
	}

	public boolean varChecker(String var) {
		String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		String numeric = "0123456789";

		Scanner parser = new Scanner(var);
		parser.useDelimiter("");

		for( boolean start = true; parser.hasNext() ; ){
			String current = parser.next();

			if(start){
				if(numeric.contains(current))
					return false;

				start = false;
			}

			if(!alpha.contains(current) && !numeric.contains(current))
				return false;
		}


		parser.close();

		return true;
	}

	public String clean (String target){
		String whitelist = ",+-/*%=(){}[];";


		String cleaned = "";

		for(int i = 0; i < target.length() ; i++){
			String current = target.charAt(i) + "";

			if(whitelist.contains(current)){
				if(current.contains("+") || current.contains("-")){
					if (current.contains (target.charAt(i+1) + "") ){
						i++;

						cleaned += (" " + current + current + " ");
					}
					else
						cleaned += (" " + current + " ");
				}
				else
					cleaned += (" " + current + " ");
			}
			else
				cleaned += current;
		}

		return cleaned;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void variableDef(String testCase){	//Get here if none was found
		if(debug){
			System.out.println("\nSection - [VariableDef]");
		}


		Scanner parser = new Scanner(clean(testCase));
		parser.useDelimiter("\\s+");

		boolean isChar = false;

		int state = 0;

		String last = "";

		while(parser.hasNext()){
			String current = parser.next();

			if(debug){
				System.out.println("\tCurrent: " + current);
				System.out.println("\tState: " + state);

				if(current == null)
					System.out.println("\n\tYo wtf: " + last);
			}

			if (state == 0){
				if(reservedWords.contains(current))
					state = 1;
				else
					break;
			}

			else if (state == 1){
				if(varChecker(current)){
					if (variableList.contains(current))
						break;
					else{
						variableList.add(current);
						state = 2;
					}
				}
				else
					break;
			}

			else if (state == 2){
				if(current.equals(";")){
					state = 9;
					break;
				}
				else if(current.equals(",")){
					state = 5;
				}
				else if(current.equals("=")){
					state = 4;
				}
				else if(operators.contains(current)){
					state = 7;
				}
				else
					break;
			}

			else if (state == 3){
				if(current.equals(";")){
					state = 9;
					break;
				}
				else if(current.equals(",")){
					state = 5;
				}
				else if(operators.contains(current)){
					state = 7;
				}
				else if(current.equals("++") || current.equals("--")){
					if(last.equals("++") || last.equals("--"))
						break;
					else
						state = 8;
				}
				else
					break;
			}

			else if (state == 4){
				if(varChecker(current)){
					if(variableList.contains(current)){
						state = 3;
					}
					else
						state = 2;
				}
				else if(constChecker(current)){
					state = 6;
				}
				else
					break;
			}

			else if (state == 5){
				if(varChecker(current)){
					if(variableList.contains(current))
						break;
					else
						state = 2;
				}
				else
					break;
			}

			else if (state == 6){
				if(operators.contains(current)){
					state = 7;
				}
				else if(current.equals(",")){
					state = 5;
				}
				else if(current.equals(";")){
					state = 9;
					break;
				}
				else
					break;
			}

			else if (state == 7){
				if(varChecker(current)){
					if(variableList.contains(current))
						state = 3;
					else
						break;
				}
				else if(constChecker(current)){
					state = 6;
				}
				else
					break;
			}

			else if (state == 8){
				if(varChecker(current)){
					if(variableList.contains(current))
						state = 3;
					else
						break;
				}
			}

			else if (state == 9){
				break;
			}

			last = current;

		}

		if(state == 9)
			response.add(0);
		else
			response.add(1);

		parser.close();
	}

	public void prototypeDef(String testCase){	//Get here if "(" is found
		Scanner parser = new Scanner(clean(testCase));
		parser.useDelimiter("\\s+");

		tempList = new ArrayList<String>();

		if(debug){
			System.out.println("\nSection - [PrototypeDef]");
		}

		int state = 0;

		String last = "";

		while(parser.hasNext()){
			String current = parser.next();

			if(debug){
				System.out.println("\tCurrent: " + current);
				System.out.println("\tState: " + state);
			}

			if ( state == 0 ) {
				if(reservedWords.contains(current))
					state = 1;
				else
					break;
			}

			else if ( state == 1 ) {
				if(varChecker(current))
					state = 2;
				else
					break;
			}

			else if ( state == 2 ) {
				if(current.equals("("))
					state = 3;
				else
					break;
			}

			else if ( state == 3 ) {
				if(reservedWords.contains(current))
					state = 4;
				else if(current.equals(")"))
					state = 7;
				else
					break;
			}

			else if ( state == 4 ) {
				if(varChecker(current)){
					if(tempList.contains(current)){
						break;
					}
					else{
						state = 5;
						tempList.add(current);
					}
				}
				else
					break;
			}

			else if ( state == 5 ) {
				if(current.equals(",")){
					state = 6;
				}
				else if(current.equals(")")){
					state = 7;
				}
				else
					break;
			}

			else if ( state == 6 ) {
				if(varChecker(current)){
					if(tempList.contains(current)){
						break;
					}
					else{
						state = 4;
						tempList.add(current);
					}
				}
				else
					break;
			}

			else if ( state == 7 ) {
				if(current.equals(";"))
					state = 8;
				else
					break;
			}

			else if ( state == 8 ) {
				break;
			}

			last = current;
		}

		if(state == 8){
			response.add(2);
		}
		else{
			response.add(3);
		}

		parser.close();

	}

	public void functionDef(String testCase){	//Get here if "{" is found
		Scanner parser = new Scanner(clean(testCase));
		parser.useDelimiter("\\s+");

		tempList = new ArrayList<String>();

		if(debug){
			System.out.println("\nSection - [FunctionDef]");
		}

		int state = 0;

		String last = "";
		boolean nonVoid = false;

		while(parser.hasNext()){
			String current = parser.next();

			if(debug){
				System.out.println("\tCurrent: " + current);
				System.out.println("\tState: " + state);
			}

			if (state == 0) {
				if(reservedWords.contains(current) || current.equals("void")){
					state = 1;

					if(!current.equals("void"))
						nonVoid = true;
				}
				else
					break;
			}

			else if (state == 1) {
				if(varChecker(current)){
					state = 2;
				}
				else
					break;
			}

			else if (state == 2) {
				if(current.equals("(")){
					state = 3;
				}
				else
					break;
			}

			else if (state == 3) {
				if(reservedWords.contains(current)){
					state = 4;
				}
				else if(current.equals(")")){
					state = 7;
				}
				else
					break;
			}

			else if (state == 4) {
				if (varChecker(current)){
					if(tempList.contains(current)){
						break;
					}
					else{
						tempList.add(current);
						state = 5;
					}
				}
				else
					break;
			}

			else if (state == 5) {
				if(current.equals(",")){
					state = 6;
				}
				else if(current.equals(")")){
					state = 7;
				}
				else
					break;
			}

			else if (state == 6) {
				if( reservedWords.contains(current))
					state = 4;
				else
					break;
			}

			else if (state == 7) {
				if(current.equals("{")){
					state = 8;
				}

				break;
			}

			else if (state == 8) {
				break;
			}

			last = current;
		}

		// tempList = new ArrayList<String>();
		int tempState = 0;

		if(state == 8){
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TOKENIZER~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
			if(debug){
				System.out.println("\nSection - [TOKENIZER]");
			}

			ArrayList<String> lines = new ArrayList<String>();

			// Scanner parser = new Scanner(fileContents);
			parser.useDelimiter("");
	
			String currentLine = "";
	
			while(parser.hasNext()){
				String character = parser.next();
	
				currentLine += character;
	
				if(character.equals(";")){
					lines.add(clean(currentLine));
					currentLine = new String("");
				}
				else if (character.equals("}")){
					lines.add(clean(currentLine));
					currentLine = new String("");
				}
			}
	
			if (debug){										//For Debugging/Tracking purposes
				System.out.println("\nInside ArrayList 'Lines':");
	
				for(int i = 0; i < lines.size(); i++){
					System.out.println("\tLine " + (i + 1) + ": " + lines.get(i));
				}
			}

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TOKENIZER~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~VALIDATOR~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

			for(int i = 0; i < lines.size() ; i++){
				String currentTemp = lines.get(i);		//Current Line for Temp

				// tempList = new ArrayList<String>();

				if(debug){
					System.out.println("Current Line: " + currentTemp);
				}

				if(currentTemp.contains("}")){
					state = 9;
					break;
				}
				else{
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~STATE MACHINE~~~~~~~~~~~~~~~~~~~~~~~~~~~//

					Scanner tempParser = new Scanner(currentTemp);
					parser.useDelimiter("\\s+");

					tempState = 0;
					last = "";

					boolean retIn = false;		//Return was invoked

					while(tempParser.hasNext()){
						String theParsed = tempParser.next();

						if(debug){
							System.out.println("\tCurrent: " + theParsed);
							System.out.println("\tState: " + tempState);
						}


						if (tempState == 0) {
							if(reservedWords.contains(theParsed)){
								tempState = 1;
							}
							else if(theParsed.equals("return")){
								tempState = 2;
							}
							else if(tempList.contains(theParsed)){
								tempState = 4;
							}
							else
								break;
						}

						else if (tempState == 1) {
							if(varChecker(theParsed)){
								if(tempList.contains(theParsed)){
									break;
								}
								else
									tempState = 3;
							}
							else
								break;
						}

						else if (tempState == 2) {
							retIn = true;

							if(debug){
								System.out.println("\tNonVoid: " + nonVoid);
								System.out.println(tempList.contains(theParsed));
							}

							if(nonVoid){
								if(tempList.contains(theParsed)){
									tempState = 4;
								}
								else
									break;
							}
							else{
								if(theParsed.equals(";")){
									tempState = 10;
									break;
								}
								else
									break;
							}
						}

						else if (tempState == 3) {
							if(theParsed.equals("=")){
								tempState = 5;
							}
							else if(theParsed.equals(";")){
								tempState = 10;
								break;
							}
							else
								break;
						}

						else if (tempState == 4) {
							if(theParsed.equals("=")){
								tempState = 5;
							}
							else if(theParsed.equals(",")){
								if(retIn)
									break;
								else
									tempState = 6;
							}
							else if(theParsed.equals("++") || theParsed.equals("--")){
								if(last.equals("++") || last.equals("--"))
									break;
								else
									tempState = 7;
							}
							else if(operators.contains(theParsed)){
								tempState = 10;
								break;
							}
							else
								break;
						}

						else if (tempState == 5) {
							if(tempList.contains(theParsed)){
								tempState = 4;
							}
							else if(constChecker(theParsed)){
								tempState = 8;
							}
							else if(theParsed.equals(";")){
								tempState = 10;
								break;
							}
							else
								break;
						}

						else if (tempState == 6) {
							if(!tempList.contains(theParsed)){
								tempState = 3;
							}
							else
								break;
						}

						else if (tempState == 7) {
							if(tempList.contains(theParsed)){
								tempState = 4;
							}
							else if(theParsed.equals(",")){
								if(retIn)
									break;
								else{
									tempState = 6;
								}
							}
							else if(operators.contains(theParsed)){
								tempState = 9;
							}
							else if(theParsed.equals(";")){
								tempState = 10;
								break;
							}
							else
								break;
						}

						else if (tempState == 8) {
							if(operators.contains(theParsed)){
								tempState = 9;
							}
							else if(theParsed.equals(";")){
								tempState = 10;
								break;
							}
							else
								break;
						}

						else if (tempState == 9) {
							if(tempList.contains(theParsed)){
								tempState = 4;
							}
							else if(constChecker(theParsed)){
								tempState = 8;
							}
							else
								break;
						}

						else if (tempState == 10) {
							break;
						}


						last = theParsed;
					}

					if(tempState != 10)
						break;
					else
						state = 9;

					//~~~~~~~~~~~~~~~~~~~~~~~~~~~STATE MACHINE~~~~~~~~~~~~~~~~~~~~~~~~~~~//
				}

			}

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~VALIDATOR~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
		}

		if(state != 9){
			response.add(5);
		}
		else
			response.add(4);

		parser.close();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public ArrayList<Integer> respond(){
		return response;
	}
}