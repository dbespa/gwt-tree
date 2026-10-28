package com.example.myapp.client;

import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.widget.NodeTreeRowWidget;
import com.google.gwt.user.client.ui.FlowPanel;

public class UniversalRoot {

    private FlowPanel root;

    public UniversalRoot(FlowPanel root) {
        this.root = root;
    }

    public void add(NodeTreeRowWidget nodeWidget) {
        root.add(nodeWidget);
    }

    public void traversal(NodeAction action) {
        if (root != null) {
            for (int i = 0; i < root.getWidgetCount(); i++) {
                NodeTreeRowWidget widget = (NodeTreeRowWidget) root.getWidget(i);
                action.function(widget);
            }
        }
    }

    public void setNodeSelectHandler(final NodeSelectHandler onSelect) {
        traversal(new NodeAction() {
            @Override
            public void function(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.setNodeSelectHandler(onSelect);
            }
        });
    }

    public void setOtherNodesUnselected(final Long selectedNodeId) {
        traversal(new NodeAction() {
            @Override
            public void function(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.setOtherNodesUnselected(selectedNodeId);
            }
        });
    }
}
