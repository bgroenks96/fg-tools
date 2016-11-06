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

package com.forerunnergames.tools.common.controllers;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeController extends ControllerAdapter
{
  protected final Logger log = LoggerFactory.getLogger (getClass ());
  private final Map <String, Controller> childrenNamesToChildren = new LinkedHashMap<> ();

  public CompositeController (final Controller... children)
  {
    Arguments.checkIsNotNull (children, "children");
    Arguments.checkHasNoNullElements (children, "children");

    for (final Controller child : children)
    {
      addInternal (child);
    }
  }

  @Override
  public void initialize ()
  {
    for (final Controller child : getAll ())
    {
      child.initialize ();
    }
  }

  @Override
  public void update ()
  {
    for (final Controller child : getAll ())
    {
      child.update ();
    }
  }

  @Override
  public boolean shouldShutDown ()
  {
    for (final Controller child : getAll ())
    {
      if (child.shouldShutDown ()) return true;
    }

    return false;
  }

  @Override
  public void shutDown ()
  {
    for (final Controller child : getAll ())
    {
      child.shutDown ();
    }
  }

  @Override
  public String toString ()
  {
    return Strings.format ("{}: Children: {}", getClass ().getSimpleName (),
                           Strings.toString (childrenNamesToChildren));
  }

  public void add (final Controller child)
  {
    Arguments.checkIsNotNull (child, "child");

    addInternal (child);
  }

  public void remove (final Controller child)
  {
    Arguments.checkIsNotNull (child, "child");

    final Controller removed = childrenNamesToChildren.remove (child.getName ());

    if (removed == null)
    {
      log.warn ("Could not remove non-existent child {}: [{}]", child.getClass ().getSimpleName (), child);
    }
  }

  public void remove (final String name)
  {
    Arguments.checkIsNotNull (name, "name");

    final Controller removed = childrenNamesToChildren.remove (name);

    if (removed == null)
    {
      log.warn ("Could not remove non-existent child {}: [{}]", Controller.class.getSimpleName (), name);
    }
  }

  public Controller get (final String name)
  {
    Arguments.checkIsNotNull (name, "name");

    final Controller controller = childrenNamesToChildren.get (name);

    if (controller == null)
    {
      throw new IllegalStateException (Strings.format ("Cannot find controller named \"{}\"", name));
    }

    return controller;
  }

  public ImmutableCollection <Controller> getAll ()
  {
    return ImmutableList.copyOf (childrenNamesToChildren.values ());
  }

  private void addInternal (final Controller child)
  {
    Arguments.checkIsNotNull (child, "child");

    final Controller previous = childrenNamesToChildren.put (child.getName (), child);

    if (previous != null)
    {
      log.warn ("Replaced previous child {}: [{}]", previous.getClass ().getSimpleName (), previous);
    }
  }
}
