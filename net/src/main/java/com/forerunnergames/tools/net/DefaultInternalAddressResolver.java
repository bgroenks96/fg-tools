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
package com.forerunnergames.tools.net;

import com.google.common.base.Optional;
import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DefaultInternalAddressResolver implements InternalAddressResolver
{
  private final Logger log = LoggerFactory.getLogger (getClass ());

  /**
   * Attempt to resolve the internal ip address of the local machine.
   *
   * @return A valid ip address string such that {@link InetAddresses#isInetAddress(String)} returns true, or an empty
   *         string if no valid ip address could be resolved.
   */
  @Override
  public String resolveIp ()
  {
    Optional <String> ipAddr = Optional.absent ();
    Enumeration <NetworkInterface> netInterfaces;
    try
    {
      netInterfaces = NetworkInterface.getNetworkInterfaces ();
      while (netInterfaces.hasMoreElements () && !ipAddr.isPresent ())
      {
        final NetworkInterface networkInterface = netInterfaces.nextElement ();
        if (networkInterface.isLoopback ()) continue;
        final Enumeration <InetAddress> addresses = networkInterface.getInetAddresses ();
        while (addresses.hasMoreElements () && !ipAddr.isPresent ())
        {
          final InetAddress next = addresses.nextElement ();
          if (next.isSiteLocalAddress ()) ipAddr = Optional.of (next.getHostAddress ());
        }
      }
    }
    catch (final SocketException e)
    {
      log.error ("Unable to resolve local IP address: ", e);
    }

    if (!ipAddr.isPresent ()) return "";

    log.debug ("Successfully resolved internal IP address [{}].", ipAddr.get ());

    return ipAddr.get ();
  }

  public static final void main (final String[] args)
  {
    System.out.println (new DefaultInternalAddressResolver ().resolveIp ());
  }
}
