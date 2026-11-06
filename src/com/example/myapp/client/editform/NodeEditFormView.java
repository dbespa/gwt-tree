package com.example.myapp.client.editform;

import com.example.myapp.client.handler.CancelHandler;
import com.example.myapp.client.handler.SaveHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class NodeEditFormView extends Composite implements NodeEditFormDisplay {

    private static final String STYLE_MAIN = "NodeEditFormView";
    private static final String STYLE_FORM = STYLE_MAIN + "-form";
    private static final String STYLE_COLUMN = STYLE_MAIN + "-column";
    private static final String STYLE_CELL = STYLE_MAIN + "-cell";
    private static final String STYLE_BUTTON_PANEL = STYLE_MAIN + "-button-panel";
    private static final String STYLE_HEADER = STYLE_MAIN + "-header";
    private static final String STYLE_CELL_INVALID = STYLE_CELL + "-invalid";

    private static final String LABEL_SAVE = "Save";
    private static final String LABEL_CANCEL = "Cancel";
    private static final String LABEL_EDIT_NODE = "Edit node";
    private static final String LABEL_CREATE_NODE = "Create node";
    private static final String LABEL_PORT = "port";
    private static final String LABEL_IP = "ip";
    private static final String LABEL_NAME = "name";

    private static final String EMPTY_DATA = "";

    private PopupPanel main;
    private FlowPanel dataColumn;
    private FlowPanel wrapper;
    private Label header;
    private Button cancelButton;
    private Button saveButton;

    private TextBox name;
    private TextBox ip;
    private TextBox port;

    public NodeEditFormView() {
        main = new PopupPanel();

        wrapper = new FlowPanel();
        wrapper.addStyleName(STYLE_MAIN);

        header = new Label("");
        header.addStyleName(STYLE_HEADER);

        FlowPanel form = new FlowPanel();
        form.addStyleName(STYLE_FORM);

        FlowPanel fieldNameColumn = createNameColumn();
        dataColumn = createDataRow();

        FlowPanel buttonPanel = new FlowPanel();
        buttonPanel.addStyleName(STYLE_BUTTON_PANEL);

        cancelButton = new Button(LABEL_CANCEL);
        saveButton = new Button(LABEL_SAVE);

        form.add(fieldNameColumn);
        form.add(dataColumn);

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        wrapper.add(header);
        wrapper.add(form);
        wrapper.add(buttonPanel);

        main.setWidget(wrapper);
        main.setGlassEnabled(true);
    }

    private FlowPanel createNameColumn() {
        FlowPanel nameColumn = new FlowPanel();
        nameColumn.addStyleName(STYLE_COLUMN);

        nameColumn.add(createCell(LABEL_NAME));
        nameColumn.add(createCell(LABEL_IP));
        nameColumn.add(createCell(LABEL_PORT));
        return nameColumn;
    }

    private FlowPanel createDataRow() {
        FlowPanel dataColumn = new FlowPanel();
        dataColumn.addStyleName(STYLE_COLUMN);

        name = createTextBoxCell();
        ip = createTextBoxCell();
        port = createTextBoxCell();

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

    private TextBox createTextBoxCell() {
        TextBox textBox = new TextBox();
        textBox.addStyleName(STYLE_CELL);
        return textBox;
    }

    public void setSelectedNode(NodeViewData node) {
        if (node == null) {
            return;
        }

        name.setValue(node.getName());
        ip.setValue(node.getIp());
        port.setValue(node.getPort().toString());
    }

    public void setEmpty() {
        name.setValue(EMPTY_DATA);
        ip.setValue(EMPTY_DATA);
        port.setValue(EMPTY_DATA);
    }

    public void setCreateHeader() {
        header.setText(LABEL_CREATE_NODE);
    }

    public void setEditHeader() {
        header.setText(LABEL_EDIT_NODE);
    }

    public void show() {
        main.center();
        main.show();
    }

    public void hide() {
        main.hide();
    }

    private NodeViewData getFormData() {
        String nameStr = name.getValue();
        String ipStr = ip.getValue();
        String portStr = port.getValue();

        NodeViewData formData = new NodeViewData(nameStr, ipStr, portStr);
        return formData;
    }

    public void setCancelHandler(final CancelHandler onCancel) {
        if (onCancel == null) {
            return;
        }

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onCancel.onCancel();
            }
        });
    }

    @Override
    public void setSaveHandler(final SaveHandler onSave) {
        if (onSave == null) {
            return;
        }

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onSave.onSave(getFormData());
            }
        });
    }

    public void setNameFieldInvalid() {
        name.addStyleName(STYLE_CELL_INVALID);
        name.setFocus(true);
    }

    public void setIpFieldInvalid() {
        ip.addStyleName(STYLE_CELL_INVALID);
        ip.setFocus(true);
    }

    public void setPortFieldInvalid() {
        port.addStyleName(STYLE_CELL_INVALID);
        port.setFocus(true);
    }

    public void setAllFieldsValid() {
        name.removeStyleName(STYLE_CELL_INVALID);
        name.setFocus(false);

        ip.removeStyleName(STYLE_CELL_INVALID);
        ip.setFocus(false);

        port.removeStyleName(STYLE_CELL_INVALID);
        port.setFocus(false);
    }
}
