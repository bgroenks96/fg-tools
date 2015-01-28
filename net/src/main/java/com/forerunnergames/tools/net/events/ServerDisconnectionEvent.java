package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.net.Remote;

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
