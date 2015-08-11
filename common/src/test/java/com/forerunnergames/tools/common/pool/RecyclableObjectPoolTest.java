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
