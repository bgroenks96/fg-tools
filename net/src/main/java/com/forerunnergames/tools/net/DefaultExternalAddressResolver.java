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
