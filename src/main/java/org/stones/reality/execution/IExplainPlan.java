package org.stones.reality.execution;

public interface IExplainPlan {
	abstract ExplainPlanInfo checkPlan(String sql);
}
