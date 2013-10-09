// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

//import ch.fourmilab.randomX.randomHotBits;

/**
 * Static Utility Class for Random Number-related Functionality
 *
 * @author Aaron Mahan <aaron@forerunnergames.com>
 */
public class RandomUtils
{
  // Initialize the cryptographically secure pseudo random number generator.
  static
  {
    // Begin production code
    /*
    randomHotBits trng = new randomHotBits();

    final int NUMBER_OF_SEED_BYTES = 8;

    byte[] trueRandomEntropySeed = new byte [NUMBER_OF_SEED_BYTES];

    for (int i = 0; i < trueRandomEntropySeed.length; ++i)
    {
      trueRandomEntropySeed [i] = trng.nextByte();
    }

    try
    {
      RandomUtils.prng = SecureRandom.getInstance ("SHA1PRNG", "SUN");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }
    catch (NoSuchProviderException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }

    RandomUtils.prng.setSeed (trueRandomEntropySeed);
    */
    // End production code

    // Begin testing code
    // @TESTING
    try
    {
      RandomUtils.prng = SecureRandom.getInstance ("SHA1PRNG", "SUN");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }
    catch (NoSuchProviderException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }

    RandomUtils.prng.nextBytes (new byte [8]);
    // End testing code
  }

  /**
   * Gets a random integer in the range [inclusiveLowerBound,
   * inclusiveUpperBound] using a cryptographically secure pseudo random number
   * generator. 
   *
   * @param inclusiveLowerBound The inclusive lower bound, must be strictly less
   *                            than inclusiveUpperBound.
   * 
   * @param inclusiveUpperBound The inclusive upper bound, must be strictly
   *                            greater than inclusiveLowerBound.
   *
   * @return A random integer in the range [inclusiveLowerBound,
   *                                        inclusiveUpperBound].
   * 
   * @throws IllegalArgumentException If inclusiveLowerBound is not strictly
   *                                  less than inclusiveUpperBound.
   */
  public static int getRandomIntegerBetween (int inclusiveLowerBound,
                                             int inclusiveUpperBound)
  {
    Arguments.checkUpperExclusiveBound (inclusiveLowerBound,
            inclusiveUpperBound, "inclusiveLowerBound", "inclusiveUpperBound");

    long n = (long) inclusiveUpperBound - inclusiveLowerBound + 1;

    long randomNumber;

    assert n > 0;
    assert n < (long) Integer.MAX_VALUE;

    randomNumber =
            (long) inclusiveLowerBound + RandomUtils.prng.nextInt ((int) n);

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
  public static <T> List <T> shuffle (List <T> list)
  {
    Arguments.checkIsNotNull (list, "List");

    assert (RandomUtils.prng != null);

    Collections.shuffle (list, RandomUtils.prng);

    return list;
  }

  // Cryptographically secure pseudo random number generator.
  private static SecureRandom prng;
}
