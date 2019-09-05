package org.stones.reality.sqlformatter;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeautifierTest {

	@Test
	public void testCase1() {
		String sql = "select s.name, d.name from student s, department d  where s.deptno = d.deptno;";
		String expected = "SELECT\n" + 
					"       s.name,\n" + 
				    "       d.name\n" + 
					"FROM  \n" + 
				    "       student s,\n" + 
					"       department d\n" + 
				    "WHERE \n" + 
					"       s.deptno = d.deptno;";
		
		Beautifier bf = new Beautifier();
		
		String result = bf.beautifier(sql);
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testCase2() {
		String sql = "select \r\n" + 
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
				"order by obj.run, obj.camCol, obj.field";
		String expected = "select \r\n" + 
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
				"order by obj.run, obj.camCol, obj.field";
		expected = expected.replaceAll(" \r\n", "\n");
		
		Beautifier bf = new Beautifier();
		bf.getBeautifierOption().setStyle(FormatOptions.STYLE_TWO);
		
		String result = bf.beautifier(sql);
		
		assertEquals(expected, result);
	}
}
