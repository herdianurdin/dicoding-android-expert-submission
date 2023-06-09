package com.samiode.core.utils

import com.samiode.core.BuildConfig.API_IMG_ENDPOINT

object StringUtils {
    fun getPosterImageUrl(path: String?): String =
        "$API_IMG_ENDPOINT/w200$path"

    fun getBackdropImageUrl(path: String?): String =
        "$API_IMG_ENDPOINT/w500$path"

    fun getProfileImageUrl(path: String?): String =
        "$API_IMG_ENDPOINT/w92$path"
}