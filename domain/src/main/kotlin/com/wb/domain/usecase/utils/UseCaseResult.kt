package com.wb.domain.usecase.utils

/**
 * Result similar to kotlin [Result] with the following differences:
 * - visible child classes to enable smart cast
 * - failure is a sealed class and encapsulate a throwable instead of extending it
 *
 * @param V the type of data held in [UseCaseResult.Success].
 * @param D the type of data help in [UseCaseResult.Failure.WithData].
 */
sealed class UseCaseResult<out V : Any, out D : Any> {
    data class Success<out V : Any, out D : Any>(val value: V) : UseCaseResult<V, D>() {
        override fun toString() = "Success { $value }"
    }

    sealed class Failure<out V : Any, out D : Any> : UseCaseResult<V, D>() {
        abstract val cause: Throwable

        data class Default<out V : Any, out D : Any>(
            override val cause: Throwable
        ) : Failure<V, D>()

        data class WithData<out V : Any, out D : Any>(
            val data: D, override val cause: Throwable
        ) : Failure<V, D>()
    }

    companion object {
        fun <V : Any, D : Any> failureWithData(data: D, cause: Throwable): UseCaseResult<V, D> =
            Failure.WithData(data, cause)

        fun <V : Any, D : Any> defaultFailure(cause: Throwable): UseCaseResult<V, D> =
            Failure.Default(cause)

        fun <V : Any, D : Any> success(value: V): UseCaseResult<V, D> = Success(value)
    }
}
