public class Player {
    String username;
    String ip;
    Integer socket;

    public Player(String username, String ip, Integer socket) {
        this.username = username;
        this.socket = socket;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSocket() {
        return socket;
    }

    public void setSocket(Integer socket) {
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
