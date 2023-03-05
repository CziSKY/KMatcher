class Matcher<R, O>(val value: O) {

    var result: R? = null

    private var default: R? = null

    fun case(block: (O) -> Boolean, result: O.() -> R) {
        if (!block.invoke(value)) {
            return
        }
        if (this.result == null) this.result = result.invoke(value)
    }

    @JvmName("caseType")
    inline fun <reified T> case(block: (T) -> Boolean = { true }, noinline result: T.() -> R) {
        (value as? T)?.apply {
            if (!block.invoke(this)) {
                return
            }
            if (this@Matcher.result == null) this@Matcher.result = result.invoke(this)
        }
    }

    fun default(result: R) {
        this.default = result
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