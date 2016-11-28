/*
 * Copyright © 2016 Forerunner Games, LLC
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

package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;
import com.forerunnergames.tools.common.annotations.AllowNegative;
import com.forerunnergames.tools.net.annotations.RequiredForNetworkSerialization;

public abstract class AbstractRemoteConfiguration implements RemoteConfiguration
{
  private final String address;
  @AllowNegative
  private final int port;

  protected AbstractRemoteConfiguration (final String address, @AllowNegative final int port)
  {
    Arguments.checkIsNotNull (address, "address");

    this.address = address;
    this.port = port;
  }

  @RequiredForNetworkSerialization
  protected AbstractRemoteConfiguration ()
  {
    address = null;
    port = -1;
  }

  @Override
  public String getAddress ()
  {
    return address;
  }

  public int getPort ()
  {
    return port;
  }

  @Override
  public String toString ()
  {
    return Strings.format ("{}: Address: [{}] | Port: [{}]", getClass ().getSimpleName (), address, port);
  }
}
