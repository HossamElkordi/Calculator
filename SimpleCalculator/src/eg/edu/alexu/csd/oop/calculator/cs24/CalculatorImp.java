package eg.edu.alexu.csd.oop.calculator.cs24;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorImp implements Calculator{
	
	private static final String OPERAND_REGEX = "[\\-]?[0-9]+[\\.]?[0-9]*";
	private static final String OPERATOR_REGEX = "[\\+\\-\\*\\/]?";
	private static final String FORMULA_REGEX = OPERAND_REGEX + OPERATOR_REGEX + OPERAND_REGEX;
	private Pattern numPattern;
	private Pattern operatorPattern;
	
	private final String saveFile = "Save.txt";
	
	private double firstNum;
	private double secondNum;
	private String operator;
	private String result = "";
	private final int stackSize = 5;
	private String[] stack = new String[stackSize];
	private int stackIndex = 0;
	private boolean moved;
	private boolean loaded;
	
	public CalculatorImp() {
		numPattern = Pattern.compile(OPERAND_REGEX);
		operatorPattern = Pattern.compile(OPERATOR_REGEX);
		restStack();
	}

	public void input(String s) {
		if(Pattern.matches(FORMULA_REGEX, s)) {
			fillStackArray(s);
			int index = 0;
						
			Matcher numMatch = numPattern.matcher(s);
			Matcher operatorMatch = operatorPattern.matcher(s);
			
			while(numMatch.find()) {
				firstNum = Double.parseDouble(numMatch.group());
				index = numMatch.end();
				break;
			}
			while(operatorMatch.find(index)) {
				operator = operatorMatch.group();
				index = numMatch.end();
				break;
			}
			while(numMatch.find(index+1)) {
				secondNum = Double.parseDouble(numMatch.group());
				break;
			}
		}
	}

	public String getResult() {
		switch(this.operator) {
			case "+":
				this.result = "" + (firstNum + secondNum);
				break;
			case "-":
				this.result = "" + (firstNum - secondNum);
				break;
			case "*":
				this.result = "" + (firstNum * secondNum + 0.0);
				break;
			case "/":
				if(secondNum == 0.0) {
					this.result = "Can't divide by zero";
					try {
						throw new ArithmeticException("Can't divide by zero");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					this.result = "" + (firstNum / secondNum + 0.0);
				}
				break;
		}
		return this.result;
	}

	public String current() {
		if(this.stack[this.stackIndex].length() == 0) {
			return null;
		}
		return this.stack[this.stackIndex];
	}

	public String prev() {
		if(this.stackIndex - 1 < 0) {
			return null;
		}
		moved = true;
		this.stackIndex--;
		input(this.stack[this.stackIndex]);	
		return this.stack[this.stackIndex];
	}

	public String next() {
		if((this.stackIndex + 1 >= this.stackSize) || (this.stack[this.stackIndex + 1].length() == 0)) {
			return null;
		}
		moved = true;
		this.stackIndex++;
		input(this.stack[this.stackIndex]);
		return this.stack[this.stackIndex];
	}

	public void save() {
		try {
			File file = new File(saveFile);
			if(!file.exists()) {
				file.createNewFile();
			}
			
			PrintWriter pw = new PrintWriter(file);
			pw.println(this.stackIndex);
			for(int i = 0; i < this.stackSize; i++) {
				if((this.stack[i] != null) && (this.stack[i].length() != 0)) {
					pw.println(this.stack[i]);
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		File file = new File(saveFile);
		if(!file.exists()) {
			return;
		}
		try {
			restStack();
			Scanner scan = new Scanner(file);
			this.stackIndex = Integer.parseInt(scan.nextLine());
			int i = 0;
			while(scan.hasNextLine()) {
				if(!(i >= this.stackSize)) {
					this.stack[i++] = scan.nextLine();
				}
			}
			scan.close();
			if(i == 0) {
				return;
			}
			loaded = true;
			input(this.stack[this.stackIndex]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void fillStackArray(String formula) {
		if(!moved && !loaded) {
			if(this.stackIndex + 1 >= this.stackSize) {
				shiftStackArray();
				this.stack[this.stackIndex] = formula;
				return;
			}
			this.stackIndex = getSavingIndex();
			this.stack[this.stackIndex] = formula;
		}else {
			moved = false;
			loaded = false;
		}
	}
	
	private void shiftStackArray() {
		for(int i = 0; i < this.stackSize - 1; i++) {
			this.stack[i] = this.stack[i + 1];
		}
	}
	
	private void restStack() {
		for(int i = 0; i < this.stackSize; i++) {
			this.stack[i] = "";
		}
	}

	private int getSavingIndex() {
		for(int i = 0; i < this.stackSize; i++) {
			if((this.stack[i] == null) || (this.stack[i].length() == 0)) {
				return i;
			}
		}
		return 0;
	}
	
}
