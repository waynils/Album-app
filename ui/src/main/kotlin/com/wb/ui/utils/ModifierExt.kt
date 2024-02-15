package com.wb.ui.utils

import androidx.compose.ui.Modifier

/**
 * Allows to apply optionally some modification on a [Modifier], if a given argument is not null, or
 * else apply an other modification (optional).
 * It allows to implement conditional modification of a `Modifier` without breaking the build chain.
 * Arguments:
 * - [optional]: the argument needed for the modifier, nullable. If not null, will run the [apply]
 * block with this argument. If null, applies the [orElse] block (if not null).
 * - [apply] the block that will be run if [optional] is not null, with [optional] as argument.
 * - [orElse] the block that will be run if [optional] is null, if not null.
 */
inline fun <C : Any> Modifier.applyIfNotNull(
    optional: C?,
    noinline orElse: (Modifier.() -> Modifier)? = null,
    apply: Modifier.(C) -> Modifier,
): Modifier = optional?.let { apply(it) } ?: orElse?.invoke(this) ?: this

