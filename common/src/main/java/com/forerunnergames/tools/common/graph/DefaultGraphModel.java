package com.forerunnergames.tools.common.graph;

import com.forerunnergames.tools.common.Arguments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DefaultGraphModel <T> extends AbstractGraphModel <T>
{
  protected DefaultGraphModel (final Map <T, Set <T>> adjList, final int size)
  {
    super (adjList, size);
  }

  public static <T> Builder <T> builder ()
  {
    return new Builder <> ();
  }

  public static <T> GraphModel <T> from (final Map <T, Iterable <T>> adjListData)
  {
    Arguments.checkIsNotNull (adjListData, "adjListData");
    Arguments.checkHasNoNullKeysOrValues (adjListData, "adjListData");

    final Builder <T> mapGraphBuilder = builder ();
    for (final T country : adjListData.keySet ())
    {
      for (final T adjCountry : adjListData.get (country))
      {
        mapGraphBuilder.setAdjacent (country, adjCountry);
      }
    }
    return mapGraphBuilder.build ();
  }

  public static class Builder <T>
  {
    private final Map <T, Set <T>> adjList = new HashMap <> ();
    private int size;

    /**
     * Adds 'node' to the graph builder, if it doesn't already exist.
     */
    public Builder <T> addNode (final T node)
    {
      Arguments.checkIsNotNull (node, "node");

      if (!adjList.containsKey (node))
      {
        adjList.put (node, new HashSet <T> ());
        size++;
      }

      return this;
    }

    /**
     * Sets 'node1' as adjacent to 'node0' if it is not already set.
     */
    public Builder <T> setAdjacent (final T node0, final T node1)
    {
      Arguments.checkIsNotNull (node0, "node0");
      Arguments.checkIsNotNull (node1, "node1");

      // add nodes if they don't already exist
      addNode (node0);
      addNode (node1);

      final Set <T> adj0 = adjList.get (node0);
      adj0.add (node1);
      final Set <T> adj1 = adjList.get (node1);
      adj1.add (node0);

      return this;
    }

    public DefaultGraphModel <T> build ()
    {
      return new DefaultGraphModel <> (adjList, size);
    }
  }
}
