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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NetworkConstantsTest
{
  @Test
  public void testIsLocalhostAddressTrue ()
  {
    assertTrue (NetworkConstants.isLocalhostAddress ("localhost"));
  }

  @Test
  public void testIsLocalhostAddressTrue2 ()
  {
    assertTrue (NetworkConstants.isLocalhostAddress ("127.0.0.1"));
  }

  @Test
  public void testIsLocalhostAddressFalse ()
  {
    assertFalse (NetworkConstants.isLocalhostAddress ("localhost2"));
  }

  @Test
  public void testIsLocalhostAddressFalse2 ()
  {
    assertFalse (NetworkConstants.isLocalhostAddress ("127.0.0..1"));
  }

  @Test
  public void testIsValidIpAddressTrue ()
  {
    assertTrue (NetworkConstants.isValidIpAddress ("1.2.3.4"));
  }

  @Test
  public void testIsValidIpAddressFalseNull ()
  {
    assertFalse (NetworkConstants.isValidIpAddress (null));
  }

  @Test
  public void testIsValidIpAddressFalseEmpty ()
  {
    assertFalse (NetworkConstants.isValidIpAddress (""));
  }

  @Test
  public void testIsValidIpAddressFalseWhitespace ()
  {
    assertFalse (NetworkConstants.isValidIpAddress ("    "));
  }

  @Test
  public void testIsValidIpAddressFalseMissingOctet ()
  {
    assertFalse (NetworkConstants.isValidIpAddress (".2.3.4"));
  }

  @Test
  public void testIsValidIpAddressFalseDomainName ()
  {
    assertFalse (NetworkConstants.isValidIpAddress ("example.com"));
  }

  @Test
  public void testIsValidAddressTrueIpAddress ()
  {
    assertTrue (NetworkConstants.isValidAddress ("1.2.3.4"));
  }

  @Test
  public void testIsValidAddressTrueDomainName ()
  {
    assertTrue (NetworkConstants.isValidAddress ("example.com"));
  }

  @Test
  public void testIsValidAddressFalseNull ()
  {
    assertFalse (NetworkConstants.isValidAddress (null));
  }

  @Test
  public void testIsValidAddressFalseEmpty ()
  {
    assertFalse (NetworkConstants.isValidAddress (""));
  }

  @Test
  public void testIsValidAddressFalseWhitespace ()
  {
    assertFalse (NetworkConstants.isValidAddress ("    "));
  }

  @Test
  public void testIsValidAddressFalseIpAddressMissingOctet ()
  {
    assertFalse (NetworkConstants.isValidAddress (".2.3.4"));
  }

  @Test
  public void testIsValidAddressFalseDomainNameMissingTLD ()
  {
    assertFalse (NetworkConstants.isValidAddress ("example"));
  }

  @Test
  public void testIsValidDomainNameTrue ()
  {
    assertTrue (NetworkConstants.isValidDomainName ("example.com"));
  }

  @Test
  public void testIsValidDomainNameFalseIpAddress ()
  {
    assertFalse (NetworkConstants.isValidDomainName ("1.2.3.4"));
  }

  @Test
  public void testIsValidDomainNameFalseNull ()
  {
    assertFalse (NetworkConstants.isValidDomainName (null));
  }

  @Test
  public void testIsValidDomainNameFalseEmpty ()
  {
    assertFalse (NetworkConstants.isValidDomainName (""));
  }

  @Test
  public void testIsValidDomainNameFalseWhitespace ()
  {
    assertFalse (NetworkConstants.isValidDomainName ("    "));
  }
}
