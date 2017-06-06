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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

class Tester{
	public static void main(String[] args) {
		/*int table[][] = {
			{ 1, 2, 3 },
			{ 4, 5, 6 },
			{ 7, 8, 9 }
		};


		System.out.println("State 2 -> Input 0: " + table[2][0]);*/

		// StringBuffer buffer = new StringBuffer();
		// char c = 'c';
		// buffer.append(c + ".");

		// System.out.println(buffer.toString());

		SyntaxChecker checker = new SyntaxChecker();
		VarDeclaration varDec = new VarDeclaration("Hello");
		FuncDefinition funcDef = new FuncDefinition();

		// checker.toggleDebug();
		// varDec.toggleDebug();
		// FuncDeclaration.toggleDebug();
		// FuncDefinition.toggleDebug();
		// Statements.toggleDebug();

		// System.out.println(funcDef.tokenize("void function(int ) {int x = a * a; return x;} int square(int b}{return b*b;}"));
		System.out.println(funcDef.evaluate("void function(int hug) {int a = 1; int x = a * a; return x;} int square(int b){return b*b;}"));
		// System.out.println(funcDef.evaluate("void function(int ) {int x = a * a; return x;} int square(int b}{return b*b;}"));

		// System.out.println(varDec.tokenize("+-+-") );
		// System.out.println(varDec.evaluate("int x, xy = x, z[] = {23.5,23,52};"));
		// System.out.println(VarDeclaration.verify("int x, xy = x, z[] = {23.5,23,52}; int a, ab = a, c[] = {23.5,23,52};"));

		// Statements.setReturnType("void");
		// System.out.println(Statements.verify("int x, xy = x, z[] = {23.5,23,52}; int a, ab = a, c[] = {23.5,23,52}; return;"));  //
		// System.out.println(FuncDeclaration.verify("void function(char jump);"));  //int auxialliary(int input);char coal(double dd);
		// System.out.println(varDec.validIdentifier("asda123"));
		// System.out.println(varDec.endStates());
		// System.out.println(checker.isAlphaNumeric('+'));

		// System.out.println(varDec.statement());
		// System.out.println(checker.statement());
	}

	public static String arrayToString( ArrayList array){
		String out = "";

		for(int i = 0; i < array.size() ; i++){
			out += (array.get(i)).toString();

			if(i+1 < array.size())
				out += ", ";
		}

		return out;
	}
}

