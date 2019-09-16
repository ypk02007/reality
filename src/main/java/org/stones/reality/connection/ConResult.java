package org.stones.reality.connection;

import java.sql.Connection;

public class ConResult {
	private Connection conn = null;
	private boolean isConnected;
	private String msg;
	
	public Connection getConn() {return conn;}
	public void setConn(Connection conn) {this.conn = conn;}
	public boolean isConnected() {return isConnected;}
	public void setConnected(boolean isConnected) {this.isConnected = isConnected;}
	public String getMsg() {return msg;}
	public void setMsg(String msg) {this.msg = msg;}

}

