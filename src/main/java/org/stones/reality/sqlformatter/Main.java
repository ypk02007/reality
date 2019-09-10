package org.stones.reality.sqlformatter;

public class Main {
	public static void main(String[] args) {
		Beautifier bf = new Beautifier();
		Beautifier bf2 = new Beautifier();
		String sql = "SELECT tT1.cOl01, Tt1.Col02, tT2.coL01 fRoM taBlE01 tT1, (select col01, col04, col05 from TablE02 where col02 < 25) Tt2, table03 Tt3 wHERe tT1.col01 = 'aAaA' or tT1.col01 = 'aAAa' AND (Tt2.cOl04 = 'bBBb' And (Tt2.Col05 = 200 or tT2.cOL05 = fuNC())) aND Tt1.col01 != 'DdDD' order by Tt1.col01 desc;";
		String sql2 = "select \r\n" + 
				"obj.run, obj.camCol, str(obj.field, 3) as field, \r\n" + 
				"str(obj.psfMag_z - 0*obj.extinction_z, 6, 3) as z, \r\n" + 
				"str(60*distance, 3, 1) as D, \r\n" + 
				"dbo.fField(neighborObjId) as nfield, \r\n" + 
				"str(dbo.fObj(neighborObjId), 4) as nid,'new' as 'new' \r\n" + 
				"from \r\n" + 
				"(select obj.objId, \r\n" + 
				"psfMag_z, extinction_z, \r\n" + 
				"NN.neighborObjId, NN.distance \r\n" + 
				"from photoObj as obj \r\n" + 
				"join neighbors as NN on obj.objId = NN.objId \r\n" + 
				"where \r\n" + 
				"60*NN.distance between 0 and 15 and \r\n" + 
				"NN.mode = 1 and NN.neighborMode = 1 and \r\n" + 
				"run = 756 and camCol = 5 and \r\n" + 
				"obj.type = 6 and (obj.flags & 0x40006) = 0 and \r\n" + 
				"nchild = 0 and obj.psfMag_i < 20 and \r\n" + 
				"(g - r between 0.3 and 1.1 and r - i between -0.1 and 0.6) \r\n" + 
				") as obj \r\n" + 
				"join photoObj as nobj on nobj.objId = obj.neighborObjId \r\n" + 
				"where \r\n" + 
				"nobj.run = obj.run and \r\n" + 
				"(abs(obj.psfMag_g - nobj.psfMag_g) < 0.5 or \r\n" + 
				"abs(obj.psfMag_r - nobj.psfMag_r) < 0.5 or \r\n" + 
				"abs(obj.psfMag_i - nobj.psfMag_i) < 0.5) \r\n" + 
				"order by obj.run, obj.camCol, obj.field;";
		
		String result = "";
		
		bf.getBeautifierOption().setColumnNameCase(FormatOptions.UPPERCASE);
		bf.getBeautifierOption().setTableNameCase(FormatOptions.INITCAP);
		//bf.getBeautifierOption().setStackAlign(FormatOptions.ALIGN_RIGHT);
		//bf.getBeautifierOption().setStyle(FormatOptions.STYLE_TWO);
		result = bf.beautifier(sql);
		System.out.println(result);
		System.out.println("\n=======================================\n");
		//bf2.getBeautifierOption().setStyle(FormatOptions.STYLE_TWO);
		bf2.getBeautifierOption().setStackAlign(FormatOptions.ALIGN_RIGHT);
		result = bf2.beautifier(sql2);
		System.out.println(result);
	}
}
