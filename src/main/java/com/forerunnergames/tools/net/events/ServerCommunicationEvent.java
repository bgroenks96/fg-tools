package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Arguments;

import java.net.InetSocketAddress;

public final class ServerCommunicationEvent
{
  private final Object remoteObject;
  private final int connectionId;
  private final InetSocketAddress serverAddress;

  public ServerCommunicationEvent (final Object remoteObject, final int connectionId, final InetSocketAddress serverAddress)
  {
    Arguments.checkIsNotNull (remoteObject, "remoteObject");

    this.remoteObject = remoteObject;
    this.connectionId = connectionId;
    this.serverAddress = serverAddress;
  }

  public final Object getRemoteObject()
  {
    return remoteObject;
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