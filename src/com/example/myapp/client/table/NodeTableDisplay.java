package com.example.myapp.client.table;

import java.util.List;

import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.ui.IsWidget;

public interface NodeTableDisplay extends IsWidget {

    void setNodeList(List<NodeBean> newNodeList);
}
