package com.forerunnergames.tools.common;

import com.forerunnergames.tools.common.controllers.Controller;

public interface Application
{
  void initialize ();

  void add (final Controller controller);

  void remove (final Controller controller);

  void update ();

  void shutDown ();

  boolean shouldShutDown ();
}
