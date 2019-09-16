package org.stones.reality.connection;

public class DBModel {
	private String username;
	private String password;
	private String url;
	private String drivername;
	private String dbname;
	private String host;
	private String servicename;
	private int port;
	
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getUrl() {return url;}
	public String getDrivername() {return drivername;}
	public String getDbname() {return dbname;}
	public void setDbname(String dbname) {this.dbname = dbname;}
	public String getHost() {return host;}
	public void setHost(String host) {this.host = host;}
	public int getPort() {return port;}
	public void setPort(int port) {this.port = port;}
	public String getServicename() {return servicename;}
	public void setServicename(String servicename) {this.servicename = servicename;}
	
	
	//url, drivename 포맷
	public void driveurlFormat(String name) {
		switch(name){
			case "oracle" :
				url = "jdbc:oracle:thin:@"+host+":"+port+":"+servicename;
				drivername = "oracle.jdbc.driver.OracleDriver";
				break;
			case "postgresql" :
				url = "jdbc:postgresql://"+host+":"+port+"/"+servicename;
				drivername = "org.postgresql.Driver";
				break;
			
			default:
				break;
			
		}
		
	}

}