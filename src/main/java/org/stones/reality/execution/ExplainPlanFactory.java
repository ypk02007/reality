package org.stones.reality.execution;

import java.sql.Connection;
import java.sql.SQLException;

public class ExplainPlanFactory {
	private static ExplainPlanFactory instance = new ExplainPlanFactory();

	private ExplainPlanFactory() {
		
	}

	public static ExplainPlanFactory getInstance() {
		return instance;
	}

	public IExplainPlan getExplainPlan(Connection con) {
		String dbmsName = "oracle";
		
		try {
			dbmsName = con.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(dbmsName) {
		case "oracle" : return new OracleExplainPlan(con);
		default : return new OracleExplainPlan(con); 
		//case "postgres" : 
		}
		
	}
}
