/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
