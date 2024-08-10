package entities;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The ClientInfo class represents information about a client, including
 * its host name, IP address, and status.
 * This class implements the Serializable interface, allowing it to be
 * serialized for storage or transmission.
 * 
 * The host, IP, and status properties are implemented using JavaFX's
 * StringProperty, which allows for easy binding and change listening in
 * a JavaFX application.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furmann
 * @author Omri Heit
 * @author Eithan Zerbel
 */
public class ClientInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** The host name of the client. */
    private final StringProperty host;
    
    /** The IP address of the client. */
    private final StringProperty ip;
    
    /** The status of the client. */
    private final StringProperty status;

    /**
     * Constructs a new ClientInfo object with the specified host name,
     * IP address, and status.
     * 
     * @param host the host name of the client
     * @param ip the IP address of the client
     * @param status the status of the client
     */
    public ClientInfo(String host, String ip, String status) {
        this.host = new SimpleStringProperty(host);
        this.ip = new SimpleStringProperty(ip);
        this.status = new SimpleStringProperty(status);
    }

    /**
     * Gets the host name of the client.
     * 
     * @return the host name of the client
     */
    public String getHost() {
        return host.get();
    }

    /**
     * Sets the host name of the client.
     * 
     * @param host the new host name of the client
     */
    public void setHost(String host) {
        this.host.set(host);
    }

    /**
     * Returns the host name property, which can be used for binding.
     * 
     * @return the host name property
     */
    public StringProperty hostProperty() {
        return host;
    }

    /**
     * Gets the IP address of the client.
     * 
     * @return the IP address of the client
     */
    public String getIp() {
        return ip.get();
    }

    /**
     * Sets the IP address of the client.
     * 
     * @param ip the new IP address of the client
     */
    public void setIp(String ip) {
        this.ip.set(ip);
    }

    /**
     * Returns the IP address property, which can be used for binding.
     * 
     * @return the IP address property
     */
    public StringProperty ipProperty() {
        return ip;
    }

    /**
     * Gets the status of the client.
     * 
     * @return the status of the client
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * Sets the status of the client.
     * 
     * @param status the new status of the client
     */
    public void setStatus(String status) {
        this.status.set(status);
    }

    /**
     * Returns the status property, which can be used for binding.
     * 
     * @return the status property
     */
    public StringProperty statusProperty() {
        return status;
    }
}