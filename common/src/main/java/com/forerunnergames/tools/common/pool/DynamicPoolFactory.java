package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of PoolFactory that uses reflection to dynamically locate and invoke the constructor for the
 * given type matching the given arguments. Note that this <b>will not work with argument sets that include primitive
 * types</b>. This is a limitation of Java varargs + reflection libraries. When the arguments are passed as varargs of
 * type Object, all primitive types get auto-boxed to their corresponding java.lang class types. Those class types do
 * not, however, match up with primitive types declared in constructors/methods. This will in turn cause the
 * DynamicPoolFactory constructor to throw a NoSuchMethodException.
 */
class DynamicPoolFactory <T> implements PoolFactory <T>
{
  private static final Logger log = LoggerFactory.getLogger (DynamicPoolFactory.class);
  private final Constructor <T> init;
  private final Object[] args;

  DynamicPoolFactory (final Class <T> type, final Object... args) throws NoSuchMethodException, SecurityException
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (args, "args");

    this.args = args;

    final Class <?>[] argTypes = new Class <?> [args.length];
    for (int i = 0; i < argTypes.length; i++)
    {
      argTypes [i] = args [i].getClass ();
    }
    init = type.getDeclaredConstructor (argTypes);
    init.setAccessible (true);
  }

  @Override
  public T make ()
  {
    try
    {
      return init.newInstance (args);
    }
    catch (final Exception e)
    {
      log.error ("Error allocating objects for type [{}] with args [{}]: {}", init.getDeclaringClass (),
                 Arrays.toString (args), e);
      throw new RuntimeException (e);
    }
  }
}
