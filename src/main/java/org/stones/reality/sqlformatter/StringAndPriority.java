package org.stones.reality.sqlformatter;

public class StringAndPriority {
	private String str = null;
	private int priority = 0;
	private String indentation = null;
	private FormatOptions option = null;
	
	public StringAndPriority(String str, int priority) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		this.priority = priority;
		option = FormatOptions.getInstance();
		indentation = option.getIndentation();
	}
	
	public String getString() {return str;}
	public int getPriority() {return priority;}
	public void addNewLine() {str += "\n";}
	public void addOneSpace() {str += " ";}
	public void addSomeSpaceForKeyword() {
		int len = indentation.length() - str.length() - 1;
		if(len < 0) len = 0;
		for(int i = 0; i < len; i++) {
			if(option.getStackAlign() == FormatOptions.ALIGN_LEFT)
				str += " ";
			else
				str = " " + str;
		}
	}
	public void addIndentation() {
		str = indentation + str;
		if(priority > 1)
			indentation += " ";
	}
}
