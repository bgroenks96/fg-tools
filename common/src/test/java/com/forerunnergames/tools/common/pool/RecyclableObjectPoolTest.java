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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.forerunnergames.tools.common.pool.RecyclableObjectPool.RecycleListener;

import com.google.common.base.Optional;

import org.junit.Test;

public abstract class RecyclableObjectPoolTest <T> extends ObjectPoolTest <T>
{
  @Override
  protected abstract RecyclableObjectPool <T> createObjectPool ();

  @Test
  public void testReleasedObjectIsAcquirable ()
  {
    try (final RecyclableObjectPool <T> pool = createObjectPool ())
    {
      pool.allocate (1);
      final Optional <T> ref = pool.acquire ();
      assertTrue (ref.isPresent ());
      assertFalse (pool.canAcquire ());
      final T obj = ref.get ();
      pool.release (obj);
      // ironically, this is what you *shouldn't* do... i.e. release objects back into the pool while still using strong
      // references... but this is for testing ;)
      final Optional <T> newRef = pool.acquire ();
      assertTrue (newRef.isPresent ());
      assertEquals (obj, newRef.get ());
    }
  }

  @Test (expected = IllegalStateException.class)
  public void testReleaseFailsWithForeignObject ()
  {
    try (final RecyclableObjectPool <T> pool = createObjectPool ();
            final ObjectPool <T> otherPool = createObjectPool ())
    {
      pool.allocate (1);
      otherPool.allocate (1);
      pool.release (otherPool.acquire ().get ());
    }
  }

  @Test
  public void testListenerCalledOnRelease ()
  {
    try (final RecyclableObjectPool <T> pool = createObjectPool ())
    {
      @SuppressWarnings ("unchecked")
      final RecycleListener <T> mockListener = mock (RecycleListener.class);
      pool.addRecycleListener (mockListener);
      pool.allocate (1);
      final Optional <T> ref = pool.acquire ();
      assertTrue (ref.isPresent ());
      final T obj = ref.get ();
      pool.release (obj);
      verify (mockListener).onRelease (eq (obj));
    }
  }
}
