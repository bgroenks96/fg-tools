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

    return Thread.currentThread ().getStackTrace ()[level + 2].getMethodName ();
  }

  /**
   * Throws an exception informing the caller that the calling method is unsupported.
   *
   * @throws UnsupportedOperationException
   *           When called.
   */
  public static void unsupportedMethod () throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException ("Unsupported method: " + Classes.getClassName (1) + "."
                    + Methods.getMethodName (1));
  }

  private Methods ()
  {
    Classes.instantiationNotAllowed ();
  }
}
