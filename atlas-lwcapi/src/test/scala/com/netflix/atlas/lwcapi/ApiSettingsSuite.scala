/*
 * Copyright 2014-2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.atlas.lwcapi

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuite

class ApiSettingsSuite extends FunSuite {
  test("loads") {
    assert(ApiSettings.defaultFrequency > 0)
    assert(ApiSettings.redisHost.nonEmpty)
    assert(ApiSettings.redisPort > 0)
    assert(ApiSettings.redisTTL > 0)
    assert(ApiSettings.redisPrefixFor("this") === "this")
    assert(ApiSettings.stripRedisPrefix("this") === "this")
  }

  test("adds prefix") {
    val config = ConfigFactory.load("test-redis-prefixes.conf")
    val c = new ApiSettings(config)
    assert(c.redisPrefix === "testPrefix.")
    assert(c.redisPrefixFor("this") === "testPrefix.this")
    assert(c.stripRedisPrefix("this") === "this")
    assert(c.stripRedisPrefix("testPrefix") === "testPrefix")
    assert(c.stripRedisPrefix("testPrefix.") === "")
    assert(c.stripRedisPrefix("testPrefix.this") === "this")
  }
}
