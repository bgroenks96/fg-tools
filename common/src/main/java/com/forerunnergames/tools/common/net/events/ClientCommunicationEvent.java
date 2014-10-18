package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.net.Remote;

public final class ClientCommunicationEvent extends NetworkCommunicationEvent
{
  public ClientCommunicationEvent (final QuestionEvent question, final Remote client)
  {
    super (question, client);
  }

  public QuestionEvent getQuestion()
  {
    return (QuestionEvent) getMessage();
  }

  public Remote getClient()
  {
    return getSender();
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Question: %2$s | Client: %3$s", getClass().getSimpleName(), getQuestion(), getClient());
  }
}