package com.forerunnergames.tools.net.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks default constructors that are required to be present for network serialization.
 */
@Retention (RetentionPolicy.SOURCE)
@Target (ElementType.CONSTRUCTOR)
public @interface RequiredForNetworkSerialization
{
}
