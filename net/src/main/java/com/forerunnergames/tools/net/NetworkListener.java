package com.forerunnergames.tools.net;

public interface NetworkListener
{
  public void connected (final Remote remote);
  public void disconnected (final Remote remote);
  public void received (final Object object, final Remote sender);
}
