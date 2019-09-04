package org.stones.reality.execution;

public class ExecutionFactory {

	private static ExecutionFactory instance = new ExecutionFactory();
	
	private ExecutionFactory() {
		
	}
	
	public static ExecutionFactory getInstance() {
		return instance;
	}
	
	public IExecution getExecution() {
		return new Execution();
	}
}
