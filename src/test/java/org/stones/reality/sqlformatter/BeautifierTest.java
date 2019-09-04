package org.stones.reality.sqlformatter;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeautifierTest {

	@Test
	public void test1() {
		String sql = "select s.name, d.name from student s, department d  where s.deptno = d.deptno;";
		String expected = "SELECT\n       s.name,\n       d.name\nFROM  \n       student s,\n       department d\nWHERE \n       s.deptno = d.deptno; ";
		
		Beautifier bf = new Beautifier();
		
		FormatOptions option = FormatOptions.getInstance();
		option.setDefaultOptions();
		String result = bf.beautifier(sql);
		
		assertEquals("다르다", expected, result);
	}

}
