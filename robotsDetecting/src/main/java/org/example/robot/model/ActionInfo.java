package org.example.robot.model;

import javax.persistence.Column;

public class ActionInfo {
    @Column(name = "sessionid")
    private String sessionId;

    @Column(name = "actiontime")
    private String actionTime;

    @Column(name = "actiontype")
    private String actionType;

    @Column(name="userid")
    private String userId;

    @Column(name = "itemid")
    private String itemId;

    @Column(name="categoryid")
    private String categoryId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isBuyAction(){
        return this.actionType.equals("buy");
    }

    public boolean isGetDetailAction(){
        return this.actionType.equals("getDetail");
    }


}
