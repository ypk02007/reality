package org.stones.reality.sqlformatter;

public class StringAndType {
	private String str = null;
	private String type = null;
	
	public StringAndType(String str, String type) {
		if(str == null) this.str = "NULL STRING";
		else this.str = str;
		if(type == null) this.type = "NULL TYPE";
		else this.type = type;
	}
	
	public String getString() {return str;}
	public String getType() {return type;}
}