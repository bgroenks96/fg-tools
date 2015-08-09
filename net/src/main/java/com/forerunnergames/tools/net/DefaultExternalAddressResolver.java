package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;

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
   *          The URL where the ip address and hostname will be printed on subsequent lines. The first line must be the
   *          ip address string, and the second line must be the hostname string.
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

      return reader.readLine ();
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

  @Override
  public String resolveHostName ()
  {
    BufferedReader reader = null;

    try
    {
      reader = new BufferedReader (new InputStreamReader (new URL (url).openStream ()));
      reader.readLine ();

      return reader.readLine ();
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
