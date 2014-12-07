package com.forerunnergames.tools.common.net.events;

public interface DeniedEvent <T> extends AnswerEvent
{
  public T getReason();
}
