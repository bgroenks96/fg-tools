package com.forerunnergames.tools.net;

import com.forerunnergames.tools.AbstractController;
import com.forerunnergames.tools.Arguments;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class AbstractClientController extends AbstractController <Client>
{
  private final Class[] classesToRegister;

  public AbstractClientController (final Client client, final Class... classesToRegisterForNetworkSerialization)
  {
    super (client);

    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");

    classesToRegister = classesToRegisterForNetworkSerialization;
  }

  public abstract void onConnectionToServer (final int connectionId, final InetSocketAddress remoteAddress);
  public abstract void onDisconnectionFromServer (final int connectionId, final InetSocketAddress remoteAddress);
  public abstract void onServerCommunication (final Object remoteObject, final int connectionId, final InetSocketAddress remoteAddress);

  @Override
  public void initialize()
  {
    checkIsNotInitialized();

    registerClasses();
    startClient();
    addListener();

    setInitialized (true);
  }

  private void registerClasses()
  {
    for (Class classToRegister : classesToRegister)
    {
      registerClass (classToRegister);
    }
  }

  private void registerClass (final Class <?> type)
  {
    getClient().registerClass (type);
  }

  private Client getClient()
  {
    return getControllee();
  }

  private void startClient()
  {
    getClient().start();
  }

  private void addListener()
  {
    getClient().addListener (new NetworkListener()
    {
      @Override
      public void connected (final int connectionId, final InetSocketAddress remoteAddress)
      {
        onConnectionToServer (connectionId, remoteAddress);
      }

      @Override
      public void disconnected (final int connectionId, InetSocketAddress remoteAddress)
      {
        onDisconnectionFromServer (connectionId, remoteAddress);
      }

      @Override
      public void received (final Object remoteObject, final int connectionId, final InetSocketAddress remoteAddress)
      {
        Arguments.checkIsNotNull (remoteObject, "object");

        onServerCommunication (remoteObject, connectionId, remoteAddress);
      }
    });
  }

  public void connect (final int timeoutMs, final String host, final int tcpPort) throws IOException
  {
    Arguments.checkIsNotNegative (timeoutMs, "timeoutMs");
    Arguments.checkIsNotNull (host, "host");
    Arguments.checkIsNotNegative (tcpPort, "tcpPort");

    getClient().connect (timeoutMs, host, tcpPort);
  }

  public int getConnectionId()
  {
    return getClient().getConnectionId();
  }

  public boolean isConnected()
  {
    return getClient().isConnected();
  }

  public void disconnect()
  {
    getClient().disconnect();
  }

  @Override
  public boolean shouldShutDown()
  {
    return false;
  }

  @Override
  public void shutDown()
  {
    if (! clientIsRunning())
    {
      return;
    }

    shutDownClient();
  }

  public boolean clientIsRunning()
  {
    return getClient().isRunning();
  }

  private void shutDownClient()
  {
    disconnectClient();
    stopClient();
  }

  private void disconnectClient()
  {
    getClient().disconnect();
  }

  @Override
  public void update()
  {
    if (! isInitialized())
    {
      return;
    }

    updateClient();
  }

  private void updateClient()
  {
    getClient().update();
  }

  private void stopClient()
  {
    getClient().stop();
  }

  public void sendToServer (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    getClient().sendToServer (object);
  }
}
