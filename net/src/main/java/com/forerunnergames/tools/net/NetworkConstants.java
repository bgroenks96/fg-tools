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
import com.forerunnergames.tools.common.Classes;

import com.google.common.net.HostSpecifier;
import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

public final class NetworkConstants
{
  public static final int MAX_PORT = 0xFFFF;
  public static final Pattern SERVER_ADDRESS_PATTERN = Pattern.compile ("[A-Za-z0-9.]");
  public static final Pattern SERVER_PORT_PATTERN = Pattern.compile ("[0-9]{1,5}");
  public static final String LOCALHOST_ADDRESS = "127.0.0.1";
  public static final String LOCALHOST_NAME = "localhost";
  public static final int MIN_SERVER_ADDRESS_STRING_LENGTH = 4;
  public static final int MAX_SERVER_ADDRESS_STRING_LENGTH = 255;
  public static final int MIN_SERVER_PORT_STRING_LENGTH = 1;
  public static final int MAX_SERVER_PORT_STRING_LENGTH = 5;

  public static boolean isLocalhostAddress (final String address)
  {
    Arguments.checkIsNotNull (address, "address");

    return address.equals (NetworkConstants.LOCALHOST_ADDRESS)
            || address.equalsIgnoreCase (NetworkConstants.LOCALHOST_NAME);
  }

  public static boolean isValidIpAddress (@Nullable final String address)
  {
    return address != null && InetAddresses.isInetAddress (address);
  }

  public static boolean isValidAddress (@Nullable final String address)
  {
    return address != null && HostSpecifier.isValid (address);
  }

  public static boolean isValidDomainName (@Nullable final String address)
  {
    return address != null && InternetDomainName.isValid (address);
  }

  private NetworkConstants ()
  {
    Classes.instantiationNotAllowed ();
  }
}
