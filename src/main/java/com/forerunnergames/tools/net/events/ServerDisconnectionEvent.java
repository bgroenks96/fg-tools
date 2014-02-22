package com.forerunnergames.tools.net.events;

import java.net.InetSocketAddress;

public final class ServerDisconnectionEvent
{
  private final int connectionId;
  private final InetSocketAddress serverAddress;

  public ServerDisconnectionEvent (final int connectionId, final InetSocketAddress serverAddress)
  {
    this.connectionId = connectionId;
    this.serverAddress = serverAddress;
  }

  public final int getConnectionId()
  {
    return connectionId;
  }

  public boolean hasServerAddress()
  {
    return serverAddress != null;
  }

  public final InetSocketAddress getServerAddress()
  {
    return serverAddress;
  }
}