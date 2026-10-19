package com.example.myapp.server.entity;

import com.example.myapp.shared.StringUtils;

public class NodeEntity {

    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private Integer port;

    public NodeEntity() {
        //do nothing
    }

    public NodeEntity(Integer id, Integer parentId, String name, String ip, Integer port) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return StringUtils.NODE_ENTITY_TO_STRING_START + id + 
                StringUtils.PARENT_ID_TO_STRING + parentId + 
                StringUtils.NAME_TO_STRING + name + 
                StringUtils.IP_TO_STRING + ip + 
                StringUtils.PORT_TO_STRING + port + 
                StringUtils.NODE_TO_STRING_END;
    }
}
