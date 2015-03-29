package com.forerunnergames.tools.common.enums;

public interface IterableEnum <E extends Enum <E>>
{
  public boolean hasNext ();

  public E next ();

  public boolean hasPrevious ();

  public E previous ();

  public boolean is (E e);

  public boolean isNot (E e);

  public int getPosition ();

  public String toMixedOrdinalPosition ();
}
