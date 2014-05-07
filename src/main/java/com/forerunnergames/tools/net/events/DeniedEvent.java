package com.forerunnergames.tools.net.events;

public interface DeniedEvent extends AnswerEvent
{
  public String getReasonForDenial();
}
