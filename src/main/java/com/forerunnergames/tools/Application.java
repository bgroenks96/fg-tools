package com.forerunnergames.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Application extends AbstractController <List <Controller>>
{
  public Application (final Controller... controllers)
  {
    super (Arrays.asList (controllers));
  }

  @Override
  public void initialize()
  {
    initializeControllers();
    setInitialized (true);
  }

  private void initializeControllers()
  {
    for (Controller controller : getControllers())
    {
      controller.initialize();
    }
  }

  private Collection <Controller> getControllers()
  {
    return getControllee();
  }

  @Override
  public boolean shouldShutDown()
  {
    checkIsInitialized();

    return anyControllerShouldShutDown();
  }

  private boolean anyControllerShouldShutDown()
  {
    for (Controller controller : getControllers())
    {
      if (controller.shouldShutDown())
      {
        return true;
      }
    }

    return false;
  }

  @Override
  public void shutDown()
  {
    if (! isInitialized())
    {
      return;
    }

    shutDownControllers();
  }

  private void shutDownControllers()
  {
    for (Controller controller : getControllers())
    {
      controller.shutDown();
    }
  }

  @Override
  public void update()
  {
    if (! isInitialized())
    {
      return;
    }

    updateControllers();
  }

  private void updateControllers()
  {
    for (Controller controller : getControllers())
    {
      controller.update();
    }
  }
}
