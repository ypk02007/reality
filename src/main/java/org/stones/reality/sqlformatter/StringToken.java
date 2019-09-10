package org.stones.reality.sqlformatter;

public class StringToken {
	private String str = null;
	private String type = null;
	private int priority = 0;
	private int paraDepth = 0;
	
	public StringToken(String str, String type, int priority) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		if(type == null) this.type = "NULL TYPE";
		else this.type = type;
		this.priority = priority;
	}
	
	public StringToken(String str, int priority) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		type = "none";
		this.priority = priority;
	}
	
	public StringToken(String str, String type) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		if(type == null) this.type = "NULL TYPE";
		else this.type = type;
	}
	
	public StringToken(String str) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		type = "none";
	}
	
	public String getString() {return str;}
	public String getType() {return type;}
	public int getPriority() {return priority;}
	public int getParaDepth() {return paraDepth;}
	public void setParaDepth(int paraDepth) {this.paraDepth = paraDepth;}
	public void addNewLine() {str += "\n";}
	public void addOneSpace() {str += " ";}
	public void addSomeSpaceForKeyword(FormatOptions option) {
		int len = option.getIndentation().length() - str.length() - 1;
		if(len < 0) len = 0;
		for(int i = 0; i < len; i++) {
			if(option.getStackAlign() == FormatOptions.ALIGN_LEFT)
				str += " ";
			else
				str = " " + str;
		}
	}
	public void addSomeSpaceInParantheses(int paraDepth, FormatOptions option) {
		String paranthesesSpace = " ";
		if(option.getLinebreakWithComma() == FormatOptions.BEFORE)
			paranthesesSpace = "  ";
		else if(option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE)
			paranthesesSpace = "   ";
		for(int i = 0; i < paraDepth; i++)
			str = paranthesesSpace + str;
	}
	
	public void addSomeSpaceInParantheses() {str = "   " + str;}
	public void addIndentation(String indentation) {str = indentation + str;}
}
