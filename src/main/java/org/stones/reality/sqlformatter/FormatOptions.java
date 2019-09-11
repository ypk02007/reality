package org.stones.reality.sqlformatter;

public class FormatOptions {
	final static int UPPERCASE = 0; // 대소문자 관련
	final static int LOWERCASE = 1;
	final static int INITCAP = 2;
	final static int UNCHANGED = 3;
	
	final static int AFTER = 0; // 콤마 위치 관련
	final static int BEFORE = 1;
	final static int BEFORE_WITH_SPACE = 2;
	
	final static int ALIGN_LEFT = 0; // 맨 앞 키워드 정렬 관련
	final static int ALIGN_RIGHT = 1;
	
	final static int STYLE_ONE = 0; // 스타일
	final static int STYLE_TWO = 1;
	
	private String indentation = null;
	private int keywordCase = 0;
	private int tableNameCase = 0;
	private int columnNameCase = 0;
	private int functionCase = 0;
	private int datatypeCase = 0;
	private int variableCase = 0;
	private int aliasCase = 0;
	
	private int linebreakWithComma = 0;
	private int stackAlign = 0;
	private int style = 0;
	
	public FormatOptions() {setDefaultOptions();}
	
	public void setDefaultOptions() {
		setIndentation(4);
		keywordCase = UPPERCASE;
		tableNameCase = LOWERCASE;
		columnNameCase = LOWERCASE;
		functionCase = INITCAP;
		datatypeCase = UPPERCASE;
		variableCase = LOWERCASE;
		aliasCase = UNCHANGED;
		linebreakWithComma = AFTER;
		stackAlign = ALIGN_LEFT;
		style = STYLE_ONE;
	}
	public void setIndentation(int len) {
		if(len > 0 && len < 12) {
			StringBuilder indentation = new StringBuilder("");
			for(int i = 0; i < len; i++)
				indentation.append(" ");
			this.indentation = indentation.toString();
		} else {
			indentation = "";
		}
	}
	private int validCode(int code, int end) {
		if(code >= 0 && code <= end) return code;
		else return 0;
	}
	
	public String getIndentation() {return indentation;}
	public int getKeywordsCase() {return keywordCase;}
	public int getTableNameCase() {return tableNameCase;}
	public int getcolumnNameCase() {return columnNameCase;}
	public int getFunctionCase() {return functionCase;}
	public int getDatatypeCase() {return datatypeCase;}
	public int getVariableCase() {return variableCase;}
	public int getAliasCase() {return aliasCase;}
	public int getLinebreakWithComma() {return linebreakWithComma;}
	public int getStackAlign() {return stackAlign;}
	public int getStyle() {return style;}
	
	public void setKeywordCase(int code) {keywordCase = validCode(code, 3);}
	public void setTableNameCase(int code) {tableNameCase = validCode(code, 3);}
	public void setColumnNameCase(int code) {columnNameCase = validCode(code, 3);}
	public void setFunctionCase(int code) {functionCase = validCode(code, 3);}
	public void setDatatypeCase(int code) {datatypeCase = validCode(code, 3);}
	public void setVariableCase(int code) {variableCase = validCode(code, 3);}
	public void setAliasCase(int code) {aliasCase = validCode(code, 3);}
	public void setLinebreakWithComma(int code) {linebreakWithComma = validCode(code, 2);}
	public void setStackAlign(int code) {stackAlign = validCode(code, 2);}
	public void setStyle(int code) {style = validCode(code, 2);}
}
