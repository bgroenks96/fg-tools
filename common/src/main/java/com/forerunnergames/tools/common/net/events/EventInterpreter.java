package com.forerunnergames.tools.common.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.net.Remote;

public final class EventInterpreter
{
  public static QuestionEvent questionFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getQuestion();
  }

  public static Remote clientFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static Remote clientFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static Remote clientFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static AnswerEvent answerFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getAnswer();
  }

  public static Remote serverFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static Remote serverFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static Remote serverFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }
}
