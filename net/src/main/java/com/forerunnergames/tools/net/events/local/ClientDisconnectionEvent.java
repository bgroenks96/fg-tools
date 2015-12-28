package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.net.Remote;

/**
 * The server should use this event to notify all local listeners that a client disconnected from it. It contains the
 * information of the newly-disconnected remote client.
 *
 * This event is not intended to be sent over the network.
 */
public final class ClientDisconnectionEvent extends NetworkEvent
{
  public ClientDisconnectionEvent (final Remote client)
  {
    super (client);
  }

  public Remote getClient ()
  {
    return getRemote ();
  }
}
