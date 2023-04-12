package com.arash.altafi.crashlytics

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://sentry.io/
 * sentry is not work without vpn in iran!
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sentry.setOnClickListener {
            sentry()
        }
    }

    private fun sentry() {
        try {
            throw RuntimeException("This is a crash!")
        } catch (e: Exception) {
            Toast.makeText(this, "crash message send success", Toast.LENGTH_SHORT).show()
            CrashlyticsUtils.captureException(
                e,
                category = this::class.simpleName,
                message = e.message
            )
        }
    }
}