package com.forerunnergames.tools.common.net;

public interface ClientConnector
{
  public boolean isConnected (final Remote client);
  public void disconnect (final Remote client);
  public void disconnectAll();
}
