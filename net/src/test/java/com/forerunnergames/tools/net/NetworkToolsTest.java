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

public class NetworkToolsTest
{
  @Test
  public void testIsLocalhostAddressTrue ()
  {
    assertTrue (NetworkTools.isLocalhostAddress ("localhost"));
  }

  @Test
  public void testIsLocalhostAddressTrue2 ()
  {
    assertTrue (NetworkTools.isLocalhostAddress ("127.0.0.1"));
  }

  @Test
  public void testIsLocalhostAddressFalse ()
  {
    assertFalse (NetworkTools.isLocalhostAddress ("localhost2"));
  }

  @Test
  public void testIsLocalhostAddressFalse2 ()
  {
    assertFalse (NetworkTools.isLocalhostAddress ("127.0.0..1"));
  }

  @Test
  public void testIsValidIpAddressTrue ()
  {
    assertTrue (NetworkTools.isValidIpAddress ("1.2.3.4"));
  }

  @Test
  public void testIsValidIpAddressFalseNull ()
  {
    assertFalse (NetworkTools.isValidIpAddress (null));
  }

  @Test
  public void testIsValidIpAddressFalseEmpty ()
  {
    assertFalse (NetworkTools.isValidIpAddress (""));
  }

  @Test
  public void testIsValidIpAddressFalseWhitespace ()
  {
    assertFalse (NetworkTools.isValidIpAddress ("    "));
  }

  @Test
  public void testIsValidIpAddressFalseMissingOctet ()
  {
    assertFalse (NetworkTools.isValidIpAddress (".2.3.4"));
  }

  @Test
  public void testIsValidIpAddressFalseDomainName ()
  {
    assertFalse (NetworkTools.isValidIpAddress ("example.com"));
  }

  @Test
  public void testIsValidAddressTrueIpAddress ()
  {
    assertTrue (NetworkTools.isValidAddress ("1.2.3.4"));
  }

  @Test
  public void testIsValidAddressTrueDomainName ()
  {
    assertTrue (NetworkTools.isValidAddress ("example.com"));
  }

  @Test
  public void testIsValidAddressFalseNull ()
  {
    assertFalse (NetworkTools.isValidAddress (null));
  }

  @Test
  public void testIsValidAddressFalseEmpty ()
  {
    assertFalse (NetworkTools.isValidAddress (""));
  }

  @Test
  public void testIsValidAddressFalseWhitespace ()
  {
    assertFalse (NetworkTools.isValidAddress ("    "));
  }

  @Test
  public void testIsValidAddressFalseIpAddressMissingOctet ()
  {
    assertFalse (NetworkTools.isValidAddress (".2.3.4"));
  }

  @Test
  public void testIsValidAddressFalseDomainNameMissingTLD ()
  {
    assertFalse (NetworkTools.isValidAddress ("example"));
  }

  @Test
  public void testIsValidDomainNameTrue ()
  {
    assertTrue (NetworkTools.isValidDomainName ("example.com"));
  }

  @Test
  public void testIsValidDomainNameFalseIpAddress ()
  {
    assertFalse (NetworkTools.isValidDomainName ("1.2.3.4"));
  }

  @Test
  public void testIsValidDomainNameFalseNull ()
  {
    assertFalse (NetworkTools.isValidDomainName (null));
  }

  @Test
  public void testIsValidDomainNameFalseEmpty ()
  {
    assertFalse (NetworkTools.isValidDomainName (""));
  }

  @Test
  public void testIsValidDomainNameFalseWhitespace ()
  {
    assertFalse (NetworkTools.isValidDomainName ("    "));
  }
}
