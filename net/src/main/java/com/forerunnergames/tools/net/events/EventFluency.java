package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;
import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;
import com.forerunnergames.tools.net.events.local.NetworkCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ServerCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ServerConnectionEvent;
import com.forerunnergames.tools.net.events.local.ServerDisconnectionEvent;
import com.forerunnergames.tools.net.events.local.ClientCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ClientConnectionEvent;
import com.forerunnergames.tools.net.events.local.ClientDisconnectionEvent;

public final class EventFluency
{
  public static Remote clientFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient ();
  }

  public static Remote clientFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient ();
  }

  public static Remote clientFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient ();
  }

  public static Event messageFrom (final NetworkCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getMessage ();
  }

  public static Remote serverFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer ();
  }

  public static Remote serverFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer ();
  }

  public static Remote serverFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer ();
  }

  private EventFluency ()
  {
    Classes.instantiationNotAllowed ();
  }
}
