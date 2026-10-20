package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.StringUtils;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeTableWidget extends Composite {

    private static final String NULL_PARENT_ID_PLACEHOLDER = "-";

    private static final String STYLE_TABLE = "table";
    private static final String STYLE_HEADER_ROW = "header-row";
    private static final String STYLE_ROW = "row";
    private static final String STYLE_CELL = "cell";

    private FlowPanel main;
    private FlowPanel headerRow;
    private FlowPanel table;

    private NodeRpcServiceAsync nodeTableRefreshService;

    public NodeTableWidget(NodeRpcServiceAsync refreshService) {
        nodeTableRefreshService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        table = new FlowPanel();
        table.addStyleName(STYLE_TABLE);

        headerRow = createHeaderRow();

        main.add(headerRow);
        main.add(table);
    }

    public void refreshTable() {
        nodeTableRefreshService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                setNodeList(result);
            }
        });
    }

    public void setNodeList(List<NodeBean> newNodeList) {
        table.clear();

        if (newNodeList == null || newNodeList.isEmpty()) {
            return;
        }

        for (NodeBean node : newNodeList) {
            table.add(createRow(node));
        }
    }

    private FlowPanel createHeaderRow() {
        FlowPanel headerRow = new FlowPanel();
        headerRow.addStyleName(STYLE_ROW);
        headerRow.addStyleName(STYLE_HEADER_ROW);

        headerRow.add(createCell(StringUtils.ID_TO_STRING));
        headerRow.add(createCell(StringUtils.PARENT_ID_TO_STRING));
        headerRow.add(createCell(StringUtils.NAME_TO_STRING));
        headerRow.add(createCell(StringUtils.IP_TO_STRING));
        headerRow.add(createCell(StringUtils.PORT_TO_STRING));

        return headerRow;
    }

    private Label createCell(String cellName) {
        Label label = new Label(cellName);
        label.addStyleName(STYLE_CELL);
        return label;
    }

    private FlowPanel createRow(NodeBean node) {
        FlowPanel row = new FlowPanel();
        row.addStyleName(STYLE_ROW);

        if (node == null) {
            return null;
        }

        Long parentId = node.getParentId();
        String strParentId = (parentId == null ? NULL_PARENT_ID_PLACEHOLDER : String.valueOf(parentId));

        row.add(createCell(String.valueOf(node.getId())));
        row.add(createCell(strParentId));
        row.add(createCell(node.getName()));
        row.add(createCell(node.getIp()));
        row.add(createCell(String.valueOf(node.getPort())));

        return row;
    }
}
