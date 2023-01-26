class Matcher<R, O>(val value: O) {

    val branches = mutableListOf<MatcherBranch<R, *>>()

    fun case(block: (O) -> Boolean, result: O.() -> R) {
        if (!block.invoke(value)) {
            return
        }
        branches += MatcherBranch(value = value, result = result)
    }

    @JvmName("caseType")
    inline fun <reified T> case(block: (T) -> Boolean = { true }, noinline result: T.() -> R) {
        (value as? T)?.apply {
            if (!block.invoke(this)) {
                return
            }
            branches += MatcherBranch(value = this, result = result)
        }
    }

    fun default(result: R) {
        branches += MatcherBranch(value = value, result = { result }, isDefault = true)
    }

    fun match(): R {
        return branches
            .filter { !it.isDefault }
            .firstNotNullOfOrNull { it.get() } ?: branches.find { it.isDefault }?.get() ?: error("Match error.")
    }
}

class MatcherBranch<R, O>(val value: O, val result: O.() -> R, val isDefault: Boolean = false) {

    fun get() = result.invoke(value)
}

fun <R, O> matcher(value: O, func: Matcher<R, O>.() -> Unit): Matcher<R, O> {
    return Matcher<R, O>(value).apply {
        func.invoke(this)
    }
}

@JvmName("extensionMatcher")
fun <R, O> O.matcher(func: Matcher<R, O>.() -> Unit): Matcher<R, O> {
    return Matcher<R, O>(this).also {
        func.invoke(it)
    }
}