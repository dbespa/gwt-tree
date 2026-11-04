package com.example.myapp.client.editform;

import com.example.myapp.client.handler.CancelHandler;
import com.example.myapp.client.handler.SaveHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeEditFormDisplay extends IsWidget {

    void setCancelHandler(CancelHandler onCancel);
    
    void setSaveHandler(SaveHandler onSave);

    void setSelectedNode(NodeBean node);

    void setCreateHeader();

    void setEditHeader();

    void setEmpty();

    void show();

    void hide();
}
