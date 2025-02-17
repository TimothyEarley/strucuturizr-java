package com.structurizr.inspection.view;

import com.structurizr.Workspace;
import com.structurizr.inspection.Inspector;
import com.structurizr.inspection.Violation;

public class EmptyViewsInspection extends AbstractViewsInspection {

    public EmptyViewsInspection(Inspector inspector) {
        super(inspector);
    }

    @Override
    public Violation inspect(Workspace workspace) {
        if (workspace.getViews().isEmpty()) {
            return violation("This workspace has no views.");
        }

        return noViolation();
    }

    @Override
    protected String getType() {
        return "views.empty";
    }

}