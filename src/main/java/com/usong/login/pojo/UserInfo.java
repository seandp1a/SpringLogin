package com.usong.login.pojo;

public class UserInfo {
    Integer id;
    String password;
    String name;
    String access;

    public String getAccess() {
        return access;
    }

    public void setAccess(String accesses) {
        this.access = accesses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer  id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
