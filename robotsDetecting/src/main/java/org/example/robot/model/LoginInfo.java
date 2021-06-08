package org.example.robot.model;

import javax.persistence.Column;

public class LoginInfo {
    @Column(name = "sessionid")
    private String sessionId=null;

    @Column(name="logintime")
    private String loginTime=null;

    @Column(name = "ip")
    private String ip=null;

    @Column(name = "userid")
    private String userId=null;

    @Column(name = "passwd")
    private String password=null;

    @Column(name = "authcode")
    private String authCode=null;

    @Column(name="success")
    private int success=0;

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

    @Override
    public String toString() {
        return "LoginInfo{" +
                "sessionId='" + sessionId + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", ip='" + ip + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", authCode='" + authCode + '\'' +
                ", success=" + success +
                '}';
    }
}
