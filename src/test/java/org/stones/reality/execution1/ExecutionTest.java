package org.stones.reality.execution1;

import java.sql.Connection;

import org.junit.Test;
import org.stones.reality.connection.ConnectionFactory;
import org.stones.reality.connection.DBModel;
import org.stones.reality.connection.IConnection;
import org.stones.reality.execution.ExecutionFactory;
import org.stones.reality.execution.ExplainPlanFactory;
import org.stones.reality.execution.IExecution;
import org.stones.reality.execution.IExplainPlan;

public class ExecutionTest {

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

		IExecution e = ExecutionFactory.getInstance().getExecution(conn);
		// String plan = e.checkPlan("select * from order_m");
		// System.out.println(plan);

		e.executeQuery("select * from order_m");
		
		IExplainPlan explainPlan = ExplainPlanFactory.getInstance().getExplainPlan(conn);
		explainPlan.checkPlan("select * from order_m");


	}

}
