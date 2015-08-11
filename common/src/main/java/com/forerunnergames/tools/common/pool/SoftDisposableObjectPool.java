package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;

import java.lang.ref.SoftReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoftDisposableObjectPool <T extends Disposable> extends SoftRecyclableObjectPool <T>
        implements DisposableObjectPool <T>
{
  private static final Logger log = LoggerFactory.getLogger (SoftDisposableObjectPool.class);

  protected SoftDisposableObjectPool (final PoolFactory <T> factory, final Class <T> type)
  {
    super (factory, type);
  }

  protected SoftDisposableObjectPool (final Class <T> type, final Object... args)
  {
    super (type, args);
  }

  /**
   * Calls {@link #dispose(Disposable)} on all objects currently in the pool and on references held for objects checkout
   * out of the pool before forwarding the call to SoftRecyclableObjectPool.close.
   */
  @Override
  public synchronized void close ()
  {
    for (final T obj : pool)
    {
      dispose (obj);
    }

    for (final SoftReference <T> ref : checkout)
    {
      final T obj = ref.get ();
      if (obj != null) dispose (obj);
    }

    super.close ();
  }

  @Override
  public void dispose (final T t)
  {
    Arguments.checkIsNotNull (t, "t");

    try
    {
      t.dispose ();
    }
    catch (final Exception e)
    {
      log.error ("Caught exception while disposing object: ", e);
    }
  }
}
