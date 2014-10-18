package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

public final class ServerConnectionEvent extends NetworkEvent
{
  public ServerConnectionEvent (final Remote server)
  {
    super (server);
  }

  public Remote getServer()
  {
    return getRemote();
  }
}