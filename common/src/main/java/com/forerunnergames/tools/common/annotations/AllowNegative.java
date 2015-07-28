package com.forerunnergames.tools.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (value = RetentionPolicy.SOURCE)
@Target (value = { ElementType.PARAMETER, ElementType.FIELD })
public @interface AllowNegative
{
}
