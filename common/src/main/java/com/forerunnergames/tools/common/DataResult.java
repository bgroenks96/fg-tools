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

    return new DataResult<> (true, returnValue, null);
  }

  public static <V, R> DataResult <V, R> failureNoData (final R failureReason)
  {
    Arguments.checkIsNotNull (failureReason, "failureReason");

    return new DataResult<> (false, null, failureReason);
  }

  public V getReturnValue ()
  {
    if (failed ()) Exceptions.throwIllegalState ("Cannot fetch return value from failure result.");

    return returnValue;
  }
}
