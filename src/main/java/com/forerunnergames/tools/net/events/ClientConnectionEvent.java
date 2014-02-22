package com.forerunnergames.tools.net.events;

import java.net.InetSocketAddress;

public final class ClientConnectionEvent
{
  private final int connectionId;
  private final InetSocketAddress clientAddress;

  public ClientConnectionEvent (final int connectionId, final InetSocketAddress clientAddress)
  {
    this.connectionId = connectionId;
    this.clientAddress = clientAddress;
  }

  public final int getConnectionId()
  {
    return connectionId;
  }

  public boolean hasClientAddress()
  {
    return clientAddress != null;
  }

  public final InetSocketAddress getClientAddress()
  {
    return clientAddress;
  }
}