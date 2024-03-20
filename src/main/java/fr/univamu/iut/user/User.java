package fr.univamu.iut.user;

public class User {
    protected String id;
    protected String name;
    protected String pwd;
    protected String mail;

    public User() {
    }

    public User(String id, String mail, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
