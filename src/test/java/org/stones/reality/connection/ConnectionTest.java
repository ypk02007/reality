package org.stones.reality.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;


public class ConnectionTest {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ConnectionTest.class);
	
	@Test
	public void test() {
		IConnection connectionModule = ConnectionFactory.getInstance().makeModule();
		DBModel dbModel = new DBModel();
		
		dbModel.setDbname("oracle");
		dbModel.setHost("localhost");
		dbModel.setPassword("user01");
		dbModel.setUsername("user01");
		dbModel.setPort(1521);
		dbModel.setVersion("xe");
		
		Connection conn = connectionModule.connect(dbModel);
		
		try {
			Statement st = conn.createStatement();
			String sql = "select * from member";
			
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				logger.info("===="+rs.getInt(1)+rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void test2() {
		IConnection connectionModule = ConnectionFactory.getInstance().makeModule();
		DBModel dbModel = new DBModel();
		
		dbModel.setDbname("postgresql");
		dbModel.setHost("localhost");
		dbModel.setPassword("postgres");
		dbModel.setUsername("postgres");
		dbModel.setPort(5439);
		
		Connection conn = connectionModule.connect(dbModel);
		
	}
}
