package com.example.myapp.client.mainwidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MainWidget extends Composite {

    private static final String STYLE_MAIN = "MainWidget";
    private static final String STYLE_WRAPPER = STYLE_MAIN + "-wrapper";
    private static final String STYLE_HEADING = STYLE_MAIN + "-heading";
    private static final String STYLE_TREE_CONTAINER = STYLE_MAIN + "-tree-container";
    private static final String STYLE_SELECTED_NODE_FORM_CONTAINER = STYLE_MAIN + "-selected-node-form-container";
    private static final String STYLE_UI_PANEL_CONTAINER = STYLE_MAIN + "-ui-panel-container";
    private static final String STYLE_NODE_TABLE_CONTAINER = STYLE_MAIN + "-node-table-container";

    private static final String LABEL_ALL_NODES = "All nodes:";
    private static final String LABEL_TREE = "Tree:";
    private static final String LABEL_SELECTED = "Selected:";

    private FlowPanel main;
    private FlowPanel treeContainer;
    private FlowPanel selectedNodeFormContainer;
    private FlowPanel uiPanelContainer;
    private FlowPanel tableContainer;

    public MainWidget() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_MAIN);

        FlowPanel wrapper = new FlowPanel();
        wrapper.addStyleName(STYLE_WRAPPER);

        treeContainer = createTreeContainer();
        selectedNodeFormContainer = createSelectedNodeFormContainer();
        uiPanelContainer = createUiPanel();
        tableContainer = createTableContainer();

        wrapper.add(treeContainer);
        wrapper.add(selectedNodeFormContainer);

        main.add(wrapper);
        main.add(uiPanelContainer);
        main.add(tableContainer);
    }

    private FlowPanel createTreeContainer() {
        FlowPanel treeContainer = new FlowPanel();
        treeContainer.addStyleName(STYLE_TREE_CONTAINER);

        Label label = new Label(LABEL_TREE);
        label.addStyleName(STYLE_HEADING);
        treeContainer.add(label);
        return treeContainer;
    }

    private FlowPanel createSelectedNodeFormContainer() {
        FlowPanel selectedNodeFormContainer = new FlowPanel();
        selectedNodeFormContainer.addStyleName(STYLE_SELECTED_NODE_FORM_CONTAINER);

        Label label = new Label(LABEL_SELECTED);
        label.addStyleName(STYLE_HEADING);
        selectedNodeFormContainer.add(label);
        return selectedNodeFormContainer;
    }

    private FlowPanel createUiPanel() {
        FlowPanel uiPanel = new FlowPanel();
        uiPanel.addStyleName(STYLE_UI_PANEL_CONTAINER);
        return uiPanel;
    }

    private FlowPanel createTableContainer() {
        FlowPanel tableContainer = new FlowPanel();
        tableContainer.addStyleName(STYLE_NODE_TABLE_CONTAINER);
        RootPanel.get().add(tableContainer);

        Label label = new Label(LABEL_ALL_NODES);
        label.addStyleName(STYLE_HEADING);
        tableContainer.add(label);
        return tableContainer;
    }

    public FlowPanel getTreeContainer() {
        return treeContainer;
    }

    public FlowPanel getSelectedNodeFormContainer() {
        return selectedNodeFormContainer;
    }

    public FlowPanel getUiPanelContainer() {
        return uiPanelContainer;
    }

    public FlowPanel getTableContainer() {
        return tableContainer;
    }
}
