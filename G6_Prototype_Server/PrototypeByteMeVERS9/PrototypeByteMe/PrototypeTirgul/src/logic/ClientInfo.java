package logic;

import java.io.Serializable;

public class ClientInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String host;
	private String ip;
	private String status;
	
	public ClientInfo(String host, String ip, String status)
	{
        this.ip = ip;
        this.host = host;
        this.status = status;
    }
	
	
	public String getHost() 
	{
		return this.host;
	}
	
	
	public void setHost(String host) 
	{
		this.host = host;
	}
	
	
	public String getIp() 
	{
		return this.ip;
	}
	
	
	public void setIp(String ip) 
	{
		this.ip = ip;
	}
	
	
	public String getStatus() 
	{
        return this.status;
    }

	
    public void setStatus(String status) 
    {
        this.status = status;
    }

}
