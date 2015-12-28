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
