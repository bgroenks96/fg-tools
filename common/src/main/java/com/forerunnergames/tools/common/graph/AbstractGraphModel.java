package com.forerunnergames.tools.common.graph;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Preconditions;
import com.forerunnergames.tools.common.Strings;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public abstract class AbstractGraphModel <T> implements GraphModel <T>
{
  private final Map <T, Set <T>> adjList;
  private final int size;

  protected AbstractGraphModel (final Map <T, Set <T>> adjList, final int size)
  {
    Arguments.checkIsNotNull (adjList, "adjList");
    Arguments.checkHasNoNullKeysOrValues (adjList, "adjList");
    Arguments.checkIsNotNegative (size, "size");

    this.adjList = ImmutableMap.copyOf (adjList);
    this.size = size;
  }

  @Override
  public int size ()
  {
    return size;
  }

  @Override
  public boolean isEmpty ()
  {
    return size == 0;
  }

  @Override
  public ImmutableSet <T> getAdjacentNodes (final T node)
  {
    Arguments.checkIsNotNull (node, "node");
    Preconditions.checkIsTrue (adjList.containsKey (node), Strings.format ("Node not in graph: {}", node));

    final Set <T> adj = adjList.get (node);
    return ImmutableSet.copyOf (adj);
  }

  @Override
  public boolean areAdjacent (final T node0, final T node1)
  {
    Arguments.checkIsNotNull (node0, "node0");
    Arguments.checkIsNotNull (node1, "node1");
    Preconditions.checkIsTrue (adjList.containsKey (node0), Strings.format ("Node not in graph: {}", node0));

    if (node0.equals (node1)) return true;

    final Set <T> adj = adjList.get (node0);
    return adj.contains (node1);
  }

  @Override
  public boolean areNotAdjacent (final T node0, final T node1)
  {
    Arguments.checkIsNotNull (node0, "node0");
    Arguments.checkIsNotNull (node1, "node1");
    Preconditions.checkIsTrue (adjList.containsKey (node0), Strings.format ("Node not in graph: {}", node0));

    return !areAdjacent (node0, node1);
  }

  /**
   * Computes the distance between two nodes using a conventional breadth first search algorithm.
   *
   * @return the distance between the two nodes, or -1 if there is no path from 'node0' to 'node1'
   */
  @Override
  public int distanceBetween (final T node0, final T node1)
  {
    Arguments.checkIsNotNull (node0, "node0");
    Arguments.checkIsNotNull (node1, "node1");
    Preconditions.checkIsTrue (adjList.containsKey (node0), Strings.format ("Node not in graph: {}", node0));
    Preconditions.checkIsTrue (adjList.containsKey (node1), Strings.format ("Node not in graph: {}", node1));

    if (node0.equals (node1)) return 0;
    if (areAdjacent (node0, node1)) return 1;

    final Set <T> adj = adjList.get (node0);
    final Queue <T> visitQueue = new ArrayDeque <> ();
    final Map <T, Integer> distMap = new HashMap <> ();
    for (final T node : adj)
    {
      visitQueue.offer (node);
      distMap.put (node, 1);
    }
    while (!visitQueue.isEmpty ())
    {
      final T node = visitQueue.poll ();
      final int dist = distMap.get (node);
      if (node.equals (node1)) return dist;
      for (final T nAdj : getAdjacentNodes (node))
      {
        if (distMap.containsKey (nAdj)) continue;
        visitQueue.offer (nAdj);
        distMap.put (nAdj, dist + 1);
      }
    }

    return -1;
  }

  @Override
  public Iterator <T> iterator ()
  {
    return new GraphIterator ();
  }

  private class GraphIterator implements Iterator <T>
  {
    private final Iterator <T> mapIterator;

    GraphIterator ()
    {
      mapIterator = adjList.keySet ().iterator ();
    }

    @Override
    public boolean hasNext ()
    {
      return mapIterator.hasNext ();
    }

    @Override
    public T next ()
    {
      return mapIterator.next ();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove ()
    {
      throw new UnsupportedOperationException (
              Strings.format ("{} does not support Iterator.remove()", getClass ().getSimpleName ()));
    }
  }
}
