package com.forerunnergames.tools.common;

import javax.annotation.Nullable;

public final class DataResult <V, R> extends Result <R>
{
  private final V returnValue;

  protected DataResult (final boolean isSuccessful, @Nullable final V returnValue, @Nullable final R failureReason)
  {
    super (isSuccessful, failureReason);

    this.returnValue = returnValue;
  }

  public static <V, R> DataResult <V, R> success (final V returnValue)
  {
    Arguments.checkIsNotNull (returnValue, "returnValue");

    return new DataResult <> (true, returnValue, null);
  }

  public static <V, R> DataResult <V, R> failureNoData (final R failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    return new DataResult <> (false, null, failureReason);
  }

  public V getReturnValue ()
  {
    if (failed ()) Exceptions.throwIllegalState ("Cannot fetch return value from failure result.");

    return returnValue;
  }
}
