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
import com.forerunnergames.tools.net.annotations.RequiredForNetworkSerialization;

public final class DefaultClientConfiguration implements ClientConfiguration
{
  private final String clientAddress;
  private final int clientTcpPort;

  public DefaultClientConfiguration (final String clientAddress, final int clientTcpPort)
  {
    Arguments.checkIsNotNull (clientAddress, "clientAddress");
    Arguments.checkIsNotNegative (clientTcpPort, "clientTcpPort");

    this.clientAddress = clientAddress;
    this.clientTcpPort = clientTcpPort;
  }

  @Override
  public String getClientAddress ()
  {
    return clientAddress;
  }

  @Override
  public int getClientTcpPort ()
  {
    return clientTcpPort;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Client address: %2$s | Client port: %3$s (TCP)", getClass ().getSimpleName (),
                          clientAddress, clientTcpPort);
  }

  @RequiredForNetworkSerialization
  private DefaultClientConfiguration ()
  {
    clientAddress = null;
    clientTcpPort = 0;
  }
}
