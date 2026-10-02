package com.example.myapp.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myapp.client.DataProviderService;
import com.example.myapp.shared.TreeNodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataProviderImpl extends RemoteServiceServlet implements DataProviderService {
    private static int idCounter = 0;

    private int nextId() {
        return idCounter++;
    }

    public List<TreeNodeBean> getTreeNodes() {
        try {
            GWT.log(String.valueOf(idCounter));
            TreeNodeBean rootNode = new TreeNodeBean(nextId(), null, "rootNode", "127.0.0.1", 1111);
            TreeNodeBean node1 = new TreeNodeBean(nextId(), rootNode.getId(), "node-1", "127.0.1.1", 11);
            TreeNodeBean node2 = new TreeNodeBean(nextId(), rootNode.getId(), "node-2", "127.0.1.2", 12);
            TreeNodeBean node3 = new TreeNodeBean(nextId(), node2.getId(), "node-3", "127.0.1.21", 121);

            List<TreeNodeBean> result = new ArrayList<>(Arrays.asList(rootNode, node1, node2, node3));

            GWT.log(String.valueOf(idCounter));

            return result;
        } catch (Throwable e) {
            System.err.println("Ошибка сервера");
            e.printStackTrace();
            throw e;
        }

    }
}
