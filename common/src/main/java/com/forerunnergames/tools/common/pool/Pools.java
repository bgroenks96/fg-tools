/*
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
import com.forerunnergames.tools.common.Classes;

import com.google.common.reflect.TypeToken;

public final class Pools
{
  public static <T> ObjectPool <T> createSoftPool (final Class <T> type, final PoolFactory <T> factory)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (factory, "factory");

    return new SoftObjectPool <> (factory, type);
  }

  public static <T> ObjectPool <T> createSoftPoolWithArgs (final Class <T> type, final Object... args)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (args, "args");

    return new SoftObjectPool <> (type, args);
  }

  public static <T> RecyclableObjectPool <T> createSoftRecyclablePool (final Class <T> type,
                                                                       final PoolFactory <T> factory)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (factory, "factory");

    return new SoftRecyclableObjectPool <> (factory, type);
  }

  public static <T> RecyclableObjectPool <T> createSoftRecyclablePoolWithArgs (final Class <T> type,
                                                                               final Object... args)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (args, "args");

    return new SoftRecyclableObjectPool <> (type, args);
  }

  public static <T extends Disposable> DisposableObjectPool <T> createSoftDisposablePool (final Class <T> type,
                                                                                          final PoolFactory <T> factory)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (factory, "factory");

    return new SoftDisposableObjectPool <> (factory, type);
  }

  public static <T extends Disposable> DisposableObjectPool <T> createSoftDisposablePoolWithArgs (final Class <T> type,
                                                                                                  final Object... args)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (args, "args");

    return new SoftDisposableObjectPool <> (type, args);
  }

  public static <T> DisposableObjectPool <AutoDisposable <T>> createSoftAutoDisposablePool (final Class <T> type,
                                                                                            final PoolFactory <AutoDisposable <T>> factory)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (factory, "factory");

    // Use Guava's generic type token hacks to deal with type erasure issues...
    final TypeToken <AutoDisposable <T>> autoDisposableToken = new TypeToken <AutoDisposable <T>> ()
    {
    };

    @SuppressWarnings ("unchecked")
    final Class <AutoDisposable <T>> autoDisposableType = (Class <AutoDisposable <T>>) autoDisposableToken
            .getRawType ();

    return new SoftDisposableObjectPool <> (factory, autoDisposableType);
  }

  private Pools ()
  {
    Classes.instantiationNotAllowed ();
  }
}
