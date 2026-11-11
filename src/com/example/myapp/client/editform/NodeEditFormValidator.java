package com.example.myapp.client.editform;

public class NodeEditFormValidator {

    public NodeEditFormValidator() {
        //do nothing
    }

    public Boolean isValid(NodeViewData node) {
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
            try {
                Integer part = Integer.parseInt(parts[i]);
                if (part < 0 || part > 255) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public Boolean portIsValid(String port) {
        if (port == null || port.trim().isEmpty()) {
            return false;
        }

        Integer portInt = null;
        try {
            portInt = Integer.parseInt(port.trim());

            if (portInt < 0 || portInt > 65535) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
