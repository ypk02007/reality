package org.stones.reality.connection;

import java.sql.Connection;

public interface IConnection {
	public Connection connect(DBModel dbModel);
}
