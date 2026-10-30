package com.example.myapp.client.tree.view;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.tree.NodeAction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NodeTreeRowWidget extends Composite {

    private static final String STYLE_NODE_CONTAINER = "node-container";
    private static final String STYLE_CHILDREN_CONTAINER = "children-container";
    private static final String STYLE_INDENT = "indent";
    private static final String STYLE_CHILDREN_WRAPPER = "children-wrapper";
    private static final String STYLE_TOGGLE_BUTTON = "toggle-button";
    private static final String STYLE_TREE_ROW = "tree-row";
    private static final String STYLE_TREE_ROW_ACTIVE = STYLE_TREE_ROW + "-active";

    private static final String BUTTON_COLLAPSE = "-";

    private NodeSelectHandler onNodeSelect;
    private HandlerRegistration clickRegistration;

    private FlowPanel main;
    private FlowPanel nodeRow;
    private FlowPanel childrenContainer;
    private FlowPanel indent;

    private Long id;

    public NodeTreeRowWidget(NodeViewData node) {
        if (node == null) {
            throw new NullPointerException("Node is null!");
        }

        id = node.getId();
        build(node);
        fillChildrenWidgets(node);
        initWidget(main);
    }

    public Long getId() {
        return id;
    }

    public void setCaptionVisible(Boolean isCaptionVisible) {
        nodeRow.setVisible(isCaptionVisible);
        indent.setVisible(isCaptionVisible);
    }

    private void build(NodeViewData node) {
        main = new FlowPanel();
        main.addStyleName(STYLE_NODE_CONTAINER);

        nodeRow = createNodeRow(node);
        main.add(nodeRow);

        if (node.isEmpty()) {
            return;
        }

        FlowPanel children = createChildrenPanel(node);
        main.add(children);
    }

    private void fillChildrenWidgets(NodeViewData node) {
        List<NodeViewData> children = node.getChildren();
        for (NodeViewData child : children) {
            NodeTreeRowWidget childWidget = new NodeTreeRowWidget(child);
            childrenContainer.add(childWidget);
        }
    }

    private FlowPanel createNodeRow(NodeViewData node) {
        FlowPanel resultNodeRow = new FlowPanel();
        resultNodeRow.addStyleName(STYLE_TREE_ROW);

        Button toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        resultNodeRow.add(toggleButton);

        resultNodeRow.add(new Label(node.getName()));

        return resultNodeRow;
    }

    private FlowPanel createChildrenPanel(NodeViewData node) {
        if (node == null || node.isEmpty()) {
            return null;
        }

        FlowPanel panel = new FlowPanel();
        panel.addStyleName(STYLE_CHILDREN_WRAPPER);

        indent = new FlowPanel();
        indent.addStyleName(STYLE_INDENT);

        childrenContainer = new FlowPanel();
        childrenContainer.addStyleName(STYLE_CHILDREN_CONTAINER);

        panel.add(indent);
        panel.add(childrenContainer);

        return panel;
    }

    private void traversal(NodeAction action) {
        if (childrenContainer != null) {
            for (int i = 0; i < childrenContainer.getWidgetCount(); i++) {
                Widget widget = childrenContainer.getWidget(i);
                if (widget instanceof NodeTreeRowWidget) {
                    NodeTreeRowWidget nodeRowWidget = (NodeTreeRowWidget) widget;
                    action.function(nodeRowWidget);
                }
            }
        }
    }

    public void setNodeSelectHandler(NodeSelectHandler onSelect) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }

        this.onNodeSelect = onSelect;

        if (childrenContainer != null) {
            setChildrenNodeSelectHandler(onSelect);
        }

        clickRegistration = nodeRow.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setSelected(true);
                if (onNodeSelect != null) {
                    onNodeSelect.onNodeSelect(id);
                }
            }
        }, ClickEvent.getType());
    }

    private void setChildrenNodeSelectHandler(final NodeSelectHandler onSelect) {
        traversal(new NodeAction() {
            @Override
            public void function(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.setNodeSelectHandler(onSelect);
            }
        });
    }

    public void setSelected(Boolean isSelected) {
        if (isSelected) {
            nodeRow.addStyleName(STYLE_TREE_ROW_ACTIVE);
        } else {
            nodeRow.removeStyleName(STYLE_TREE_ROW_ACTIVE);
        }
    }

    public void unselectOtherNodes(Long selectedNodeId) {
        if (!id.equals(selectedNodeId)) {
            setSelected(false);
        }
        if (childrenContainer != null) {
            unselectChildren(selectedNodeId);
        }
    }

    private void unselectChildren(final Long selectedNodeId) {
        traversal(new NodeAction() {
            @Override
            public void function(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.unselectOtherNodes(selectedNodeId);
            }
        });
    }
}
