package com.forerunnergames.tools.common;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public final class MutatorResult <R> extends Result <R>
{
  private final Optional <MutatorCallback> callback;

  private MutatorResult (final boolean isSuccessful, final R failureReason, final MutatorCallback callback)
  {
    super (isSuccessful, failureReason);

    this.callback = Optional.fromNullable (callback);
  }

  public static <R> MutatorResult <R> success (final MutatorCallback callback)
  {
    Arguments.checkIsNotNull (callback, "callback");

    return new MutatorResult <> (true, null, callback);
  }

  public static <R> MutatorResult <R> failure (final R reason)
  {
    Arguments.checkIsNotNull (reason, "reason");

    return new MutatorResult <> (false, reason, null);
  }

  public static void commitAllSuccessful (final MutatorResult <?>... results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    commitAllSuccessful (ImmutableSet.copyOf (results));
  }

  public static void commitAllSuccessful (final Iterable <MutatorResult <?>> results)
  {
    Arguments.checkIsNotNull (results, "results");
    Arguments.checkHasNoNullElements (results, "results");

    for (final MutatorResult <?> result : results)
    {
      result.commitIfSuccessful ();
    }
  }

  public final boolean commitIfSuccessful ()
  {
    if (callback.isPresent ()) callback.get ().commitChanges ();
    return succeeded ();
  }

  public interface MutatorCallback
  {
    void commitChanges ();
  }
}
