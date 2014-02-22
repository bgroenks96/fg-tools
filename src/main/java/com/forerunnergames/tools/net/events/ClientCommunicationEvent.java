package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Arguments;

import java.net.InetSocketAddress;

public final class ClientCommunicationEvent
{
  private final Object remoteObject;
  private final int connectionId;
  private final InetSocketAddress clientAddress;

  public ClientCommunicationEvent (final Object remoteObject, final int connectionId, final InetSocketAddress clientAddress)
  {
    Arguments.checkIsNotNull (remoteObject, "remoteObject");

    this.remoteObject = remoteObject;
    this.connectionId = connectionId;
    this.clientAddress = clientAddress;
  }

  public final Object getRemoteObject()
  {
    return remoteObject;
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