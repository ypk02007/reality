package org.stones.reality.execution;

public interface IExecution {

	/**
	 * @param url
	 * @param username
	 * @param password
	 */

	abstract ResultSetInfo executeQuery(String sql);

	abstract void commit();

	abstract void rollback();

}
