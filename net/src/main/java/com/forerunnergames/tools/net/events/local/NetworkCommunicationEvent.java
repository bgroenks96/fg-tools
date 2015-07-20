package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Event;
import com.forerunnergames.tools.net.Remote;

/**
 * Clients & servers should use this event to notify all local listeners that communication was received over the
 * network.
 *
 * This event is not intended to be sent over the network. Rather, it wraps an event (message) that was already sent
 * over the network.
 */
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

  protected final Remote getSender ()
  {
    return getRemote ();
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Message: %2$s | Sender: %3$s", getClass ().getSimpleName (), getMessage (),
                          getSender ());
  }
}
