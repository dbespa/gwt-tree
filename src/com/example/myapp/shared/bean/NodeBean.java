package com.example.myapp.shared.bean;

import java.io.Serializable;

public class NodeBean implements Serializable {
    private int id;
    private Integer parentId;
    private String name;
    private String ip;
    private int port;

    public NodeBean() {

    }

    public NodeBean(int id, Integer parentId, String name, String ip, int port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
