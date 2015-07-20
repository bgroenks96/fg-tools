package com.forerunnergames.tools.net.server;

import com.forerunnergames.tools.common.Result;

public interface ServerCreator
{
  Result <String> create (final ServerConfiguration config);

  String resolveAddress ();

  void destroy ();
}
