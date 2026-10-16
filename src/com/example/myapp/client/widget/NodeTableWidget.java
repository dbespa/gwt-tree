package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.service.NodeTableRefreshService;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeTableWidget extends Composite {
    private FlowPanel table = new FlowPanel();
    private NodeTableRefreshService nodeTableRefreshService = new NodeTableRefreshService();

    public NodeTableWidget(final Command command) {
        nodeTableRefreshService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                table.addStyleName("table");
                table.add(createHeaderRow());

                for (NodeBean node : result) {
                    table.add(createRow(node));
                }

                initWidget(table);
                command.execute();
            }
        });
    }

    private FlowPanel createHeaderRow() {
        FlowPanel headerRow = new FlowPanel();
        headerRow.addStyleName("row");

        headerRow.add(createHeaderCell("id"));
        headerRow.add(createHeaderCell("parentId"));
        headerRow.add(createHeaderCell("name"));
        headerRow.add(createHeaderCell("ip"));
        headerRow.add(createHeaderCell("port"));

        return headerRow;
    }

    private Label createHeaderCell(String cellName) {
        Label label = new Label(cellName);
        label.addStyleName("cell");
        label.addStyleName("header");
        return label;
    }

    private Label createCell(String cellName) {
        Label label = new Label(cellName);
        label.addStyleName("cell");
        return label;
    }

    private FlowPanel createRow(NodeBean node) {
        FlowPanel row = new FlowPanel();
        row.addStyleName("row");

        Integer parentId = node.getParentId();
        String strParentId = parentId == null ? "" : String.valueOf(node.getId());

        row.add(createCell(String.valueOf(node.getId())));
        row.add(createCell(strParentId));
        row.add(createCell(node.getName()));
        row.add(createCell(node.getIp()));
        row.add(createCell(String.valueOf(node.getPort())));

        return row;
    }
}
