package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Exceptions;

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

    // find the object in the checkout set and return it to the pool
    final Iterator <SoftReference <T>> checkoutItr = checkout.iterator ();
    boolean foundMatch = false;
    while (checkoutItr.hasNext ())
    {
      final SoftReference <T> nextRef = checkoutItr.next ();
      final Optional <T> obj = Optional.fromNullable (nextRef.get ());
      // remove cleared references from checkout set + the reference to this object, if it hasn't been cleared
      final boolean isMatch = obj.get ().equals (t);
      if (!obj.isPresent () || isMatch)
      {
        checkoutItr.remove ();
        if (!foundMatch) foundMatch = true;
      }
    }

    if (!foundMatch) Exceptions.throwIllegalState ("Object [{}] is not a member of this pool.", t);

    notifyListeners (t);

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
