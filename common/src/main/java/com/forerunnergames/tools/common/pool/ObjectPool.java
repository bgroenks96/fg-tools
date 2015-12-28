package com.forerunnergames.tools.common.pool;

import com.google.common.base.Optional;

import java.util.Iterator;

/**
 * Implementations of ObjectPool allow for pre-allocation and pooled distribution of Objects.
 */
public interface ObjectPool <T> extends AutoCloseable, Iterable <T>
{
  /**
   * Disposes all references currently held by this pool and permanently closes it. Calling any other methods on this
   * pool after calling {@code close()} will cause and {@link IllegalStateException} to be thrown. Note that calling
   * this method is an <b>optional operation</b>. Discarding a strong reference to an ObjectPool should effectively
   * produce the same effect in most circumstances. However, this method can be useful for auto-closing locally
   * allocated pools that might be injected into long-running tasks, e.g:
   *
   * <pre>
   * <code>
   * try(final ObjectPool pool = new ...)
   * {
   *   pool.allocate(nObjects);
   *   doSomethingWithObjects(pool);
   * }
   * </code>
   * </pre>
   *
   * <b>Important note:</b> Be very careful when closing pools in use across multiple threads. If the method in the
   * example above were executed asynchronously, the forked thread would be attempting to use a closed pool due to the
   * try/resource block.
   */
  @Override
  void close ();

  /**
   * Creates a thread-safe iterator that will call {@link #acquire()} until there are no more objects in the pool. Note
   * that the iterator returned by this method will throw an {@link UnsupportedOperationException} if Iterator.remove is
   * called.
   */
  @Override
  Iterator <T> iterator ();

  /**
   * @param n
   *          the number of objects to allocate and add to the object pool
   */
  void allocate (final int n);

  /**
   * Attempt to acquire an object from this pool.
   *
   * @return Optional wrapping the pooled object; present if available, absent otherwise.
   */
  Optional <T> acquire ();

  /**
   * @return true if this ObjectPool has pooled objects available, false otherwise.
   */
  boolean canAcquire ();

  /**
   * @return the number of Objects currently in this pool.
   */
  int size ();

  /**
   * @return true if this pool has been closed, false otherwise.
   */
  boolean isClosed ();
}
