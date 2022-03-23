package com.arash.altafi.crashlytics

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.sentry.Sentry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sentry.setOnClickListener {
            sentry()
        }
    }

    private fun sentry() {
        Sentry.captureMessage("testing SDK setup")
        // Transaction can be started by providing, at minimum, the name and the operation
        val transaction = Sentry.startTransaction("test-transaction-name", "test-transaction-operation")
        // Transactions can have child spans (and those spans can have child spans as well)
        val span = transaction.startChild("test-child-operation")
        // (Perform the operation represented by the span/transaction)
        span.finish() // Mark the span as finished
        transaction.finish() // Mark the transaction as finished and send it to Sentry

        Toast.makeText(this , "crash message send success" , Toast.LENGTH_SHORT).show()
    }
}