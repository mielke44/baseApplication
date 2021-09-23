package br.com.wilson.mielke.baseapplication.utils

import android.text.TextUtils
import java.util.regex.Pattern

class StringUtils {
    companion object {
        private val REGULAR_EXPRESSION_EMAIL = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")


        fun isValidEmail(target: CharSequence): Boolean {
            return !TextUtils.isEmpty(target) && REGULAR_EXPRESSION_EMAIL.matcher(target).matches()
        }
    }
}