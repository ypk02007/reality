package org.stones.reality.connection;

public class ConnectionFactory {
	
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	
	
	public static ConnectionFactory getInstance() {
		return connectionFactory;
	}
	
	public IConnection makeModule() {
		return new ConnectionModule();
	}

}
