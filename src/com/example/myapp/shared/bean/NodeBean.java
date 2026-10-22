package com.example.myapp.shared.bean;

import java.io.Serializable;

public class NodeBean implements Serializable {

    private static final long serialVersionUID = 7700175907117996475L;

    private Long id;
    private Long parentId;
    private String name;
    private String ip;
    private Integer port;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public NodeBean() {
        // do nothing
    }

    public NodeBean(Long id, Long parentId, String name, String ip, Integer port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
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

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "NodeBean [id=" + id + ", parentId=" + parentId + ", name=" + name + ", ip=" + ip + ", port=" + port + "]";
    }
}
