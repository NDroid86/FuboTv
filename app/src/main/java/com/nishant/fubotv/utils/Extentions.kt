package com.nishant.fubotv.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nishant Rajput on 15/09/22.
 *
 */
fun Date.formatToViewDateTimeDefaults(timeZone: TimeZone = TimeZone.getDefault()): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mma", Locale.getDefault())
    sdf.timeZone = timeZone
    return sdf.format(this)
}

fun Date.formatToViewDateDefaults(timeZone: TimeZone = TimeZone.getDefault()): String {
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
    sdf.timeZone = timeZone
    return sdf.format(this)
}

fun Date.formatToViewTimeDefaults(timeZone: TimeZone = TimeZone.getDefault()): String {
    val sdf = SimpleDateFormat("HH:mma", Locale.getDefault())
    sdf.timeZone = timeZone
    return sdf.format(this)
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()