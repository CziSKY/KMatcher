@Suppress("UNCHECKED_CAST")
class Matcher<R, O>(val value: O) {

    val branches = mutableListOf<MatcherBranch<*, *>>()

    fun case(block: (O) -> Boolean, result: O.() -> R) {
        branches += MatcherBranch(value = value, bool = block.invoke(value), result = result)
    }

    inline fun <reified T> case(block: (T) -> Boolean = { true }, noinline result: T.() -> R) {
        (value as? T)?.apply {
            branches += MatcherBranch(value = this, bool = block.invoke(this), result = result)
        }
    }

    fun default(result: R) {
        branches += MatcherBranch(value = value, bool = true, result = { result })
    }

    fun match(): R {
        return branches.firstNotNullOfOrNull { it.get() } as R? ?: error("Match error.")
    }
}

class MatcherBranch<R, O>(val value: O, val bool: Boolean, val result: (O) -> R) {

    fun get(): R? {
        return when {
            bool -> result.invoke(value)
            else -> null
        }
    }
}

fun <R, O> matcher(value: O, func: Matcher<R, O>.() -> Unit): Matcher<R, O> {
    return Matcher<R, O>(value).apply {
        func.invoke(this)
    }
}