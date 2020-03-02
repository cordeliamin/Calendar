public class User {
    public String userName;
    public String password;

    public User(String name, String pswd){
        userName = name;
        password = pswd;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
