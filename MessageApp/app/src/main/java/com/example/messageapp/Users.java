package com.example.messageapp;

public class Users {
    String profilepic,mail,userName, password, userId, lastMessage, status;
    public Users(){}

    public Users(String userId, String UserName, String emaill, String password, String profilepic, String status) {
        this.userId=userId;
        this.userName=UserName;
        this.mail=emaill;
        this.password=password;
        this.profilepic=profilepic;
        this.status=status;
        

    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
