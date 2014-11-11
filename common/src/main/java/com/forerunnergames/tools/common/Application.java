package com.forerunnergames.tools.common;

import com.forerunnergames.tools.common.controllers.Controller;

public interface Application
{
  public void initialize();
  public void add (final Controller controller);
  public void remove (final Controller controller);
  public void update();
  public void shutDown();
  public boolean shouldShutDown();
}
