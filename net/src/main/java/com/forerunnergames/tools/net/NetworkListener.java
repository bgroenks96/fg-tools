package com.forerunnergames.tools.net;

public interface NetworkListener
{
  void connected (final Remote remote);

  void disconnected (final Remote remote);

  void received (final Object object, final Remote sender);
}
