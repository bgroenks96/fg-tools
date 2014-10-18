package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.net.Remote;

public abstract class NetworkEvent
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
