package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

public final class ServerDisconnectionEvent extends NetworkEvent
{
  public ServerDisconnectionEvent (final Remote server)
  {
    super (server);
  }

  public Remote getServer()
  {
    return getRemote();
  }
}