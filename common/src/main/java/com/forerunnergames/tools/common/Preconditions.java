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

public final class Preconditions
{
  /**
   * Checks if the specified boolean condition evaluates to true.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception.
   *
   * @throws IllegalArgumentException
   *           If condition evaluates to false.
   */
  public static void checkIsTrue (final boolean condition, final String errorMessage)
  {
    if (!condition) throw new IllegalStateException (errorMessage);
  }

  /**
   * Checks if the specified boolean condition evaluates to true.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception, must not be null.
   * @param errorMessageArgs
   *          The arguments to include in the error message, must not be null, may contain null elements. See
   *          {@link Strings#format(String, Object...)}.
   *
   * @throws IllegalStateException
   *           If condition evaluates to false, errorMessage is null, errorMessageArgs is null.
   */
  public static void checkIsTrue (final boolean condition, final String errorMessage, final Object... errorMessageArgs)
  {
    Arguments.checkIsNotNull (errorMessage, "errorMessage");
    Arguments.checkIsNotNull (errorMessageArgs, "errorMessageArgs");

    if (!condition) throw new IllegalStateException (Strings.format (errorMessage, errorMessageArgs));
  }

  /**
   * Checks if the specified boolean condition evaluates to false.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception.
   *
   * @throws IllegalStateException
   *           If condition evaluates to true.
   */
  public static void checkIsFalse (final boolean condition, final String errorMessage)
  {
    if (condition) throw new IllegalStateException (errorMessage);
  }

  /**
   * Checks if the specified boolean condition evaluates to false.
   *
   * @param condition
   *          The boolean condition to check.
   * @param errorMessage
   *          The error message to include in the exception, must not be null.
   * @param errorMessageArgs
   *          The arguments to include in the error message, must not be null, may contain null elements. See
   *          {@link Strings#format(String, Object...)}.
   *
   * @throws IllegalStateException
   *           If condition evaluates to true, errorMessage is null, errorMessageArgs is null.
   */
  public static void checkIsFalse (final boolean condition, final String errorMessage, final Object... errorMessageArgs)
  {
    Arguments.checkIsNotNull (errorMessage, "errorMessage");
    Arguments.checkIsNotNull (errorMessageArgs, "errorMessageArgs");

    if (condition) throw new IllegalStateException (Strings.format (errorMessage, errorMessageArgs));
  }

  private Preconditions ()
  {
    Classes.instantiationNotAllowed ();
  }
}
