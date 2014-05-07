package com.forerunnergames.tools.net;

public interface ServerConnector
{
  public void connect (final String address, final int tcpPort, final int timeoutMs, final int maxAttempts);
  public boolean isConnected();
  public String getErrorMessage();
  public void disconnect();
}
