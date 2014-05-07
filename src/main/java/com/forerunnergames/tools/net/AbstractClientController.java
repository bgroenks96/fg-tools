package com.forerunnergames.tools.net;

import com.forerunnergames.tools.AbstractController;
import com.forerunnergames.tools.Arguments;

import java.io.IOException;

public abstract class AbstractClientController extends AbstractController <Client> implements ServerCommunicator
{
  private final Class[] classesToRegister;

  public AbstractClientController (final Client client, final Class... classesToRegisterForNetworkSerialization)
  {
    super (client);

    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");

    classesToRegister = classesToRegisterForNetworkSerialization;
  }

  public abstract void onConnectionTo (final Remote server);
  public abstract void onDisconnectionFrom (final Remote server);
  public abstract void onCommunication (final Object object, final Remote server);

  @Override
  public void initialize()
  {
    checkIsNotInitialized();

    registerClasses();
    addListener();
    startClient();

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
    getClient().register (type);
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

  public void connectTo (final String host, final int tcpPort, final int timeoutMs) throws IOException
  {
    Arguments.checkIsNotNull (host, "host");
    Arguments.checkIsNotNegative (tcpPort, "tcpPort");
    Arguments.checkIsNotNegative (timeoutMs, "timeoutMs");

    getClient().connectTo (host, tcpPort, timeoutMs);
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

  @Override
  public void sendToServer (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    getClient().send (object);
  }
}
