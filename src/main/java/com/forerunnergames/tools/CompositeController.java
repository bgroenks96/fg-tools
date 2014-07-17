package com.forerunnergames.tools;

import com.google.common.collect.ImmutableSet;

public final class CompositeController implements Controller
{
  private final ImmutableSet <Controller> children;

  public CompositeController (final Controller... children)
  {
    Arguments.checkIsNotNull (children, "children");
    Arguments.checkHasNoNullElements (children, "children");

    this.children = ImmutableSet.copyOf (children);
  }

  @Override
  public void initialize()
  {
    for (final Controller controller : children)
    {
      controller.initialize();
    }
  }

  @Override
  public boolean shouldShutDown()
  {
    for (final Controller controller : children)
    {
      if (controller.shouldShutDown()) return true;
    }

    return false;
  }

  @Override
  public void shutDown()
  {
    for (final Controller controller : children)
    {
      controller.shutDown();
    }
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Children: %2$s", getClass().getSimpleName(), Strings.toString (children));
  }
}
