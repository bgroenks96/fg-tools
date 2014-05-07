package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Arguments;
import com.forerunnergames.tools.net.Remote;

public final class EventInterpreter
{
  public static final QuestionEvent questionFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getQuestion();
  }

  public static final Remote clientFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static final Remote clientFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static final Remote clientFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getClient();
  }

  public static final AnswerEvent answerFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getAnswer();
  }

  public static final Remote serverFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static final Remote serverFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }

  public static final Remote serverFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getServer();
  }
}
