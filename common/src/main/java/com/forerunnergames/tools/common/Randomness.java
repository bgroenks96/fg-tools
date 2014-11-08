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
   * @param inclusiveLowerBound The inclusive lower bound, must be less than or equal to inclusiveUpperBound.
   * 
   * @param inclusiveUpperBound The inclusive upper bound, must be greater than or equal to inclusiveLowerBound.
   *
   * @return A random integer in the range [inclusiveLowerBound, inclusiveUpperBound].
   */
  public static int getRandomIntegerFrom (int inclusiveLowerBound, int inclusiveUpperBound)
  {
    Arguments.checkUpperInclusiveBound (inclusiveLowerBound, inclusiveUpperBound, "inclusiveLowerBound", "inclusiveUpperBound");

    long n = (long) inclusiveUpperBound - inclusiveLowerBound + 1;

    long randomNumber;

    assert n > 0;
    assert n < (long) Integer.MAX_VALUE;

    randomNumber = (long) inclusiveLowerBound + Randomness.prng.nextInt ((int) n);

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
