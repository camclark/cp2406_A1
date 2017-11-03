public class Player {
    private String username;
    private Integer number;
    private String ip;
    private Integer socket;

    public Player(String username, String ip, Integer socket, int number) {
        this.username = username;
        this.socket = socket;
        this.ip = ip;
        this.number = number;
    }

    String getUsername() {
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

    String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    Integer getNumber() {
        return number;
    }
}
