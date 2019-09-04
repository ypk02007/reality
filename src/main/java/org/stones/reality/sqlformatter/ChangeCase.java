package org.stones.reality.sqlformatter;

public class ChangeCase {
	FormatOptions option = FormatOptions.getInstance();
	
	private int which(String type) {
		int which = FormatOptions.UNCHANGED;
		switch(type) {
		case "keyword":
			which = option.getKeywordsCase(); break;
		case "tableName":
			which = option.getTableNameCase(); break;
		case "columnName":
			which = option.getcolumnNameCase(); break;
		case "function":
			which = option.getFunctionCase(); break;
		case "datatype":
			which = option.getDatatypeCase(); break;
		case "variable":
			which = option.getVariableCase(); break;
		case "alias":
			which = option.getAliasCase(); break;
		}
		return which;
	}
	
	public String change(String str, String type) {
		int which = which(type); // 해당 토큰의 특성에 따른 옵션값 가져오기
		
		switch(which) {
		case FormatOptions.UPPERCASE:
			str = str.toUpperCase(); break;
		case FormatOptions.LOWERCASE:
			str = str.toLowerCase(); break;
		case FormatOptions.INITCAP:
			String init = str.substring(0, 1);
			String others = str.substring(1, str.length());
			init = init.toUpperCase();
			others = others.toLowerCase();
			str = init + others;
			break;
		case FormatOptions.UNCHANGED:
			break; // Do nothing
		}
		return str;
	}
}
