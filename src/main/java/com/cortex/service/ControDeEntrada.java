/*
 * SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
 *
 * SPDX-License-Identifier: EPL-2.0
 */


package com.cortex.service;

import java.util.concurrent.atomic.AtomicBoolean;

public class ControDeEntrada {

    private final AtomicBoolean isPosting = new AtomicBoolean(false);

    public void executePost(Runnable postAction) {
        if (!isPosting.compareAndSet(false, true)) {
            throw new IllegalStateException("Ya hay una petición POST en curso. Espera a que termine.");
        }

        try {
            postAction.run();
        } finally {
            isPosting.set(false);
        }
    }

    public boolean tryExecutePost(Runnable postAction) {
        if (!isPosting.compareAndSet(false, true)) {
            return false;
        }

        try {
            postAction.run();
            return true;
        } finally {
            isPosting.set(false);
        }
    }

    public boolean isCurrentlyPosting() {
        return isPosting.get();
    }

}
