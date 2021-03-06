package com.mhtmalpani.superextensions.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import java.io.Serializable

/**
 * Create an intent of the Activity in demand from the invoked Context
 *
 * Usage:
 *      context.intentOf<LoginActivity>()
 *
 * We can pass custom parameters within the intent.
 *
 * Usage:
 *      context.intentOf<LoginActivity>(
 *          HEADER to "header"
 *      )
 */
inline fun <reified T : Activity> Context.intentOf(
    vararg params: Pair<String, Any?>
): Intent = Intent(this, T::class.java).apply { intentOf(*params) }


/**
 * Create an intent of the Activity in demand from the invoked Context
 *
 * Usage:
 *      context.intentOf<LoginActivity>()
 *
 * We can pass custom bundle within the intent.
 *
 * Usage:
 *      context.intentOf<LoginActivity>(
 *          Bundle()
 *      )
 */
inline fun <reified T : Activity> Context.intentOf(
    bundle: Bundle? = null
): Intent = Intent(this, T::class.java).apply { bundle?.let { putExtras(it) } }


/**
 * Gets the relevant intent extra parameter into a variable lazily
 *
 * Usage:
 *      private val username: String by intentExtra<String>(USERNAME)
 */
inline fun <reified T : Any> Activity.intentExtra(key: String): Lazy<T> =
    lazy { intent?.extras?.get(key) as T }


/**
 * `extra` would get the extra at runtime when accessing the data.
 *
 * Usage:
 *      private val id by extra<String>(EXTRA_ID_KEY, "some_default_value")
 *              -> will return String?
 */
inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}


/**
 * `extraNotNull` behaves like `extra`, but would crash at runtime if the value was null
 *
 * Usage:
 *      private val id by extraNotNull<String>(EXTRA_ID_KEY)
 *              -> will return String
 */
inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}


///////////////////////////////////////////////////////////////////////////////////////////////////
//                                      Bind Arguments
///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Binds a String Argument
 *
 * Usage:
 *      private val data by bindStringArgument(DATA)
 */
fun Activity.bindStringArgument(key: String): Lazy<String> = lazy {
    this.intent?.extras?.getString(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a Int Argument
 *
 * Usage:
 *      private val data by bindIntArgument(DATA)
 */
fun Activity.bindIntArgument(key: String): Lazy<Int> = lazy {
    this.intent?.extras?.getInt(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a String Argument
 *
 * Usage:
 *      private val data by bindStringArgument(DATA)
 */
fun Activity.bindLongArgument(key: String): Lazy<Long> = lazy {
    this.intent?.extras?.getLong(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Bind a Float Argument
 *
 * Usage:
 *      private val data by bindFloatArgument(DATA)
 */
fun Activity.bindFloatArgument(key: String): Lazy<Float> = lazy {
    this.intent?.extras?.getFloat(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a String Argument
 *
 * Usage:
 *      private val data by bindStringArgument(DATA)
 */
fun Activity.bindDoubleArgument(key: String): Lazy<Double> = lazy {
    this.intent?.extras?.getDouble(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a Boolean Argument
 *
 * Usage:
 *      private val data by bindBooleanArgument(DATA)
 */
fun Activity.bindBooleanArgument(key: String): Lazy<Boolean> = lazy {
    this.intent?.extras?.getBoolean(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a Char Argument
 *
 * Usage:
 *      private val data by bindCharArgument(DATA)
 */
fun Activity.bindCharArgument(key: String): Lazy<Char> = lazy {
    this.intent?.extras?.getChar(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a Parcelable Argument
 *
 * Usage:
 *      private val data by bindParcelableArgument(DATA)
 */
fun <T : Parcelable> Activity.bindParcelableArgument(key: String): Lazy<T> = lazy {
    this.intent?.extras?.getParcelable<T>(key)
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Binds a Serializable Argument
 *
 * Usage:
 *      private val data by bindSerializableArgument(DATA)
 */
fun <T : Serializable> Activity.bindSerializableArgument(key: String): Lazy<T> = lazy {
    this.intent?.extras?.getSerializable(key) as? T
        ?: throw IllegalArgumentException("Argument not passed for key: $key")
}


/**
 * Creates a fragment transaction smoothly
 *
 * Usage:
 *      fragmentTransaction {
 *          replace(R.id.fragment_container, YourFragment(), YourFragment.TAG)
 *      }
 */
fun AppCompatActivity.fragmentTransaction(body: FragmentTransaction.() -> Unit) {
    supportFragmentManager.beginTransaction().also {
        body(it)
    }.commit()
}