package com.forerunnergames.tools.common.net.events;

public interface DeniedEvent extends AnswerEvent
{
  public String getReasonForDenial();
}
