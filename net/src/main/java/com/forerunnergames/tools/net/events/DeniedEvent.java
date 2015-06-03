package com.forerunnergames.tools.net.events;

public interface DeniedEvent <T> extends AnswerEvent
{
  T getReason ();
}
