package com.forerunnergames.tools.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DiceUtils
{
  private static final int FACES_PER_DIE = 6;
  private static int currentTestRoll = 0;
  private static List <Integer> testRolls;

  /**
   * Get a preset (fake) dice roll. Useful for testing purposes. It will get a preset (fake) dice roll until the presets
   * are all used up, and then it will recycle the test rolls from the beginning, indefinitely.
   *
   * Note {@link #setTestRolls(int...)} must be called prior, or an {@link IllegalStateException} will be thrown.
   *
   * @return The roll amount.
   *
   * @see #setTestRolls(int...)
   */
  public static int getTestRoll ()
  {
    Preconditions.checkIsTrue (testRolls != null, "DiceUtils#setTestRolls must be called first.");

    if (currentTestRoll >= testRolls.size ()) currentTestRoll = 0;

    return testRolls.get (currentTestRoll++);
  }

  /**
   * Create preset (fake) dice rolls. Useful for testing purposes. The rolls are used on a first-in first-out basis.
   *
   * @param rollAmounts
   *          The list of preset (fake) roll amounts, must not be null, must not be empty.
   *
   * @see #getTestRoll()
   */
  public static void setTestRolls (final int... rollAmounts)
  {
    Arguments.checkIsNotNullOrEmpty (rollAmounts, "rollAmounts");

    testRolls = new ArrayList <> (rollAmounts.length);

    for (final int rollAmount : rollAmounts)
    {
      testRolls.add (rollAmount);
    }
  }

  /**
   * Roll dieCount 6-sided dice and get the resulting roll amounts.
   *
   * @param dieCount
   *          The number of dice to roll, must be > 0.
   *
   * @return A collection of the roll amounts as integers.
   */
  public static Collection <Integer> rollDice (final int dieCount)
  {
    Arguments.checkLowerExclusiveBound (dieCount, 0, "dieCount");

    final Collection <Integer> rolls = new ArrayList <> (dieCount);

    int rollAmount;

    for (int i = 0; i < dieCount; ++i)
    {
      rollAmount = Randomness.getRandomIntegerFrom (1, FACES_PER_DIE);
      rolls.add (rollAmount);
    }

    return rolls;
  }

  /**
   * Roll dieCount 6-sided dice and get the resulting roll sum.
   *
   * @param dieCount
   *          The number of dice to roll, must be > 0.
   *
   * @return The sum of the values of the individual dice.
   */
  public static int rollSum (final int dieCount)
  {
    Arguments.checkLowerExclusiveBound (dieCount, 0, "dieCount");

    int sum = 0;

    for (final Integer rollAmount : rollDice (dieCount))
    {
      sum += rollAmount;
    }

    return sum;
  }

  private DiceUtils ()
  {
    Classes.instantiationNotAllowed ();
  }
}
