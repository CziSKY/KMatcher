class Matcher<R, O>(val value: O) {

    var result: R? = null

    var default: R? = null

    fun case(block: (O) -> Boolean, resultBlock: O.() -> R) {
        if (!block.invoke(value)) {
            return
        }
        if (result == null) result = resultBlock.invoke(value)
    }

    @JvmName("caseType")
    inline fun <reified T> case(block: (T) -> Boolean = { true }, noinline resultBlock: T.() -> R) {
        val casted = value as? T ?: return
        if (!block.invoke(casted)) {
            return
        }
        if (result == null) result = resultBlock.invoke(casted)
    }

    fun default(defaultResult: R) {
        default = defaultResult
    }

    fun match(): R {
        return result ?: default ?: error("Match error.")
    }
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