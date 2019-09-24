package org.stones.reality.execution;


import java.sql.Connection;


public class ExecutionFactory {

	private static ExecutionFactory instance = new ExecutionFactory();
	
	
	private ExecutionFactory() {
		
	}
	
	public static ExecutionFactory getInstance() {
		return instance;
	}

	public IExecution getExecution(Connection con) {
		return new Execution(con);

	}
}
