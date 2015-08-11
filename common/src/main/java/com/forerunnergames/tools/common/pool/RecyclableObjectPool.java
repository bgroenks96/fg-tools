package com.forerunnergames.tools.common.pool;

/**
 * Specification for an ObjectPool that allows for recycling of object instances. Once the user has finished processing
 * an acquired object instance, it can be returned to the pool for further reuse via the {@link #release(Object)}
 * method.
 */
public interface RecyclableObjectPool <T> extends ObjectPool <T>
{
  void release (final T t);

  void addRecycleListener (final RecycleListener <T> t);

  void removeRecycleListener (final RecycleListener <T> t);

  /**
   * Listener type for when objects are released back into the object pool. These should be used to perform any
   * necessary cleanup operations to prepare the object for recycled use.
   */
  public interface RecycleListener <T>
  {
    /**
     * Called when a given object 't' is recycled via RecyclableObjectPool.release. Note that this method will be called
     * <b>before</b> the object is processed and returned to the pool; so implementations should not assume that the
     * object has already become available in the pool again.
     */
    void onRelease (final T t);
  }
}
