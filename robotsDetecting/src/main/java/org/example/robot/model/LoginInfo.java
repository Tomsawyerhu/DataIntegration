package org.example.robot.model;

import javax.persistence.Column;

public class LoginInfo {
    @Column(name = "sessionid")
    private String sessionId;

    @Column(name="logintime")
    private String loginTime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "userid")
    private String userId;

    @Column(name = "passwd")
    private String password;

    @Column(name = "authcode")
    private String authCode;

    @Column(name="success")
    private int success;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public boolean isSuccess(){return success==1;}
}
