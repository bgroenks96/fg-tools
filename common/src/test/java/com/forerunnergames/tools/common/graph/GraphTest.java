/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forerunnergames.tools.common.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.forerunnergames.tools.common.graph.DefaultGraph.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;

public class GraphTest
{
  private static final int MAX_OBJECT_COUNT = 10;
  private ImmutableList <Object> testObjects;
  private Graph<Object> mapGraph;

  @Before
  public void genTestObjects ()
  {
    final ImmutableList.Builder <Object> builder = ImmutableList.builder ();
    for (int i = 0; i < MAX_OBJECT_COUNT; i++)
    {
      builder.add (new Object ());
    }
    testObjects = builder.build ();
  }

  @Test
  public void testGetAdjacentNodesOnOneNodeGraphIsEmpty ()
  {
    final Object obj = new Object ();
    mapGraph = DefaultGraph.builder ().addNode (obj).build ();
    assertTrue (mapGraph.getAdjacentNodes (obj).isEmpty ());
  }

  @Test
  public void testGetAdjacentNodesOnTwoNodeGraph ()
  {
    final Object obj0 = new Object ();
    final Object obj1 = new Object ();
    mapGraph = parseGraphFrom ("0,1;1,0", ImmutableList.of (obj0, obj1));
    final ImmutableSet <Object> adj = mapGraph.getAdjacentNodes (obj0);
    assertTrue (adj.contains (obj1));
    assertEquals (1, adj.size ());
  }

  @Test
  public void testGetAdjacentNodesOnTwoNodeGraphIsEmpty ()
  {
    final Object Object0 = new Object ();
    final Object Object1 = new Object ();
    mapGraph = parseGraphFrom ("", ImmutableList.of (Object0, Object1));
    assertFalse (mapGraph.getAdjacentNodes (Object0).contains (Object1));
    assertFalse (mapGraph.getAdjacentNodes (Object1).contains (Object0));
    assertEquals (2, mapGraph.size ());
  }

  @Test
  public void testAreAdjacentOnTwoNodeGraph ()
  {
    final Object Object0 = new Object ();
    final Object Object1 = new Object ();
    mapGraph = parseGraphFrom ("0,1;1,0", ImmutableList.of (Object0, Object1));
    assertTrue (mapGraph.areAdjacent (Object0, Object1));
  }

  @Test
  public void testAreNotAdjacentOnTwoNodeGraph ()
  {
    final Object Object0 = new Object ();
    final Object Object1 = new Object ();
    mapGraph = parseGraphFrom ("", ImmutableList.of (Object0, Object1));
    assertTrue (mapGraph.areNotAdjacent (Object0, Object1));
  }

  @Test
  public void testAdjacencyMultiNodeConnectedGraph ()
  {
    mapGraph = parseGraphFrom ("0,1,3;1,0,2,3;2,1,3", testObjects);
    assertTrue (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (1)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (3)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (1), testObjects.get (0)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (1), testObjects.get (2)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (1), testObjects.get (3)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (2), testObjects.get (1)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (2), testObjects.get (3)));
    assertFalse (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (2)));
    assertFalse (mapGraph.areAdjacent (testObjects.get (2), testObjects.get (0)));
  }

  @Test
  public void testAdjacencyMultiNodeDisconnectedGraph ()
  {
    mapGraph = parseGraphFrom ("0;1,2,3", testObjects);
    assertTrue (mapGraph.areAdjacent (testObjects.get (1), testObjects.get (2)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (1), testObjects.get (3)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (2), testObjects.get (1)));
    assertTrue (mapGraph.areAdjacent (testObjects.get (3), testObjects.get (1)));
    assertFalse (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (1)));
    assertFalse (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (2)));
    assertFalse (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (3)));
  }

  @Test
  public void testSelfAdjacency ()
  {
    mapGraph = parseGraphFrom ("0,1,2", testObjects);
    assertTrue (mapGraph.areAdjacent (testObjects.get (0), testObjects.get (0)));
  }

  @Test
  public void testDistanceBetweenTwoAdjacentNodes ()
  {
    mapGraph = parseGraphFrom ("0,1", testObjects);
    assertEquals (1, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (1)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v1 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2", testObjects);
    assertEquals (2, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (2)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v2 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2;2,1,3", testObjects);
    assertEquals (3, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (3)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v3 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2;2,1,3;3,2,4", testObjects);
    assertEquals (4, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (4)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v4 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2,3,4;2,1,4;4,1,2,5", testObjects);
    assertEquals (3, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (5)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v5 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2,3,4;2,1,4;3,1,5;4,1,2,5", testObjects);
    assertEquals (3, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (5)));
  }

  @Test
  public void testDistanceBetweenTwoNonAdjacentNodes_v6 ()
  {
    mapGraph = parseGraphFrom ("0,1;1,0,2,3,4,5;2,1,4;3,1,5;4,1,2,5", testObjects);
    assertEquals (2, mapGraph.distanceBetween (testObjects.get (0), testObjects.get (5)));
  }

  // adjListStr uses indices to indicate which elements in the list should be adjacent to each other
  private static Graph<Object> parseGraphFrom (final String adjListStr, final ImmutableList <Object> nodes)
  {
    assert adjListStr != null;
    assert nodes != null;

    final Builder <Object> mapGraphBuilder = DefaultGraph.builder ();
    for (final Object Object : nodes)
    {
      mapGraphBuilder.addNode (Object);
    }

    if (adjListStr.length () == 0) return mapGraphBuilder.build ();

    final String[] adjLists = adjListStr.split (";");
    for (final String list : adjLists)
    {
      final String[] elems = list.split (",");
      final Object c0 = nodes.get (Integer.parseInt (elems [0]));
      for (int i = 1; i < elems.length; i++)
      {
        final Object node = nodes.get (Integer.parseInt (elems [i]));
        mapGraphBuilder.setAdjacent (c0, node);
      }
    }

    return mapGraphBuilder.build ();
  }
}
