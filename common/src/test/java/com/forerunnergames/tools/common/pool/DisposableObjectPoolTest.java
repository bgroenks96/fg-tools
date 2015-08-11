package com.forerunnergames.tools.common.pool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.common.base.Optional;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DisposableObjectPoolTest <T extends Disposable> extends ObjectPoolTest <T>
{
  private static final Logger log = LoggerFactory.getLogger (DisposableObjectPoolTest.class);

  @Override
  protected abstract DisposableObjectPool <T> createObjectPool ();

  @Test
  public void testDisposedObjectIsNotReturnedToPool ()
  {
    try (final DisposableObjectPool <T> pool = createObjectPool ())
    {
      pool.allocate (1);
      final Optional <T> ref = pool.acquire ();
      assertTrue (ref.isPresent ());
      pool.dispose (ref.get ());
      assertFalse (pool.canAcquire ());
    }
  }

  @Test
  public void testDisposeCalled ()
  {
    try (final DisposableObjectPool <T> pool = createObjectPool ())
    {
      pool.allocate (1);
      final Optional <T> ref = pool.acquire ();
      assertTrue (ref.isPresent ());
      final T obj = spy (ref.get ());
      pool.dispose (obj);
      try
      {
        verify (obj).dispose ();
      }
      catch (final Exception e)
      {
        log.error ("Exception: ", e);
        fail (e.toString ());
      }
    }
  }
}
