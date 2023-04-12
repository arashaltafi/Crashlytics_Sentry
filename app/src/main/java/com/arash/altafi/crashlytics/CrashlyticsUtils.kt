package com.arash.altafi.crashlytics

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import io.sentry.Breadcrumb
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import io.sentry.android.fragment.FragmentLifecycleIntegration

object CrashlyticsUtils {

    fun setupCrashlytics(
        application: Application,
        isDebug: Boolean,
        versionName: String,
        versionCode: Int
    ) {
        SentryAndroid.init(application) { options ->
            options.dsn =
                "https://b02e33277c4746508ef61522cd94f2c0@o1126595.ingest.sentry.io/6270980"

            if (isDebug) {
                options.isDebug = true
                options.environment = "DEBUG"

                // enable "performance monitoring"
                options.tracesSampleRate = 1.0

                options.addIntegration(
                    FragmentLifecycleIntegration(
                        application,
                        enableFragmentLifecycleBreadcrumbs = true, // enabled by default
                        enableAutoFragmentLifecycleTracing = true  // disabled by default
                    )
                )
            } else {
                options.isDebug = false
                options.environment = "PRODUCTION"
                options.release = "$versionName - ($versionCode)"

                options.setDiagnosticLevel(SentryLevel.INFO)

                // enable "performance monitoring", lower pressure!
                options.tracesSampleRate = 0.2

                options.addIntegration(
                    FragmentLifecycleIntegration(
                        application,
                        enableFragmentLifecycleBreadcrumbs = true, // enabled by default
                        enableAutoFragmentLifecycleTracing = false  // disabled by default
                    )
                )
            }

            /*options.beforeSend =
                SentryOptions.BeforeSendCallback { event: SentryEvent, hint: Any? ->

                }*/
        }
    }

    fun captureException(e: Throwable, category: String? = null, message: String? = null) {
        e.printStackTrace()
        Log.e("TAG", e.stackTraceToString())

        Breadcrumb().apply {
            setCategory(category)
            setMessage(message)
        }

        Sentry.captureException(e)
    }

    fun captureExceptionFireBase(userId: String? = null, message: String? = null) {
        message?.let { Firebase.crashlytics.log(it) }
        userId?.let { Firebase.crashlytics.setUserId(it) }
        Firebase.crashlytics.setCustomKeys {
            key("my_string_key", "test")  // String value
            key("my_bool_key", true)      // boolean value
            key("my_double_key", 1.0)     // double value
            key("my_float_key", 1.0f)     // float value
            key("my_int_key", 1)          // int value
        }

    }
}