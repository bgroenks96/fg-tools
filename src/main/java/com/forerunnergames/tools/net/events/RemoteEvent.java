package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.Event;

public interface RemoteEvent <T extends Event> extends Event
{
  public T unwrap();
}
