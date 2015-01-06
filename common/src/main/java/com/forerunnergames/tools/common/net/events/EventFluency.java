package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.common.net.Remote;

public final class EventFluency
{

  public static Remote clientFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static Remote clientFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static Remote clientFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static Event messageFrom (final NetworkCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getMessage();
  }

  public static Remote serverFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static Remote serverFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static Remote serverFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }
}
