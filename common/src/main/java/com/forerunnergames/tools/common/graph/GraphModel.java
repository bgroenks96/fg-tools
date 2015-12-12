package com.forerunnergames.tools.common.graph;

import com.google.common.collect.ImmutableSet;

public interface GraphModel <T> extends Iterable <T>
{
  int size ();

  boolean isEmpty ();

  ImmutableSet <T> getAdjacentNodes (final T node);

  boolean areAdjacent (final T node0, final T node1);

  boolean areNotAdjacent (final T node0, final T node1);

  int distanceBetween (final T node0, final T node1);
}
