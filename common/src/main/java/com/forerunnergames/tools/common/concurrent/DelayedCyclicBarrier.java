package com.forerunnergames.tools.common.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Extension of CyclicBarrier that allows for delaying threads according to the order they arrive at the barrier.
 *
 * Note: it might be useful to add delay modes in the future for LIFO instead of the default implemented here (which is
 * implicitly FIFO).
 *
 * @author Brian Groenke
 */
public class DelayedCyclicBarrier extends CyclicBarrier
{
  protected final long baseDelayMillis;
  protected final DelayMode delayMode;

  public DelayedCyclicBarrier (final int parties, final long baseDelayMillis, final DelayMode delayMode)
  {
    this (parties, null, baseDelayMillis, delayMode);
  }

  public DelayedCyclicBarrier (final int parties,
                               final Runnable task,
                               final long baseDelayMillis,
                               final DelayMode delayMode)
  {
    super (parties, task);

    this.baseDelayMillis = baseDelayMillis;
    this.delayMode = delayMode;
  }

  @Override
  public int await (final long timeout, final TimeUnit timeUnit)
          throws InterruptedException, BrokenBarrierException, TimeoutException
  {
    final int arrivalIndex = super.await (timeout, timeUnit);

    // for some reason, CyclicBarrier uses descending order in the indices to indicate arrival time.
    // so here we invert it for the party number (makes computing the wait time more mathematically intuitive).
    final int partyNumber = getParties () - arrivalIndex - 1;
    final long waitTime = getWaitTimeFor (partyNumber);

    Thread.sleep (waitTime);

    return arrivalIndex;
  }

  @Override
  public int await () throws InterruptedException, BrokenBarrierException
  {
    try
    {
      return await (Long.MAX_VALUE, TimeUnit.HOURS);
    }
    // this probably won't happen, unless this thread waits for approximately:
    // (the entire remaining life span of the sun)*376071.
    // Seems sufficiently unlikely, but I'll leave that decision to posterity ;)
    catch (final TimeoutException e)
    {
      return -1;
    }
  }

  /**
   * Computes the wait time in milliseconds for the given party number. This method can be overridden by subclasses to
   * provide alternative delay distributions. Note that the delay mode passed to this parent type does not matter in
   * this case, and the subclass is free to define its own enum to cover its own cases.
   *
   * @param partyNumber
   *          the number indicating which for which party to compute the wait time 0 indicates the first party to
   *          arrive, <code>getParties() - 1</code> indicates the last. Note that this is opposite of the oddly
   *          non-intuitive return result of <code>CyclicBarrier.await()</code>
   * @return the time for the party to wait before resuming execution in milliseconds
   */
  protected long getWaitTimeFor (final int partyNumber)
  {
    if (partyNumber == 0)
    {
      return 0;
    }

    final long delayTimeMillis;
    switch (delayMode)
    {
      case UNIFORM:
        delayTimeMillis = baseDelayMillis;
        break;
      case LINEAR:
        delayTimeMillis = partyNumber * baseDelayMillis;
        break;
      default:
        throw new UnsupportedOperationException ("unhandled delay mode type: " + delayMode);
    }

    return delayTimeMillis;
  }

  public enum DelayMode
  {
    /**
     * Delay mode indicating that all threads that arrived after the first arriver should be delayed by the same amount
     */
    UNIFORM,
    /**
     * Delay mode indicating that each thread that arrives after the first arriver should be delayed by a linearly
     * increasing amount equal to that of its party number. i.e. if thread0 is the first party to arrive and the base
     * delay time is 5ms: thread1 will wait for 5ms, thread2 for 10ms, thread3 for 15ms, etc.
     */
    LINEAR
  }
}
