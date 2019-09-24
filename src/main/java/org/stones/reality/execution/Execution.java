package org.stones.reality.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class Execution implements IExecution {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData rsmd = null;
	ResultSetInfo resultInfo = null;

	IExplainPlan iExplainPlan = null;

	public Execution(Connection con) {
		this.con = con;
		iExplainPlan = ExplainPlanFactory.getInstance().getExplainPlan(con);
	}

	public static void main(String[] args) {
		/*
		 * Execution e = new Execution();
		 * e.connection("jdbc:oracle:thin:@localhost:1521:xe", "user01", "user01");
		 * e.executeQuery("select * from order_m");
		 * e.checkPlan("select * from order_m");
		 */

	}

	/*
	 * @Override public void connection(String url, String username, String
	 * password) { try { Class.forName("oracle.jdbc.driver.OracleDriver"); con =
	 * DriverManager.getConnection(url, username, password);
	 * System.out.println("연결되었습니다."); } catch (Exception e) { e.printStackTrace();
	 * } }
	 */

	@Override
	public void commit() {
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("commit");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
  
	public void rollback() {
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("rollback");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSetInfo executeQuery(String sql) {

		StringBuffer sb = new StringBuffer();
		List<String> result = new ArrayList<>();
		resultInfo = new ResultSetInfo();

		long start = System.currentTimeMillis();

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			stmt.setFetchSize(10);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				result.add(rs.getString(1));
				System.out.println(rs.getString(1));

				// sb.append(rs.getString(1) + "\n");

			}

			rs.last();
			int row = rs.getRow();
			resultInfo.setRow(row);

			resultInfo.setResultSet(result);

			// sb.append("쿼리의 개수: " + row + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		resultInfo.setRunTime((end - start) / 1000.0);

		// sb.append("실행시간 : " + (end - start) / 1000.0 + "초" + "\n");

		return resultInfo;

	}


}