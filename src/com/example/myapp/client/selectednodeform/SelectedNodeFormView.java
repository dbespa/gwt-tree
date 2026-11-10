package com.example.myapp.client.selectednodeform;

import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class SelectedNodeFormView extends Composite implements SelectedNodeFormDisplay {

    private static final String STYLE_MAIN = "SelectedNodeFormView";
    private static final String STYLE_FORM = STYLE_MAIN + "-form";
    private static final String STYLE_COLUMN = STYLE_MAIN + "-column";
    private static final String STYLE_CELL = STYLE_MAIN + "-cell";

    private static final String LABEL_PORT = "port";
    private static final String LABEL_IP = "ip";
    private static final String LABEL_NAME = "name";
    private static final String LABEL_PARENT_ID = "parentId";
    private static final String LABEL_ID = "id";

    private static final String EMPTY_DATA = "-";

    private FlowPanel main;
    private FlowPanel dataColumn;

    private Label id;
    private Label parentId;
    private Label name;
    private Label ip;
    private Label port;

    public SelectedNodeFormView() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_FORM);

        FlowPanel fieldNameColumn = createFieldNameColumn();
        dataColumn = createDataColumn();
        main.add(fieldNameColumn);
        main.add(dataColumn);
    }

    private FlowPanel createFieldNameColumn() {
        FlowPanel fieldNameColumn = new FlowPanel();
        fieldNameColumn.addStyleName(STYLE_COLUMN);

        fieldNameColumn.add(createCell(LABEL_ID));
        fieldNameColumn.add(createCell(LABEL_PARENT_ID));
        fieldNameColumn.add(createCell(LABEL_NAME));
        fieldNameColumn.add(createCell(LABEL_IP));
        fieldNameColumn.add(createCell(LABEL_PORT));
        return fieldNameColumn;
    }

    private FlowPanel createDataColumn() {
        FlowPanel dataColumn = new FlowPanel();
        dataColumn.addStyleName(STYLE_COLUMN);

        id = createCell(EMPTY_DATA);
        parentId = createCell(EMPTY_DATA);
        name = createCell(EMPTY_DATA);
        ip = createCell(EMPTY_DATA);
        port = createCell(EMPTY_DATA);

        dataColumn.add(id);
        dataColumn.add(parentId);
        dataColumn.add(name);
        dataColumn.add(ip);
        dataColumn.add(port);
        return dataColumn;
    }

    private Label createCell(String name) {
        Label label = new Label(name);
        label.addStyleName(STYLE_CELL);
        return label;
    }

    public void setSelectedNode(NodeBean node) {
        if (node == null) {
            return;
        }

        Long idValue = node.getId();
        String idStr = idValue == null ? EMPTY_DATA : idValue.toString();

        Long parentIdValue = node.getParentId();
        String parentIdStr = parentIdValue == null ? EMPTY_DATA : parentIdValue.toString();

        Integer portValue = node.getPort();
        String portStr = portValue == null ? EMPTY_DATA : portValue.toString();

        id.setText(idStr);
        parentId.setText(parentIdStr);
        name.setText(node.getName());
        ip.setText(node.getIp());
        port.setText(portStr);
    }

    public void setEmpty() {
        id.setText(EMPTY_DATA);
        parentId.setText(EMPTY_DATA);
        name.setText(EMPTY_DATA);
        ip.setText(EMPTY_DATA);
        port.setText(EMPTY_DATA);
    }
}
