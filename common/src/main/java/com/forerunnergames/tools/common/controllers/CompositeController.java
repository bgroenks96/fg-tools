package com.forerunnergames.tools.common.controllers;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CompositeController implements Controller
{
  private final List <Controller> children = new ArrayList <> ();

  public CompositeController (final Controller... children)
  {
    Arguments.checkIsNotNull (children, "children");
    Arguments.checkHasNoNullElements (children, "children");

    this.children.addAll (Arrays.asList (children));
  }

  public void add (final Controller child)
  {
    Arguments.checkIsNotNull (child, "child");

    children.add (child);
  }

  public void remove (final Controller child)
  {
    Arguments.checkIsNotNull (child, "child");
    Arguments.checkIsTrue (children.contains (child), "Child [" + child + "] not found.");

    children.remove (child);
  }

  @Override
  public void initialize ()
  {
    for (final Controller controller : children)
    {
      controller.initialize ();
    }
  }

  @Override
  public void update ()
  {
    for (final Controller controller : children)
    {
      controller.update ();
    }
  }

  @Override
  public boolean shouldShutDown ()
  {
    for (final Controller controller : children)
    {
      if (controller.shouldShutDown ()) return true;
    }

    return false;
  }

  @Override
  public void shutDown ()
  {
    for (final Controller controller : children)
    {
      controller.shutDown ();
    }
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Children: %2$s", getClass ().getSimpleName (), Strings.toString (children));
  }
}
