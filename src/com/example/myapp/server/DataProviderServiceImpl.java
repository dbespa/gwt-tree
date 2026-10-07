package com.example.myapp.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myapp.client.DataProviderService;
import com.example.myapp.shared.TreeNodeBean;
import com.example.myapp.shared.tree.Tree;
import com.example.myapp.shared.tree.TreeNode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataProviderServiceImpl extends RemoteServiceServlet implements DataProviderService {
    private static int idCounter = 0;

    TreeNodeBean rootNode = new TreeNodeBean(nextId(), null, "rootNode", "127.0.0.1", 1111);
    TreeNodeBean node1 = new TreeNodeBean(nextId(), rootNode.getId(), "node-1", "127.0.1.1", 11);
    TreeNodeBean node2 = new TreeNodeBean(nextId(), rootNode.getId(), "node-2", "127.0.1.2", 12);
    TreeNodeBean node3 = new TreeNodeBean(nextId(), node2.getId(), "node-3", "127.0.1.21", 121);

    List<TreeNodeBean> result = new ArrayList<>(Arrays.asList(rootNode, node1, node2, node3));

    private TreeDao treeDao = new TreeDao(result);
    private final TreeServiceImpl treeService =new TreeServiceImpl();

    private int nextId() {
        return idCounter++;
    }

    public List<TreeNode> getTreeNodes() {
        try {
            Tree tree = treeDao.getTree();
            return treeService.getAllDescendants(tree);
        } catch (Throwable e) {
            System.err.println("Ошибка сервера");
            e.printStackTrace();
            throw e;
        }
    }

    public Tree getTree() {
        return treeDao.getTree();
    }
}
