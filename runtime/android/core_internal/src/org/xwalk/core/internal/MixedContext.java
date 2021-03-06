// Copyright (c) 2013 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core.internal;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * MixedContext provides ApplicationContext for the contextImpl object
 * created by Context.CreatePackageContext().
 *
 * For cross package usage, the library part need the possibility to
 * get both the application's context and the library itself's context.
 *
 */
class MixedContext extends ContextWrapper {
    private Context mActivityCtx;

    public MixedContext(Context base, Context activity) {
        super(base);
        mActivityCtx = activity;
    }

    @Override
    public Context getApplicationContext() {
        return mActivityCtx.getApplicationContext();
    }

    @Override
    public boolean bindService(Intent in, ServiceConnection conn, int flags) {
        return getApplicationContext().bindService(in, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        getApplicationContext().unbindService(conn);
    }

    @Override
    public Object getSystemService(String name) {
        if (name.equals(Context.LAYOUT_INFLATER_SERVICE)) {
            return super.getSystemService(name);
        }
        return mActivityCtx.getSystemService(name);
    }
}
