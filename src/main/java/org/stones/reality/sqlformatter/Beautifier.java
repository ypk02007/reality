package org.stones.reality.sqlformatter;

import java.util.*;

public class Beautifier {
	private FormatOptions option = null;
	private ChangeCase changeCase = null;
	private ArrayList<StringToken> stringTokens = null;
	
	public Beautifier() {
		option = new FormatOptions();
		changeCase = new ChangeCase(option);
		stringTokens = new ArrayList<StringToken>();
	}
	
	public FormatOptions getBeautifierOption() {return option;}
	
	public String beautifier(String sql) {
		/*
		 * 작업 순서
		 * 1. tokenization(): 입력받은 sql문을 스페이스 한 칸을 기준으로 잘라 여러 토큰으로 만든다. 한 토큰을 세분화하기도 한다.
		 * 2. insertTokenType(): 토큰들의 특징을 분석해 어떤 토큰인지 타입을 저장한다.
		 * 3. changeTokenCase(): 토큰들의 타입, 옵션에 따라 대소문자 변경 작업을 해준다.
		 * 4. insertTokenPriority(): 토큰들의 타입, 순서 등의 여러 요인을 분석하여 우선순위를 저장한다. 우선순위는 텍스트 출력 형식(특히 들여쓰기)을 정하는데 사용된다.
		 * 5. insertNewLine(): 토큰들 중 조건에 맞는 토큰들에게 개행문자('\n')를 추가한다. 조건은 옵션에 따라 달라진다.
		 * 6. inlineBlock(): 한 줄에 나타낼 토큰들을 규합한다. 개행문자가 있는 토큰 까지의 모든 토큰을 한 덩어리로 만든다.
		 * 7. insertIndentation(): 토큰 덩어리들에 그것들의 우선순위에 따른 들여쓰기를 넣어준다.
		 * 8. resultString(): 최종 결과를 String으로 출력한다.
		 * 
		 * 1 ~ 4 작업은 일종의  사전작업으로, 문자열에 큰 변화를 주지 않는다.
		 * 5 ~ 7 작업은 출력 결과와 관련된 작업으로, 문자열에 크고 작은 변화를 준다
		 */
		
		tokenization(sql);
		insertTokenType();
		changeTokenCase();
		insertTokenPriority();
		insertNewLine();
		inlineBlock();
		insertIndentation();
		
		return resultString();
	}
	
	public void tokenization(String sql) { // sql문 쪼개기
		sql = sql.replaceAll("\r\n", ""); // 특정 문자 제거
		String[] splitted = sql.split(" "); // 먼저 띄어쓰기 한 칸을 기준으로 쪼갬
		ArrayList<StringToken> stringTokens = new ArrayList<StringToken>();
		for(int i = 0; i < splitted.length; i++) {
			StringBuilder sb = new StringBuilder(splitted[i]);
			StringBuilder temp = new StringBuilder("");
			int len = sb.length();
			for(int j = 0; j < len; j++) { // 쪼개진 조각에 특수한 문자가 포함되어 있을 경우 더 잘게 쪼갬
				char c = sb.charAt(j);
				if(specialCharacterCheck(c)) {
					if(temp.length() != 0) {
						stringTokens.add(new StringToken(temp.toString()));
						temp = new StringBuilder("");
					}
					stringTokens.add(new StringToken(Character.toString(c)));
				} else {
					temp.append(c);
				}
				if(j == len - 1 && temp.length() != 0)
					stringTokens.add(new StringToken(temp.toString()));
			}
		}
		this.stringTokens = stringTokens;
	}
	
