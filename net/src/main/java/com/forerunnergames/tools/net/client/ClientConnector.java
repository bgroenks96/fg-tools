package com.forerunnergames.tools.net.client;

import com.forerunnergames.tools.net.Remote;

public interface ClientConnector
{
  boolean isConnected (final Remote client);

  void disconnect (final Remote client);

  void disconnectAll ();
}
