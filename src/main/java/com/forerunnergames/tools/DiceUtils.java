// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Aaron Mahan
 */
public class DiceUtils 
{
  // Begin public interface

  /**
   * Get a preset (fake) dice roll. Useful for testing purposes.
   * It will get a preset (fake) dice roll until the presets are all used up,
   * and then it will return 6 on each subsequent call.
   * 
   * @see DiceUtils#setTestRolls(int... rollAmounts) 
   * 
   * @return The roll amount.
   */
  public static int getTestRoll()
  {
    int rollAmount;

    if (DiceUtils.currentTestRoll < DiceUtils.testRolls.size())
    {
      rollAmount = DiceUtils.testRolls.get (DiceUtils.currentTestRoll);
    }
    else
    {
      rollAmount = 6;
    }

    ++DiceUtils.currentTestRoll;

    return rollAmount;
  }

  /**
   * Create preset (fake) dice rolls. Useful for testing purposes.
   * The rolls are used on a first-in first-out basis.
   * 
   * @see DiceUtils#getTestRoll() 
   * 
   * @param rollAmounts The list of preset (fake) roll amounts.
   */
  public static void setTestRolls (int... rollAmounts)
  {
    DiceUtils.testRolls = new ArrayList <Integer>();

    for (int i = 0; i < rollAmounts.length; ++i)
    {
      testRolls.add (rollAmounts [i]);
    }
  }

  /**
   * Roll diceCount 6-sided dice and get the resulting roll amounts.
   * 
   * @param diceCount The number of dice to roll.
   * 
   * @return A collection of the roll amounts as integers.
   */
  public static Collection <Integer> rollDice (int diceCount)
  {
    Arguments.checkLowerExclusiveBound (diceCount, 0, "diceCount");

    Collection <Integer> rolls = new ArrayList <Integer> (diceCount);

    for (int i = 0; i < diceCount; ++i)
    {
      int rollAmount = 
              RandomUtils.getRandomIntegerBetween (1, DiceUtils.FACES_PER_DIE);

      rolls.add (rollAmount);
    }

    return rolls;
  }

  public static int rollSum (int diceCount)
  {
    Arguments.checkLowerExclusiveBound (diceCount, 0, "diceCount");

    int sum = 0;

    for (Integer rollAmount : rollDice (diceCount))
    {
      sum += rollAmount;
    }

    return sum;
  }

  // End public interface

  // --------------------

  // Begin private interface

  private static int currentTestRoll = 0;

  private static List <Integer> testRolls;

  private static final int FACES_PER_DIE = 6;

  private DiceUtils()
  {
    ClassUtils.instantiationNotAllowed();
  }

  // End private interface
}
