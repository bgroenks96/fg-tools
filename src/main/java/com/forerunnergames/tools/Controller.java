package com.forerunnergames.tools;

public interface Controller
{
  public abstract void initialize();
  public abstract boolean isInitialized();
  public abstract boolean shouldShutDown();
  public abstract void shutDown();
  public abstract void update();
}
