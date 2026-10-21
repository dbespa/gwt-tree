package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.NodeDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeHierarchicalListWidget extends Composite {

    private static final String STYLE_TOGGLE_BUTTON = "toggle-button";
    private static final String STYLE_LIST_ITEM = "list-item";
    private static final String STYLE_LIST = "list";

    private FlowPanel main;
    private FlowPanel list;

    private NodeRpcServiceAsync nodeTableRefreshService;

    public NodeHierarchicalListWidget(NodeRpcServiceAsync refreshService) {
        nodeTableRefreshService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        list = new FlowPanel();
        list.addStyleName(STYLE_LIST);
        main.add(list);
    }

    public void refreshList() {
        nodeTableRefreshService.getTreeRoots(new AsyncCallback<List<NodeDto>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<NodeDto> result) {
                setNodeList(result);
            }
        });
    }

    private void setNodeList(List<NodeDto> rootList) {
        list.clear();

        if (rootList == null || rootList.isEmpty()) {
            return;
        }

        for (NodeDto node : rootList) {
            list.add(createNodeContainer(node, 0));
        }
    }

    private FlowPanel createNodeContainer(NodeDto node, Integer nestedLevel) {
        if (node == null) {
            return null;
        }

        FlowPanel nodeItem = createListItem(node, nestedLevel);

        if (node.getChildren().isEmpty()) {
            return nodeItem;
        }

        FlowPanel nodeContainer = new FlowPanel();
        FlowPanel children = addChildren(node, nestedLevel + 1);

        nodeContainer.add(nodeItem);
        nodeContainer.add(children);

        return nodeContainer;
    }

    private FlowPanel addChildren(NodeDto node, Integer nestedLevel) {
        if (node == null) {
            return null;
        }

        List<NodeDto> children = node.getChildren();
        FlowPanel childrenPanel = new FlowPanel();

        if (children.isEmpty()) {
            return childrenPanel;
        }

        for (NodeDto child : children) {
            childrenPanel.add(createNodeContainer(child, nestedLevel));
        }

        return childrenPanel;
    }

    private FlowPanel createListItem(NodeDto node, Integer nestedLevel) {
        if (node == null) {
            return null;
        }

        FlowPanel listItem = new FlowPanel();
        listItem.addStyleName(STYLE_LIST_ITEM);
        listItem.getElement().getStyle().setMarginLeft(nestedLevel * 30, Unit.PX);

        Button toggleButton = new Button("-");
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        listItem.add(toggleButton);

        listItem.add(new Label(node.getName()));

        Label isLeaf;
        if (node.getChildren().isEmpty()) {
            isLeaf = new Label("(*)");
        } else {
            isLeaf = new Label("(.)");
        }
        listItem.add(isLeaf);

        return listItem;
    }
}
