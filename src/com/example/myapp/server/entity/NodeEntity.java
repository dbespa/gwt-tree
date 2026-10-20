package com.example.myapp.server.entity;

import com.example.myapp.shared.StringUtils;

public class NodeEntity {

    private static final String NODE_ENTITY_TO_STRING = "NodeEntity: ";

    private Long id;
    private Long parentId;
    private String name;
    private String ip;
    private Integer port;

    public NodeEntity() {
        //do nothing
    }

    public NodeEntity(Long id, Long parentId, String name, String ip, Integer port) {
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return NODE_ENTITY_TO_STRING + StringUtils.LEFT_BRACKET +
                StringUtils.ID_TO_STRING + StringUtils.EQUALS + id + StringUtils.SEPARATOR +
                StringUtils.PARENT_ID_TO_STRING + StringUtils.EQUALS + parentId + StringUtils.SEPARATOR +
                StringUtils.NAME_TO_STRING + StringUtils.EQUALS + name + StringUtils.SEPARATOR +
                StringUtils.IP_TO_STRING + StringUtils.EQUALS + ip + StringUtils.SEPARATOR +
                StringUtils.PORT_TO_STRING + StringUtils.EQUALS + port + StringUtils.RIGTH_BRACKET;
    }
}
