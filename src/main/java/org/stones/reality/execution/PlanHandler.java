package org.stones.reality.execution;

import java.util.ArrayList;
import java.util.List;

public class PlanHandler {
	
	List<ExplainPlanInfo> explainPlanInfo = new ArrayList<ExplainPlanInfo>();

	public void DisplayPlan() {
		
	}
	
	public List<ExplainPlanInfo> getExplainPlanInfo() {
		return explainPlanInfo;
	}

	public void setExplainPlanInfo(List<ExplainPlanInfo> explainPlanInfo) {
		this.explainPlanInfo = explainPlanInfo;
	}
	
}
