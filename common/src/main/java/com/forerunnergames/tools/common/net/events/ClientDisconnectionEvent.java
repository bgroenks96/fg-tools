package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

public final class ClientDisconnectionEvent extends NetworkEvent
{
  public ClientDisconnectionEvent (final Remote client)
  {
    super (client);
  }

  public Remote getClient()
  {
    return getRemote();
  }
}