public class Mandreza4{
	public static void main(String[] args) {
		try{
			CLS.main(args);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		FileHandler file = new FileHandler();

		file.process();
	}
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

class SyntaxChecker {
	protected int stateDiagram[][];
	protected ArrayList<Integer> validStates;
	protected int deadState;

	protected ArrayList<String> intList;
	protected ArrayList<String> doubleList;
	protected ArrayList<String> floatList;
	protected ArrayList<String> charList;

	protected ArrayList<String> intArrayList;
	protected ArrayList<String> doubleArrayList;
	protected ArrayList<String> floatArrayList;
	protected ArrayList<String> charArrayList;

	protected ArrayList<String> funcList;

	protected String statement;
	protected String lastType;

	protected char delimiter = '~';

	private static String className = "SyntaxChecker";
	private static boolean debug = false;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println(className + ": Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println(className + ": Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	SyntaxChecker(){
		intList = new ArrayList<String>();
		doubleList = new ArrayList<String>();
		floatList = new ArrayList<String>();
		charList = new ArrayList<String>();

		intArrayList = new ArrayList<String>();
		doubleArrayList = new ArrayList<String>();
		floatArrayList = new ArrayList<String>();
		charArrayList = new ArrayList<String>();

		validStates = new ArrayList<Integer>();

		lastType = "void";
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public String tokenize( String input ){
		StringBuffer tokens = new StringBuffer();
		// StringBuffer stringBuild = new StringBuffer();

		String whiteList = " ,(){}[]+-/*=;";

		for(int i = 0; i < input.length(); i++){
			char c = input.charAt(i);

			if (whiteList.indexOf(c) > -1) {
				if(tokens.length() > 0)
					tokens.append(delimiter);
			}
			else{
				if( i > 0 && whiteList.indexOf( tokens.charAt(tokens.length() - 1) ) > -1)
					tokens.append(delimiter);
			}

			tokens.append(c);
		}
		return tokens.toString();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	protected int inputType(int state, String input){
		System.out.println("You're not supposed to be here.");

		return 0;
	}

	protected boolean evaluate(String input){ //main routine
		String tokens = tokenize(input);

		int state = 0;

		Scanner parser = new Scanner(tokens);
		parser.useDelimiter(delimiter + "");

		for( ;parser.hasNext();){
			String cast = parser.next();
			// int inputType = inputType(cast);

			if(debug){
				System.out.println("Cast: " + cast);
				// System.out.println("Current State: " + state + " | Next: state[" + state + "][" + inputType + "]");
			}

			// state = stateDiagram[state][inputType];
			state = inputType(state,cast);

			if(state == deadState)
				break;
		}

		if(debug){
			System.out.println("Last State: " + state);

			System.out.println("Int Array: " + Tester.arrayToString(intArrayList));
			System.out.println("Int: " + Tester.arrayToString(intList));
		}

		parser.close();



		if(validStates.contains(state))
			return true;
		else
			return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean validIdentifier( String input ){
		boolean valid = false;
	
		if(debug){
			System.out.println("\tInput String: " + input);
		}

		for(int i = 0; i < input.length(); i++){
			char c = input.charAt(i);

			if(debug){
				System.out.println("\tC is: " + c);
			}

			if(i == 0){
				if(isNumeric(c))
					return false;
			}

			if(isAlpha(c))
				valid = true;

			if(!isAlphaNumeric(c))
				return false;
		}

		return valid;
	}

	protected boolean isAlphaNumeric( char c ){
		if( (isAlpha(c)) || (isNumeric(c)) || (c == '_') )
			return true;
		else
			return false;
	}

	protected boolean isAlpha( char c ){
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
			return true;
		else
			return false;
	}

	protected boolean isNumeric( char c){
		if( c >= '0' && c <= '9')
			return true;
		else
			return false;
	}

	protected boolean isDatatype( String datatype ){
		if(datatype.equals("int") || datatype.equals("double") || datatype.equals("float") || datatype.equals("char"))
			return true;
		else
			return false;
	}

	protected boolean isOperator( String operand ){
		if( operand.equals("+") ||  operand.equals("-") || operand.equals("*") ||  operand.equals("/") )
			return true;
		else
			return false;
	}

	protected boolean isNumber( String numeral ){
		boolean point = false;

		for(int i = 0; i < numeral.length(); i++){
			char c = numeral.charAt(i);

			if( c == '.'){
				if(!point)
					point = true;
				else	//should reach here if c is a '.' and one has been found
					return false;
			}
			else{
				if(!isNumeric(c))
					return false;
			}
		}

		return true;
	}

	protected boolean hasFloat( String numeral ){
		if(numeral.indexOf('.') > -1)
			return true;
		else
			return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	protected boolean isNewIdentifier( String identifier){
		if( intList.contains(identifier) || doubleList.contains(identifier) || floatList.contains(identifier) || charList.contains(identifier) ||
			intArrayList.contains(identifier) || doubleArrayList.contains(identifier) || floatArrayList.contains(identifier) )
			return false;
		else
			return true;
	}

	protected boolean isArrayIdentifier ( String identifier ){
		if(intArrayList.contains(identifier) || doubleArrayList.contains(identifier) || floatArrayList.contains(identifier))
			return true;
		else
			return false;
	}

	protected boolean addIdentifier( String identifier, String type){
		if( type.equals("int") ){
			intList.add(identifier);
			return true;
		}

		else if( type.equals("double") ){
			doubleList.add(identifier);
			return true;
		}

		else if( type.equals("float") ){
			floatList.add(identifier);
			return true;
		}

		else if( type.equals("char") ){
			charList.add(identifier);
			return true;
		}

		return false;
	}

	protected boolean convertToArray( String type ){
		if( type.equals("int") ){
			if(intList.size() <= 0)
				return false;

			intArrayList.add(intList.remove(intList.size() - 1));
			return true;
		}

		else if( type.equals("double") ){
			if(doubleList.size() <= 0)
				return false;

			doubleArrayList.add(doubleList.remove(doubleList.size() - 1));
			return true;
		}

		else if( type.equals("float") ){
			if(floatList.size() <= 0)
				return false;

			floatArrayList.add(floatList.remove(floatList.size() - 1));
			return true;
		}

		else if( type.equals("char") ){
			if(charList.size() <= 0)
				return false;

			charArrayList.add(charList.remove(charList.size() - 1));
			return true;
		}

		return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	protected void addStates(int[] array){
		for(int i = 0; i < array.length; i++){
			validStates.add(array[i]);
		}
	}

	public String endStates(){
		String out = "";

		for(int i = 0; i < validStates.size(); i++){
			out += validStates.get(i);

			if((i+1) < validStates.size())
				out += ", ";
		}

		return out;
	}

	public String statement(){
		return this.statement;
	}
}

/*Child Classes*/

class VarDeclaration extends SyntaxChecker{
	
	private static String className = "VarDeclaration";
	private static boolean debug = false;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println(className + ": Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println(className + ": Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	VarDeclaration(){
		super();

		int finalStates[] = {5};
		addStates(finalStates);

		deadState = 6;

		stateDiagram = new int[][]{
			/* input:   0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15*/
			/* q0*/	 {  0 ,  1 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 },
			/* q1*/	 {  1 ,  6 ,  2 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q2*/	 {  2 ,  6 ,  6 ,  6 ,  6 ,  6 ,  3 ,  1 ,  7 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 },
			/* q3*/	 {  3 ,  6 ,  6 ,  4 ,  4 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  4 ,  6 ,  6 },
			/* q4*/	 {  4 ,  1 ,  6 ,  6 ,  6 ,  6 ,  6 ,  1 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  3 },
			/* q5*/	 {  5 ,  1 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 },
			/* q6*/	 {  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q7*/	 {  7 ,  6 ,  6 ,  6 ,  8 ,  6 ,  6 ,  6 ,  6 ,  9 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q8*/	 {  8 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  9 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q9*/	 {  9 ,  6 ,  6 ,  6 ,  6 ,  6 , 10 ,  1 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 },
			/* q10*/ { 10 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 11 ,  6 ,  6 ,  6 ,  4 ,  6 },
			/* q11*/ { 11 ,  6 ,  6 ,  6 , 12 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 12 ,  6 ,  6 },
			/* q12*/ { 12 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 11 ,  6 ,  6 ,  6 ,  4 ,  6 ,  6 ,  6 ,  6 }
		};
	}

	VarDeclaration(String statement){
		this();

		this.statement = statement;
	}

	public static boolean verify(String expression){
		VarDeclaration varCheck = new VarDeclaration();

		return varCheck.evaluate(expression);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public int inputType(int state, String input){
		/*
		Overwrite inputType here

		Note:
			0	=	' '
			1	=	datatype
			2	=	new identifier
			3	=	old identifier
			4	=	constant
			5	=	invalid
			6	=	'='
			7	=	','
			8	=	'['
			9	=	']'
			10	=	'{'
			11	=	'}'
			12	=	';'
			13	=	float
			14	=	array var
			15	=	operator


		*/
		// System.out.println("Hi");
		if(input.equals("void")){

			if(debug){
				System.out.println("Call: DeadState");
			}

			return deadState;
		}

		if(input.equals(" ")){
			return stateDiagram[state][0];
		}
		else if(input.equals("=")){
			return stateDiagram[state][6];
		}
		else if(input.equals(",")){
			return stateDiagram[state][7];
		}
		else if(input.equals("[")){
			return stateDiagram[state][8];
		}
		else if(input.equals("]")){
			return stateDiagram[state][9];
		}
		else if(input.equals("{")){
			return stateDiagram[state][10];
		}
		else if(input.equals("}")){
			return stateDiagram[state][11];
		}
		else if(input.equals(";")){
			return stateDiagram[state][12];
		}

		else if(isOperator(input)){
			return stateDiagram[state][15];
		}
		else if(isNumber(input)){
			if(hasFloat(input))
				return stateDiagram[state][13];
			else
				return stateDiagram[state][4];
		}
		else if(isDatatype(input)){
			/*
			if(lastType.equals("void"))
				lastType = input;
			else
				return 5;
			*/
			lastType = input;
			return stateDiagram[state][1];
		}

		else if(validIdentifier(input)){
			if(isNewIdentifier(input)){
				// if(lastType.equals("void"))
					// return 5;

				if(!addIdentifier (input, lastType))
					return stateDiagram[state][5];
				else
					return stateDiagram[state][2];
			}
			else{
				if(isArrayIdentifier(input))
					return stateDiagram[state][14];
				else
					return stateDiagram[state][3];
			}
		}

		// return stateDiagram[state][5];
		return deadState;
	}
}

class FuncDeclaration extends SyntaxChecker{
	private static String className = "FuncDeclaration";
	private static boolean debug = false;

	protected ArrayList<String> funcList;

	private boolean funcFlag = true;
	private String funcType = "null";

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println(className + ": Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println(className + ": Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	FuncDeclaration(){
		super();

		int finalStates[] = {7};
		addStates(finalStates);

		funcList = new ArrayList<String>();

		deadState = 8;

		stateDiagram = new int[][]{
			/* input:   0    1    2    3    4    5    6    7    8    9   10 */
			/* q0*/	 {  0 ,  1 ,  8 ,  8 ,  1 ,  8 ,  8 ,  8 ,  8 ,  8 ,  7 },
			/* q1*/	 {  1 ,  8 ,  2 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q2*/	 {  2 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  3 ,  8 ,  8 },
			/* q3*/	 {  3 ,  4 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  6 ,  8 },
			/* q4*/	 {  4 ,  8 ,  5 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q5*/	 {  5 ,  8 ,  8 ,  8 ,  8 ,  8 ,  5 ,  3 ,  8 ,  6 ,  8 },
			/* q6*/	 {  6 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  7 },
			/* q7*/	 {  7 ,  1 ,  8 ,  1 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  7 },
			/* q8*/	 {  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 }
		};
	}

	FuncDeclaration(String statement){
		this();

		this.statement = statement;
	}

	public static boolean verify(String expression){
		FuncDeclaration funcCheck = new FuncDeclaration();

		return funcCheck.evaluate(expression);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	protected boolean isNewIdentifier( String identifier){
		if( intList.contains(identifier) || doubleList.contains(identifier) || floatList.contains(identifier) || charList.contains(identifier) ||
			intArrayList.contains(identifier) || doubleArrayList.contains(identifier) || floatArrayList.contains(identifier) || funcList.contains(identifier))
			return false;
		else
			return true;
	}

	protected boolean addFunctionIdentifier( String identifier ){
		if(!funcList.contains(identifier)){
			funcList.add(identifier);
			return true;
		}

		else
			return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public int inputType( int state, String input ){
		/*
		Overwrite inputType here

		Note:
			0	=	' '
			1	=	datatype
			2	=	new identifier
			3	=	old identifier
			4	=	void
			5	=	invalid
			6	=	'*'
			7	=	','
			8	=	'('
			9	=	')'
			10	=	';'

		*/
		if(input.equals(" ")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][0]");
			}

			return stateDiagram[state][0];
		}
		else if(input.equals("*")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][6]");
			}

			return stateDiagram[state][6];
		}
		else if(input.equals(",")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][7]");
			}

			return stateDiagram[state][7];
		}
		else if(input.equals("(")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][8]");
			}

			return stateDiagram[state][8];
		}
		else if(input.equals(")")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][9]");
			}

			return stateDiagram[state][9];
		}
		else if(input.equals(";")){

			if(debug){
				System.out.println("Call: Diagram[" + state + "][10]");
			}

			return stateDiagram[state][10];
		}

		// else if(isNumber(input)){
		// 	return stateDiagram[state][5];
		// }

		else if(isDatatype(input)){
			lastType = input;

			if(debug){
				System.out.println("Call: Diagram[" + state + "][1]");
			}

			return stateDiagram[state][1];
		}
		else if(input.equals("void")){
			lastType = input;

			if(debug){
				System.out.println("Call: Diagram[" + state + "][4]");
			}

			return stateDiagram[state][4];
		}

		else if(validIdentifier(input)){
			if(isNewIdentifier(input)){
				if(!addIdentifier (input, lastType)){
					if(state == 1 && lastType.equals("void")){
						if(!addFunctionIdentifier(input))
							return deadState;

						if(debug){
							System.out.println("Call: Diagram[" + state + "][2]"); //considered new identifier (function)
						}

						return stateDiagram[state][2];
					}
					else{
						if(debug){
							System.out.println("Call: Diagram[" + state + "][13]");
						}

						return stateDiagram[state][5];
					}
				}
				else{

					if(debug){
						System.out.println("Call: Diagram[" + state + "][2]");
					}

					return stateDiagram[state][2];
				}
			}
			else{

				if(debug){
					System.out.println("Call: Diagram[" + state + "][3]");
				}

				return stateDiagram[state][3];
			}
		}

		if(debug){
			System.out.println("Call: DeadState");
		}

		return deadState;
	}
}

class FuncDefinition extends SyntaxChecker{

	private static String className = "FuncDefinition";
	private static boolean debug = false;

	private ArrayList<String> funcList;

	private String returnType;
	private boolean inBody;

	private Statements innerMachine;


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println(className + ": Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println(className + ": Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	FuncDefinition(){
		super();

		int finalStates[] = {9};
		addStates(finalStates);

		funcList = new ArrayList<String>();
		innerMachine = new Statements();

		returnType = "null";
		inBody = false;

		deadState = 8;

		stateDiagram = new int[][]{
			/* input:   0    1    2    3    4    5    6    7    8    9   10   11   12   13 */
			/* q0*/	 {  0 ,  1 ,  8 ,  8 ,  1 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q1*/	 {  1 ,  8 ,  2 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q2*/	 {  2 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  3 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q3*/	 {  3 ,  4 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  6 ,  8 ,  8 ,  8 ,  8 },
			/* q4*/	 {  4 ,  8 ,  5 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q5*/	 {  5 ,  8 ,  8 ,  8 ,  8 ,  8 ,  5 ,  3 ,  8 ,  6 ,  8 ,  8 ,  8 ,  8 },
			/* q6*/	 {  6 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  7 ,  8 ,  8 },
			/* q7*/	 {  7 ,  8 ,  8 ,  8 ,  8 ,  7 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  9 ,  8 },
			/* q8*/	 {  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 },
			/* q9*/	 {  9 ,  1 ,  8 ,  8 ,  1 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 ,  9 ,  8 ,  8 }
		};
	}

	FuncDefinition(String statement){
		this();

		this.statement = statement;
	}

	public static boolean verify(String expression){
		FuncDefinition funcCheck = new FuncDefinition();

		return funcCheck.evaluate(expression);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public String tokenize( String input ){
		StringBuffer tokens = new StringBuffer();
		// StringBuffer stringBuild = new StringBuffer();
		boolean inBounds = false;
		boolean skip = false;

		String whiteList = " ,(){}[]+-/*=;";

		for(int i = 0; i < input.length(); i++){
			char c = input.charAt(i);

			if(!inBounds){
				if (whiteList.indexOf(c) > -1) {
					if(tokens.length() > 0)
						tokens.append(delimiter);

					if(c == '{'){
						inBounds = true;
						skip = true;
					}
				}
				else{
					if( i > 0 && whiteList.indexOf( tokens.charAt(tokens.length() - 1) ) > -1)
						tokens.append(delimiter);
				}
			}
			else{
				if(c == '}'){ // if c == '}'
					inBounds = false;
					tokens.append(delimiter);
				}
			}

			tokens.append(c);

			if(skip){
				skip = false;
				tokens.append(delimiter);
			}
		}
		return tokens.toString();
	}

	protected boolean isNewIdentifier( String identifier){
		if( intList.contains(identifier) || doubleList.contains(identifier) || floatList.contains(identifier) || charList.contains(identifier) ||
			intArrayList.contains(identifier) || doubleArrayList.contains(identifier) || floatArrayList.contains(identifier) || funcList.contains(identifier))
			return false;
		else
			return true;
	}

	protected boolean addFunctionIdentifier( String identifier ){
		if(!funcList.contains(identifier)){
			funcList.add(identifier);
			return true;
		}

		else
			return false;
	}

	public void inherit(){
		/*
		intList
		doubleList
		floatList
		charList

		intArrayList
		doubleArrayList
		floatArrayList
		charArrayList
		lastType
		returnType
		*/

		this.intList = innerMachine.intList();
		this.doubleList = innerMachine.doubleList();
		this.floatList = innerMachine.floatList();
		this.charList = innerMachine.charList();

		this.intArrayList = innerMachine.intArrayList();
		this.doubleArrayList = innerMachine.doubleArrayList();
		this.floatArrayList = innerMachine.floatArrayList();
		this.charArrayList = innerMachine.charArrayList();
		this.lastType = innerMachine.lastType();
		this.returnType = innerMachine.returnType();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public int inputType(int state, String input){
		/*
		Overwrite inputType here

		Note:
			0	=	' '
			1	=	datatype
			2	= 	new identifier
			3	=	old identifier
			4	=	void
			5	=	valid statement
			6	=	'*'
			7	=	','
			8	=	'('
			9	=	')'
			10	=	';'
			11	=	'{'
			12	=	'}'
			13	=	invalid input {unnecessary}


		*/
		// System.out.println("Hi");

		if(inBody){
			innerMachine.inherit( intList, doubleList, floatList, charList,
			 intArrayList, doubleArrayList, floatArrayList, charArrayList, lastType, returnType);

			inBody = false;

			if(innerMachine.evaluate(input)){
				if(debug){
					System.out.println("Call: Diagram[" + state + "][5]");
				}

				inherit();

				return stateDiagram[state][5];
			}
			else{
				if(debug){
					System.out.println("Call: DeadState");
				}

				return deadState;
			}
		}
		else{	
			if(input.equals(" ")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][0]");
				}
	
				return stateDiagram[state][0];
			}
			else if(input.equals("*")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][6]");
				}
	
				return stateDiagram[state][6];
			}
			else if(input.equals(",")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][7]");
				}
	
				return stateDiagram[state][7];
			}
			else if(input.equals("(")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][8]");
				}
	
				return stateDiagram[state][8];
			}
			else if(input.equals(")")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][9]");
				}
	
				return stateDiagram[state][9];
			}
			else if(input.equals(";")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][10]");
				}
	
				return stateDiagram[state][10];
			}
			else if(input.equals("{")){
				inBody = true;

				if(debug){
					System.out.println("Call: Diagram[" + state + "][11]");
				}
	
				return stateDiagram[state][11];
			}
			else if(input.equals("}")){
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][12]");
				}
	
				return stateDiagram[state][12];
			}
	
			// else if(isNumber(input)){
			// 	return stateDiagram[state][5];
			// }
	
			else if(isDatatype(input)){
				if(state == 0 || state == 9)
					returnType = input;

				lastType = input;
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][1]");
				}
	
				return stateDiagram[state][1];
			}
			else if(input.equals("void")){
				lastType = input;
	
				if(debug){
					System.out.println("Call: Diagram[" + state + "][4]");
				}
	
				return stateDiagram[state][4];
			}
	
			else if(validIdentifier(input)){
				if(isNewIdentifier(input)){
					if(!addIdentifier (input, lastType)){
						if(state == 1 && lastType.equals("void")){
							if(!addFunctionIdentifier(input))
								return deadState;

							if(debug){
								System.out.println("Call: Diagram[" + state + "][2]"); //considered new identifier (function)
							}

							return stateDiagram[state][2];
						}
						else{
							if(debug){
								System.out.println("Call: Diagram[" + state + "][13]");
							}

							return stateDiagram[state][13];
						}
							
					}
					else{
	
						if(debug){
							System.out.println("Call: Diagram[" + state + "][2]");
						}
	
						return stateDiagram[state][2];
					}
				}
				else{
	
					if(debug){
						System.out.println("Call: Diagram[" + state + "][3]");
					}
	
					return stateDiagram[state][3];
				}
			}
		}

		if(debug){
			System.out.println("Call: DeadState");
		}
		// return stateDiagram[state][5];
		return deadState;
	}
}

