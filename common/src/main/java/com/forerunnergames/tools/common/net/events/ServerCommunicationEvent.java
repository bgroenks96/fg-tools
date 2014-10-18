package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

public final class ServerCommunicationEvent extends NetworkCommunicationEvent
{
  public ServerCommunicationEvent (final AnswerEvent answer, final Remote server)
  {
    super (answer, server);
  }

  public AnswerEvent getAnswer()
  {
    return (AnswerEvent) getMessage();
  }

  public Remote getServer()
  {
    return getSender();
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Answer: %2$s | Server: %3$s", getClass().getSimpleName(), getAnswer(), getServer());
  }
}