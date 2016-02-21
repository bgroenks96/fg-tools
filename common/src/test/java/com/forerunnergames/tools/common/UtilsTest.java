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

package com.forerunnergames.tools.common;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UtilsTest
{
  @Test
  public void testOptionalCast ()
  {
    final Object object = new ArrayList ();

    final Optional <ArrayList> expected = Optional.of ((ArrayList) object);
    final Optional <ArrayList> actual = Utils.optionalCast (object, ArrayList.class);

    assertEquals (expected, actual);
  }

  @Test
  public void testOptionalCastPrimitives ()
  {
    final int n = 1;

    final Optional <Integer> expected = Optional.of (n);
    final Optional <Integer> actual = Utils.optionalCast (n, Integer.class);

    assertEquals (expected, actual);
  }

  @Test
  public void testOptionalCastGenericsTypeErasure ()
  {
    final List <Integer> list = new ArrayList <> ();

    final Optional <ArrayList <Integer>> expected = Optional.of ((ArrayList <Integer>) list);
    final Optional <ArrayList> actual = Utils.optionalCast (list, ArrayList.class);

    assertEquals (expected, actual);
  }

  @Test
  public void testOptionalCastInvalidCastAbsent ()
  {
    final Object object = "Hello";

    final Optional <Integer> expected = Optional.absent ();
    final Optional <Integer> actual = Utils.optionalCast (object, Integer.class);

    assertEquals (expected, actual);
  }

  @Test
  public void testOptionalCastInvalidPrimitiveAbsent ()
  {
    final int n = 1;

    final Optional <Boolean> expected = Optional.absent ();
    final Optional <Boolean> actual = Utils.optionalCast (n, Boolean.class);

    assertEquals (expected, actual);
  }

  @Test
  public void testOptionalCastNullObjectAbsent ()
  {
    final Object object = null;

    final Optional <String> expected = Optional.absent ();
    final Optional <String> actual = Utils.optionalCast (object, String.class);

    assertEquals (expected, actual);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOptionalCastNullClassThrowsException ()
  {
    Utils.optionalCast (new Object (), null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOptionalCastNullObjectNullClassThrowsException ()
  {
    Utils.optionalCast (null, null);
  }
}
