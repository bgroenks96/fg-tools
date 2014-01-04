// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public class Classes
{
  /**
   * Throws an exception informing the caller that the default constructor is not supported for the calling class.
   *
   * @throws UnsupportedOperationException When called.
   */
  public static void defaultConstructorNotSupported() throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException ("Default constructor not supported for class " + Classes.getClassName (1) + ".");
  }

  /**
   * Gets the name of a class in the call stack of the current thread.
   *
   * @param level The desired stack level, where level 0 will return the name of the current class (i.e., the caller of
   *              this method), and each successive increasing level will get the name of the parent calling class.
   *              Must be >= 0, must not specify a level greater than the bottom of the call stack (i.e. the main class).
   *
   * @return The name of the class at the specified stack level.
   */
  public static String getClassName (final int level)
  {
    Arguments.checkIsNotNegative (level, "level");

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    Arguments.checkUpperExclusiveBound (level, stackTrace.length - 2, "level");

    return stackTrace[level + 2].getClassName();
  }

  /**
   * Throws an exception informing the caller that instantiation is not allowed for the calling class.
   *
   * @throws UnsupportedOperationException When called.
   */
  public static void instantiationNotAllowed() throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException ("Instantiation not allowed for class " + Classes.getClassName (1) + ".");
  }

  private Classes()
  {
    Classes.instantiationNotAllowed();
  }
}
