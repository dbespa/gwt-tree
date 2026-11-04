package com.example.myapp.client.editform;

public class NodeDto {
    private String parentId;
    private String name;
    private String ip;
    private String port;

    public NodeDto(String parentId, String name, String ip, String port) {
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "NodeDto [parentId=" + parentId + ", name=" + name + ", ip=" + ip + ", port=" + port + "]";
    }
}
