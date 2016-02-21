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

import com.google.common.base.Optional;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An automated delegate type for wrapping non-Disposable types. A DisposableCallback can be provided to be to which the
 * AutoDisposable will delegate a call to {@link #dispose()}.
 *
 * @see DisposableCallback
 */
public class AutoDisposable <T> implements Disposable
{
  private static final Logger log = LoggerFactory.getLogger (AutoDisposable.class);
  private final T objRef;
  private final DisposableCallback <T> disposableCallback;

  @Override
  public void dispose () throws Exception
  {
    disposableCallback.dispose (objRef);
  }

  /**
   * Creates an AutoDisposable with the given object instance and an empty callback task.
   */
  public static <T> AutoDisposable <T> forObject (final T objRef)
  {
    Arguments.checkIsNotNull (objRef, "objRef");

    return new AutoDisposable <T> (objRef, new DisposableCallback <T> ()
    {
      @Override
      public void dispose (final T objRef)
      {
        Arguments.checkIsNotNull (objRef, "objRef");

      }
    });
  }

  /**
   * Wraps the given object factory with an AutoDisposable factory.
   */
  public static <T> PoolFactory <AutoDisposable <T>> forObject (final PoolFactory <T> factory)
  {
    Arguments.checkIsNotNull (factory, "factory");

    return new PoolFactory <AutoDisposable <T>> ()
    {
      @Override
      public AutoDisposable <T> make ()
      {
        return forObject (factory.make ());
      }
    };
  }

  public static <T extends Closeable> AutoDisposable <T> forCloseable (final T objRef)
  {
    Arguments.checkIsNotNull (objRef, "objRef");

    return new AutoDisposable <T> (objRef, new DisposableCallback <T> ()
    {
      @Override
      public void dispose (final T objRef)
      {
        Arguments.checkIsNotNull (objRef, "objRef");

        try
        {
          objRef.close ();
        }
        catch (final IOException e)
        {
          log.error ("Caught error from closeable: ", e);
        }
      }
    });
  }

  public static <T extends Closeable> PoolFactory <AutoDisposable <T>> forCloseable (final PoolFactory <T> factory)
  {
    Arguments.checkIsNotNull (factory, "factory");

    return new PoolFactory <AutoDisposable <T>> ()
    {
      @Override
      public AutoDisposable <T> make ()
      {
        return forCloseable (factory.make ());
      }
    };
  }

  /**
   * @param objRef
   *          the object instance to wrap
   * @param disposableCallback
   *          the callback task to invoke when {@link #dispose()} is called on AutoDisposable.
   */
  public static <T> AutoDisposable <T> forObjectWith (final T objRef, final DisposableCallback <T> disposableCallback)
  {
    Arguments.checkIsNotNull (objRef, "objRef");
    Arguments.checkIsNotNull (disposableCallback, "disposableCallback");

    return new AutoDisposable <T> (objRef, disposableCallback);
  }

  public static <T> PoolFactory <AutoDisposable <T>> forObjectWith (final PoolFactory <T> factory,
                                                                    final DisposableCallback <T> disposableCallback)
  {
    Arguments.checkIsNotNull (factory, "factory");
    Arguments.checkIsNotNull (disposableCallback, "disposableCallback");

    return new PoolFactory <AutoDisposable <T>> ()
    {
      @Override
      public AutoDisposable <T> make ()
      {
        return forObjectWith (factory.make (), disposableCallback);
      }
    };
  }

  /**
   * Acquires an AutoDisposable from the given pool and unpacks the reference, returning it as an Optional of the
   * AutoDisposable's parameterized type.
   */
  public static <T> Optional <T> acquireFrom (final ObjectPool <AutoDisposable <T>> pool)
  {
    Arguments.checkIsNotNull (pool, "pool");

    final Optional <AutoDisposable <T>> ref = pool.acquire ();
    if (ref.isPresent ()) return Optional.of (ref.get ().unpack ());
    return Optional.absent ();
  }

  /**
   * @return the object reference; guaranteed to be non-null.
   */
  public T unpack ()
  {
    return objRef;
  }

  @Override
  public int hashCode ()
  {
    return objRef.hashCode ();
  }

  @Override
  public boolean equals (final Object obj)
  {
    Arguments.checkIsNotNull (obj, "obj");

    return obj instanceof AutoDisposable && objRef.equals (((AutoDisposable <?>) obj).unpack ());
  }

  private AutoDisposable (final T objRef, final DisposableCallback <T> disposableCallback)
  {
    this.objRef = objRef;
    this.disposableCallback = disposableCallback;
  }

  public interface DisposableCallback <R>
  {
    void dispose (final R objRef);
  }
}
