package com.forerunnergames.tools.common;

public class Result <T>
{
  private final boolean isSuccessful;
  private final T failureReason;

  public static <U> Result <U> success ()
  {
    return new Result <> (true, null);
  }

  @SuppressWarnings ("unchecked")
  public static <V> Result <V> failure (final V failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    return new Result <> (false, failureReason);
  }

  public static <V> boolean anyResultsFailed (final Iterable <Result <V>> results)
  {
    for (final Result <V> result : results)
    {
      if (result.failed ()) return true;
    }

    return false;
  }

  public static <V extends ReturnStatus <?>> boolean anyStatusFailed (final Iterable <V> results)
  {
    for (final V status : results)
    {
      if (status.getResult ().failed ()) return true;
    }

    return false;
  }

  public boolean isSuccessful ()
  {
    return isSuccessful;
  }

  public boolean succeeded ()
  {
    return isSuccessful ();
  }

  public boolean isFailure ()
  {
    return !isSuccessful;
  }

  public boolean failed ()
  {
    return isFailure ();
  }

  public boolean failedBecauseOf (final T failureReason)
  {
    return isFailure () && failureReasonIs (failureReason);
  }

  public boolean failureReasonIs (final T failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    return getFailureReason ().equals (failureReason);
  }

  public T getFailureReason ()
  {
    Preconditions.checkIsTrue (isFailure (), "Cannot get failure reason when result is successful.");

    return failureReason;
  }

  public interface ReturnStatus <R>
  {
    Result <R> getResult ();
  }

  protected Result (final boolean isSuccessful, final T failureReason)
  {
    this.isSuccessful = isSuccessful;
    this.failureReason = failureReason;
  }
}