class Statements extends VarDeclaration{

	private static String className = "Statements";
	private static boolean debug = false;

	protected ArrayList<String> funcList;

	private static String returnType = "null";

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void toggleDebug(){
		if (debug){
			System.out.println(className + ": Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println(className + ": Debug-Mode Turned on.");
		debug = true;

		return;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	Statements(){
		super();

		int finalStates[] = {5};
		addStates(finalStates);

		funcList = new ArrayList<String>();

		deadState = 6;

		stateDiagram = new int[][]{
			/* input:   0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16 */
			/* q0*/	 {  0 ,  1 ,  6 , 15 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 , 13 ,  6 },
			/* q1*/	 {  1 ,  6 ,  2 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q2*/	 {  2 ,  6 ,  6 ,  6 ,  6 ,  6 ,  3 ,  1 ,  7 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 ,  6 },
			/* q3*/	 {  3 ,  6 ,  6 ,  4 ,  4 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  4 ,  6 ,  6 ,  6 },
			/* q4*/	 {  4 ,  1 ,  6 ,  6 ,  6 ,  6 ,  6 ,  1 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 ,  3 },
			/* q5*/	 {  5 ,  1 ,  6 , 15 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 , 13 ,  6 },
			/* q6*/	 {  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q7*/	 {  7 ,  6 ,  6 ,  6 ,  8 ,  6 ,  6 ,  6 ,  6 ,  9 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q8*/	 {  8 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  9 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q9*/	 {  9 ,  6 ,  6 ,  6 ,  6 ,  6 , 10 ,  1 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 ,  6 },
			/* q10*/ { 10 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 11 ,  6 ,  6 ,  6 ,  4 ,  6 ,  6 },
			/* q11*/ { 11 ,  6 ,  6 ,  6 , 12 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 12 ,  6 ,  6 ,  6 },
			/* q12*/ { 12 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 11 ,  6 ,  6 ,  6 ,  4 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q13*/ { 13 ,  6 ,  6 , 14 , 14 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 ,  6 },
			/* q14*/ { 14 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 , 13 },
			/* q15*/ { 15 ,  6 ,  6 ,  6 ,  6 ,  6 , 16 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q16*/ { 16 ,  6 ,  6 , 17 , 17 ,  6 ,  6 ,  6 ,  6 ,  6 , 19 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q17*/ { 17 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 18 ,  6 ,  6 ,  6 ,  6 ,  5 ,  6 ,  6 ,  6 , 16 },
			/* q18*/ { 18 ,  6 ,  6 , 15 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q19*/ { 19 ,  6 ,  6 ,  6 , 20 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 },
			/* q20*/ { 20 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 19 ,  6 ,  6 ,  6 , 21 ,  6 ,  6 ,  6 ,  6 , 	6 },
			/* q21*/ { 21 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 , 18 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 ,  6 }
		};
	}

	Statements(String statement){
		this();

		this.statement = statement;
	}

	public static boolean verify(String expression){
		Statements statCheck = new Statements();

		return statCheck.evaluate(expression);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void inherit(ArrayList<String> intList, ArrayList<String> doubleList, ArrayList<String> floatList, ArrayList<String> charList, ArrayList<String> intArrayList, ArrayList<String> doubleArrayList, ArrayList<String> floatArrayList, ArrayList<String> charArrayList, String lastType, String returnType){
		/*
		intList
		doubleList
		floatList
		charList

		intArrayList
		doubleArrayList
		floatArrayList
		charArrayList
		lastType
		returnType
		*/

		this.intList = intList;
		this.doubleList = doubleList;
		this.floatList = floatList;
		this.charList = charList;

		this.intArrayList = intArrayList;
		this.doubleArrayList = doubleArrayList;
		this.floatArrayList = floatArrayList;
		this.charArrayList = charArrayList;
		this.lastType = lastType;
		this.returnType = returnType;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public ArrayList<String> intList(){
		return intList;
	}
	public ArrayList<String> doubleList(){
		return doubleList;
	}
	public ArrayList<String> floatList(){
		return floatList;
	}
	public ArrayList<String> charList(){
		return charList;
	}
	public ArrayList<String> intArrayList(){
		return intArrayList;
	}
	public ArrayList<String> doubleArrayList(){
		return doubleArrayList;
	}
	public ArrayList<String> floatArrayList(){
		return floatArrayList;
	}
	public ArrayList<String> charArrayList(){
		return charArrayList;
	}
	public String lastType(){
		return lastType;
	}
	public String returnType(){
		return returnType;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void setReturnType( String type ){
		returnType = type;
	}

	public int inputType(int state, String input){
		/*
		Overwrite inputType here

		Note:
			0	=	' '
			1	=	datatype
			2	=	new identifier
			3	=	old identifier
			4	=	constant
			5	=	invalid
			6	=	'='
			7	=	','
			8	=	'['
			9	=	']'
			10	=	'{'
			11	=	'}'
			12	=	';'
			13	=	float
			14	=	array var
			15	=	return
			16	=	operator
		*/
		// System.out.println("Hi");

		if(input.equals(" ")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][0]");
			}

			return stateDiagram[state][0];
		}
		else if(input.equals("=")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][6]");
			}

			return stateDiagram[state][6];
		}
		else if(input.equals(",")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][7]");
			}

			return stateDiagram[state][7];
		}
		else if(input.equals("[")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][8]");
			}

			return stateDiagram[state][8];
		}
		else if(input.equals("]")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][9]");
			}

			return stateDiagram[state][9];
		}
		else if(input.equals("{")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][10]");
			}

			return stateDiagram[state][10];
		}
		else if(input.equals("}")){
			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][11]");
			}

			return stateDiagram[state][11];
		}
		else if(input.equals(";")){			
			if(state == 13 && !returnType.equals("void"))
				return deadState;

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][12]");
			}

