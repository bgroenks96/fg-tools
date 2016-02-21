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

package com.forerunnergames.tools.common;

import java.math.BigInteger;

public final class HashUtil
{
  private static final BigInteger MOD64 = new BigInteger ("2").pow (64);
  private static final BigInteger MOD128 = new BigInteger ("2").pow (128);
  private static final BigInteger FNV64_OFFSET = new BigInteger ("14695981039346656037");
  private static final BigInteger FNV64_PRIME = new BigInteger ("1099511628211");
  private static final BigInteger FNV128_OFFSET = new BigInteger ("144066263297769815596495629667062367629");
  private static final BigInteger FNV128_PRIME = new BigInteger ("309485009821345068724781371");

  public static BigInteger fnvHash64 (final byte[] data)
  {
    Arguments.checkIsNotNull (data, "data");

    return fnvHash (data, FNV64_OFFSET, FNV64_PRIME, MOD64);
  }

  public static BigInteger fnvHash128 (final byte[] data)
  {
    Arguments.checkIsNotNull (data, "data");

    return fnvHash (data, FNV128_OFFSET, FNV128_PRIME, MOD128);
  }

  private static BigInteger fnvHash (final byte[] data,
                                     final BigInteger fnvOffs,
                                     final BigInteger fnvPrime,
                                     final BigInteger mod)
  {
    BigInteger hash = fnvOffs;
    for (final byte next : data)
    {
      final BigInteger bigIntValue = BigInteger.valueOf (next & 0xff);
      hash = hash.xor (bigIntValue);
      hash = hash.multiply (fnvPrime).mod (mod);
    }
    return hash;
  }
}
