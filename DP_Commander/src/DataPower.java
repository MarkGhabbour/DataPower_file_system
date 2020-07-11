public class DataPower {

  private String ip ="192.168.232.128";
  private String port="5550";
  private String username = "admin";
  private String password = "idgadmin";

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  public String getIp() {
    return ip;
  }

  public String getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
