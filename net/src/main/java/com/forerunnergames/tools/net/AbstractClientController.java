package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Result;
import com.forerunnergames.tools.common.controllers.ControllerAdapter;

import com.google.common.collect.ImmutableSet;

public abstract class AbstractClientController extends ControllerAdapter implements ClientController
{
  private final Client client;
  private final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization;

  protected AbstractClientController (final Client client,
                                      final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization,
                                      "classesToRegisterForNetworkSerialization");

    this.client = client;
    this.classesToRegisterForNetworkSerialization = classesToRegisterForNetworkSerialization;
  }

  @Override
  public void initialize ()
  {
    registerClasses ();
    addListener ();
    startClient ();
  }

  @Override
  public void shutDown ()
  {
    disconnect ();
    client.stop ();
  }

  @Override
  public Result <String> connect (final String address, final int tcpPort, final int timeoutMs, final int maxAttempts)
  {
    Arguments.checkIsNotNull (address, "address");
    Arguments.checkIsNotNegative (tcpPort, "tcpPort");
    Arguments.checkIsNotNegative (timeoutMs, "timeoutMs");
    Arguments.checkIsNotNegative (maxAttempts, "maxAttempts");

    return client.connect (address, tcpPort, timeoutMs, maxAttempts);
  }

  @Override
  public boolean isConnected ()
  {
    return client.isConnected ();
  }

  @Override
  public void disconnect ()
  {
    client.disconnect ();
  }

  @Override
  public void send (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    client.send (object);
  }

  protected abstract void onConnectionTo (final Remote server);

  protected abstract void onDisconnectionFrom (final Remote server);

  protected abstract void onCommunication (final Object object, final Remote server);

  private void registerClasses ()
  {
    for (final Class <?> classToRegister : classesToRegisterForNetworkSerialization)
    {
      client.register (classToRegister);
    }
  }

  private void addListener ()
  {
    client.add (new NetworkListener ()
    {
      @Override
      public void connected (final Remote server)
      {
        Arguments.checkIsNotNull (server, "server");

        onConnectionTo (server);
      }

      @Override
      public void disconnected (final Remote server)
      {
        Arguments.checkIsNotNull (server, "server");

        onDisconnectionFrom (server);
      }

      @Override
      public void received (final Object object, final Remote server)
      {
        Arguments.checkIsNotNull (object, "object");
        Arguments.checkIsNotNull (server, "server");

        onCommunication (object, server);
      }
    });
  }

  private void startClient ()
  {
    client.start ();
  }
}
