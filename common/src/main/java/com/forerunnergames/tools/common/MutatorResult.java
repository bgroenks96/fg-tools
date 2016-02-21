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
import com.google.common.collect.ImmutableSet;

public final class MutatorResult <R> extends Result <R>
{
  private final Optional <MutatorCallback> callback;

  private MutatorResult (final boolean isSuccessful, final R failureReason, final MutatorCallback callback)
  {
    super (isSuccessful, failureReason);

    this.callback = Optional.fromNullable (callback);
  }

  public static <R> MutatorResult <R> success (final MutatorCallback callback)
  {
    Arguments.checkIsNotNull (callback, "callback");

    return new MutatorResult <> (true, null, callback);
  }

  public static <R> MutatorResult <R> failure (final R reason)
  {
    Arguments.checkIsNotNull (reason, "reason");

    return new MutatorResult <> (false, reason, null);
  }

  public static void commitAllSuccessful (final MutatorResult <?>... results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    commitAllSuccessful (ImmutableSet.copyOf (results));
  }

  public static void commitAllSuccessful (final Iterable <MutatorResult <?>> results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    for (final MutatorResult <?> result : results)
    {
      result.commitIfSuccessful ();
    }
  }

  public final boolean commitIfSuccessful ()
  {
    if (callback.isPresent ()) callback.get ().commitChanges ();
    return succeeded ();
  }

  public interface MutatorCallback
  {
    void commitChanges ();
  }
}
