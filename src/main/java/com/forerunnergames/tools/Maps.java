package com.forerunnergames.tools;

import com.badlogic.gdx.utils.ArrayMap;

/**
 * Static utility class for methods involving maps.
 */
public final class Maps
{
  /**
   * Creates an {@link com.badlogic.gdx.utils.ArrayMap} with ordered values of the specified initial capacity.
   *
   * @param capacity The initial number of values to allocate space for; any values added beyond this will cause the
   *                 backing arrays to grow, must be non-negative.
   * @param <K> The type of the map keys.
   * @param <V> The type of the map values.
   */
  public static <K, V> ArrayMap <K, V> createOrderedMapOfCapacity (final int capacity)
  {
    Arguments.checkIsNotNegative (capacity, "capacity");

    return new ArrayMap <K, V> (true, capacity);
  }
}
