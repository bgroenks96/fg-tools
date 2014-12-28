package com.forerunnergames.tools.common;

public final class ResultFluency
{
  public static <T> T failureReasonFrom (final Result <T> result)
  {
    Arguments.checkIsNotNull (result, "result");

    return result.getFailureReason();
  }

  public static <T> T withFailureReasonFrom (final Result <T> result)
  {
    return failureReasonFrom (result);
  }

  private ResultFluency()
  {
    Classes.instantiationNotAllowed();
  }
}
