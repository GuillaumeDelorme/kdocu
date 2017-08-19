/*
 * Copyright 2017 Guillaume Delorme
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.kdocu.sessions

import com.documentum.com.DfClientX
import com.documentum.fc.client.IDfSession

/**
 * Get a Documentum session
 */
fun getSession(repository: String, username: String, password: String): IDfSession {
    val clientX = DfClientX()

    val loginInfo = clientX.loginInfo
    loginInfo.user = username
    loginInfo.password = password

    val sessionManager = clientX.localClient.newSessionManager()
    sessionManager.setIdentity(repository, loginInfo)
    return sessionManager.getSession(repository)
}

/**
 * Release a Documentum session
 */
fun releaseSession(session: IDfSession?) {
    if (session != null && session.isConnected) {
        session.sessionManager.release(session)
    }
}

/**
 * Execute the given block function and then release the session. This is similar to AutoCloseable interface
 */
inline fun <T : IDfSession?, R> T.use(block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Exception) {
        closed = true
        try {
            releaseSession(this)
        } catch (closeException: Exception) {
        }
        throw e
    } finally {
        if (!closed) {
            releaseSession(this)
        }
    }
}