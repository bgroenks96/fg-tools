package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

public abstract class NetworkEvent implements Event
{
  private final Remote remote;

  public NetworkEvent (final Remote remote)
  {
    Arguments.checkIsNotNull (remote, "remote");

    this.remote = remote;
  }

  public Remote getRemote()
  {
    return remote;
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Remote : %2$s", getClass().getSimpleName(), remote);
  }
}
