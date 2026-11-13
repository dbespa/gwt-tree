package com.example.myapp.client.uipanel;

import com.example.myapp.event.AddChildClickEvent;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.AfterDeleteEvent;
import com.example.myapp.event.EditClickEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.AddChildClickHandler;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.EditClickHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UiPanelView extends Composite implements UiPanelDisplay {

    private static final String STYLE_MAIN = "UiPanelView";
    private static final String STYLE_UI_PANEL = STYLE_MAIN + "-ui-panel";

    private static final String LABEL_REFRESH = "Refresh";
    private static final String LABEL_ADD_ROOT_NODE = "Add root node";
    private static final String LABEL_ADD_CHILD = "Add child";
    private static final String LABEL_EDIT = "Edit";
    private static final String LABEL_DELETE = "Delete";
    private static final String LABEL_SELECTED_ID = "selected node id = ";

    private static final String EMPTY_DATA = "-";

    private FlowPanel main;
    private Button refreshButton;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;
    private Label selectedNodeLabel;

    public UiPanelView() {
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_UI_PANEL);

        refreshButton = new Button(LABEL_REFRESH);
        addRootButton = new Button(LABEL_ADD_ROOT_NODE);
        addChildButton = new Button(LABEL_ADD_CHILD);
        editButton = new Button(LABEL_EDIT);
        deleteButton = new Button(LABEL_DELETE);
        selectedNodeLabel = new Label(LABEL_SELECTED_ID);

        main.add(refreshButton);
        main.add(addRootButton);
        main.add(addChildButton);
        main.add(editButton);
        main.add(deleteButton);
        main.add(selectedNodeLabel);
    }

    public void setSelectedNodeId(Long id) {
        String selectedNodeId = id == null ? EMPTY_DATA : String.valueOf(id);
        selectedNodeLabel.setText(LABEL_SELECTED_ID + selectedNodeId);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (onRefresh == null) {
                    return;
                }
                onRefresh.onRefresh(new RefreshEvent());
            }
        });
    }

    public void setAddRootClickHandler(final AddRootClickHandler onAddRootClick) {
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (onAddRootClick == null) {
                    return;
                }
                onAddRootClick.onAddRootClick(new AddRootClickEvent());
            }
        });
    }

    public void setAddChildClickHandler(final AddChildClickHandler onAddChildClick) {
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (onAddChildClick == null) {
                    return;
                }
                onAddChildClick.onAddChildClick(new AddChildClickEvent());
            }
        });
    }

    public void setEditClickHandler(final EditClickHandler onEditClick) {
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (onEditClick == null) {
                    return;
                }
                onEditClick.onEditClick(new EditClickEvent());
            }
        });
    }

    public void setAfterDeleteHandler(final AfterDeleteHandler onAfterDelete) {
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (onAfterDelete == null) {
                    return;
                }
                onAfterDelete.onAfterDelete(new AfterDeleteEvent());
            }
        });
    }

    public void setButtonsActive(Boolean isActive) {
        addChildButton.setEnabled(isActive);
        editButton.setEnabled(isActive);
        deleteButton.setEnabled(isActive);
    }
}
