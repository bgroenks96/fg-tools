package com.forerunnergames.tools.common;

import com.forerunnergames.tools.randomx.RandomHotBits;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for random-number-related functionality
 */
public final class Randomness
{
  private static final Logger log = LoggerFactory.getLogger (Randomness.class);
  private static final int SEED_BYTES = 8; // The number of bytes used to seed the pseudo random number generator.
  private static final int RESEED_THRESHOLD = 10000; // The number of random numbers that can be generated before reseeding.
  private static final RandomHotBits TRNG = new RandomHotBits(); // True random number generator, used for seeding only, and only in RELEASE mode.
  private static long reseedCounter = 0; // Keeps track of how many random numbers have been generated for reseeding purposes.
  private static SecureRandom prng; // Cryptographically secure pseudo random number generator.
  private static Mode currentMode;

  /**
   * The default mode is DEBUG.
   */
  public enum Mode
  {
    /**
     * Uses system entropy to seed a cryptographically-secure random number generator.
     */
    DEBUG,

    /**
     * Uses HotBits entropy (https://www.fourmilab.ch/hotbits) to seed a cryptographically-secure random number
     * generator. WARNING: HotBits will attempt to connect to and download the entropy from a remote server,
     * so internet access is required, and the remote server must be online & functioning. If obtaining HotBits
     * entropy fails, it will automatically fall back to using system entropy, with a log entry explaining what
     * went wrong.
     */
    RELEASE
  }

  // Initialize the random number generator.
  static
  {
    setModeTo (Mode.DEBUG);
  }

  /**
   * Checks whether the specified mode is set.
   *
   * @param mode, The mode to check, must not be null
   */
  public static boolean isMode (final Mode mode)
  {
    Arguments.checkIsNotNull (mode, "mode");

    return mode.equals (currentMode);
  }

  /**
   * Sets the specified mode. Idempotent - nothing will happen if the specified mode has already been set.
   *
   * @param mode, The mode to set, must not be null.
   *
   * @see #isMode
   */
  public static void setModeTo (final Mode mode)
  {
    Arguments.checkIsNotNull (mode, "mode");

    if (isMode (mode)) return;

    currentMode = mode;

    createPrng();

    switch (mode)
    {
      case DEBUG:
      {
        reseedPrngWithSystemEntropy();

        break;
      }
      case RELEASE:
      {
        reseedPrngWithHotBitsEntropy();

        break;
      }
    }
  }

  private static void createPrng()
  {
    try
    {
      prng = SecureRandom.getInstance ("SHA1PRNG", "SUN");
    }
    catch (final NoSuchAlgorithmException | NoSuchProviderException e)
    {
      throw new RuntimeException ("Cannot create random number generator", e);
    }
  }

  private static void reseedPrngWithSystemEntropy()
  {
    prng.setSeed (generateSystemEntropySeed());
  }

  private static byte[] generateSystemEntropySeed()
  {
    return prng.generateSeed (SEED_BYTES);
  }

  private static void reseedPrngWithHotBitsEntropy()
  {
    prng.setSeed (generateHotBitsEntropySeed (SEED_BYTES));
  }

  private static byte[] generateHotBitsEntropySeed (final int numBytes)
  {
    try
    {
      final byte[] trueRandomEntropySeed = new byte[numBytes];

      for (int i = 0; i < trueRandomEntropySeed.length; ++i)
      {
        trueRandomEntropySeed[i] = TRNG.nextByte();
      }

      return trueRandomEntropySeed;
    }
    catch (final RuntimeException e)
    {
      log.warn ("Could not obtain HotBits entropy! Falling back to a system entropy source", e);

      return generateSystemEntropySeed();
    }
  }

  /**
   * Gets a random integer in the range [inclusiveLowerBound, inclusiveUpperBound] using a cryptographically secure
   * pseudo random number generator.
   *
   * @param inclusiveLowerBound The inclusive lower bound, must be >= 0 and <= inclusiveUpperBound and < Integer.MAX_VALUE.
   * @param inclusiveUpperBound The inclusive upper bound, must be >= 0 and >= inclusiveLowerBound and < Integer.MAX_VALUE.
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

    checkPrngUsage();

    final long randomNumber = (long) inclusiveLowerBound + prng.nextInt ((int) n);

    updatePrngUsage (1);

    assert randomNumber >= inclusiveLowerBound;
    assert randomNumber <= inclusiveUpperBound;

    return (int) randomNumber;
  }

  private static void checkPrngUsage()
  {
    if (shouldReseedPrng()) reseedPrng();
  }

  private static boolean shouldReseedPrng()
  {
    return reseedCounter > RESEED_THRESHOLD;
  }

  private static void reseedPrng()
  {
    reseedCounter = 0;

    switch (currentMode)
    {
      case DEBUG:
      {
        reseedPrngWithSystemEntropy();

        break;
      }
      case RELEASE:
      {
        reseedPrngWithHotBitsEntropy();

        break;
      }
    }
  }

  private static void updatePrngUsage (final int timesUsed)
  {
    reseedCounter += timesUsed;
  }

  /**
   * Shuffles a list using a cryptographically secure pseudo random number generator.
   * 
   * @param <T>  The element type of the specified list.
   * @param list The list to be shuffled, must not be null.
   * 
   * @return A copy of the shuffled list.
   */
  public static <T> List <T> shuffle (final List <T> list)
  {
    Arguments.checkIsNotNull (list, "List");

    checkPrngUsage();

    Collections.shuffle (list, prng);

    updatePrngUsage (list.size());

    return list;
  }
}
