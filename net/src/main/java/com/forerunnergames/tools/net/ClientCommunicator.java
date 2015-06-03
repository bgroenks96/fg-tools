package com.forerunnergames.tools.net;

public interface ClientCommunicator
{
  void sendTo (final Remote client, final Object object);

  void sendToAll (final Object object);

  void sendToAllExcept (final Remote client, final Object object);
}
