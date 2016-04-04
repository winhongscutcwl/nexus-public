/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks methods that require transactional behaviour.
 *
 * Transactions are acquired from the component being intercepted if it implements
 * {@link TransactionalAware}; otherwise falls back to current {@link UnitOfWork}.
 *
 * @since 3.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Transactional
{
  /**
   * List of exceptions to commit (not rollback) on.
   */
  Class<? extends Exception>[] commitOn() default {};

  /**
   * List of exceptions to retry the method on.
   */
  Class<? extends Exception>[] retryOn() default {};

  /**
   * List of TX exceptions to swallow (and log).
   *
   * Only applies to exceptions that occur after the user method has returned,
   * while the transaction is committed. Useful for "best-effort" or optional
   * updates where it's safe to proceed even if the commit threw an exception.
   */
  Class<? extends Exception>[] swallow() default {};
}