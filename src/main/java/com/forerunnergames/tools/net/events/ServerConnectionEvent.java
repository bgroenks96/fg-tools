package com.forerunnergames.tools.net.events;

import java.net.InetSocketAddress;

public final class ServerConnectionEvent
{
  private final int connectionId;
  private final InetSocketAddress serverAddress;

  public ServerConnectionEvent (final int connectionId, final InetSocketAddress serverAddress)
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