package com.ambiws.testassignment.core.providers

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import java.io.InputStream

interface ResourceProvider {

    fun resources(): Resources

    fun getString(@StringRes res: Int, vararg args: Any): String

    fun getHtml(@StringRes res: Int, vararg args: Any): CharSequence

    fun getStringArray(@ArrayRes res: Int): Array<out String>

    fun getColor(@ColorRes color: Int): Int

    fun getDrawable(@DrawableRes icon: Int): Drawable?

    fun getRaw(@RawRes raw: Int): InputStream

    fun getRawString(@RawRes raw: Int): String

    fun getSP(value: Float): Float

    fun getDP(value: Float): Float

    fun getTypedValue(value: Float, unit: Int): Float

    fun getDrawableId(resName: String, packageName: String): Int

    fun getJsonDataFromAsset(fileName: String): String?
}
