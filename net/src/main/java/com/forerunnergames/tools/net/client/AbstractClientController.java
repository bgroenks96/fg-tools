/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forerunnergames.tools.net.client;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Result;
import com.forerunnergames.tools.common.controllers.ControllerAdapter;
import com.forerunnergames.tools.net.NetworkListener;
import com.forerunnergames.tools.net.Remote;

import com.google.common.collect.ImmutableSet;

import java.util.concurrent.Future;

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
  public Result <String> connectNow (final String address,
                                     final int tcpPort,
                                     final int timeoutMs,
                                     final int maxAttempts)
  {
    Arguments.checkIsNotNull (address, "address");
    Arguments.checkIsNotNegative (tcpPort, "tcpPort");
    Arguments.checkIsNotNegative (timeoutMs, "timeoutMs");
    Arguments.checkIsNotNegative (maxAttempts, "maxAttempts");

    return client.connectNow (address, tcpPort, timeoutMs, maxAttempts);
  }

  @Override
  public Future <Result <String>> connectLater (final String address,
                                                final int tcpPort,
                                                final int timeoutMs,
                                                final int maxAttempts)
  {
    return client.connectLater (address, tcpPort, timeoutMs, maxAttempts);
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
