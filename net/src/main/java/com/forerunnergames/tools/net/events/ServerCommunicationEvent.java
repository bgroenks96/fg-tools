package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

public final class ServerCommunicationEvent extends NetworkCommunicationEvent
{
  public ServerCommunicationEvent (final Event message, final Remote server)
  {
    super (message, server);
  }

  public Remote getServer ()
  {
    return getSender ();
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s | Server: %2$s", super.toString (), getServer ());
  }
}
