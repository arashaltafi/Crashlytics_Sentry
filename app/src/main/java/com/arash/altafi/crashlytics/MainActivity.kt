package com.arash.altafi.crashlytics

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://sentry.io/
 * https://console.firebase.google.com/
 * sentry and firebase is not work in iran! (use vpn)
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sentry.setOnClickListener {
            sentry()
        }

        btn_firebase.setOnClickListener {
            firebase()
        }
    }

    private fun sentry() {
        try {
            throw RuntimeException("This is a crash in sentry!")
        } catch (e: Exception) {
            Toast.makeText(this, "crash message send success", Toast.LENGTH_SHORT).show()
            CrashlyticsUtils.captureException(
                e,
                category = this::class.simpleName,
                message = e.message
            )
        }
    }

    private fun firebase() {
        CrashlyticsUtils.captureExceptionFireBase("userId_test", "message_test")
        throw RuntimeException("This is a crash in firebase!")
    }
}