package com.forerunnergames.tools.common.id;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/*
 * Note: each test should call IdGen.reset or IdGen.setInternalTickValue
 * at both the beginning of the test method to ensure that the IdGen is
 * used independently between tests.
 */
public class IdGeneratorTest
{
  @Test
  public void testIdGenValuesUnique ()
  {
    Set <Id> idSet = new HashSet <> ();
    IdGenerator.reset (); // ensure that IdGen is in initial state
    final int N = 1000000;
    for (int i = 0; i < N; i++)
    {
      idSet.add (IdGenerator.generateUniqueId ());
    }
    // since Set disallows duplicate objects, testing that the correct number
    // were added successfully should verify uniqueness
    assertTrue (idSet.size () == N);
  }

  @Test (expected = IllegalStateException.class)
  public void testIdGenOverflow ()
  {
    IdGenerator.setInternalTickValue (Integer.MAX_VALUE + 1);
    IdGenerator.generateUniqueId ();
  }
}
