package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

import com.google.common.net.InetAddresses;

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

  private NetworkConstants ()
  {
    Classes.instantiationNotAllowed ();
  }
}
