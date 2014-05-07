package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Arguments;
import com.forerunnergames.tools.Event;
import com.forerunnergames.tools.net.Remote;

public abstract class NetworkCommunicationEvent extends NetworkEvent
{
  private final Event message;

  public NetworkCommunicationEvent (final Event message, final Remote sender)
  {
    super (sender);

    Arguments.checkIsNotNull (message, "message");

    this.message = message;
  }

  protected final Event getMessage()
  {
    return message;
  }

  protected final Remote getSender()
  {
    return getRemote();
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Message: %2$s | Sender: %3$s", getClass().getSimpleName(), getMessage(), getSender());
  }
}
