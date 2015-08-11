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

    return new SoftDisposableObjectPool <AutoDisposable <T>> (factory, autoDisposableType);
  }

  private Pools ()
  {
    Classes.instantiationNotAllowed ();
  }
}
