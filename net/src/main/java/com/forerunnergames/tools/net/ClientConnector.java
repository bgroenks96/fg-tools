package com.forerunnergames.tools.net;

public interface ClientConnector
{
  boolean isConnected (final Remote client);

  void disconnect (final Remote client);

  void disconnectAll ();
}
