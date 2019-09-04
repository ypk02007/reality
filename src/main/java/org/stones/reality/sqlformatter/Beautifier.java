package org.stones.reality.sqlformatter;

import java.util.Stack;
import java.util.Vector;

public class Beautifier {
	FormatOptions option = null;
	ChangeCase changeCase = null;
	
	public Beautifier() {
		changeCase = new ChangeCase();
		option = FormatOptions.getInstance();
	}
	
	public String beautifier(String sql) {
		String result = insertIndentation(inlineBlock(insertNewLine(insertTokenPriority(changeTokenCase(insertTokenType(tokenization(sql)))))));
		return result;
	}
	
	public Vector<String> tokenization(String sql) { // sql문 쪼개기
		String[] splitted = sql.split(" "); // 먼저 띄어쓰기 한 칸을 기준으로 쪼갬
		Vector<String> tokens = new Vector<String>();
		for(int i = 0; i < splitted.length; i++) {
			StringBuilder sb = new StringBuilder(splitted[i]);
			StringBuilder temp = new StringBuilder("");
			int len = sb.length();
			for(int j = 0; j < len; j++) { // 쪼개진 조각에 특수한 문자가 포함되어 있을 경우 더 잘게 쪼갬
				char c = sb.charAt(j);
				if(specialCharacterCheck(c)) {
					if(temp.length() != 0) {
						tokens.add(temp.toString());
						temp = new StringBuilder("");
					}
					tokens.add(Character.toString(c));
				} else {
					temp.append(c);
				}
				if(j == len - 1 && temp.length() != 0)
					tokens.add(temp.toString());
			}
		}
		return tokens;
	}
	
	public Vector<StringAndType> insertTokenType(Vector<String> tokens) { // 토큰의 특성 적용
		Vector<StringAndType> tokensWithType = new Vector<StringAndType>();
		String str = null;
		String type = null;
		boolean keywordFromFlag = false;
		tokens.add(" "); // 원활한 반복문 작업을 위해 공백 추가
		
		for(int i = 0; i < tokens.size(); i++) { // 일단 모두 소문자로
			str = tokens.get(i);
			if(str.charAt(0) == '\'' && str.charAt(str.length()-1) == '\'') {
				continue; // 값은 어떠한 경우에도 변경해서는 안 됨
			} else
				tokens.set(i, tokens.get(i).toLowerCase());
		}
		
		tokensWithType.add(new StringAndType(tokens.get(0), "keyword")); // 첫 토큰은 반드시 키워드
		
		for(int i = 1; i < tokens.size() - 1; i++) {
			str = tokens.get(i);
			
			if(str.charAt(0) == '\'' && str.charAt(str.length()-1) == '\'')
				type = "value";
			else if(keywordCheck(str)) {
				type = "keyword";
				if(str.equals("from")) keywordFromFlag = true;
				else keywordFromFlag = false;
			} else if(specialCharacterCheck(str)) {
				switch(str) {
				case "(":
					type = "openingParantheses"; break;
				case ")":
					type = "closingParantheses"; break;
				case ",":
					type = "comma"; break;
				case ";":
					type = "semiColon"; break;
				case ".":
					type = "dot"; break;
				default:
					type = "specialCharacter";
				}
			} else if(tokens.get(i+1).equals("(") && !keywordCheck(str) && !specialCharacterCheck(str))
				type = "function";
			else if((tokens.get(i+1).equals(".") || tokens.get(i-1).equals(")") || type.equals("tableName")) && !keywordCheck(str) && !specialCharacterCheck(str))
				type = "alias";
			else if(tokens.get(i-1).equals(".") || (!keywordFromFlag && ((type.equals("keyword") && (tokens.get(i+1).equals(",") || otherTypeCheck(tokens.get(i+1)))) || (tokens.get(i-1).equals(",") && tokens.get(i+1).equals(",")) || keywordPriorityCheck(tokens.get(i+1)) == 0)))
				type = "columnName";
			else if(otherTypeCheck(str))
				type = "other";
			else
				type = "tableName";
			
			tokensWithType.add(new StringAndType(str, type));
		}
		
		tokensWithType = analyzeParantheses(tokensWithType);
		
		return tokensWithType;
	}
	
