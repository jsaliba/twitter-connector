/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter;

/**
 * Specifies the authorization access type.
 */
public enum AuthAccessType {
    /**
     * Read only access connection
     */
    READ,
    /**
     * Write and Read access connection
     */
    WRITE;
}
