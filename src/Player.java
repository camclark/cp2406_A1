public class Player {
    String username;
    Integer number;
    String ip;
    Integer socket;

    public Player(String username, String ip, Integer socket, int number) {
        this.username = username;
        this.socket = socket;
        this.ip = ip;
        this.number = number;
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
