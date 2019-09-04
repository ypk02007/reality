package org.stones.reality.connection;

public class DBModel {
	private String username;
	private String password;
	private String url;
	private String drivename;
	private String dbname;
	private String host;
	private String version;
	private int port;
	
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getUrl() {return url;}
	public String getDrivename() {return drivename;}
	public String getDbname() {return dbname;}
	public void setDbname(String dbname) {this.dbname = dbname;}
	public String getHost() {return host;}
	public void setHost(String host) {this.host = host;}
	public int getPort() {return port;}
	public void setPort(int port) {this.port = port;}
	public String getVersion() {return version;}
	public void setVersion(String version) {this.version = version;}
	
	
	//url, drivename 포맷
	public void driveurlFormat(String name) {
		switch(name){
			case "oracle" :
				url = "jdbc:oracle:thin:@"+host+":"+port+":"+version;
				drivename = "oracle.jdbc.driver.OracleDriver";
				break;
			case "postgresql" :
				url = "jdbc:postgresql://"+host+":"+port+"/";
				drivename = "org.postgresql.Driver";
				break;
			
			default:
				break;
			
		}
		
	}

}
