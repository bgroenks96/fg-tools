package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.net.Remote;
import com.forerunnergames.tools.net.events.local.NetworkEvent;

/**
 * A client should use this event to notify all local listeners that it disconnected from the server. It contains the
 * information of the server that it disconnected from.
 *
 * This event is not intended to be sent over the network.
 */
public final class ServerDisconnectionEvent extends NetworkEvent
{
  public ServerDisconnectionEvent (final Remote server)
  {
    super (server);
  }

  public Remote getServer ()
  {
    return getRemote ();
  }
}
