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

package io.github.kdocu.collections

import com.documentum.fc.client.IDfCollection

/**
 * Close the collection
 */
fun closeCollection(collection: IDfCollection?) {
    if (collection != null && collection.state != IDfCollection.DF_CLOSED_STATE) {
        collection.close()
    }
}

/**
 * Execute the given block function and then close the collection. This is similar to AutoCloseable interface
 */
inline fun <T : IDfCollection?, R> T.use(block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Exception) {
        closed = true
        try {
            closeCollection(this)
        } catch (closeException: Exception) {
        }
        throw e
    } finally {
        if (!closed) {
            closeCollection(this)
        }
    }
}