	public Vector<StringAndType> analyzeParantheses(Vector<StringAndType> tokens) { // 괄호분석
		String str = null;
		String strPrevious = null;
		String strNext = null;
		String type = null;
		String previous = null;
		String next = null;
		String parantheses = null;
		Stack<String> strStack = new Stack<String>();
		tokens.add(new StringAndType(" ", "other"));
		for(int i = 1; i < tokens.size() - 1; i++) {
			str = tokens.get(i).getString();
			strPrevious = tokens.get(i-1).getString();
			strNext = tokens.get(i+1).getString();
			type = tokens.get(i).getType();
			previous = tokens.get(i-1).getType();
			next = tokens.get(i+1).getType();
			if(type.equals("openingParantheses")) {
				if(previous.equals("function")) {
					parantheses = type + "Function";
					strStack.push("Function");
				} else if(next.equals("keyword") && keywordPriorityCheck(strNext) == 0) {
					parantheses = type + "KeywordStrong";
					strStack.push("KeywordStrong");
				} else if(previous.equals("keyword") && keywordPriorityCheck(strPrevious) == 1) {
					parantheses = type + "KeywordWeak";
					strStack.push("KeywordWeak");
				} else
					parantheses = type;
				tokens.set(i, new StringAndType(str, parantheses));
			}
			if(type.equals("closingParantheses")) {
				if(strStack.peek().equals("Function")) {
					parantheses = type + "Function";
					strStack.pop();
				} else if(strStack.peek().equals("KeywordStrong")) {
					parantheses = type + "KeywordStrong";
					strStack.pop();
				} else if(strStack.peek().equals("KeywordWeak")) {
					parantheses = type + "KeywordWeak";
					strStack.pop();
				} else
					parantheses = type;
				tokens.set(i, new StringAndType(str, parantheses));
			}
		}
		return tokens;
	}
	
	public Vector<StringAndType> changeTokenCase(Vector<StringAndType> tokens) { // 대소문자 변경
		String str = null;
		String type = null;
		for(int i = 0; i < tokens.size(); i++) {
			str = tokens.get(i).getString();
			type = tokens.get(i).getType();
			str = changeCase.change(str, type);
			tokens.set(i, new StringAndType(str, type));
		}
		return tokens;
	}
	
	public Vector<StringAndPriority> insertTokenPriority(Vector<StringAndType> tokens) { // 토큰 우선순위 적용
		Vector<StringAndPriority> tokensWithPriority = new Vector<StringAndPriority>();
		int currentPriority = 0;
		int priority = 0;
		int keywordWeak = 0;
		int paraDepth = 0;
		boolean andOrFlag = false;
		boolean closingParaFlag = false;
		boolean paraDepthFlag = false;
		String str = null;
		String type = null;
		
		indentationCheck(tokens.get(0).getString());
		tokensWithPriority.add(new StringAndPriority(tokens.get(0).getString(), priority)); // 첫 토큰은 반드시 keyword
		
		for(int i = 1; i < tokens.size(); i++) {
			str = tokens.get(i).getString();
			type = tokens.get(i).getType();
			switch(type) {
			case "keyword":
				if(keywordPriorityCheck(str) == 0) {
					priority = currentPriority + keywordPriorityCheck(str);
					indentationCheck(str);
					if(str.toLowerCase().equals("select"))
						paraDepth++;
				} else if(andOrFlag) {
					andOrFlag = false;
					keywordWeak++;
					priority = currentPriority + keywordPriorityCheck(str) + keywordWeak;
				} else if(closingParaFlag) {
					closingParaFlag = false;
					keywordWeak = 0;
					priority = currentPriority + keywordPriorityCheck(str) + keywordWeak;
				}
				break;
			case "openingParanthesesKeywordStrong":
				currentPriority++;
				priority = currentPriority;
				paraDepthFlag = true;
				break;
			case "openingParanthesesKeywordWeak":
				andOrFlag = true;
				priority = currentPriority + 1 + keywordWeak;
				break;
			case "closingParanthesesKeywordStrong":
				priority = currentPriority;
				currentPriority--;
				paraDepth--;
				break;
			case "closingParanthesesKeywordWeak":
				priority = currentPriority + 1 + keywordWeak;
				if(!closingParaFlag)
					closingParaFlag = true;
				break;
			default:
				if(closingParaFlag) {
					closingParaFlag = false;
					keywordWeak = 0;
				}
				priority = currentPriority + 1 + keywordWeak;
			}
			tokensWithPriority.add(new StringAndPriority(str, priority));
			tokensWithPriority.get(i).setParaDepth(paraDepth);
		}
		
		for(int i = 0; i < tokensWithPriority.size(); i++) { // 형식 맞추기 위한 스페이스 추가
			if(keywordPriorityCheck(tokensWithPriority.get(i).getString()) == 0)
				tokensWithPriority.get(i).addSomeSpaceForKeyword();
		}
		
		return tokensWithPriority;
	}
	
