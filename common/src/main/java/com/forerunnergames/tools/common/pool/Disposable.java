package com.forerunnergames.tools.common.pool;

/**
 * Represents a disposable object type.
 */
public interface Disposable
{
  /**
   * <b>Note: This method should only be called by {@link ObjectPool} implementations. Outside handlers should call
   * <code>dispose</code> on the pool itself in order to properly dispose of the object.</b><br/>
   * Asks this Disposable to make a best effort to release all currently held external resources, if any. Since
   * Disposable objects are not expected to be reused after a call to this method is made, there is no need for
   * implementations to attempt to reset object state.
   *
   * @throws Exception
   *           handled by ObjectPool implementations
   */
  void dispose () throws Exception;
}
