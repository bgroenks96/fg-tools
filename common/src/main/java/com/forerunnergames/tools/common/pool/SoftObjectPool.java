package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Preconditions;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A non-reusable object pool that holds {@link SoftReference}s to acquired object instances. This means that the pool
 * will maintain a reference to all allocated objects from when objects are allocated via {@link #allocate(int)} to
 * after they are acquired via {@link #acquire()}. References will not be fully released until the pool is closed via
 * {@link #close()}. The benefit of this kind of pooling is that it will prevent the JVM from eagerly GC'ing allocated
 * instances even after the caller has disposed of all strong references to them. Per the nature of
 * {@link SoftReference}s, however, any held by a non-closed SoftObjectPool that point to discarded references are
 * guaranteed to be GC'd before the JVM resorts to throwing an OutOfMemoryError.
 *
 * @param <T>
 *          the type of object to be pooled
 */
public class SoftObjectPool <T> extends AbstractObjectPool <T>
{
  private static final Logger log = LoggerFactory.getLogger (SoftObjectPool.class);
  private final ReferenceQueue <T> refQueue = new ReferenceQueue <T> ();
  private final AtomicBoolean isClosed = new AtomicBoolean ();
  protected final ConcurrentLinkedQueue <T> pool = new ConcurrentLinkedQueue <> ();
  protected final Set <SoftReference <T>> checkout = Sets.newConcurrentHashSet ();

  protected SoftObjectPool (final PoolFactory <T> factory, final Class <T> type)
  {
    super (factory, type);
  }

  /**
   * Creates a new SoftObjectPool for the given object type and with the given constructor arguments.<br/>
   * <b>Note: this constructor will not work for constructors with primitive type parameters.</b> See package class
   * {@link DynamicPoolFactory} for an explanation.
   */
  protected SoftObjectPool (final Class <T> type, final Object... args)
  {
    super (type, args);
  }

  @Override
  public synchronized void allocate (final int n)
  {
    Arguments.checkIsNotNegative (n, "n");
    Preconditions.checkIsFalse (isClosed.get (), "Object pool has been closed.");

    final PoolFactory <T> factory = getFactory ();
    for (int i = 0; i < n; i++)
    {
      pool.offer (factory.make ());
    }
    log.trace ("Allocated {} objects of type {}.", n, getType ());
  }

  @Override
  public synchronized Optional <T> acquire ()
  {
    Preconditions.checkIsFalse (isClosed.get (), "Object pool has been closed.");

    if (!canAcquire ()) return Optional.absent ();
    final T obj = pool.poll ();
    checkout.add (new SoftReference <T> (obj, refQueue));
    log.trace ("Object taken from pool. type: {} | current checkout count: {}", getType (), checkout.size ());
    return Optional.of (obj);
  }

  @Override
  public boolean canAcquire ()
  {
    return !pool.isEmpty () && !isClosed.get ();
  }

  @Override
  public int size ()
  {
    Preconditions.checkIsFalse (isClosed.get (), "Object pool has been closed.");

    return pool.size ();
  }

  @Override
  public synchronized void close ()
  {
    if (isClosed.get ()) return;
    pool.clear ();
    Reference <?> ref;
    while ((ref = refQueue.poll ()) != null)
    {
      ref.clear ();
    }
    checkout.clear ();
    isClosed.set (true);
  }

  @Override
  public boolean isClosed ()
  {
    return isClosed.get ();
  }
}
