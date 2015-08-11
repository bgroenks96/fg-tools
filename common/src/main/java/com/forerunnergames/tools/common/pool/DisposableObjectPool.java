package com.forerunnergames.tools.common.pool;

public interface DisposableObjectPool <T extends Disposable> extends ObjectPool <T>
{
  void dispose (final T t);
}
