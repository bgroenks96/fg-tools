package com.forerunnergames.tools;

/**
 * This class exists as a workaround to some fundamental {@link java.lang.Enum} limitations.
 *
 * It replaces an abstract implementation of {@link com.forerunnergames.tools.IterableEnum},
 * since it is impossible to create an abstract {@code enum}, or more specifically, impossible
 * to provide an abstract base class that implements {@link com.forerunnergames.tools.IterableEnum}.
 *
 * It is also worth noting that it is also impossible for this class to be a default concrete implementation of
 * {@link com.forerunnergames.tools.IterableEnum} because it would require this class to be an enum, and therefore,
 * it could not have generic type parameters, so it would essentially be useless.
 *
 * The workaround is to provide static utility methods that will act upon an {@link java.lang.Enum},
 * which a concrete implementation of {@link com.forerunnergames.tools.IterableEnum} can delegate to.
 *
 * In this way, various implementations of {@link com.forerunnergames.tools.IterableEnum} can re-use
 * common functionality.
 */
public final class IterableEnumHelper
{
  /**
   * Gets the total number of values in this {@link java.lang.Enum}.
   */
  public static <E extends Enum <E> & IterableEnum <E>> int count (final E[] values)
  {
    Arguments.checkIsNotNull (values, "values");
    Arguments.checkHasNoNullElements (values, "values");

    return values.length;
  }

  /**
   * Gets the {@link java.lang.Enum} value at the nth position in declarative order, the first value being at position 1.
   *
   * @param n The {@link java.lang.Enum} position, must be > 0 and <= values.length.
   * @param values All of the {@link java.lang.Enum} values, must not be null, must not contain any null values.
   */
  public static <E extends Enum <E> & IterableEnum <E>> E getNthValue (final int n, final E[] values)
  {
    Arguments.checkLowerExclusiveBound (n, 0, "n");
    Arguments.checkUpperInclusiveBound (n, count (values), "n");
    Arguments.checkIsNotNull (values, "values");
    Arguments.checkHasNoNullElements (values, "values");

    return values [n - 1];
  }

  /**
   * Gets whether not the specified {@link java.lang.Enum} value has another value succeeding it in declarative order.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   * @param values All of the {@link java.lang.Enum} values, must not be null, must not contain any null values.
   */
  public static <E extends Enum <E> & IterableEnum <E>> boolean hasNext (final E e, final E[] values)
  {
    Arguments.checkIsNotNull (e, "e");
    Arguments.checkIsNotNull (values, "values");
    Arguments.checkHasNoNullElements (values, "values");

    return e.ordinal() < values.length - 1;
  }

  /**
   * Gets the {@link java.lang.Enum} value succeeding the specified {@link java.lang.Enum} value in declarative order.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   * @param values All of the {@link java.lang.Enum} values, must not be null, must not contain any null values.
   *
   * @throws java.lang.IllegalStateException if there exists no succeeding value.
   */
  public static <E extends Enum <E> & IterableEnum <E>> E next (final E e, final E[] values)
  {
    Arguments.checkIsNotNull (e, "e");
    Arguments.checkIsNotNull (values, "values");
    Arguments.checkHasNoNullElements (values, "values");

    if (! hasNext (e, values))
    {
      throw new IllegalStateException ("Cannot get next " + e.getClass().getSimpleName() + " value because " + e.name() + " is the last value.");
    }

    return values [e.ordinal() + 1];
  }

  /**
   * Gets whether not the specified {@link java.lang.Enum} value has another value preceding it in declarative order.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   */
  public static <E extends Enum <E> & IterableEnum <E>> boolean hasPrevious (final E e)
  {
    Arguments.checkIsNotNull (e, "e");

    return e.ordinal() > 0;
  }

  /**
   * Gets the {@link java.lang.Enum} value preceding the specified {@link java.lang.Enum} value in declarative order.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   * @param values All of the {@link java.lang.Enum} values, must not be null, must not contain any null values.
   *
   * @throws java.lang.IllegalStateException if there exists no preceding value.
   */
  public static <E extends Enum <E> & IterableEnum <E>> E previous (final E e, final E[] values)
  {
    Arguments.checkIsNotNull (e, "e");
    Arguments.checkIsNotNull (values, "values");
    Arguments.checkHasNoNullElements (values, "values");

    if (! hasPrevious (e))
    {
      throw new IllegalStateException ("Cannot get previous " + e.getClass().getSimpleName() + " value because " + e.name() + " is the first value.");
    }

    return values [e.ordinal() - 1];
  }

  /**
   * Compares the two specified {@link java.lang.Enum} values for equality.
   *
   * @param e1 The first specified {@link java.lang.Enum} value, must not be null.
   * @param e2 The second specified {@link java.lang.Enum} value, must not be null.
   */
  public static <E extends Enum <E> & IterableEnum <E>> boolean is (final E e1, final E e2)
  {
    Arguments.checkIsNotNull (e1, "e1");
    Arguments.checkIsNotNull (e2, "e2");

    return e1.equals (e2);
  }

  /**
   * Gets the position of the specified {@link java.lang.Enum} value, the position being defined as
   * an integer > 0 and <= {@link #count(Enum[])} with respect to declarative order,
   * the first position being 1, the second position being 2, and so on.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   */
  public static <E extends Enum <E> & IterableEnum <E>> int getPosition (final E e)
  {
    Arguments.checkIsNotNull (e, "e");

    return e.ordinal() + 1;
  }

  /**
   * Converts the specified {@link java.lang.Enum} value to a {@link java.lang.String}
   * representation of a mixed ordinal position > 0 and <= {@link #count(Enum[])},
   * with respect to declarative order, the value at position 1 being represented by
   * the {@link java.lang.String} "1st", the value at position 2 being represented by
   * the {@link java.lang.String} "2nd", and so on.
   *
   * @param e The specified {@link java.lang.Enum} value, must not be null.
   *
   * @see {@link #getPosition(Enum)}
   */
  public static <E extends Enum <E> & IterableEnum <E>> String toMixedOrdinalPosition (final E e)
  {
    Arguments.checkIsNotNull (e, "e");

    return Strings.toMixedOrdinal (getPosition (e));
  }

  private IterableEnumHelper()
  {
    Classes.instantiationNotAllowed();
  }
}
