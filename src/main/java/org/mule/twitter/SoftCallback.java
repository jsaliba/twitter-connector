/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter;

import org.apache.commons.lang.UnhandledException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.callback.SourceCallback;

import java.util.Map;

public final class SoftCallback implements SourceCallback {
    private final SourceCallback callback;

    public SoftCallback(SourceCallback callback) {
        this.callback = callback;
    }

    @Override
    public Object process() throws Exception {
        try {
            return callback.process();
        } catch (Exception e) {
            throw new UnhandledException(e);
        }
    }

    @Override
    public Object process(Object payload) {
        try {
            return callback.process(payload);
        } catch (Exception e) {
            throw new UnhandledException(e);
        }
    }

    @Override
    public Object process(Object payload, Map<String, Object> properties) throws Exception {
        try {
            return callback.process(payload);
        } catch (Exception e) {
            throw new UnhandledException(e);
        }
    }

    @Override
    public MuleEvent processEvent(MuleEvent event) throws MuleException
    {
        try {
            return callback.processEvent(event);
        } catch (Exception e) {
            throw new UnhandledException(e);
        }
    }
}
