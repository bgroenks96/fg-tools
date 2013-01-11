// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

/**
 * Static Utility Class For Class-related Functionality
 * 
 * @author Aaron Mahan
 */
public class ClassUtils
{
  /**
   * Throws an exception informing the user that the default constructor is not
   * supported for the calling class.
   * 
   * @throws UnsupportedOperationException When called. 
   */
  public static void defaultConstructorNotSupported()
  {
    throw new UnsupportedOperationException ("No-argument constructor not " +
            "supported for class " + ClassUtils.getClassName (1) + ".");
  }

  /**
   * Gets the name of a class in the call stack of the current thread.
   * 
   * @param level The desired stack level, where level 0 will return the name of
   *              the current class (i.e., the caller of this method), and each
   *              successive increasing level will get the name of the parent
   *              calling class. Must be >= 0, must not specify a level greater
   *              than the bottom of the call stack (i.e. the main class)
   * 
   * @return The name of the class at the specified stack level.
   */
  public static String getClassName (int level)
  {
    Arguments.checkLowerInclusiveBound (level, 0, "level");

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    Arguments.checkUpperExclusiveBound (level, stackTrace.length - 2, "level");

    return stackTrace[level + 2].getClassName();
  }

  /**
   * Gets the name of a method in the call stack of the current thread.
   * 
   * @param level The desired stack level, where level 0 will return the name of
   *              the current method (i.e., the caller of this method), and each
   *              successive increasing level will get the name of the parent
   *              calling method. Must be >= 0, must not specify a level greater
   *              than the bottom of the call stack (i.e. the main method).
   * 
   * @return The name of the method at the specified stack level.
   */
  public static String getMethodName (int level)
  {
    int length = Thread.currentThread().getStackTrace().length;

    Arguments.checkLowerInclusiveBound (level, 0, "level");
    Arguments.checkUpperExclusiveBound (level, length - 2, "level");

    return Thread.currentThread().getStackTrace()[level + 2].getMethodName();
  }

  /**
   * Throws an exception informing the user that instantiation is not allowed
   * for the calling class.
   * 
   * @throws UnsupportedOperationException When called. 
   */
  public static void instantiationNotAllowed()
  {
    throw new UnsupportedOperationException ("Instantiation not allowed " +
            "for class " + ClassUtils.getClassName (1) + ".");
  }

  /**
   * Throws an exception informing the user that the calling method is
   * unsupported.
   * 
   * @throws UnsupportedOperationException When called. 
   */
  public static void unsupportedMethod()
  {
    throw new UnsupportedOperationException ("Unsupported method: " +
            ClassUtils.getClassName (1) + "." + ClassUtils.getMethodName (1));
  }

  private ClassUtils()
  {
    ClassUtils.instantiationNotAllowed();
  }
}
