package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

public final class ClientCommunicationEvent extends NetworkCommunicationEvent
{
  public ClientCommunicationEvent (final Event message, final Remote client)
  {
    super (message, client);
  }

  public Remote getClient()
  {
    return getSender();
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s | Client: %2$s", super.toString(), getClient());
  }
}