			return stateDiagram[state][12];
		}

		else if(input.equals("return")){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][15]");
			}

			return stateDiagram[state][15];
		}
		else if(isOperator(input)){

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][16]");
			}

			return stateDiagram[state][16];
		}

		else if(isNumber(input)){
			if(hasFloat(input)){

				if(debug){
					System.out.println("Call: Inner Diagram[" + state + "][13]");
				}

				return stateDiagram[state][13];
			}
				
			else{

				if(debug){
					System.out.println("Call: Inner Diagram[" + state + "][4]");
				}

				return stateDiagram[state][4];
			}
		}
		else if(isDatatype(input)){
			/*
			if(lastType.equals("void"))
				lastType = input;
			else
				return 5;
			*/
			lastType = input;

			if(debug){
				System.out.println("Call: Inner Diagram[" + state + "][1]");
			}

			return stateDiagram[state][1];
		}

		else if(validIdentifier(input)){
			if(isNewIdentifier(input)){
				// if(lastType.equals("void"))
					// return 5;

				if(!addIdentifier (input, lastType)){

					if(debug){
						System.out.println("Into: Inner LastType: " + lastType);
						System.out.println("\n[<<     Make Statements inherit data from FuncDef     >>]\n");
						System.out.println("Call: Inner Diagram[" + state + "][5]");
					}

					return stateDiagram[state][5];
				}
				else{

					if(debug){
						System.out.println("Call: Inner Diagram[" + state + "][2]");
					}

					return stateDiagram[state][2];
				}
			}
			else{
				if(isArrayIdentifier(input)){

					if(debug){
						System.out.println("Call: Inner Diagram[" + state + "][14]");
					}

					return stateDiagram[state][14];
				}
				else{
					// if(state == 13 && !returnType.equals(lastType))
						// return deadState;

					if(debug){
						System.out.println("Call: Inner Diagram[" + state + "][3]");
					}

					return stateDiagram[state][3];
				}
			}
		}

		// return stateDiagram[state][5];
		return deadState;
	}
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

