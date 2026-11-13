package com.example.myapp.client.uipanel;

import com.example.myapp.event.handler.AddChildClickHandler;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.EditClickHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface UiPanelDisplay extends IsWidget {

    void setRefreshHandler(RefreshHandler onRefresh);

    void setSelectedNodeId(Long id);

    void setAddRootClickHandler(AddRootClickHandler onAddRootClick);

    void setAddChildClickHandler(AddChildClickHandler onAddChildClick);

    void setEditClickHandler(EditClickHandler onEditClick);

    void setAfterDeleteHandler(AfterDeleteHandler onAfterDelete);

    void setButtonsActive(Boolean isActive);
}
