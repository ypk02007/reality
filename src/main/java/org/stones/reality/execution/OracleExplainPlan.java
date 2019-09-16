package org.stones.reality.execution;

import java.sql.Connection;

import java.sql.ResultSet;

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


	// ExplainPlanInfo explainPlanInfo = null;

	@Override
	public PlanHandler checkPlan(String sql) {

		List<ExplainPlanInfo> planInfoList = new ArrayList<ExplainPlanInfo>();
		PlanHandler planHandler = new PlanHandler();
		ExplainPlanInfo explainPlanInfo = new ExplainPlanInfo();


		StringBuffer sb = new StringBuffer();
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			stmt.executeQuery("explain plan set statement_id = " + 1 + " for " + sql);

			rs = stmt.executeQuery("select * from table(dbms_xplan.display())");
			while (rs.next()) {
				sb.append(rs.getString(1) + "\n");
				explainPlanInfo.setPlanDisplay(sb);
			}

			
			rs = stmt.executeQuery("select statement_id,timestamp,remarks,operation,options,object_node,"
					+ "object_owner,object_name,object_instance,object_type,optimizer,search_columns,id,"
					+ "parent_id,position,cost,cardinality,bytes,other_tag,partition_start,partition_stop,"
					+ "partition_id,other,distribution from plan_table where statement_id =? ");

			while (rs.next()) {

				// System.out.println(rs.getString(2));

				explainPlanInfo.setStatementId(rs.getString("statement_id"));
				explainPlanInfo.setTimeStamp(rs.getDate("timestamp"));
				explainPlanInfo.setRemarks(rs.getString("remarks"));
				explainPlanInfo.setOperation(rs.getString("operation"));
				explainPlanInfo.setOptions(rs.getString("options"));
				explainPlanInfo.setObjectNode(rs.getString("object_node"));
				explainPlanInfo.setObjectOwner(rs.getString("object_owner"));
				explainPlanInfo.setObjectName(rs.getString("object_name"));
				explainPlanInfo.setObjectInstance(rs.getInt("object_instance"));
				explainPlanInfo.setObjectType(rs.getString("object_type"));
				explainPlanInfo.setOptimizer(rs.getString("optimizer"));
				explainPlanInfo.setSearchColumns(rs.getInt("search_columns"));
				explainPlanInfo.setId(rs.getInt("id"));
				explainPlanInfo.setParentId(rs.getInt("parent_id"));
				explainPlanInfo.setPosition(rs.getInt("position"));
				explainPlanInfo.setCost(rs.getInt("cost"));
				explainPlanInfo.setCardinality(rs.getInt("cardinality"));
				explainPlanInfo.setBytes(rs.getInt("bytes"));
				explainPlanInfo.setOtherTag(rs.getString("other_tag"));
				explainPlanInfo.setPartitionStart(rs.getString("partition_start"));
				explainPlanInfo.setPartitionStop(rs.getString("partition_stop"));
				explainPlanInfo.setParentId(rs.getInt("partition_id"));
				explainPlanInfo.setOther(rs.getLong("other"));
				explainPlanInfo.setDistribution(rs.getString("distribution"));
				planInfoList.add(explainPlanInfo);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		planHandler.setExplainPlanInfo(planInfoList);
		return planHandler;

		// return sb.toString();
	}
}
