package com.forerunnergames.tools.common.enums;

public interface IterableEnum <E extends Enum <E>>
{
  boolean hasNext ();

  E next ();

  boolean hasPrevious ();

  E previous ();

  E first ();

  E last ();

  boolean is (E e);

  boolean isNot (E e);

  int getPosition ();

  String toMixedOrdinalPosition ();
}
