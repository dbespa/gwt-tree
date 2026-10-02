package com.example.myapp.shared;

import java.io.Serializable;

public class TreeNodeBean implements Serializable{
    private int id;
    private Integer parentId;
    private String name;
    private String ip;
    private int port;
    
    public TreeNodeBean() {
        
    }

    public TreeNodeBean(int id, Integer parentId, String name, String ip, int port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
