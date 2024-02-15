package com.wb.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

sealed interface UIString {

    @Composable
    fun buildString(): kotlin.String

    data class String(val string: kotlin.String) : UIString {
        @Composable
        override fun buildString() = string
    }

    data class Resource(
        val messageRes: Int,
        val args: List<Any>? = null,
        val quantity: Int? = null,
        val longDuration: Boolean = false
    ) : UIString {

        @Composable
        override fun buildString(): kotlin.String {
            return when {
                quantity != null && args != null -> {
                    pluralStringResource(
                        id = messageRes,
                        count = quantity,
                        formatArgs = args.toTypedArray()
                    )
                }

                quantity != null -> {
                    pluralStringResource(id = messageRes, count = quantity)
                }

                else -> {
                    args
                        ?.map {
                            if (it is Int) {
                                stringResource(id = it)
                            } else it
                        }
                        ?.let { args ->
                            stringResource(id = messageRes, formatArgs = args.toTypedArray())
                        } ?: stringResource(id = messageRes)
                }
            }
        }
    }
}

fun CharSequence.toUIString(): UIString = UIString.String(this.toString())