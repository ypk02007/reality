package org.stones.reality.execution;

public interface IExecution {
	
	/**
	 * @param url
	 * @param username
	 * @param password
	 */
	abstract void connection(String url,String username,String password);
	
	abstract String checkPlan(String sql);
	
	abstract String executeQuery(String sql);
	
	abstract void commit();
	
	abstract void rollback();
	
	
}
