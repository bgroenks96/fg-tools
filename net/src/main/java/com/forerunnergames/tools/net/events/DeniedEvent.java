package com.forerunnergames.tools.net.events;

public interface DeniedEvent <T> extends AnswerEvent
{
  public T getReason();
}
