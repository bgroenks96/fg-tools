package com.forerunnergames.tools.common.net;

import com.forerunnergames.tools.common.Result;

public interface ServerCreator
{
  public Result <String> create (final String name, final int tcpPort);
  public String resolveAddress();
  public void destroy();
}
