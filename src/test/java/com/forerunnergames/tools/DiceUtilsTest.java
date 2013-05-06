// Copyright © 2013 Forerunner Games
package com.forerunnergames.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 *
 * @author Aaron Mahan
 */
public class DiceUtilsTest
{
  @BeforeClass
  public static void setUpClass()
  {
  }

  @Before
  public void setUp()
  {
  }

  @Test
  public void testRollSum()
  {
    int rollTotal = 1000000;

    List <Integer> rollSums = new ArrayList <Integer> (rollTotal);

    for (int i = 0; i < rollTotal; ++i)
    {
      int rollSum = DiceUtils.rollSum (2);

      rollSums.add (rollSum);

      assertTrue ("rollSum is out of range: " +  rollSum, rollSum >= 2 &&
                                                          rollSum <= 12);
    }

    System.out.println ("RollSum frequencies:\n\n");

    for (int i = 2; i <= 12; ++i)
    {
      System.out.println (i + ": " + Collections.frequency (rollSums, i));

      assertTrue ("Warning, rollSum of " + i + " is never rolled out of " +
                  rollTotal + " rolls!",
                  Collections.frequency (rollSums, i) > 0);
    }
  }

  @After
  public void tearDown()
  {
  }

  @AfterClass
  public static void tearDownClass()
  {
  }
}