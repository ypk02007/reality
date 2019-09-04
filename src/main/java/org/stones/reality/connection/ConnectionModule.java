package org.stones.reality.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class ConnectionModule implements IConnection {
	private Connection conn = null;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ConnectionModule.class);

	@Override
	public Connection connect(DBModel dbModel) {
		dbModel.driveurlFormat(dbModel.getDbname());
		try {
			
			InetAddress tagetIp = InetAddress.getByName(dbModel.getHost());
 
			//핑테스트
			if (tagetIp.isReachable(2000)) {
				logger.info("핑테스트 통과");
			} else {
				logger.info("핑테스트 실패");
			}
			
			//드라이브 연결
			Class.forName(dbModel.getDrivename());
			logger.info("드라이브 연결 성공 "+dbModel.getUrl());
			//Connection 생성
			conn = DriverManager.getConnection(dbModel.getUrl(), dbModel.getUsername(), dbModel.getPassword());
			logger.info("디비 연결 성공");
			


		} catch (SQLException | IOException | ClassNotFoundException e) {
			logger.info("디비 연결 실패");
		}
		return conn;
	}

}
