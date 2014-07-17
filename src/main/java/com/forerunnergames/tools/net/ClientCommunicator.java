package com.forerunnergames.tools.net;

public interface ClientCommunicator
{
  public void sendTo (final Remote client, final Object object);
  public void sendToAll (final Object object);
  public void sendToAllExcept (final Remote client, final Object object);
}
