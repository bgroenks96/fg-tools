package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Set;

// @formatter:off
public class SoftRecyclableObjectPool <T> extends SoftObjectPool <T> implements RecyclableObjectPool <T>
// @formatter:on
{
  private final Set <RecycleListener <T>> listeners = Sets.newConcurrentHashSet ();

  protected SoftRecyclableObjectPool (final PoolFactory <T> factory, final Class <T> type)
  {
    super (factory, type);
  }

  protected SoftRecyclableObjectPool (final Class <T> type, final Object... args)
  {
    super (type, args);
  }

  @Override
  public synchronized void release (final T t)
  {
    Arguments.checkIsNotNull (t, "t");

    notifyListeners (t);

    // find the object in the checkout set and return it to the pool
    final Iterator <SoftReference <T>> checkoutItr = checkout.iterator ();
    while (checkoutItr.hasNext ())
    {
      final SoftReference <T> nextRef = checkoutItr.next ();
      final Optional <T> obj = Optional.fromNullable (nextRef.get ());
      // remove cleared references from checkout set + the reference to this object, if it hasn't been cleared
      if (!obj.isPresent () || obj.get ().equals (t))
      {
        checkoutItr.remove ();
      }
    }
    pool.offer (t);
  }

  @Override
  public void addRecycleListener (final RecycleListener <T> listener)
  {
    Arguments.checkIsNotNull (listener, "listener");

    listeners.add (listener);
  }

  @Override
  public void removeRecycleListener (final RecycleListener <T> listener)
  {
    Arguments.checkIsNotNull (listener, "listener");

    listeners.remove (listener);
  }

  private void notifyListeners (final T recycledObj)
  {
    for (final RecycleListener <T> listener : listeners)
    {
      listener.onRelease (recycledObj);
    }
  }
}
