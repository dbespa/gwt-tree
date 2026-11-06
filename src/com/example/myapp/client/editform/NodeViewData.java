package com.example.myapp.client.editform;

public class NodeViewData {
    private String name;
    private String ip;
    private String port;

    public NodeViewData() {
        // do nothing
    }

    public NodeViewData(String name, String ip, String port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
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
        return "NodeDto [name=" + name + ", ip=" + ip + ", port=" + port + "]";
    }
}