	public void insertTokenType() { // 토큰의 특성 적용
		ArrayList<StringToken> tokensWithType = new ArrayList<StringToken>();
		String str = null;
		String strPrevious = null;
		String strNext = null;
		String type = null;
		boolean keywordFromFlag = false;
		stringTokens.add(new StringToken(" ")); // 원활한 반복문 작업을 위해 공백 추가
		
		tokensWithType.add(new StringToken(stringTokens.get(0).getString(), "keyword")); // 첫 토큰은 반드시 키워드
		
		for(int i = 1; i < stringTokens.size() - 1; i++) {
			str = stringTokens.get(i).getString();
			strPrevious = stringTokens.get(i-1).getString();
			strNext = stringTokens.get(i+1).getString();
			
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
				case "*":
					type = "asterisk"; break;
				default:
					type = "specialCharacter";
				}
			} else if(strNext.equals("(") && !keywordCheck(str) && !specialCharacterCheck(str))
				type = "function";
			else if((strNext.equals(".") || strPrevious.equals(")") || type.equals("tableName") || strPrevious.toLowerCase().equals("as")) && !keywordCheck(str) && !specialCharacterCheck(str))
				type = "alias";
			else if(strPrevious.equals(".") || (!keywordFromFlag && ((type.equals("keyword") && (strNext.equals(",") || otherTypeCheck(strNext))) || (strPrevious.equals(",") && strNext.equals(",")) || keywordPriorityCheck(strNext) == 0)))
				type = "columnName";
			else if(otherTypeCheck(str))
				type = "other";
			else
				type = "tableName";
			
