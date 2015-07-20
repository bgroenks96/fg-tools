package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.net.Remote;

/**
 * Clients & servers should use this event as a base class for notifying all local listeners that a network-related
 * event occurred. It contains information about the remote end of the network connection.
 *
 * This event is not intended to be sent over the network.
 */
public abstract class NetworkEvent implements LocalEvent
{
  private final Remote remote;

  public NetworkEvent (final Remote remote)
  {
    Arguments.checkIsNotNull (remote, "remote");

    this.remote = remote;
  }

  public Remote getRemote ()
  {
    return remote;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Remote : %2$s", getClass ().getSimpleName (), remote);
  }
}
