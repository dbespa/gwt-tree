package com.example.myapp.client;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp.client.tree.Data;
import com.example.myapp.client.tree.Tree;
import com.example.myapp.client.tree.TreeNode;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TreeApp implements EntryPoint {

    private class TreeListLine {
        private Integer id;
        private int parentId;
        private String name;
        private String ip;
        private int port;

        public TreeListLine(Integer id, int parentId, String name, String ip, int port) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.ip = ip;
            this.port = port;
        }
    }

    final Label listLabel = new Label("All nodes:");
    final Button refreshButton = new Button("Refresh");

    public void onModuleLoad() {

        List<TreeListLine> treeListExamples = new ArrayList<>();
        CellTable<TreeListLine> table = new CellTable<TreeListLine>();

        TextColumn<TreeListLine> idColumn = new TextColumn<TreeListLine>() {
            @Override
            public String getValue(TreeListLine treeList) {
                return String.valueOf(treeList.id);
            }
        };

        TextColumn<TreeListLine> parentIdColumn = new TextColumn<TreeListLine>() {
            @Override
            public String getValue(TreeListLine treeList) {
                return String.valueOf(treeList.parentId);
            }
        };

        TextColumn<TreeListLine> nameColumn = new TextColumn<TreeListLine>() {
            @Override
            public String getValue(TreeListLine treeList) {
                return String.valueOf(treeList.name);
            }
        };

        TextColumn<TreeListLine> ipColumn = new TextColumn<TreeListLine>() {
            @Override
            public String getValue(TreeListLine treeList) {
                return String.valueOf(treeList.ip);
            }
        };

        TextColumn<TreeListLine> portColumn = new TextColumn<TreeListLine>() {
            @Override
            public String getValue(TreeListLine treeList) {
                return String.valueOf(treeList.port);
            }
        };

        table.addColumn(idColumn, "id");
        table.addColumn(parentIdColumn, "parentId");
        table.addColumn(nameColumn, "name");
        table.addColumn(ipColumn, "ip");
        table.addColumn(portColumn, "port");

        ListDataProvider<TreeListLine> dataProvider = new ListDataProvider<TreeListLine>();
        dataProvider.addDataDisplay(table);
        List<TreeListLine> list = dataProvider.getList();

        Tree tree = new Tree();

        Data data1 = new Data("rootNode", "127.0.0.1", 1111);
        Data data2 = new Data("node-1", "127.0.1.1", 11);
        Data data3 = new Data("node-2", "127.0.1.2", 12);

        TreeNode rootNode = new TreeNode(0, data1, null);
        
        tree.addRootNode(rootNode);
        TreeNode node1 = new TreeNode(1, data2, rootNode);
        TreeNode node2 = new TreeNode(2, data3, rootNode);
        tree.addNode(rootNode, node1);
        tree.addNode(rootNode, node2);
        List<TreeNode> children = rootNode.getChildren();

        int id = rootNode.getId();
        Integer parentId = 0;
        String name = rootNode.getData().getName();
        String ip = rootNode.getData().getIp();
        int port = rootNode.getData().getPort();

        TreeListLine rootList = new TreeListLine(id, parentId, name, ip, port);
        treeListExamples.add(rootList);
        RootPanel.get().add(listLabel);
        RootPanel.get().add(refreshButton);

        for (int i = 0; i < children.size(); i++) {
            TreeNode node = children.get(i);

            id = node.getId();
            parentId = node.getParentId();
            name = node.getData().getName();
            ip = node.getData().getIp();
            port = node.getData().getPort();

            TreeListLine nodeNewList = new TreeListLine(id, parentId, name, ip, port);
            treeListExamples.add(nodeNewList);
        }

        for (TreeListLine treeList : treeListExamples) {
            list.add(treeList);
        }

        RootPanel.get().add(table);
    }
}
