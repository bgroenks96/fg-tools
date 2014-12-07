package com.forerunnergames.tools.common;

public final class ResultFluency
{
  public static <T> T failureReasonFrom (final Result <T> result)
  {
    return result.getFailureReason();
  }

  private ResultFluency()
  {
    Classes.instantiationNotAllowed();
  }
}
