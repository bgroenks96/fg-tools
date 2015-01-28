package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.net.Remote;

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
