package org.stones.reality.execution;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

public class ExplainPlanInfo {

	private StringBuffer planDisplay;
	private String statementId;
	private Date timeStamp;
	private String remarks;
	private String operation;
	private String options;
	private String objectNode;
	private String objectOwner;
	private String objectName;
	private int objectInstance;
	private String objectType;
	private String optimizer;
	private int searchColumns;
	private int id;
	private int parentId;
	private int position;
	private int cost;
	private int cardinality;
	private int bytes;
	private String otherTag;
	private String partitionStart;
	private String partitionStop;
	private int partitionId;
	private Long other;
	private String distribution;

	public StringBuffer getPlanDisplay() {
		return planDisplay;
	}

	public void setPlanDisplay(StringBuffer planDisplay) {
		this.planDisplay = planDisplay;
	}


	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getObjectNode() {
		return objectNode;
	}

	public void setObjectNode(String objectNode) {
		this.objectNode = objectNode;
	}

	public String getObjectOwner() {
		return objectOwner;
	}

	public void setObjectOwner(String objectOwner) {
		this.objectOwner = objectOwner;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(int objectInstance) {
		this.objectInstance = objectInstance;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getOptimizer() {
		return optimizer;
	}

	public void setOptimizer(String optimizer) {
		this.optimizer = optimizer;
	}

	public int getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(int searchColumns) {
		this.searchColumns = searchColumns;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public int getBytes() {
		return bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}

	public String getOtherTag() {
		return otherTag;
	}

	public void setOtherTag(String otherTag) {
		this.otherTag = otherTag;
	}

	public String getPartitionStart() {
		return partitionStart;
	}

	public void setPartitionStart(String partitionStart) {
		this.partitionStart = partitionStart;
	}

	public String getPartitionStop() {
		return partitionStop;
	}

	public void setPartitionStop(String partitionStop) {
		this.partitionStop = partitionStop;
	}

	public int getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(int partitionId) {
		this.partitionId = partitionId;
	}

	public Long getOther() {
		return other;
	}

	public void setOther(Long other) {
		this.other = other;
	}

	public String getDistribution() {
		return distribution;
	}

	
}
