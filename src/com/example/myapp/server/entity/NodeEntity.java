package com.example.myapp.server.entity;

public class NodeEntity {
    private int id;
    private Integer parentId;
    private String name;
    private String ip;
    private int port;

    public NodeEntity(int id, Integer parentId, String name, String ip, int port) {
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
