package com.example.planer_0724;

public class UserData {
    String id;
    String pw;
    String nick;
    String name;
    String age;
    String pn;


    public UserData(String id, String pw,
                    String nick, String name,
                    String age, String pn)  {
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.name = name;
        this.age = age;
        this.pn = pn;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }


}
