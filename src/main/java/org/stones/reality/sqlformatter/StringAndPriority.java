package org.stones.reality.sqlformatter;

public class StringAndPriority {
	private String str = null;
	private int priority = 0;
	private int paraDepth = 0;
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
	public int getParaDepth() {return paraDepth;}
	public void setParaDepth(int paraDepth) {this.paraDepth = paraDepth;}
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
	public void addSomeSpaceInParantheses(int paraDepth) {
		String paranthesesSpace = " ";
		if(option.getLinebreakWithComma() == FormatOptions.BEFORE)
			paranthesesSpace = "  ";
		else if(option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE)
			paranthesesSpace = "   ";
		for(int i = 0; i < paraDepth; i++)
			str = paranthesesSpace + str;
	}
	public void addIndentation() {
		str = indentation + str;
		//if(priority > 1)
			//indentation += " ";
	}
}
