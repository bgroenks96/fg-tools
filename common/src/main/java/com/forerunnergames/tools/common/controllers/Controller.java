package com.forerunnergames.tools.common.controllers;

public interface Controller
{
  public void initialize ();

  public void update ();

  public boolean shouldShutDown ();

  public void shutDown ();
}
