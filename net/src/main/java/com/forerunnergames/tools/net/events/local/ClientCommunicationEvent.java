package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

/**
 * The server should use this event to notify all local listeners that communication from a client was received over the
 * network.
 *
 * This event is not intended to be sent over the network. Rather, it wraps an event (message) that was already sent
 * over the network from a client.
 */
public final class ClientCommunicationEvent extends NetworkCommunicationEvent
{
  public ClientCommunicationEvent (final Event message, final Remote client)
  {
    super (message, client);
  }

  public Remote getClient ()
  {
    return getSender ();
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s | Client: %2$s", super.toString (), getClient ());
  }
}
