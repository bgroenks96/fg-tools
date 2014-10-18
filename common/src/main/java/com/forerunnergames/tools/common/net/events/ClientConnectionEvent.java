package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

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