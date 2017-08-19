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

package io.github.kdocu.queries

import com.documentum.fc.client.DfQuery
import com.documentum.fc.client.IDfCollection
import com.documentum.fc.client.IDfQuery
import com.documentum.fc.client.IDfSession

/**
 * Execute a DQL query
 */
fun execQuery(session: IDfSession, query: String): IDfCollection {
    return DfQuery(query).execute(session, IDfQuery.DF_QUERY)
}

/**
 * Execute a read-only select DQL query. The query must not make any change during processing
 */
fun execReadQuery(session: IDfSession, query: String): IDfCollection {
    return DfQuery(query).execute(session, IDfQuery.DF_READ_QUERY)
}

/**
 * Execute a read-only cached DQL query
 */
fun execCachedQuery(session: IDfSession, query: String): IDfCollection {
    return DfQuery(query).execute(session, IDfQuery.DF_CACHE_QUERY)
}

/**
 * Execute a DQL Query that invoke procedure. For example GET_PATH
 */
fun execApplyQuery(session: IDfSession, query: String): IDfCollection {
    return DfQuery(query).execute(session, IDfQuery.DF_APPLY)
}