package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

/**
 * A client should use this event to notify all local listeners that communication from the server was received over the
 * network.
 *
 * This event is not intended to be sent over the network. Rather, it wraps an event (message) that was already sent
 * over the network from the server.
 */
public final class ServerCommunicationEvent extends NetworkCommunicationEvent
{
  public ServerCommunicationEvent (final Event message, final Remote server)
  {
    super (message, server);
  }

  public Remote getServer ()
  {
    return getSender ();
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s | Server: %2$s", super.toString (), getServer ());
  }
}
