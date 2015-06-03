package com.forerunnergames.tools.common.controllers;

public interface Controller
{
  void initialize ();

  void update ();

  boolean shouldShutDown ();

  void shutDown ();
}
