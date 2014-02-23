package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Arguments;

import java.net.InetSocketAddress;

public final class EventInterpreter
{
  public static final int connectionIdFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getConnectionId();
  }

  public static final int connectionIdFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getConnectionId();
  }

  public static final int connectionIdFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getConnectionId();
  }

  public static final InetSocketAddress addressFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClientAddress();
  }

  public static final InetSocketAddress addressFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClientAddress();
  }

  public static final InetSocketAddress addressFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClientAddress();
  }

  public static final Object remoteObjectFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject();
  }

  public static final Class remoteObjectClassFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject().getClass();
  }

  public static final Object remoteObjectFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject();
  }

  public static final Class remoteObjectClassFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject().getClass();
  }

  public static final InetSocketAddress addressFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServerAddress();
  }

  public static final InetSocketAddress addressFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServerAddress();
  }

  public static final InetSocketAddress addressFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServerAddress();
  }

  public static final int connectionIdFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getConnectionId();
  }
}
