package org.stones.reality.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;


public class OracleMetaData implements IMetaData {

	Connection conn = null;
	String query = null;
	String schemaName = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet table = null;
	ResultSetMetaData rsmd = null;
	DatabaseMetaData data = null;

	public void connection(String url, String username, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("����Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DatabaseInfo getDatabaseMetaData() {

		DatabaseInfo databaseInfo = new DatabaseInfo();

		try {
			data = conn.getMetaData();

			databaseInfo.setDriverName(data.getDriverName());
			databaseInfo.setProductName(data.getDatabaseProductName());
			databaseInfo.setProductVersion(data.getDatabaseProductVersion());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return databaseInfo;
	}

	@Override
	public ArrayList<String> getSchemaList() {

		ArrayList<String> schemaList = new ArrayList<String>();
		try {
			rs = data.getSchemas();

			while (this.rs.next()) {
				schemaList.add(rs.getString(1));
			}
			/*
			 * for (int i=0; i<schemaList.size(); i++) {
			 * System.out.println(schemaList.get(i)); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemaList;
	}

	@Override
	public TableInfo getTableInfo(String schemaName1) {

		TableInfo tableInfo = new TableInfo();

		try {
			data = conn.getMetaData();
			//System.out.println("��Ű�� �� : " + schemaName1);
			table = data.getTables(null, schemaName1, null, new String[] { "TABLE" });

			while (table.next()) {
				tableInfo.setTableName(table.getString("TABLE_NAME"));
				tableInfo.setTableCat(table.getString("TABLE_CAT"));
				tableInfo.setTableType(table.getString("TABLE_TYPE"));
				tableInfo.setTableSchem(table.getString("TABLE_SCHEM"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableInfo;
	}

	@Override
	public ColumnInfo getColumnNameInfo(String tableName) {

		ColumnInfo columnInfo = new ColumnInfo();
		query = "SELECT * FROM " + tableName;
		try {
			stmt = conn.createStatement();
			rs2 = stmt.executeQuery(query);
			rsmd = rs2.getMetaData();

			int cols = rsmd.getColumnCount();
			columnInfo.setColumnCnt(cols);
			for (int i = 0; i < cols; i++) {
				columnInfo.setColumnName(rsmd.getColumnName(i + 1));
				columnInfo.setColumnTypeName(rsmd.getColumnTypeName(i + 1));
				columnInfo.setColumnType(rsmd.getColumnType(i + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnInfo;
	}

	public static void main(String[] args) {

		String userName = "user01";
		String pw = "user01";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		String schemaName1 = "APPQOSSYS";
		String tableName1 = "WLM_CLASSIFIER_PLAN";

		OracleMetaData d = new OracleMetaData();
		d.connection(url, userName, pw);
		d.getDatabaseMetaData();
		d.getSchemaList();
		d.getTableInfo(schemaName1);
		d.getColumnNameInfo(tableName1);

	}
}
