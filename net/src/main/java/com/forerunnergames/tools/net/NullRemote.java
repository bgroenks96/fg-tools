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

import java.net.InetSocketAddress;
import java.util.Objects;

public class NullRemote implements Remote
{
  private static final int CONNECTION_ID = Integer.MIN_VALUE;
  private static final InetSocketAddress ADDRESS = null;
  private static final int PORT = -1;
  private final String fakeAddress;

  public NullRemote (final String fakeAddress)
  {
    Arguments.checkIsNotNull (fakeAddress, "fakeAddress");

    this.fakeAddress = fakeAddress;
  }

  @Override
  public int hashCode ()
  {
    return fakeAddress.hashCode ();
  }

  @Override
  public boolean equals (final Object obj)
  {
    if (this == obj) return true;
    if (!(obj instanceof NullRemote)) return false;

    final NullRemote that = (NullRemote) obj;

    return fakeAddress.equals (that.fakeAddress);
  }

  @Override
  public String toString ()
  {
    return Strings.format ("{}: FakeAddress: [{}] | Connection Id: [{}] | Address: [{}] | PORT: [{}]",
                           getClass ().getSimpleName (), fakeAddress, CONNECTION_ID, ADDRESS, PORT);
  }

  @Override
  public int getConnectionId ()
  {
    return CONNECTION_ID;
  }

  @Override
  public boolean hasConnectionId (final int connectionId)
  {
    return connectionId == CONNECTION_ID;
  }

  @Override
  public boolean hasAddress ()
  {
    return false;
  }

  @Override
  public boolean hasPort ()
  {
    return false;
  }

  @Override
  public boolean hasAddressAndPort ()
  {
    return false;
  }

  @Override
  public boolean hasPort (final int port)
  {
    return port == PORT;
  }

  @Override
  public boolean has (final InetSocketAddress address)
  {
    return Objects.equals (ADDRESS, address);
  }

  @Override
  public boolean is (final Remote remote)
  {
    return equals (remote);
  }

  @Override
  public boolean isNot (final Remote remote)
  {
    return !is (remote);
  }

  @Override
  public String getAddress ()
  {
    return fakeAddress;
  }

  @Override
  public int getPort ()
  {
    return PORT;
  }
}
