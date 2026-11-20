package com.example.myapp.client.tree;

import java.util.List;
import java.util.Set;

import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.ToggleChildrenEvent;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.ToggleChildrenHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NodeTreeRowWidget extends Composite {

    private static final String STYLE_MAIN = "NodeTreeRowWidget";
    private static final String STYLE_NODE_CONTAINER = STYLE_MAIN + "-node-container";
    private static final String STYLE_CHILDREN_CONTAINER = STYLE_MAIN + "-children-container";
    private static final String STYLE_INDENT = STYLE_MAIN + "-indent";
    private static final String STYLE_CHILDREN_WRAPPER = STYLE_MAIN + "-children-wrapper";
    private static final String STYLE_TOGGLE_BUTTON = STYLE_MAIN + "-toggle-button";
    private static final String STYLE_TREE_ROW = STYLE_MAIN + "-tree-row";
    private static final String STYLE_TREE_ROW_ACTIVE = STYLE_TREE_ROW + "-active";

    private static final String BUTTON_COLLAPSE = "-";
    private static final String BUTTON_EXPAND = "+";

    private NodeSelectHandler onNodeSelect;
    private ToggleChildrenHandler onToggleChildren;
    private HandlerRegistration clickRegistration;

    private FlowPanel main;
    private FlowPanel nodeRow;
    private FlowPanel childrenContainer;
    private Button toggleButton;

    private Long id;

    public NodeTreeRowWidget(NodeTreeViewData node) {
        id = node.getId();
        build(node);
        bind();
        fillChildrenWidgets(node);
        initWidget(main);
    }

    private void build(NodeTreeViewData node) {
        main = new FlowPanel();
        main.addStyleName(STYLE_NODE_CONTAINER);

        nodeRow = createNodeRow(node);
        main.add(nodeRow);

        if (node.isEmpty()) {
            return;
        }

        FlowPanel children = createChildrenPanel();
        main.add(children);
    }

    private FlowPanel createNodeRow(NodeTreeViewData node) {
        FlowPanel resultNodeRow = new FlowPanel();
        resultNodeRow.addStyleName(STYLE_TREE_ROW);

        toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);

        resultNodeRow.add(toggleButton);
        resultNodeRow.add(new Label(node.getName()));
        return resultNodeRow;
    }

    private FlowPanel createChildrenPanel() {
        FlowPanel panel = new FlowPanel();
        panel.addStyleName(STYLE_CHILDREN_WRAPPER);

        FlowPanel indent = new FlowPanel();
        indent.addStyleName(STYLE_INDENT);

        childrenContainer = new FlowPanel();
        childrenContainer.addStyleName(STYLE_CHILDREN_CONTAINER);

        panel.add(indent);
        panel.add(childrenContainer);
        return panel;
    }

    private void bind() {
        toggleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                toggleChildren();
                if (childrenContainer == null) {
                    return;
                }
                Boolean childrenIsCollapsed = !childrenContainer.isVisible();
                onToggleChildren.onToggleChildren(new ToggleChildrenEvent(id, childrenIsCollapsed));
            }
        });
    }

    private void toggleChildren() {
        if (childrenContainer == null) {
            return;
        }

        Boolean childrenIsVisible = childrenContainer.isVisible();
        String toggleIcon = childrenIsVisible ? BUTTON_EXPAND : BUTTON_COLLAPSE;
        toggleButton.setText(toggleIcon);

        childrenContainer.setVisible(!childrenIsVisible);
    }

    private void fillChildrenWidgets(NodeTreeViewData node) {
        List<NodeTreeViewData> children = node.getChildren();
        for (NodeTreeViewData child : children) {
            NodeTreeRowWidget childWidget = new NodeTreeRowWidget(child);
            childrenContainer.add(childWidget);
        }
    }

    public void removeCaption() {
        main.clear();
        nodeRow = null;
        main.add(childrenContainer);
    }

    private void traversal(NodeAction action) {
        if (childrenContainer == null) {
            return;
        }

        for (int i = 0; i < childrenContainer.getWidgetCount(); i++) {
            Widget widget = childrenContainer.getWidget(i);
            if (widget instanceof NodeTreeRowWidget) {
                NodeTreeRowWidget nodeRowWidget = (NodeTreeRowWidget) widget;
                action.action(nodeRowWidget);
            }
        }
    }

    public void setNodeSelectHandler(final NodeSelectHandler onSelect) {
        if (clickRegistration != null) {
            clickRegistration.removeHandler();
        }

        this.onNodeSelect = onSelect;

        traversal(new NodeAction() {
            @Override
            public void action(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.setNodeSelectHandler(onSelect);
            }
        });

        if (nodeRow == null) {
            return;
        }

        clickRegistration = nodeRow.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setSelected(true);

                if (onNodeSelect == null) {
                    return;
                }
                onNodeSelect.onNodeSelect(new NodeSelectEvent(id));
            }
        }, ClickEvent.getType());
    }

    public void setToggleChildrenHandler(final ToggleChildrenHandler onToggleChildren) {
        this.onToggleChildren = onToggleChildren;

        traversal(new NodeAction() {
            @Override
            public void action(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.setToggleChildrenHandler(onToggleChildren);
            }
        });
    }

    public void unselectOtherNodes(final Long selectedNodeId) {
        setSelected(id.equals(selectedNodeId));

        traversal(new NodeAction() {
            @Override
            public void action(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.unselectOtherNodes(selectedNodeId);
            }
        });
    }

    private void setSelected(Boolean isSelected) {
        if (nodeRow == null) {
            return;
        }

        if (isSelected) {
            nodeRow.addStyleName(STYLE_TREE_ROW_ACTIVE);
        } else {
            nodeRow.removeStyleName(STYLE_TREE_ROW_ACTIVE);
        }
    }

    public void collapseNodes(final Set<Long> collapsedNodes) {
        if (collapsedNodes.isEmpty()) {
            return;
        }

        if (collapsedNodes.contains(id)) {
            toggleChildren();
        }

        traversal(new NodeAction() {
            @Override
            public void action(NodeTreeRowWidget treeRowWidget) {
                treeRowWidget.collapseNodes(collapsedNodes);
            }
        });
    }
}
