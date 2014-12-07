package com.forerunnergames.tools.common;

public class Id implements Comparable <Id>
{
  private final int value;

  public Id (final int value)
  {
    this.value = value;
  }

  public int value()
  {
    return value;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    final Id that = (Id) o;

    return value == that.value;
  }

  @Override
  public int hashCode()
  {
    return value;
  }

  @Override
  public int compareTo (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return this.value - id.value();
  }

  public boolean is (final Id id)
  {
    return equals (id);
  }

  public boolean isNot (final Id id)
  {
    return ! is (id);
  }

  @Override
  public String toString()
  {
    return String.valueOf (value);
  }
}
