package com.forerunnergames.tools;

public final class Result
{
  private final boolean isSuccessful;
  private final String message;

  public static final Result success()
  {
    return new Result (true, "");
  }

  public static final Result failure (final String message)
  {
    Arguments.checkIsNotNull (message, "message");

    return new Result (false, message);
  }

  public final boolean isSuccessful()
  {
    return isSuccessful;
  }

  public final boolean isFailure()
  {
    return ! isSuccessful;
  }

  public final String getMessage()
  {
    return message;
  }

  private Result (final boolean isSuccessful, final String message)
  {
    this.isSuccessful = isSuccessful;
    this.message = message;
  }
}
