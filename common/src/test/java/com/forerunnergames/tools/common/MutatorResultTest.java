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

package com.forerunnergames.tools.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.forerunnergames.tools.common.MutatorResult.MutatorCallback;

import org.junit.Test;

public class MutatorResultTest {
  
  @Test
  public void testMutableStateNotSetUntilAfterCommit ()
  {
    final MutableObject obj = new MutableObject ();
    final MutatorResult <?> result = obj.set(false);
    assertTrue (result.succeeded());
    assertFalse (obj.isSet());
    result.commitIfSuccessful();
    assertTrue (obj.isSet());
  }
  
  @Test
  public void testMutableStateNotSetAfterFailure ()
  {
    final MutableObject obj = new MutableObject ();
    final MutatorResult <?> result = obj.set(true);
    assertTrue (result.failed());
    assertFalse (obj.isSet());
    result.commitIfSuccessful();
    assertFalse (obj.isSet());
  }

  private class MutableObject {
    private boolean state = false;

    MutatorResult<String> set(final boolean fail) {
      if (fail) return MutatorResult.failure("");
      return MutatorResult.success(new MutatorCallback() {
        @Override
        public void commitChanges() {
          state = true;
        }
      });
    }
    
    boolean isSet ()
    {
      return state;
    }
  }
}
