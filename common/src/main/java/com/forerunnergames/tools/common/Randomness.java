// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.common;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

//import com.forerunnergames.tools.randomx.RandomHotBits;

/**
 * Static Utility Class for Random Number-related Functionality
 *
 * @author Aaron Mahan <aaron@forerunnergames.com>
 */
public final class Randomness
{
  // Initialize the cryptographically secure pseudo random number generator.
  static
  {
    // Begin production code
    /*
    final RandomHotBits trng = new RandomHotBits();

    final int NUMBER_OF_SEED_BYTES = 8;

    final byte[] trueRandomEntropySeed = new byte [NUMBER_OF_SEED_BYTES];

    for (int i = 0; i < trueRandomEntropySeed.length; ++i)
    {
      trueRandomEntropySeed [i] = trng.nextByte();
    }

    try
    {
      Randomness.prng = SecureRandom.getInstance ("SHA1PRNG", "SUN");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }
    catch (NoSuchProviderException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }

    Randomness.prng.setSeed (trueRandomEntropySeed);
    */
    // End production code

    // Begin testing code
    // @TESTING
    try
    {
      Randomness.prng = SecureRandom.getInstance ("SHA1PRNG", "SUN");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }
    catch (NoSuchProviderException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }

    Randomness.prng.nextBytes (new byte [8]);
    // End testing code
  }

  /**
   * Gets a random integer in the range [inclusiveLowerBound, inclusiveUpperBound] using a cryptographically secure
   * pseudo random number generator.
   *
   * @param inclusiveLowerBound The inclusive lower bound, must be >= 0 and <= inclusiveUpperBound and < Integer.MAX_VALUE.
   * 
   * @param inclusiveUpperBound The inclusive upper bound, must be >= 0 and >= inclusiveLowerBound and < Integer.MAX_VALUE.
   *
   * @return A random integer in the range [inclusiveLowerBound, inclusiveUpperBound].
   */
  public static int getRandomIntegerFrom (int inclusiveLowerBound, int inclusiveUpperBound)
  {
    Arguments.checkIsNotNegative (inclusiveLowerBound, "inclusiveLowerBound");
    Arguments.checkIsNotNegative (inclusiveUpperBound, "inclusiveUpperBound");
    Arguments.checkUpperExclusiveBound (inclusiveLowerBound, Integer.MAX_VALUE, "inclusiveLowerBound", "Integer.MAX_VALUE");
    Arguments.checkUpperExclusiveBound (inclusiveUpperBound, Integer.MAX_VALUE, "inclusiveUpperBound", "Integer.MAX_VALUE");
    Arguments.checkUpperInclusiveBound (inclusiveLowerBound, inclusiveUpperBound, "inclusiveLowerBound", "inclusiveUpperBound");

    final long n = (long) inclusiveUpperBound - inclusiveLowerBound + 1;

    assert n > 0;
    assert n <= (long) Integer.MAX_VALUE;

    final long randomNumber = (long) inclusiveLowerBound + prng.nextInt ((int) n);

    assert randomNumber >= inclusiveLowerBound;
    assert randomNumber <= inclusiveUpperBound;

    return (int) randomNumber;
  }

  /**
   * Shuffles a list using a cryptographically secure pseudo random number
   * generator.
   * 
   * @param <T>  The element type of the specified list.
   * @param list The list to be shuffled, must not be null.
   * 
   * @return A copy of the shuffled list.
   * 
   * @throws IllegalArgumentException If list is null.
   */
  public static <T> List <T> shuffle (final List <T> list)
  {
    Arguments.checkIsNotNull (list, "List");

    assert (Randomness.prng != null);

    Collections.shuffle (list, Randomness.prng);

    return list;
  }

  // Cryptographically secure pseudo random number generator.
  private static SecureRandom prng;
}
