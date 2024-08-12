package entities;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final StringProperty host;
    private final StringProperty ip;
    private final StringProperty status;

    public ClientInfo(String host, String ip, String status) {
        this.host = new SimpleStringProperty(host);
        this.ip = new SimpleStringProperty(ip);
        this.status = new SimpleStringProperty(status);
    }

    public String getHost() {
        return host.get();
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public StringProperty hostProperty() {
        return host;
    }

    public String getIp() {
        return ip.get();
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}
