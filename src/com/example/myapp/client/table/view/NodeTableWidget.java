package com.example.myapp.client.table.view;

import java.util.List;

import com.example.myapp.client.table.presenter.NodeTablePresenter;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NodeTableWidget extends Composite implements NodeTablePresenter.Display {

    private static final String NULL_PARENT_ID_PLACEHOLDER = "-";

    private static final String STYLE_TABLE = "table";
    private static final String STYLE_HEADER_ROW = "header-row";
    private static final String STYLE_ROW = "row";
    private static final String STYLE_CELL = "cell";

    private static final String ID_TO_STRING = "id";
    private static final String PARENT_ID_TO_STRING = "parentId";
    private static final String NAME_TO_STRING = "name";
    private static final String IP_TO_STRING = "ip";
    private static final String PORT_TO_STRING = "port";

    private FlowPanel main;
    private FlowPanel headerRow;
    private FlowPanel table;

    public NodeTableWidget() {
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

    public void setNodeList(List<NodeBean> newNodeList) {
        table.clear();

        if (newNodeList == null || newNodeList.isEmpty()) {
            return;
        }

        for (NodeBean node : newNodeList) {
            if (node != null) {
                table.add(createRow(node));
            }
        }
    }

    private FlowPanel createHeaderRow() {
        FlowPanel headerRow = new FlowPanel();
        headerRow.addStyleName(STYLE_ROW);
        headerRow.addStyleName(STYLE_HEADER_ROW);

        headerRow.add(createCell(ID_TO_STRING));
        headerRow.add(createCell(PARENT_ID_TO_STRING));
        headerRow.add(createCell(NAME_TO_STRING));
        headerRow.add(createCell(IP_TO_STRING));
        headerRow.add(createCell(PORT_TO_STRING));

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

    public Widget asWidget() {
        return this;
    }
}
