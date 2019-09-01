package org.stones.reality.sqlformatter;

public class Main {
	public static void main(String[] args) {
		Beautifier bf = new Beautifier();
		String sql = "SELECT tT1.cOl01, Tt1.Col02, tT2.coL01 fRoM taBlE01 tT1, (select col01, col04, col05 from TablE02 where col02 < 25) Tt2, table03 Tt3 wHERe tT1.col01 = 'aAaA' or tT1.col01 = 'aAAa' AND (Tt2.cOl04 = 'bBBb' And (Tt2.Col05 = 200 or tT2.cOL05 = fuNC())) aND Tt1.col01 != 'DdDD' order by Tt1.col01 desc;";
		String result = "";
		FormatOptions option = FormatOptions.getInstance();
		option.setDefaultOptions();
		
		result = bf.beautifier(sql);
		System.out.println(result);
	}
}
