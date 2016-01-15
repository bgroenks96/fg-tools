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
