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
* @author Eitan Zerbel
* 
* @version August 2024
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
     * Retrieves the host name of the client.
     * 
     * @return The host name of the client.
     */
	public String getHost() 
	{
		return this.host;
	}
	
    /**
     * Sets the host name of the client.
     * 
     * @param host The new host name of the client.
     */
	public void setHost(String host) 
	{
		this.host = host;
	}
	
    /**
     * Retrieves the IP address of the client.
     * 
     * @return The IP address of the client.
     */
	public String getIp() 
	{
		return this.ip;
	}
	
    /**
     * Sets the IP address of the client.
     * 
     * @param ip The new IP address of the client.
     */
	public void setIp(String ip) 
	{
		this.ip = ip;
	}
	
    /**
     * Retrieves the status of the client.
     * 
     * @return The status of the client.
     */
	public String getStatus() 
	{
        return this.status;
    }

    /**
     * Sets the status of the client.
     * 
     * @param status The new status of the client.
     */
    public void setStatus(String status) 
    {
        this.status = status;
    }

}
