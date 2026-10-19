package com.example.myapp.shared.bean;

import java.io.Serializable;

import com.example.myapp.shared.StringUtils;

public class NodeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private Integer port;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public NodeBean() {
        // do nothing
    }

    public NodeBean(Integer id, Integer parentId, String name, String ip, Integer port) {
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

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return StringUtils.NODE_BEAN_TO_STRING_START + id + 
                StringUtils.PARENT_ID_TO_STRING + parentId + 
                StringUtils.NAME_TO_STRING + name + 
                StringUtils.IP_TO_STRING + ip + 
                StringUtils.PORT_TO_STRING + port + 
                StringUtils.NODE_TO_STRING_END;
    }
}
