package com.android.own.instag.utils.display

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.own.instag.R

object Toaster {
    fun show(context: Context, text: String) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN
        )
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        toast.show()
    }
}