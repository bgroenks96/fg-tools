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
