package com.example.myapp.client.selectednodeform;

import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.IsWidget;

public interface SelectedNodeFormDisplay extends IsWidget {

    void setSelectedNode(NodeBean node);

    void setEmpty();
}
