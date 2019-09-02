package org.stones.reality.execution1;

import org.junit.Test;
import org.stones.reality.execution.ExecutionFactory;
import org.stones.reality.execution.IExecution;

public class ExecutionTest {

	@Test
	public void test() {
		IExecution e = ExecutionFactory.getInstance().getExecution();
		e.connection("jdbc:oracle:thin:@localhost:1521:xe","user01","user01");
		String plan = e.checkPlan("select * from order_m");
		System.out.println(plan);
		String result = e.executeQuery("select * from order_m");
		System.out.println(result);
	}

}
