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
package com.forerunnergames.tools.common.pool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class ObjectPoolTest <T>
{
  @Test
  public void testAllocateSingleton ()
  {
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      objPool.allocate (1);
      assertTrue (objPool.canAcquire ());
      assertTrue (objPool.acquire ().isPresent ());
    }
  }

  @Test
  public void testAllocateMore ()
  {
    final int n = 100;
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      objPool.allocate (n);
      for (int i = 0; i < n; i++)
      {
        assertTrue (objPool.acquire ().isPresent ());
      }
    }
  }

  @Test
  public void testAcquireWhenEmpty ()
  {
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      assertFalse (objPool.acquire ().isPresent ());
    }
  }

  @Test (expected = IllegalStateException.class)
  public void testAcquireFailsAfterClose ()
  {
    final ObjectPool <T> pool = createObjectPool ();
    pool.allocate (1);
    pool.close ();
    pool.acquire ();
  }

  @Test
  public void testSizeWhenEmpty ()
  {
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      assertTrue (objPool.size () == 0);
    }
  }

  @Test
  public void testSizeWithOne ()
  {
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      objPool.allocate (1);
      assertTrue (objPool.size () == 1);
    }
  }

  @Test
  public void testSizeWithOneHundredAfterAcquire ()
  {
    final int N = 101;
    try (final ObjectPool <T> objPool = createObjectPool ())
    {
      objPool.allocate (N);
      objPool.acquire ();
      assertTrue (objPool.size () == N - 1);
    }
  }

  protected abstract ObjectPool <T> createObjectPool ();
}
