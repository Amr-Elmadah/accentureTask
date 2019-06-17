/*
 * Copyright (C) 2015 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tasks.accenturetask.ui.albums.presetation.view.activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * A JUnit [@Rule][Rule] which launches an activity when your test starts. Stop extending
 * gross `ActivityInstrumentationBarfCase2`!
 *
 *
 * Usage:
 * <pre>`&#064;Rule
 * public final ActivityRule<ExampleActivity> example =
 * new ActivityRule<>(ExampleActivity.class);
`</pre> *
 *
 * This will automatically launch the activity for each test method. The instance will also be
 * created sooner should you need to use it in a [@Before][Before] method.
 *
 *
 * You can also customize the way in which the activity is launched by overriding
 * [.getLaunchIntent] and customizing or replacing the [Intent].
 * <pre>`&#064;Rule
 * public final ActivityRule<ExampleActivity> example =
 * new ActivityRule<ExampleActivity>(ExampleActivity.class) {
 * &#064;Override
 * protected Intent getLaunchIntent(String packageName, Class<ExampleActivity> activityClass) {
 * Intent intent = super.getLaunchIntent(packageName, activityClass);
 * intent.putExtra("Hello", "World!");
 * return intent;
 * }
 * };
`</pre> *
 */
class ActivityRule<T : Activity>(private val activityClass: Class<T>) : TestRule {

    private var activity: T? = null
    private var instrumentation: Instrumentation? = null

    protected fun getLaunchIntent(targetPackage: String, activityClass: Class<T>): Intent {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClassName(targetPackage, activityClass.name)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return intent
    }

    /**
     * Get the running instance of the specified activity. This will launch it if it is not already
     * running.
     */
    fun get(): T? {
        launchActivity()
        return activity
    }

    /** Get the [Instrumentation] instance for this test.  */
    fun instrumentation(): Instrumentation? {
        launchActivity()
        return instrumentation
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                launchActivity()

                base.evaluate()

                if (!activity!!.isFinishing) {
                    activity!!.finish()
                }
                activity = null // Eager reference kill in case someone leaked our reference.
            }
        }
    }

    private fun fetchInstrumentation(): Instrumentation {
        return instrumentation ?: InstrumentationRegistry.getInstrumentation()
    }

    private// Guarded by generics at the constructor.
    fun launchActivity() {
        if (activity != null) return

        val instrumentation = fetchInstrumentation()

        val targetPackage = instrumentation.targetContext.packageName
        val intent = getLaunchIntent(targetPackage, activityClass)

        activity = instrumentation.startActivitySync(intent) as T
        instrumentation.waitForIdleSync()
    }
}