	public Vector<StringAndPriority> inlineBlock(Vector<StringAndPriority> tokensWithPriority) { // 일부 문자열 조각 합치기
		Vector<StringAndPriority> inlinedTokens = new Vector<StringAndPriority>();
		StringBuilder temp = new StringBuilder(""); // 버퍼역할
		int priorityTemp = 0;
		int paraDepthTemp = 0;
		String str = null;
		for(int i = 0; i < tokensWithPriority.size() - 1; i++) {
			str = tokensWithPriority.get(i).getString();
			
			temp.append(str); // 개행문자가 나오기 전 까지 저장
			if(temp.charAt(temp.length() - 1) == '\n') {
				StringAndPriority sap = new StringAndPriority(temp.toString(), priorityTemp);
				sap.addSomeSpaceInParantheses(paraDepthTemp);
				inlinedTokens.add(sap);
				temp = new StringBuilder("");
				priorityTemp = tokensWithPriority.get(i+1).getPriority();
				paraDepthTemp = tokensWithPriority.get(i+1).getParaDepth();
			} else if(addSpaceCheck(tokensWithPriority.get(i), tokensWithPriority.get(i+1)))
				temp.append(" ");
		}
		// index예외 방지를 위해 마지막 요소를 반복문 밖에서 처리
		str = tokensWithPriority.get(tokensWithPriority.size() - 1).getString();
		temp.append(str);
		inlinedTokens.add(new StringAndPriority(temp.toString(), priorityTemp));
		
		return inlinedTokens;
	}
	
	public Vector<StringAndPriority> insertNewLine(Vector<StringAndPriority> tokensWithPriority) { // 개행문자 추가
		for(int i = 0; i < tokensWithPriority.size() - 1; i++) { // 마지막 문자열 조각은 개행문자가 필요없음
			if(newLineCheck(tokensWithPriority.get(i), tokensWithPriority.get(i+1)))
				tokensWithPriority.get(i).addNewLine();
		}
		return tokensWithPriority;
	}
	
	public String insertIndentation(Vector<StringAndPriority> tokensWithPriority) { // 들여쓰기 추가
		StringBuilder result = new StringBuilder("");
		for(int i = 0; i < tokensWithPriority.size(); i++) {
			for(int j = 0; j < tokensWithPriority.get(i).getPriority(); j++)  // 들여쓰기
				tokensWithPriority.get(i).addIndentation();
			result.append(tokensWithPriority.get(i).getString());
		}
		return result.toString();
	}
	
	public boolean specialCharacterCheck(char c) { // 이하의 특수문자는 따로 분리함
		switch(c) {
		case ',': return true;
		case '(': return true;
		case ')': return true;
		case ';': return true;
		case '.': return true;
		default: return false;
		}
	}
	
