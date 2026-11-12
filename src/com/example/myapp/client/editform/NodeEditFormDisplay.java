package com.example.myapp.client.editform;

import com.google.gwt.user.client.ui.IsWidget;

public interface NodeEditFormDisplay extends IsWidget {

    void setCancelHandler(CancelHandler onCancel);

    void setSaveHandler(SaveHandler onSave);

    void setSelectedNode(NodeEditFormViewData node);

    void showCreateForm();

    void showEditForm(NodeEditFormViewData node);

    void hide();

    void setNameFieldInvalid();

    void setIpFieldInvalid();

    void setPortFieldInvalid();

    void setAllFieldsValid();
}
