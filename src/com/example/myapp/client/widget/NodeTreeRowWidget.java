package com.example.myapp.client.widget;

import com.example.myapp.client.bean.NodeViewData;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeTreeRowWidget extends Composite {

    private static final String ATTRIBUTE_NODE_ID = "node-id";

    private static final String STYLE_TOGGLE_BUTTON = "toggle-button";
    private static final String STYLE_TREE_ROW = "tree-row";
    private static final String STYLE_TREE_ROW_ACTIVE = STYLE_TREE_ROW + "_active";

    private static final String BUTTON_COLLAPSE = "-";

    private static final String LABEL_NODE = "(.)";
    private static final String LABEL_LEAF = "(*)";

    private FlowPanel main;

    public NodeTreeRowWidget(NodeViewData node) {
        build(node);
        initWidget(main);
    }

    private FlowPanel build(NodeViewData node) {
        if (node == null) {
            return null;
        }

        main = new FlowPanel();
        main.addStyleName(STYLE_TREE_ROW);

        Button toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        main.add(toggleButton);

        main.add(new Label(node.getName()));

        String strNodeType = node.isLeaf() ? LABEL_LEAF : LABEL_NODE;
        Label nodeType = new Label(strNodeType);
        main.add(nodeType);

        Long nodeId = node.getId();
        if (nodeId != null) {
            main.getElement().setAttribute(ATTRIBUTE_NODE_ID, String.valueOf(nodeId));
        }

        return main;
    }

    public void setSelected(Boolean isSelected) {
        if (isSelected) {
            main.addStyleName(STYLE_TREE_ROW_ACTIVE);
        } else {
            main.removeStyleName(STYLE_TREE_ROW_ACTIVE);
        }
    }
}
