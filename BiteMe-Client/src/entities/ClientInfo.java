package entities;

import java.io.Serializable;

/**
 * The ClientInfo class represents information about a client, including
 * its host name, IP address, and status.
 * This class implements the Serializable interface, allowing it to be
 * serialized for storage or transmission.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eithan Zerbel
 */

public class ClientInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String host;
    private String ip;
    private String status;
    
    /**
     * Constructs a new ClientInfo object with the specified host, IP, and status.
     * 
     * @param host 		The host name of the client
     * @param ip the 	IP address of the client
     * @param status 	The status of the client
     */
    public ClientInfo(String host, String ip, String status)
    {
        this.ip = ip;
        this.host = host;
        this.status = status;
    }
    
    /**
     * Returns the host name of the client.
     * 
     * @return the host name of the client
     */
    public String getHost() 
    {
        return this.host;
    }
    
    /**
     * Sets the host name of the client.
     * 
     * @param host the new host name of the client
     */
    public void setHost(String host) 
    {
        this.host = host;
    }
    
    /**
     * Returns the IP address of the client.
     * 
     * @return the IP address of the client
     */
    public String getIp() 
    {
        return this.ip;
    }
    
    /**
     * Sets the IP address of the client.
     * 
     * @param ip the new IP address of the client
     */
    public void setIp(String ip) 
    {
        this.ip = ip;
    }
    
    /**
     * Returns the status of the client.
     * 
     * @return the status of the client
     */
    public String getStatus() 
    {
        return this.status;
    }

    /**
     * Sets the status of the client.
     * 
     * @param status the new status of the client
     */
    public void setStatus(String status) 
    {
        this.status = status;
    }
}