	public boolean specialCharacterCheck(String s) { // 이하의 특수문자는 따로 분리함
		switch(s) {
		case ",": return true;
		case "(": return true;
		case ")": return true;
		case ";": return true;
		case ".": return true;
		default: return false;
		}
	}
	
	public boolean otherTypeCheck(String s) {
		switch(s) {
		case "=": return true;
		case "<": return true;
		case ">": return true;
		case "<=": return true;
		case ">=": return true;
		case "<>": return true;
		default: return false;
		}
	}
	
	public boolean keywordCheck(String s) {
		Vector<StringAndPriority> keywords = keywordList();
		s = s.toLowerCase().trim();
		for(int j = 0; j < keywords.size(); j++) {
			if(s.equals(keywords.get(j).getString()))
				return true;
		}
		return false;
	}
	
	public int keywordPriorityCheck(String s) {
		Vector<StringAndPriority> keywords = keywordList();
		s = s.toLowerCase().trim();
		for(int i = 0; i < keywords.size(); i++) {
			if(s.equals(keywords.get(i).getString()))
				return keywords.get(i).getPriority();
		}
		return 1;
	}
	
	public void indentationCheck(String s) { // 들여쓰기 길이는 가장 긴 키워드의 길이
		if(s.length() > option.getIndentation().length())
			option.setIndentation(s.length() + 1);
	}
	
	public boolean newLineCheck(StringAndPriority current, StringAndPriority next) { // 개행조건 체크
		// 다음 토큰과 우선순위가 다른 경우 & style one 옵션
		if((current.getPriority() != next.getPriority()) && (option.getStyle() == FormatOptions.STYLE_ONE)) return true;
		// 다음 토큰이 우선도 0인 keyword면서 이번 토큰이 여는 괄호가 아닌 경우 & style two 옵션
		if((keywordPriorityCheck(next.getString()) == 0) && !current.getString().equals("(") && (option.getStyle() == FormatOptions.STYLE_TWO)) return true;
		// 이번 토큰이 콤마인 경우 & after 옵션
		if((current.getString().equals(",")) && (option.getLinebreakWithComma() == FormatOptions.AFTER)) return true;
		// 다음 토큰이 콤마인 경우 & before 또는 before with space 옵션
		if(next.getString().equals(",") && ((option.getLinebreakWithComma() == FormatOptions.BEFORE) || (option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE))) return true;
		// 다음 토큰이 AND나 OR일 경우
		if(next.getString().toLowerCase().trim().equals("and") || next.getString().toLowerCase().trim().equals("or")) return true;
		else return false;
	}
	
	public boolean addSpaceCheck(StringAndPriority current, StringAndPriority next) { // 스페이스 한 칸 추가 조건 체크
		if(specialCharacterCheck(current.getString())) {
			if(current.getString().equals(")") && !(next.getString().equals(")") || next.getString().equals(")\n")))
				return true;
			else if(option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE && current.getString().equals(","))
				return true;
			else return false;
		} else if(specialCharacterCheck(next.getString())) {
			if(keywordCheck(current.getString()) && !next.getString().equals(";"))
				return true;
			else return false;
		} else if(next.getString().equals(",\n"))
			return false;
		else return true;
	}
	
	public Vector<StringAndPriority> keywordList() { // SQL 키워드 목록
		Vector<StringAndPriority> keywords = new Vector<StringAndPriority>();
		keywords.add(new StringAndPriority("select", 0));
		keywords.add(new StringAndPriority("from", 0));
		keywords.add(new StringAndPriority("where", 0));
		keywords.add(new StringAndPriority("order", 0));
		keywords.add(new StringAndPriority("by", 1));
		keywords.add(new StringAndPriority("and", 1));
		keywords.add(new StringAndPriority("or", 1));
		keywords.add(new StringAndPriority("desc", 1));
		
		return keywords;
	}
}