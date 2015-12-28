package com.forerunnergames.tools.common.id;

import com.forerunnergames.tools.common.Arguments;

import java.util.UUID;

public final class Id implements Comparable <Id>
{
  private final UUID value;

  public Id (final UUID value)
  {
    Arguments.checkIsNotNull (value, "value");

    this.value = value;
  }

  public UUID value ()
  {
    return value;
  }

  public boolean is (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return equals (id);
  }

  public boolean isNot (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return !is (id);
  }

  public boolean hasValue (final UUID value)
  {
    Arguments.checkIsNotNull (value, "value");

    return this.value.equals (value);
  }

  @Override
  public int compareTo (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return this.value.compareTo (id.value);
  }

  @Override
  public int hashCode ()
  {
    return value.hashCode ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;

    if (o == null || getClass () != o.getClass ()) return false;

    final Id that = (Id) o;

    return value.equals (that.value);
  }

  @Override
  public String toString ()
  {
    return String.valueOf (value);
  }
}
