package com.forerunnergames.tools.common;

public interface Controller
{
  public void initialize();
  public void update();
  public boolean shouldShutDown();
  public void shutDown();
}
