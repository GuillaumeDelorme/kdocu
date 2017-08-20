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

package io.github.kdocu.objects

import com.documentum.fc.client.IDfTypedObject
import com.documentum.fc.common.IDfId
import com.documentum.fc.common.IDfTime

// Type aliases for repeating types
typealias RepeatingBoolean = List<Boolean>
typealias RepeatingInt = List<Int>
typealias RepeatingString = List<String>
typealias RepeatingId = List<IDfId>
typealias RepeatingTime = List<IDfTime>
typealias RepeatingDouble = List<Double>

// Attributes types
data class BooleanAttr(val name: String)
data class IntAttr(val name: String)
data class StringAttr(val name: String)
data class IdAttr(val name: String)
data class TimeAttr(val name: String)
data class DoubleAttr(val name: String)

data class RepeatingBooleanAttr(val name: String)
data class RepeatingIntAttr(val name: String)
data class RepeatingStringAttr(val name: String)
data class RepeatingIdAttr(val name: String)
data class RepeatingTimeAttr(val name: String)
data class RepeatingDoubleAttr(val name: String)

// Get and set operators for single attributes
operator fun <T : IDfTypedObject> T.get(attribute: BooleanAttr): Boolean {
    return getBoolean(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: BooleanAttr, value: Boolean) {
    setBoolean(attribute.name, value)
}

operator fun <T : IDfTypedObject> T.get(attribute: IntAttr): Int {
    return getInt(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: IntAttr, value: Int) {
    setInt(attribute.name, value)
}

operator fun <T : IDfTypedObject> T.get(attribute: StringAttr): String {
    return getString(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: StringAttr, value: String) {
    setString(attribute.name, value)
}

operator fun <T : IDfTypedObject> T.get(attribute: IdAttr): IDfId {
    return getId(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: IdAttr, value: IDfId) {
    setId(attribute.name, value)
}

operator fun <T : IDfTypedObject> T.get(attribute: TimeAttr): IDfTime {
    return getTime(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: TimeAttr, value: IDfTime) {
    setTime(attribute.name, value)
}

operator fun <T : IDfTypedObject> T.get(attribute: DoubleAttr): Double {
    return getDouble(attribute.name)
}

operator fun <T : IDfTypedObject> T.set(attribute: DoubleAttr, value: Double) {
    setDouble(attribute.name, value)
}

// Get and set operators for repeating attributes
operator fun <T : IDfTypedObject> T.get(attribute: RepeatingBooleanAttr): RepeatingBoolean {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingBoolean(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingBooleanAttr, values: RepeatingBoolean) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingBoolean(attribute.name, index, value) })
}

operator fun <T : IDfTypedObject> T.get(attribute: RepeatingIntAttr): RepeatingInt {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingInt(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingIntAttr, values: RepeatingInt) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingInt(attribute.name, index, value) })
}

operator fun <T : IDfTypedObject> T.get(attribute: RepeatingStringAttr): RepeatingString {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingString(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingStringAttr, values: RepeatingString) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingString(attribute.name, index, value) })
}

operator fun <T : IDfTypedObject> T.get(attribute: RepeatingIdAttr): RepeatingId {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingId(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingIdAttr, values: RepeatingId) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingId(attribute.name, index, value) })
}

operator fun <T : IDfTypedObject> T.get(attribute: RepeatingTimeAttr): RepeatingTime {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingTime(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingTimeAttr, values: RepeatingTime) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingTime(attribute.name, index, value) })
}

operator fun <T : IDfTypedObject> T.get(attribute: RepeatingDoubleAttr): RepeatingDouble {
    return getRepeatingValues(attribute.name, this, { index -> getRepeatingDouble(attribute.name, index) })
}

operator fun <T : IDfTypedObject> T.set(attribute: RepeatingDoubleAttr, values: RepeatingDouble) {
    setRepeatingValues(attribute.name, this, values,
            { index, value -> setRepeatingDouble(attribute.name, index, value) })
}

internal fun <T> getRepeatingValues(attrName: String, obj: IDfTypedObject, getter: (Int) -> T): List<T> {
    return 0.until(obj.getValueCount(attrName))
            .map { index ->
                getter(index)
            }
}

internal fun <T> setRepeatingValues(attrName: String, obj: IDfTypedObject, values: List<T>, setter: (Int, T) -> Unit) {
    obj.removeAll(attrName)

    values.forEachIndexed { index, value ->
        setter(index, value)
    }
}