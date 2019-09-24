package org.stones.reality.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionModule implements IConnection {
	private Connection conn = null;
	private static final Logger logger = LoggerFactory.getLogger(ConnectionModule.class);
	private boolean isConnected = false;
	
	@SuppressWarnings("unused")
	private String msg = null;

	@Override
	public ConResult connect(DBModel dbModel) {
		dbModel.driveurlFormat(dbModel.getDbname());
		
		ConResult conRe = new ConResult();
		
		try {
			
			InetAddress tagetIp = InetAddress.getByName(dbModel.getHost());
 
			//핑테스트
			if (tagetIp.isReachable(2000)) {
				logger.info("핑테스트 통과");
			} else {
				logger.info("핑테스트 실패");
			}
			
			//드라이브 연결
			Class.forName(dbModel.getDrivername());
			logger.info("드라이브 연결 성공 "+dbModel.getUrl());
			
			//Connection 생성
			conn = DriverManager.getConnection(dbModel.getUrl(), dbModel.getUsername(), dbModel.getPassword());
			
			if(conn.isValid(1000)) {
				logger.info("Connection 성공적으로 생성");
				isConnected = true;
				msg = "Connection 성공적으로 생성";
			}else {
				logger.info("Connection 생성 실패");
				msg = "Connection 생성 실패";
			}

			

			
		}catch (SQLException e) {
			msg = "Connection 생성 실패";
			isConnected = false;
			logger.info("디비 연결 실패");

		}catch(IOException e) {
			msg = "핑테스트  실패";
			isConnected = false;
			logger.info("핑테스트 실패");
		}catch(ClassNotFoundException e) {
			msg = "드라이브 연결 실패";
			isConnected = false;
			logger.info("드라이브 연결 실패");
		}
		
		conRe.setConn(conn);
		conRe.setConnected(isConnected);
		conRe.setMsg(msg);
		return conRe;
		
	}

}