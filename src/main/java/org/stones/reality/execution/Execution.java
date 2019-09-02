package org.stones.reality.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Execution implements IExecution {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData rsmd = null;

	public static void main(String[] args) {
		Execution e = new Execution();
		e.connection("jdbc:oracle:thin:@localhost:1521:xe", "user01", "user01");
		e.executeQuery("select * from order_m");
		e.checkPlan("select * from order_m");

	}

	public void connection(String url, String username, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, username, password);
			System.out.println("연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rollback() {
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("rollback");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String executeQuery(String sql) {

		StringBuffer sb = new StringBuffer();

		long start = System.currentTimeMillis();

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();

			while (rs.next()) {
				sb.append(rs.getString(1) + "\n");
			}
			rs.last();
			int row = rs.getRow();
			sb.append("쿼리의 개수: " + row + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		sb.append("실행시간 : " + (end - start) / 1000.0 + "초" + "\n");

		return sb.toString();

	}

	public String checkPlan(String sql) {
		StringBuffer sb = new StringBuffer();
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("explain plan for " + sql);
			rs = stmt.executeQuery("select plan_table_output from table(dbms_xplan.display())");
			while (rs.next()) {
				sb.append(rs.getString(1) + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}