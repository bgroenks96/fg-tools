package com.forerunnergames.tools.common;

public final class Result
{
  private final boolean isSuccessful;
  private final String message;

  public static Result success()
  {
    return new Result (true, "");
  }

  public static Result failure (final String message)
  {
    Arguments.checkIsNotNull (message, "message");

    return new Result (false, message);
  }

  public boolean isSuccessful()
  {
    return isSuccessful;
  }

  public boolean isFailure()
  {
    return ! isSuccessful;
  }

  public String getMessage()
  {
    return message;
  }

  private Result (final boolean isSuccessful, final String message)
  {
    this.isSuccessful = isSuccessful;
    this.message = message;
  }
}
