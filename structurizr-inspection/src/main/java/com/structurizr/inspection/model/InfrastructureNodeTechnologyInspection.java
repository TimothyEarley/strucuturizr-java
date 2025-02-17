package com.structurizr.inspection.model;

import com.structurizr.inspection.Inspector;
import com.structurizr.inspection.Violation;
import com.structurizr.model.InfrastructureNode;
import com.structurizr.util.StringUtils;

public class InfrastructureNodeTechnologyInspection extends AbstractInfrastructureNodeInspection {

    public InfrastructureNodeTechnologyInspection(Inspector inspector) {
        super(inspector);
    }

    @Override
    protected Violation inspect(InfrastructureNode infrastructureNode) {
        if (StringUtils.isNullOrEmpty(infrastructureNode.getTechnology())) {
            return violation("Add a technology to the " + terminologyFor(infrastructureNode).toLowerCase() + " named \"" + infrastructureNode.getName() + "\".");
        }

        return noViolation();
    }

    @Override
    protected String getType() {
        return "model.infrastructurenode.technology";
    }

}