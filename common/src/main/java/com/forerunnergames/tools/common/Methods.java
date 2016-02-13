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

public final class Methods
{
  /**
   * Gets the name of a method in the call stack of the current thread.
   *
   * @param level
   *          The desired stack level, where level 0 will return the name of the current method (i.e., the caller of
   *          this method), and each successive increasing level will get the name of the parent calling method. Must be
   *          >= 0, must not specify a level greater than the bottom of the call stack (i.e. the main method).
   *
   * @return The name of the method at the specified stack level.
   */
  public static String getMethodName (final int level)
  {
    int length = Thread.currentThread ().getStackTrace ().length;

    Arguments.checkLowerInclusiveBound (level, 0, "level");
    Arguments.checkUpperExclusiveBound (level, length - 2, "level");

    return Thread.currentThread ().getStackTrace () [level + 2].getMethodName ();
  }

  /**
   * Throws an exception informing the caller that the calling method is unsupported.
   *
   * @throws UnsupportedOperationException
   *           When called.
   */
  public static void unsupportedMethod () throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException (
            "Unsupported method: " + Classes.getClassName (1) + "." + Methods.getMethodName (1));
  }

  private Methods ()
  {
    Classes.instantiationNotAllowed ();
  }
}
