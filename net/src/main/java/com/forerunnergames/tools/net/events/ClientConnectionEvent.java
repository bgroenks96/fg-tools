package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.net.Remote;

public final class ClientConnectionEvent extends NetworkEvent
{
  public ClientConnectionEvent (final Remote client)
  {
    super (client);
  }

  public Remote getClient()
  {
    return getRemote();
  }
}
