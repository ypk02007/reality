package org.stones.reality.execution;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

public class ExplainPlanInfo {

	private StringBuffer planDisplay;
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

	public StringBuffer getPlanDisplay() {
		return planDisplay;
	}

	public void setPlanDisplay(StringBuffer planDisplay) {
		this.planDisplay = planDisplay;
	}

	public List<String> getStatementId() {
		return statementId;
	}

	public void setStatementId(List<String> statementId) {
		this.statementId = statementId;
	}

	public List<Date> getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(List<Date> timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public List<String> getOperation() {
		return operation;
	}

	public void setOperation(List<String> operation) {
		this.operation = operation;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public List<String> getObjectNode() {
		return objectNode;
	}

	public void setObjectNode(List<String> objectNode) {
		this.objectNode = objectNode;
	}

	public List<String> getObjectOwner() {
		return objectOwner;
	}

	public void setObjectOwner(List<String> objectOwner) {
		this.objectOwner = objectOwner;
	}

	public List<String> getObjectName() {
		return objectName;
	}

	public void setObjectName(List<String> objectName) {
		this.objectName = objectName;
	}

	public List<Integer> getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(List<Integer> objectInstance) {
		this.objectInstance = objectInstance;
	}

	public List<String> getObjectType() {
		return objectType;
	}

	public void setObjectType(List<String> objectType) {
		this.objectType = objectType;
	}

	public List<String> getOptimizer() {
		return optimizer;
	}

	public void setOptimizer(List<String> optimizer) {
		this.optimizer = optimizer;
	}

	public List<Integer> getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(List<Integer> searchColumns) {
		this.searchColumns = searchColumns;
	}

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public List<Integer> getParentId() {
		return parentId;
	}

	public void setParentId(List<Integer> parentId) {
		this.parentId = parentId;
	}

	public List<Integer> getPosition() {
		return position;
	}

	public void setPosition(List<Integer> position) {
		this.position = position;
	}

	public List<Integer> getCost() {
		return cost;
	}

	public void setCost(List<Integer> cost) {
		this.cost = cost;
	}

	public List<Integer> getCardinality() {
		return cardinality;
	}

	public void setCardinality(List<Integer> cardinality) {
		this.cardinality = cardinality;
	}

	public List<Integer> getBytes() {
		return bytes;
	}

	public void setBytes(List<Integer> bytes) {
		this.bytes = bytes;
	}

	public List<String> getOtherTag() {
		return otherTag;
	}

	public void setOtherTag(List<String> otherTag) {
		this.otherTag = otherTag;
	}

	public List<String> getPartitionStart() {
		return partitionStart;
	}

	public void setPartitionStart(List<String> partitionStart) {
		this.partitionStart = partitionStart;
	}

	public List<String> getPartitionStop() {
		return partitionStop;
	}

	public void setPartitionStop(List<String> partitionStop) {
		this.partitionStop = partitionStop;
	}

	public List<Integer> getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(List<Integer> partitionId) {
		this.partitionId = partitionId;
	}

	public List<Long> getOther() {
		return other;
	}

	public void setOther(List<Long> other) {
		this.other = other;
	}

	public List<String> getDistribution() {
		return distribution;
	}

	public void setDistribution(List<String> distribution) {
		this.distribution = distribution;
	}


}
