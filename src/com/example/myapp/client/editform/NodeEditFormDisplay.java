package com.example.myapp.client.editform;

import com.example.myapp.client.handler.CancelHandler;
import com.example.myapp.client.handler.SaveHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeEditFormDisplay extends IsWidget {

    void setCancelHandler(CancelHandler onCancel);

    void setSaveHandler(SaveHandler onSave);

    void setSelectedNode(NodeFormViewData node);

    void setModeCreate();

    void setModeEdit(NodeFormViewData node);

    void show();

    void hide();

    void setNameFieldInvalid();

    void setIpFieldInvalid();

    void setPortFieldInvalid();

    void setAllFieldsValid();
}
