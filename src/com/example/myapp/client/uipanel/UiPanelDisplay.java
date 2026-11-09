package com.example.myapp.client.uipanel;

import com.example.myapp.client.handler.AddChildHandler;
import com.example.myapp.client.handler.AddRootHandler;
import com.example.myapp.client.handler.DeleteHandler;
import com.example.myapp.client.handler.EditHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface UiPanelDisplay extends IsWidget {

    void setRefreshHandler(RefreshHandler onRefresh);

    void setSelectedNodeId(Long id);

    void setAddRootHandler(AddRootHandler onAddRoot);

    void setAddChildHandler(AddChildHandler onAddChild);

    void setEditHandler(EditHandler onEdit);

    void setDeleteHandler(DeleteHandler onDelete);

    void setButtonsActive(Boolean isActive);
}
