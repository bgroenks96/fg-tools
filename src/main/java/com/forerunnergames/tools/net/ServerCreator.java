package com.forerunnergames.tools.net;

public interface ServerCreator
{
  public void create (final int tcpPort);
  public boolean isCreated();
  public String getErrorMessage();
  public void destroy();
}
