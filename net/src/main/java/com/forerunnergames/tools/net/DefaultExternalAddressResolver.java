package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.net.InetAddresses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExternalAddressResolver implements ExternalAddressResolver
{
  protected final Logger log = LoggerFactory.getLogger (getClass ());
  private final ImmutableList <String> urls;
  private boolean alreadyLoggedErrorMessage = false;

  /**
   * @param url
   *          The URL where the ip address will be printed in plain text on the first line of the web page. Must not be
   *          null.
   */
  public DefaultExternalAddressResolver (final String url)
  {
    Arguments.checkIsNotNull (url, "url");

    urls = ImmutableList.of (url);
  }

  /**
   * @param urls
   *          The URL's where the ip address will be printed in plain text on the first line of the web page. Must not
   *          be null, must not contain any null elements. The URL's will be attempted in the specified order until a
   *          valid ip address is found.
   */
  public DefaultExternalAddressResolver (final String... urls)
  {
    Arguments.checkIsNotNull (urls, "urls");
    Arguments.checkHasNoNullElements (urls, "urls");

    this.urls = ImmutableList.copyOf (Arrays.asList (urls));
  }

  /**
   * Attempt to use the specified URL('s) to resolve the external ip address of the local machine.
   *
   * @return A valid ip address string such that {@link InetAddresses#isInetAddress(String)} returns true, or an empty
   *         string if no valid ip address could be resolved from the supplied URL('s).
   */
  @Override
  public String resolveIp ()
  {
    for (final String url : urls)
    {
      final Optional <String> ipAddress = readIpAddressFrom (url);

      if (isValid (ipAddress))
      {
        log.debug ("Successfully resolved external IP address [{}] from URL [{}].", ipAddress.get (), url);
        return ipAddress.get ();
      }

      if (!alreadyLoggedErrorMessage)
      {
        log.error ("Could not resolve external IP address [{}] using URL [{}]", ipAddress.orNull (), url);
      }
    }

    log.error ("Could not resolve external IP address using the specified URL('s):\n\n[{}]\n\nThe returned ip address will be empty.",
               urls);

    return "";
  }

  private Optional <String> readIpAddressFrom (final String url)
  {
    BufferedReader reader = null;
    alreadyLoggedErrorMessage = false;

    try
    {
      log.debug ("Attempting to resolve external ip address from URL [{}]...", url);

      reader = new BufferedReader (new InputStreamReader (new URL (url).openStream ()));

      return Optional.fromNullable (reader.readLine ());
    }
    catch (final IOException e)
    {
      log.error ("Could not resolve external IP address using URL [{}]\n\nCause:\n\n{}", url, e);

      alreadyLoggedErrorMessage = true;

      return Optional.absent ();
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

  private boolean isValid (final Optional <String> ipAddress)
  {
    return ipAddress.isPresent () && InetAddresses.isInetAddress (ipAddress.get ());
  }
}
