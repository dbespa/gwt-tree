package com.example.myapp.client.ui;

import java.util.List;

import com.example.myapp.client.tree.Tree;
import com.example.myapp.client.tree.TreeNode;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TreeWidget extends Composite {
    private FlowPanel tableBase = new FlowPanel();
    private FlowPanel headerRow = new FlowPanel();

    private Label columnId = new Label("id");
    private Label columnParentId = new Label("parentId");
    private Label columnName = new Label("name");
    private Label columnIp = new Label("ip");
    private Label columnPort = new Label("port");

    private void addHeaderCell(Label label) {
        headerRow.add(label);
        label.addStyleName("cell");
        label.addStyleName("header");
    }

    private void addHeaderRow() {
        addHeaderCell(columnId);
        addHeaderCell(columnParentId);
        addHeaderCell(columnName);
        addHeaderCell(columnIp);
        addHeaderCell(columnPort);
    }

    private void addCell(Label label, FlowPanel row) {
        Label cell = label;
        row.add(cell);
        cell.addStyleName("cell");
    }

    public TreeWidget() {
        tableBase.addStyleName("table");
        addHeaderRow();

        tableBase.add(headerRow);

        initWidget(tableBase);
    }

    public TreeWidget(Tree tree) {
        List<TreeNode> allNodes = tree.getAllNodes();

        tableBase.addStyleName("table");
        headerRow.addStyleName("row");

        addHeaderRow();

        tableBase.add(headerRow);

        for (int i = 0; i < allNodes.size(); i++) {
            FlowPanel newRow = new FlowPanel();
            newRow.addStyleName("row");

            TreeNode currentNode = allNodes.get(i);

            Label label = new Label(String.valueOf(currentNode.getId()));
            addCell(label, newRow);

            label = new Label(String.valueOf(currentNode.getParentId()));
            addCell(label, newRow);

            label = new Label(currentNode.getName());
            addCell(label, newRow);

            label = new Label(currentNode.getIp());
            addCell(label, newRow);

            label = new Label(String.valueOf(currentNode.getPort()));
            addCell(label, newRow);

            tableBase.add(newRow);
        }

        initWidget(tableBase);
    }

}
