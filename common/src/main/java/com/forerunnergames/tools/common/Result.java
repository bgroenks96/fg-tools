/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.forerunnergames.tools.common;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public class Result <T>
{
  private final boolean isSuccessful;
  @Nullable
  private final T failureReason;

  protected Result (final boolean isSuccessful, @Nullable final T failureReason)
  {
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
