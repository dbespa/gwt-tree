package com.example.myapp.client.widget;

import com.example.myapp.client.handler.NodeSelectHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class UniversalRoot {

    private FlowPanel root;

    public UniversalRoot(FlowPanel root) {
        this.root = root;
    }

    public void add(NodeTreeRowWidget nodeWidget) {
        if (root != null) {
            root.add(nodeWidget);
        }
    }

    private void traversal(NodeAction action) {
        if (root != null) {
            for (int i = 0; i < root.getWidgetCount(); i++) {
                Widget widget = root.getWidget(i);
                if (widget instanceof NodeTreeRowWidget) {
                    NodeTreeRowWidget treeWidget = (NodeTreeRowWidget) widget;
                    action.function(treeWidget);
                }
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
