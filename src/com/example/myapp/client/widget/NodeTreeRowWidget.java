package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.UniversalRoot;
import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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
    private FlowPanel childrenContainer;
    private UniversalRoot childrenRoot;

    private Long id;

    public NodeTreeRowWidget(NodeViewData node) {
        if (node == null) {
            return;
        }

        childrenRoot = null;
        id = node.getId();
        build(node);
        initWidget(main);
    }

    public Long getId() {
        return id;
    }

    private FlowPanel build(NodeViewData node) {
        if (node == null) {
            return null;
        }

        main = createNodeContainer(node);
        return main;
    }

    private FlowPanel createNodeContainer(NodeViewData node) {
        if (node == null) {
            return null;
        }

        FlowPanel nodeContainer = new FlowPanel();
        nodeContainer.addStyleName(STYLE_NODE_CONTAINER);

        FlowPanel nodeRow = createNodeRow(node);
        nodeContainer.add(nodeRow);

        if (node.isEmpty()) {
            childrenContainer = null;
            return nodeContainer;
        }

        FlowPanel children = createChildrenContainer(node);
        nodeContainer.add(children);

        return nodeContainer;
    }

    private FlowPanel createNodeRow(NodeViewData node) {
        nodeRow = new FlowPanel();
        nodeRow.addStyleName(STYLE_TREE_ROW);

        Button toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        nodeRow.add(toggleButton);

        nodeRow.add(new Label(node.getName()));

        String strNodeType = node.isEmpty() ? LABEL_LEAF : LABEL_NODE;
        Label nodeType = new Label(strNodeType);
        nodeRow.add(nodeType);
        return nodeRow;
    }

    private FlowPanel createChildrenContainer(NodeViewData node) {
        if (node == null || node.isEmpty()) {
            childrenRoot = null;
            return null;
        }

        List<NodeViewData> children = node.getChildren();

        FlowPanel childrenWrapper = new FlowPanel();
        childrenWrapper.addStyleName(STYLE_CHILDREN_WRAPPER);

        FlowPanel indent = new FlowPanel();
        indent.addStyleName(STYLE_INDENT);

        childrenContainer = new FlowPanel();
        childrenContainer.addStyleName(STYLE_CHILDREN_CONTAINER);
        childrenRoot = new UniversalRoot(childrenContainer);

        childrenWrapper.add(indent);
        childrenWrapper.add(childrenContainer);

        for (NodeViewData child : children) {
            NodeTreeRowWidget childWidget = new NodeTreeRowWidget(child);
            childrenRoot.add(childWidget);
        }

        return childrenWrapper;
    }

    public void setNodeSelectHandler(NodeSelectHandler onSelect) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }

        this.onNodeSelect = onSelect;

        if (childrenRoot != null) {
            childrenRoot.setNodeSelectHandler(onSelect);
        }

        if (onSelect != null) {
            ClickHandler onClick = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Long id = NodeTreeRowWidget.this.id;
                    NodeSelectHandler nodeSelectHandler = NodeTreeRowWidget.this.onNodeSelect;
                    if (nodeSelectHandler != null) {
                        setSelected(true);
                        nodeSelectHandler.onNodeSelect(id);
                    }
                }
            };
            clickRegistration = nodeRow.addDomHandler(onClick, ClickEvent.getType());
        }
    }

    public void setSelected(Boolean isSelected) {
        if (isSelected) {
            nodeRow.addStyleName(STYLE_TREE_ROW_ACTIVE);
        } else {
            nodeRow.removeStyleName(STYLE_TREE_ROW_ACTIVE);
        }
    }

    public void setOtherNodesUnselected(Long selectedNodeId) {
        if (!id.equals(selectedNodeId)) {
            setSelected(false);
        }

        if (childrenContainer != null) {
            childrenRoot.setOtherNodesUnselected(selectedNodeId);
        }
    }
}