			tokensWithType.add(new StringToken(str, type));
		}
		
		tokensWithType = deepAnalyzing(tokensWithType);
		
		stringTokens = tokensWithType;
	}
	
	public ArrayList<StringToken> deepAnalyzing(ArrayList<StringToken> tokens) { // 괄호, 콤마분석
		String str = null;
		String strPrevious = null;
		String strNext = null;
		String type = null;
		String previous = null;
		String next = null;
		String parantheses = null;
		int opfCount = 0;
		Stack<String> strStack = new Stack<String>();
		tokens.add(new StringToken(" ", "other"));
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
					opfCount++;
				} else if(next.equals("keyword") && keywordPriorityCheck(strNext) == 0) {
					parantheses = type + "KeywordStrong";
					strStack.push("KeywordStrong");
				} else if(previous.equals("keyword") && keywordPriorityCheck(strPrevious) == 1) {
					parantheses = type + "KeywordWeak";
					strStack.push("KeywordWeak");
				} else
					parantheses = type;
				tokens.set(i, new StringToken(str, parantheses));
			}
			if(type.equals("closingParantheses")) {
				if(strStack.peek().equals("Function")) {
					parantheses = type + "Function";
					strStack.pop();
					opfCount--;
				} else if(strStack.peek().equals("KeywordStrong")) {
					parantheses = type + "KeywordStrong";
					strStack.pop();
				} else if(strStack.peek().equals("KeywordWeak")) {
					parantheses = type + "KeywordWeak";
					strStack.pop();
				} else
					parantheses = type;
				tokens.set(i, new StringToken(str, parantheses));
			}
			if(type.equals("comma") && opfCount > 0) {
				tokens.set(i, new StringToken(str, type + "Function"));
			}
		}
		return tokens;
	}
	
	public void changeTokenCase() { // 대소문자 변경
		ArrayList<StringToken> tokens = stringTokens;
		String str = null;
		String type = null;
		for(int i = 0; i < tokens.size(); i++) {
			str = tokens.get(i).getString();
			type = tokens.get(i).getType();
			str = changeCase.change(str, type);
			tokens.set(i, new StringToken(str, type));
		}
		stringTokens = tokens;
	}
	
	public void insertTokenPriority() { // 토큰 우선순위 적용
		ArrayList<StringToken> tokensWithPriority = new ArrayList<StringToken>();
		int currentPriority = 0;
		int priority = 0;
		int keywordWeak = 0;
		int paraDepth = 0;
		boolean newLineKeywordFlag = false;
		boolean closingParaFlag = false;
		String str = null;
		String type = null;
		
		indentationCheck(stringTokens.get(0).getString());
		tokensWithPriority.add(new StringToken(stringTokens.get(0).getString(), priority)); // 첫 토큰은 반드시 keyword
		
		for(int i = 1; i < stringTokens.size(); i++) {
			str = stringTokens.get(i).getString();
			type = stringTokens.get(i).getType();
			switch(type) {
			case "keyword":
				if(keywordPriorityCheck(str) == 0) {
					priority = currentPriority + keywordPriorityCheck(str);
					indentationCheck(str);
					if(str.toLowerCase().equals("select"))
						paraDepth++;
				} else if(closingParaFlag) {
					closingParaFlag = false;
					keywordWeak = 0;
					priority = currentPriority + keywordPriorityCheck(str) + keywordWeak;
				} else if(newLineKeywordFlag && newLineKeywordCheck(str)) {
					newLineKeywordFlag = false;
					keywordWeak++;
					priority = currentPriority + keywordPriorityCheck(str) + keywordWeak;
				}
				break;
			case "openingParanthesesKeywordStrong":
				currentPriority++;
				priority = currentPriority;
				break;
			case "openingParanthesesKeywordWeak":
				newLineKeywordFlag = true;
				priority = currentPriority + 1 + keywordWeak;
				break;
			case "closingParanthesesKeywordStrong":
				priority = currentPriority;
				currentPriority--;
				paraDepth--;
				break;
			case "closingParanthesesKeywordWeak":
				priority = currentPriority + 1 + keywordWeak;
				newLineKeywordFlag = false;
				closingParaFlag = true;
				break;
			default:
				if(closingParaFlag) {
					closingParaFlag = false;
					keywordWeak = 0;
				}
				priority = currentPriority + 1 + keywordWeak;
			}
			tokensWithPriority.add(new StringToken(str, type, priority));
			tokensWithPriority.get(i).setParaDepth(paraDepth);
		}
		
		for(int i = 0; i < tokensWithPriority.size(); i++) { // 형식 맞추기 위한 스페이스 추가
			if(keywordPriorityCheck(tokensWithPriority.get(i).getString()) == 0 && !tokensWithPriority.get(i).getString().toLowerCase().equals("by"))
				tokensWithPriority.get(i).addSomeSpaceForKeyword(option);
		}
		
		stringTokens = tokensWithPriority;
	}
	
	public void inlineBlock() { // 일부 문자열 조각 합치기
		ArrayList<StringToken> inlinedTokens = new ArrayList<StringToken>();
		StringBuilder temp = new StringBuilder(""); // 버퍼역할
		int priorityTemp = 0;
		int paraDepthTemp = 0;
		String str = null;
		for(int i = 0; i < stringTokens.size() - 1; i++) {
			str = stringTokens.get(i).getString();
			
			temp.append(str); // 개행문자가 나오기 전 까지 저장
			if(temp.charAt(temp.length() - 1) == '\n') {
				StringToken st = new StringToken(temp.toString(), priorityTemp);
				st.addSomeSpaceInParantheses(paraDepthTemp, option);
				inlinedTokens.add(st);
				temp = new StringBuilder("");
				priorityTemp = stringTokens.get(i+1).getPriority();
				paraDepthTemp = stringTokens.get(i+1).getParaDepth();
			} else if(addSpaceCheck(stringTokens.get(i), stringTokens.get(i+1)))
				temp.append(" ");
		}
		// index예외 방지를 위해 마지막 요소를 반복문 밖에서 처리
		str = stringTokens.get(stringTokens.size() - 1).getString();
		temp.append(str);
		inlinedTokens.add(new StringToken(temp.toString(), priorityTemp));
		
		stringTokens = inlinedTokens;
	}
	
	public void insertNewLine() { // 개행문자 추가
		boolean betweenFlag = false;
		for(int i = 0; i < stringTokens.size() - 1; i++) { // 마지막 문자열 조각은 개행문자가 필요없음
			if(stringTokens.get(i).getString().toLowerCase().equals("between"))
				betweenFlag = true;
			if(newLineCheck(stringTokens.get(i), stringTokens.get(i+1))) { // BETWEEN A AND B
				if(betweenFlag) {
					betweenFlag = false;
					continue;
				} else
					stringTokens.get(i).addNewLine();
			}
		}
	}
	
	public void insertIndentation() { // 들여쓰기 추가
		boolean byFlag = false;
		for(int i = 0; i < stringTokens.size(); i++) {
			for(int j = 0; j < stringTokens.get(i).getPriority(); j++) // 들여쓰기
				stringTokens.get(i).addIndentation(option.getIndentation());
			if(byFlag && option.getStyle() == FormatOptions.STYLE_TWO)
				stringTokens.get(i).addSomeSpaceInParantheses();
			if(stringTokens.get(i).getString().toLowerCase().contains("by"))
				byFlag = true;
		}
	}
	
	public String resultString() {
		StringBuilder result = new StringBuilder("");
		for(int i = 0; i < stringTokens.size(); i++) {
			result.append(stringTokens.get(i).getString());
		}
		return result.toString().trim();
	}
	
	public boolean specialCharacterCheck(char c) { // 이하의 특수문자는 따로 분리함
		switch(c) {
		case ',': return true;
		case '(': return true;
		case ')': return true;
		case ';': return true;
		case '.': return true;
		case '*': return true;
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
		case "*": return true;
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
		ArrayList<StringToken> keywords = keywordList();
		s = s.toLowerCase().trim();
		for(int j = 0; j < keywords.size(); j++) {
			if(s.equals(keywords.get(j).getString()))
				return true;
		}
		return false;
	}
	
	public int keywordPriorityCheck(String s) {
		ArrayList<StringToken> keywords = keywordList();
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
	
	public boolean newLineCheck(StringToken current, StringToken next) { // 개행조건 체크
		// 다음 토큰과 우선순위가 다른 경우 & style one 옵션
		if((current.getPriority() != next.getPriority()) && (option.getStyle() == FormatOptions.STYLE_ONE)) return true;
		// 다음 토큰이 우선도 0인 keyword면서 이번 토큰이 여는 괄호가 아닌 경우 & style two 옵션
		if((keywordPriorityCheck(next.getString()) == 0) && !current.getString().equals("(") && (option.getStyle() == FormatOptions.STYLE_TWO)) return true;
		// 이번 토큰이 콤마인 경우 & after 옵션
		if((current.getType().equals("comma")) && (option.getLinebreakWithComma() == FormatOptions.AFTER)) return true;
		// 다음 토큰이 콤마인 경우 & before 또는 before with space 옵션
		if(next.getType().equals("comma") && ((option.getLinebreakWithComma() == FormatOptions.BEFORE) || (option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE))) return true;
		// 다음 토큰이 특정한 키워드인 경우
		if(newLineKeywordCheck(next.getString())) return true;
		else return false;
	}
	
	public boolean newLineKeywordCheck(String keyword) {
		keyword = keyword.toLowerCase().trim();
		switch(keyword) {
		case "and": return true;
		case "or": return true;
		case "join": return true;
		case "on": return true;
		default: return false;
		}
	}
	
	public boolean addSpaceCheck(StringToken current, StringToken next) { // 스페이스 한 칸 추가 조건 체크
		if(specialCharacterCheck(current.getString())) {
			if(current.getString().equals(")") && !(next.getString().equals(")") || next.getString().equals(")\n") || next.getString().equals(",")))
				return true;
			else if(option.getLinebreakWithComma() == FormatOptions.BEFORE_WITH_SPACE && current.getString().equals(","))
				return true;
			else if(current.getType().equals("commaFunction"))
				return true;
			else if(current.getString().equals("*"))
				return true;
			else return false;
		} else if(specialCharacterCheck(next.getString())) {
			if(keywordCheck(current.getString()) && !next.getString().equals(";"))
				return true;
			else if(next.getString().equals("*"))
				return true;
			else return false;
		} else if(next.getString().equals(",\n") || next.getString().equals(")\n"))
			return false;
		else return true;
	}
	
	public ArrayList<StringToken> keywordList() { // SQL 키워드 목록
		ArrayList<StringToken> keywords = new ArrayList<StringToken>();
		int twoWordsKeywordPriority = 0;
		if(option.getStyle() == FormatOptions.STYLE_TWO)
			twoWordsKeywordPriority = 1;
		keywords.add(new StringToken("select", 0));
		keywords.add(new StringToken("from", 0));
		keywords.add(new StringToken("where", 0));
		keywords.add(new StringToken("order", 0));
		keywords.add(new StringToken("group", 0));
		keywords.add(new StringToken("union", 0));
		keywords.add(new StringToken("join", 1));
		keywords.add(new StringToken("on", 1));
		keywords.add(new StringToken("by", twoWordsKeywordPriority));
		keywords.add(new StringToken("and", 1));
		keywords.add(new StringToken("or", 1));
		keywords.add(new StringToken("between", 1));
		keywords.add(new StringToken("desc", 1));
		keywords.add(new StringToken("as", 1));
		
		return keywords;
	}
}