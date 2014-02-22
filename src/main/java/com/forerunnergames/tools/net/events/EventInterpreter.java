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

    return event.getClientAddress ();
  }

  public static final InetSocketAddress addressFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClientAddress ();
  }

  public static final InetSocketAddress addressFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClientAddress ();
  }

  public static final Object objectFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject ();
  }

  public static final Class objectClassFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemoteObject ().getClass();
  }
}
