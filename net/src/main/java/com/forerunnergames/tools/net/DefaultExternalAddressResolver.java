package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Exceptions;

import com.google.common.net.InetAddresses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExternalAddressResolver implements ExternalAddressResolver
{
  protected final Logger log = LoggerFactory.getLogger (getClass ());
  private final String url;

  /**
   * @param url
   *          The URL where the ip address will be printed in plain text on the first line of the web page.
   */
  public DefaultExternalAddressResolver (final String url)
  {
    Arguments.checkIsNotNull (url, "url");

    this.url = url;
  }

  @Override
  public String resolveIp ()
  {
    BufferedReader reader = null;

    try
    {
      reader = new BufferedReader (new InputStreamReader (new URL (url).openStream ()));

      final String ipAddress = reader.readLine ();

      if (ipAddress == null || !InetAddresses.isInetAddress (ipAddress))
      {
        Exceptions.throwIO ("Invalid IP address [{}].", ipAddress);
      }

      return ipAddress;
    }
    catch (final IOException e)
    {
      log.error ("Could not resolve external ip address using URL [{}]\n\nCause:\n\n{}", url, e);

      return "";
    }
    finally
    {
      if (reader != null)
      {
        try
        {
          reader.close ();
        }
        catch (final IOException ignored)
        {
        }
      }
    }
  }
}
