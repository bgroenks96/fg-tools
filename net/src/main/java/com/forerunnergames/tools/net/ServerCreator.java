package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Result;

public interface ServerCreator
{
  public Result <String> create (final ServerConfiguration config);

  public String resolveAddress ();

  public void destroy ();
}
