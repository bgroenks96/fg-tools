package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.net.Remote;

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