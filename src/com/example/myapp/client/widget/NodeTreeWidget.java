package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;

public class NodeTreeWidget extends Composite {

    private static final String ATTRIBUTE_NODE_ID = "node-id";

    private static final String STYLE_TREE = "tree";
    private static final String STYLE_NODE_CONTAINER = "node-container";
    private static final String STYLE_CHILDREN_CONTAINER = "children-container";
    private static final String STYLE_INDENT = "indent";
    private static final String STYLE_CHILDREN_WRAPPER = "children-wrapper";

    private FocusPanel root;
    private FlowPanel main;

    private NodeRpcServiceAsync nodeRpcService;

    private NodeSelectHandler handler;

    private NodeTreeRowWidget previousSelectedNode;

    public NodeTreeWidget(NodeRpcServiceAsync refreshService) {
        nodeRpcService = refreshService;
        build();
        initWidget(root);
    }

    private void build() {
        root = new FocusPanel();
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
        root.add(main);
    }

    public void setHandler(NodeSelectHandler handler) {
        this.handler = handler;
        bind();
    }

    private void bind() {
        if (handler != null) {
            root.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Element element = Element.as(event.getNativeEvent().getEventTarget());

                    while (element != null && !element.hasAttribute(ATTRIBUTE_NODE_ID)) {
                        element = element.getParentElement();
                    }

                    if (element == null) {
                        return;
                    }

                    if (element.hasAttribute(ATTRIBUTE_NODE_ID)) {
                        String selectedNodeId = element.getAttribute(ATTRIBUTE_NODE_ID);
                        handler.onNodeSelect(Long.parseLong(selectedNodeId));
                        setSelectedNodeRow(element);
                    }
                }
            });
        }
    }

    private void setSelectedNodeRow(Element element) {
        EventListener listener = DOM.getEventListener(element);

        if (listener instanceof NodeTreeRowWidget) {
            NodeTreeRowWidget widget = (NodeTreeRowWidget) listener;

            if (previousSelectedNode != null && previousSelectedNode != widget) {
                previousSelectedNode.setSelected(false);
            }

            widget.setSelected(true);
            previousSelectedNode = widget;
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
                previousSelectedNode = null;
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

        if (node.isLeaf()) {
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

        if (tree == null || tree.isLeaf()) {
            return;
        }

        List<NodeViewData> rootNodes = tree.getChildren();

        for (NodeViewData node : rootNodes) {
            if (node != null) {
                main.add(createNodeContainer(node));
            }
        }
    }

    private FlowPanel createNodeContainer(NodeViewData node) {
        if (node == null) {
            return null;
        }

        FlowPanel nodeContainer = new FlowPanel();
        nodeContainer.addStyleName(STYLE_NODE_CONTAINER);

        NodeTreeRowWidget nodeRow = new NodeTreeRowWidget(node);
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
            childrenContainer.add(createNodeContainer(child));
        }

        return childrenWrapper;
    }
}
