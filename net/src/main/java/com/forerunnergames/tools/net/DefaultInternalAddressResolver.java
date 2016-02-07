package com.forerunnergames.tools.net;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    try
    {
      final String ipAddress = InetAddress.getLocalHost ().getHostAddress ();

      log.debug ("Successfully resolved internal IP address [{}].", ipAddress);

      return ipAddress;
    }
    catch (final UnknownHostException e)
    {
      log.error ("Could not resolve internal IP address.\n\nCause:\n\n{}", e);
      return "";
    }
  }
}
