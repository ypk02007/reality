package org.stones.reality.execution;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OracleExplainPlan implements IExplainPlan {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	public OracleExplainPlan(Connection con) {
		this.con = con;
	}

	ExplainPlanInfo explainPlanInfo = null;
	private List<String> statementId = new ArrayList<String>();
	private List<Date> timeStamp = new ArrayList<Date>();
	private List<String> remarks = new ArrayList<String>();
	private List<String> operation = new ArrayList<String>();
	private List<String> options = new ArrayList<String>();
	private List<String> objectNode = new ArrayList<String>();
	private List<String> objectOwner = new ArrayList<String>();
	private List<String> objectName = new ArrayList<String>();
	private List<Integer> objectInstance = new ArrayList<Integer>();
	private List<String> objectType = new ArrayList<String>();
	private List<String> optimizer = new ArrayList<String>();
	private List<Integer> searchColumns = new ArrayList<Integer>();
	private List<Integer> id = new ArrayList<Integer>();
	private List<Integer> parentId = new ArrayList<Integer>();
	private List<Integer> position = new ArrayList<Integer>();
	private List<Integer> cost = new ArrayList<Integer>();
	private List<Integer> cardinality = new ArrayList<Integer>();
	private List<Integer> bytes = new ArrayList<Integer>();
	private List<String> otherTag = new ArrayList<String>();
	private List<String> partitionStart = new ArrayList<String>();
	private List<String> partitionStop = new ArrayList<String>();
	private List<Integer> partitionId = new ArrayList<Integer>();
	private List<Long> other = new ArrayList<Long>();
	private List<String> distribution = new ArrayList<String>();

	@Override
	public ExplainPlanInfo checkPlan(String sql) {

		explainPlanInfo = new ExplainPlanInfo();

		StringBuffer sb = new StringBuffer();
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("explain plan for " + sql);
			rs = stmt.executeQuery("select * from table(dbms_xplan.display())");
			while (rs.next()) {
				sb.append(rs.getString(1) + "\n");
				explainPlanInfo.setPlanDisplay(sb);
			}
			rs = stmt.executeQuery("select statement_id,timestamp,remarks,operation,options,object_node,"
					+ "object_owner,object_name,object_instance,object_type,optimizer,search_columns,id,"
					+ "parent_id,position,cost,cardinality,bytes,other_tag,partition_start,partition_stop,"
					+ "partition_id,other,distribution from plan_table");

			while (rs.next()) {
				System.out.println(rs.getString(2));
				statementId.add(rs.getString("statement_id"));
				timeStamp.add(rs.getDate("timestamp"));
				remarks.add(rs.getString("remarks"));
				operation.add(rs.getString("operation"));
				options.add(rs.getString("options"));
				objectNode.add(rs.getString("object_node"));
				objectOwner.add(rs.getString("object_owner"));
				objectName.add(rs.getString("object_name"));
				objectInstance.add(rs.getInt("object_instance"));
				objectType.add(rs.getString("object_type"));
				optimizer.add(rs.getString("optimizer"));
				searchColumns.add(rs.getInt("search_columns"));
				id.add(rs.getInt("id"));
				parentId.add(rs.getInt("parent_id"));
				position.add(rs.getInt("position"));
				cost.add(rs.getInt("cost"));
				cardinality.add(rs.getInt("cardinality"));
				bytes.add(rs.getInt("bytes"));
				otherTag.add(rs.getString("other_tag"));
				partitionStart.add(rs.getString("partition_start"));
				partitionStop.add(rs.getString("partition_stop"));
				partitionId.add(rs.getInt("partition_id"));
				other.add(rs.getLong("other"));
				distribution.add(rs.getString("distribution"));
			}

			explainPlanInfo.setStatementId(statementId);
			explainPlanInfo.setTimeStamp(timeStamp);
			explainPlanInfo.setRemarks(remarks);
			explainPlanInfo.setOperation(operation);
			explainPlanInfo.setOptions(options);
			explainPlanInfo.setObjectNode(objectNode);
			explainPlanInfo.setObjectOwner(objectOwner);
			explainPlanInfo.setObjectName(objectName);
			explainPlanInfo.setObjectInstance(objectInstance);
			explainPlanInfo.setObjectType(objectType);
			explainPlanInfo.setOptimizer(optimizer);
			explainPlanInfo.setSearchColumns(searchColumns);
			explainPlanInfo.setId(id);
			explainPlanInfo.setParentId(parentId);
			explainPlanInfo.setPosition(position);
			explainPlanInfo.setCost(cost);
			explainPlanInfo.setCardinality(cardinality);
			explainPlanInfo.setBytes(bytes);
			explainPlanInfo.setOtherTag(otherTag);
			explainPlanInfo.setPartitionStart(partitionStart);
			explainPlanInfo.setPartitionStop(partitionStop);
			explainPlanInfo.setPartitionId(partitionId);
			explainPlanInfo.setOther(other);
			explainPlanInfo.setDistribution(distribution);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return explainPlanInfo;
		// return sb.toString();
	}
}
