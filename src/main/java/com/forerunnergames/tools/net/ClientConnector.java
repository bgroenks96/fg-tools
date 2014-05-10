package com.forerunnergames.tools.net;

public interface ClientConnector
{
  public void disconnect (final Remote client);
  public void disconnectAll();
  public boolean isConnected (final Remote client);
}
