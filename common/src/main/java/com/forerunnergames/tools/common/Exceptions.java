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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides static utility methods for throwing Exceptions with slf4j formatted error messages.
 */
public final class Exceptions
{
  private static final Logger log = LoggerFactory.getLogger (Exceptions.class);

  /**
   * Throws an Exception of the given type with the given formatted message.
   *
   * @param type
   *          the type of the exception
   * @param message
   *          the error message; use slf4j formatting
   * @param args
   *          the formatting arguments
   * @throws T
   *           ....do you really need to ask why?
   */
  public static <T extends Throwable> void throwAny (final Class <T> type, final String message, final Object... args)
          throws T
  {

    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");
    Arguments.checkHasNoNullElements (args, "args");

    try
    {
      throw type.getConstructor (String.class).newInstance (Strings.format (message, args));
    }
    catch (final Exception e)
    {
      log.error ("Failed to throw exception of type {}. Maybe it doesn't have a String only constructor?\n{}",
                 type.getSimpleName (), e);
      throw new RuntimeException (Strings.format ("[{}] " + message, args));
    }
  }

  public static void throwIllegalArg (final String message, final Object... args)
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");

    throw new IllegalArgumentException (Strings.format (message, args));
  }

  public static void throwIllegalState (final String message, final Object... args)
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");
    Arguments.checkHasNoNullElements (args, "args");

    throw new IllegalStateException (Strings.format (message, args));
  }

  public static void throwRuntime (final String message, final Object... args)
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");
    Arguments.checkHasNoNullElements (args, "args");

    throw new RuntimeException (Strings.format (message, args));
  }

  public static void throwIO (final String message, final Object... args) throws IOException
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");
    Arguments.checkHasNoNullElements (args, "args");

    throw new IOException (Strings.format (message, args));
  }

  public static void throwGeneric (final String message, final Object... args) throws Exception
  {
    Arguments.checkIsNotNull (message, "message");
    Arguments.checkIsNotNull (args, "args");
    Arguments.checkHasNoNullElements (args, "args");

    throw new Exception (Strings.format (message, args));
  }

  private Exceptions ()
  {
    Classes.instantiationNotAllowed ();
  }
}
