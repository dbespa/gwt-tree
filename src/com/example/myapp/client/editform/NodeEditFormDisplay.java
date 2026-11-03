package com.example.myapp.client.editform;

import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeEditFormDisplay extends IsWidget {

    void setSelectedNode(NodeBean node);
}
