package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NodeTreeWidget extends Composite {

    private static final String STYLE_TREE = "tree";

    private FlowPanel main;

    private NodeRpcServiceAsync nodeRpcService;

    private NodeSelectHandler onNodeSelect;

    public NodeTreeWidget(NodeRpcServiceAsync refreshService) {
        nodeRpcService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
    }

    public void setNodeSelectHandler(NodeSelectHandler handler) {
        this.onNodeSelect = handler;
        setChildrenHandler();
    }

    private void setChildrenHandler() {
        if (onNodeSelect != null) {
            Element root = main.getElement();
            for (int i = 0; i < root.getChildCount(); i++) {
                Element child = (Element) root.getChild(i);
                setChildrenHandlerIterative(child);
            }
        }
    }

    private void setChildrenHandlerIterative(Element node) {
        final NodeSelectHandler handler = this.onNodeSelect;
        EventListener listener = (EventListener) DOM.getEventListener(node);

        if (listener instanceof NodeTreeRowWidget) {
            NodeTreeRowWidget widget = (NodeTreeRowWidget) listener;
            widget.setNodeSelectHandler(new NodeSelectHandler() {
                @Override
                public void onNodeSelect(Long selectedNodeId) {
                    setOtherNodesUnselected(selectedNodeId);
                    handler.onNodeSelect(selectedNodeId);
                }
            });
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            Element child = (Element) node.getChild(i);
            setChildrenHandlerIterative(child);
        }
    }

    public void refreshTree() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                NodeViewData convertedTree = convertTree(result);
                setNodeList(convertedTree);
            }
        });
    }

    private NodeViewData convertToNodeViewData(NodeTreeBean node) {
        return new NodeViewData(node.getId(), node.getName());
    }

    private NodeViewData convertTree(NodeTreeBean node) {
        NodeViewData convertedNode = convertToNodeViewData(node);

        if (node.isEmpty()) {
            return convertedNode;
        }

        List<NodeTreeBean> children = node.getChildren();

        for (NodeTreeBean nodeBean : children) {
            convertedNode.addChild(convertTree(nodeBean));
        }

        return convertedNode;
    }

    private void setNodeList(NodeViewData tree) {
        main.clear();

        if (tree == null || tree.isEmpty()) {
            return;
        }

        List<NodeViewData> rootNodes = tree.getChildren();

        for (NodeViewData node : rootNodes) {
            if (node != null) {
                NodeTreeRowWidget nodeRow = new NodeTreeRowWidget(node);
                main.add(nodeRow);
            }
        }

        setChildrenHandler();
    }

    private void setOtherNodesUnselected(Long selectedNodeId) {
        Element root = main.getElement();
        for (int i = 0; i < root.getChildCount(); i++) {
            Element child = (Element) root.getChild(i);
            NodeTreeRowWidget treeRow = (NodeTreeRowWidget) DOM.getEventListener(child);
            treeRow.setOtherNodesUnselected(selectedNodeId);
        }
    }
}
