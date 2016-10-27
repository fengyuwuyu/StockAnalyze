package com.stock.model.email;

public class CecepEmail {
    private Integer id;

    private String host;

    private String username;

    private String password;

    private String eamilAddr;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEamilAddr() {
        return eamilAddr;
    }

    public void setEamilAddr(String eamilAddr) {
        this.eamilAddr = eamilAddr == null ? null : eamilAddr.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}