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
