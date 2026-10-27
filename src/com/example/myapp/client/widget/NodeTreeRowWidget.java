package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeTreeRowWidget extends Composite {

    private static final String STYLE_NODE_CONTAINER = "node-container";
    private static final String STYLE_CHILDREN_CONTAINER = "children-container";
    private static final String STYLE_INDENT = "indent";
    private static final String STYLE_CHILDREN_WRAPPER = "children-wrapper";
    private static final String STYLE_TOGGLE_BUTTON = "toggle-button";
    private static final String STYLE_TREE_ROW = "tree-row";
    private static final String STYLE_TREE_ROW_ACTIVE = STYLE_TREE_ROW + "-active";

    private static final String BUTTON_COLLAPSE = "-";

    private static final String LABEL_NODE = "(.)";
    private static final String LABEL_LEAF = "(*)";

    private NodeSelectHandler onNodeSelect;
    private HandlerRegistration clickRegistration;

    private FlowPanel main;
    private FlowPanel nodeRow;
    private FocusPanel wrapper;

    final private Long id;

    public NodeTreeRowWidget(NodeViewData node) {
        id = node.getId();

        if (node != null) {
            build(node);
            initWidget(main);
        }
    }

    public Long getId() {
        return id;
    }

    public void setNodeSelectHandler(NodeSelectHandler handler) {
        this.onNodeSelect = handler;

        if (handler == null && clickRegistration != null) {
            clickRegistration.removeHandler();
        }

        clickRegistration = wrapper.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setSelected(true);
                NodeSelectHandler handler = NodeTreeRowWidget.this.onNodeSelect;
                handler.onNodeSelect(id);
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

    private FlowPanel build(NodeViewData node) {
        if (node == null) {
            return null;
        }

        main = createNodeContainer(node);
        return main;
    }

    private FocusPanel createNodeRow(NodeViewData node) {
        wrapper = new FocusPanel();

        nodeRow = new FlowPanel();
        nodeRow.addStyleName(STYLE_TREE_ROW);

        Button toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        nodeRow.add(toggleButton);

        nodeRow.add(new Label(node.getName()));

        String strNodeType = node.isLeaf() ? LABEL_LEAF : LABEL_NODE;
        Label nodeType = new Label(strNodeType);
        nodeRow.add(nodeType);
        wrapper.add(nodeRow);
        return wrapper;
    }

    private FlowPanel createNodeContainer(NodeViewData node) {
        if (node == null) {
            return null;
        }

        FlowPanel nodeContainer = new FlowPanel();
        nodeContainer.addStyleName(STYLE_NODE_CONTAINER);

        FocusPanel nodeRow = createNodeRow(node);
        nodeContainer.add(nodeRow);

        if (node.isLeaf()) {
            return nodeContainer;
        }

        FlowPanel children = createChildrenContainer(node);
        nodeContainer.add(children);

        return nodeContainer;
    }

    private FlowPanel createChildrenContainer(NodeViewData node) {
        if (node == null || node.isLeaf()) {
            return null;
        }

        List<NodeViewData> children = node.getChildren();

        FlowPanel childrenWrapper = new FlowPanel();
        childrenWrapper.addStyleName(STYLE_CHILDREN_WRAPPER);

        FlowPanel indent = new FlowPanel();
        indent.addStyleName(STYLE_INDENT);

        FlowPanel childrenContainer = new FlowPanel();
        childrenContainer.addStyleName(STYLE_CHILDREN_CONTAINER);

        childrenWrapper.add(indent);
        childrenWrapper.add(childrenContainer);

        for (NodeViewData child : children) {
            NodeTreeRowWidget childWidget = new NodeTreeRowWidget(child);
            childrenContainer.add(childWidget);
        }

        return childrenWrapper;
    }
}
