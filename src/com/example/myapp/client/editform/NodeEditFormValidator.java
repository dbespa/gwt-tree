package com.example.myapp.client.editform;

import com.example.myapp.client.StringUtils;

public class NodeEditFormValidator {

    public NodeEditFormValidator() {
        //do nothing
    }

    public Boolean isValid(NodeFormViewData node) {
        return nameIsValid(node.getName())
                && ipIsValid(node.getIp())
                && portIsValid(node.getPort());
    }

    public Boolean nameIsValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public Boolean ipIsValid(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }

        String[] parts = ip.trim().split("\\.");

        if (parts.length != 4) {
            return false;
        }

        for (int i = 0; i < parts.length; i++) {
            Integer part = StringUtils.parseIntQuiet(parts[i]);

            if (part == null) {
                return false;
            }

            if (part < 0 || part > 255) {
                return false;
            }
        }
        return true;
    }

    public Boolean portIsValid(String port) {
        if (port == null || port.trim().isEmpty()) {
            return false;
        }

        Integer portInt = StringUtils.parseIntQuiet(port.trim());

        if (portInt == null) {
            return false;
        }

        if (portInt < 0 || portInt > 65535) {
            return false;
        }

        return true;
    }
}
