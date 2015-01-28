package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Event;
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

  public final Event getMessage ()
  {
    return message;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Message: %2$s | Sender: %3$s", getClass ().getSimpleName (), getMessage (),
                    getSender ());
  }

  protected final Remote getSender ()
  {
    return getRemote ();
  }
}
