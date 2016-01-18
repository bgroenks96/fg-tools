package com.forerunnergames.tools.common;

import com.google.common.base.Optional;

public class Result <T>
{
  private final boolean isSuccessful;
  private final T failureReason;

  protected Result (final boolean isSuccessful, final T failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    this.isSuccessful = isSuccessful;
    this.failureReason = failureReason;
  }

  public static <U> Result <U> success ()
  {
    return new Result <> (true, null);
  }

  public static <V> Result <V> failure (final V failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    return new Result <> (false, failureReason);
  }

  public static <V> boolean anyResultsFailed (final Iterable <Result <V>> results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    return firstFailedFrom (results).isPresent ();
  }

  public static <V, R extends Result <V>> Optional <R> firstFailedFrom (final Iterable <R> results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    for (final R result : results)
    {
      if (result.failed ()) return Optional.of (result);
    }

    return Optional.absent ();
  }

  public static <V extends ReturnStatus <?>> boolean anyStatusFailed (final Iterable <V> results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

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
    Arguments.checkIsNotNull (failureReason, "failureReason");

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
}
