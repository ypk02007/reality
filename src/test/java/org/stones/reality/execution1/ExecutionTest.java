package org.stones.reality.execution1;

import java.sql.Connection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stones.reality.connection.ConResult;
import org.stones.reality.connection.ConnectionFactory;
import org.stones.reality.connection.DBModel;
import org.stones.reality.connection.IConnection;
import org.stones.reality.execution.ExecutionFactory;
import org.stones.reality.execution.ExplainPlanFactory;
import org.stones.reality.execution.IExecution;
import org.stones.reality.execution.IExplainPlan;
import org.stones.reality.execution.PlanHandler;

public class ExecutionTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTest.class);
	
	@Test
	public void test() {

		IConnection connectionModule = ConnectionFactory.getInstance().makeModule();
		DBModel dbModel = new DBModel();

		dbModel.setDbname("oracle");
		dbModel.setHost("localhost");
		dbModel.setPassword("edu");
		dbModel.setUsername("edu");
		dbModel.setPort(1521);
		dbModel.setServicename("xe");

		ConResult conResult = connectionModule.connect(dbModel);
		if(conResult.isConnected()) {
			Connection conn = conResult.getConn();
			IExecution e = ExecutionFactory.getInstance().getExecution(conn);
			// String plan = e.checkPlan("select * from order_m");
			// System.out.println(plan);

			e.executeQuery("select * from order_m");
			
			IExplainPlan explainPlan = ExplainPlanFactory.getInstance().getExplainPlan(conn);
			explainPlan.checkPlan("select * from order_detail");
			PlanHandler planHandler = explainPlan.checkPlan("select * from order_m");
			LOGGER.debug("{}", planHandler.getExplainPlanInfo().size());
		}


	}

}
