package org.stones.reality.execution;

import java.util.ArrayList;
import java.util.List;

public class ResultSetInfo {

	private List<String> ResultSet = new ArrayList<>();
	private int Row;
	private double RunTime;

	public List<String> getResultSet() {
		return ResultSet;
	}

	public void setResultSet(List<String> resultSet) {
		ResultSet = resultSet;
	}

	public int getRow() {
		return Row;
	}

	public void setRow(int row) {
		Row = row;
	}

	public double getRunTime() {
		return RunTime;
	}

	public void setRunTime(double runTime) {
		RunTime = runTime;
	}

	@Override
	public String toString() {
		return "ResultSetInfo [ResultSet=" + ResultSet + ", Row=" + Row + ", RunTime=" + RunTime + "]";
	}

}