class FileHandler {
	private String fileContents;
	private String outContents;

	public File currentFile;				//File being parsed
	public String fileHandle;				//File name
	public String outHandle;

	private String pattern = "  ";			//Pattern for splitting

	private static boolean debug = false;

	public FileHandler(){
		fileContents = "";
		outContents = "";
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

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

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void menu(){
		Scanner inputParser;

		System.out.print("File Name: ");
		inputParser = new Scanner(System.in);
		fileHandle = inputParser.nextLine();

		System.out.print("Out Name:  ");
		inputParser = new Scanner(System.in);
		outHandle = inputParser.nextLine();

		System.out.println();

		inputParser.close();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void process(){
		try{
			menu();

			openFile(fileHandle);
			parseFile();

			routine();
			outFile();
		}
		catch(NonExistentFileException e){
			System.out.println(e.getMessage());
		}
	}

	private void routine(){
		Scanner parser = new Scanner(fileContents);

		String cast = parser.nextLine();
		int cases = Integer.parseInt(cast);

		for(int i = 0; i < cases; i++){
			cast = parser.nextLine();
			int type = Integer.parseInt(cast);

			cast = parser.nextLine();

			switch(type){
				case 1:	//Variable Declaration

					if(VarDeclaration.verify(cast)){
						outContents += ("Yes" + pattern);
					}
					else{
						outContents += ("No" + pattern);
					}

					break;
				case 2:	//Function Declaration/Prototype

					if(FuncDeclaration.verify(cast)){
						outContents += ("Yes" + pattern);
					}
					else{
						outContents += ("No" + pattern);
					}

					break;
				case 3: //Function Definition

					if(FuncDefinition.verify(cast)){
						outContents += ("Yes" + pattern);
					}
					else{
						outContents += ("No" + pattern);
					}

					break;
				default:
					outContents += ("Invalid Type: " + type + pattern);
					break;
			}
		}

		parser.close();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

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
		Scanner handleParser = new Scanner(outHandle);
		handleParser.useDelimiter("\\.");

		File outFile;

		String outHandle = "";

		if(!handleParser.hasNext()){
			// outHandle = "output.out";

			outFile = new File("Mandreza4.out");
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


		if(debug){
			System.out.println();
		}
	}

}