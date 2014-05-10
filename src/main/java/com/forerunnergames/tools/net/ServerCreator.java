package com.forerunnergames.tools.net;

import com.forerunnergames.tools.Result;

public interface ServerCreator
{
  public Result create (final String name, final int tcpPort);
  public String resolveAddress();
  public void destroy();
}
