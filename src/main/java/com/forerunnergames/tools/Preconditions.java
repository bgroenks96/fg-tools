package com.forerunnergames.tools;

public final class Preconditions
{
  /**
   * Checks if the specified boolean condition evaluates to true.
   *
   * @param condition The boolean condition to check.
   * @param errorMessage The error message to include in the exception.
   *
   * @throws IllegalArgumentException If condition evaluates to false;
   */
  public static void checkIsTrue (final boolean condition, final String errorMessage)
  {
    if (! condition) throw new IllegalStateException (errorMessage);
  }
  /**
   * Checks if the specified boolean condition evaluates to false.
   *
   * @param condition The boolean condition to check.
   * @param errorMessage The error message to include in the exception.
   *
   * @throws IllegalArgumentException If condition evaluates to true;
   */
  public static void checkIsFalse (final boolean condition, final String errorMessage)
  {
    if (condition) throw new IllegalStateException (errorMessage);
  }

  private Preconditions()
  {
    Classes.instantiationNotAllowed();
  }
}
