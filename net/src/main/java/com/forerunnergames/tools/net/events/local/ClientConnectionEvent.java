package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.net.Remote;
import com.forerunnergames.tools.net.events.local.NetworkEvent;

/**
 * The server should use this event to notify all local listeners that a client connected to it. It contains the
 * information of the newly-connected remote client.
 *
 * This event is not intended to be sent over the network.
 */
public final class ClientConnectionEvent extends NetworkEvent
{
  public ClientConnectionEvent (final Remote client)
  {
    super (client);
  }

  public Remote getClient ()
  {
    return getRemote ();
  }
}
