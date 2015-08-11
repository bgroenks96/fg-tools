package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.id.Id;
import com.forerunnergames.tools.common.id.IdGenerator;
import com.forerunnergames.tools.common.pool.SoftRecyclableObjectPoolTest.TestRecyclablePoolWithTypeId;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith (Suite.class)
@Suite.SuiteClasses ({ TestRecyclablePoolWithTypeId.class })
public class SoftRecyclableObjectPoolTest
{
  public static class TestRecyclablePoolWithTypeId extends RecyclableObjectPoolTest <Id>
  {
    @Override
    protected RecyclableObjectPool <Id> createObjectPool ()
    {
      return Pools.createSoftRecyclablePool (Id.class, new PoolFactory <Id> ()
      {
        @Override
        public Id make ()
        {
          return IdGenerator.generateUniqueId ();
        }
      });
    }
  }
}
