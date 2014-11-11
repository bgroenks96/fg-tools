package com.forerunnergames.tools.common;

public interface Application
{
  public void initialize();
  public void add (final Controller controller);
  public void remove (final Controller controller);
  public void update();
  public void shutDown();
  public boolean shouldShutDown();
}
