import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

public class JMatcher<I, R> {

    @NotNull
    private final I input;

    @Nullable
    private R result;
    @Nullable
    private R defaultResult;

    public JMatcher(@NotNull I input) {
        this.input = input;
    }

    public void casePredicate(@NotNull Predicate<I> block, @NotNull Function<I, R> result) {
        if (checkResult() || !block.test(input)) {
            return;
        }
        setResult(result.apply(input));
    }

    public <T> void caseType(@NotNull Class<T> clazz, @NotNull Predicate<T> block, @NotNull Function<T, R> result) {
        if (checkResult()) {
            return;
        }
        T castedValue = clazz.cast(input);
        if (!block.test(castedValue)) {
            return;
        }
        setResult(result.apply(castedValue));
    }

    public void caseDefault(@NotNull R result) {
        if (checkResult()) {
            return;
        }
        this.defaultResult = result;
    }

    @Nullable
    public <T> T match(@NotNull Class<T> clazz) {
        if (clazz.isInstance(result)) {
            return clazz.cast(result);
        }
        if (clazz.isInstance(defaultResult)) {
            return clazz.cast(defaultResult);
        }
        throw new RuntimeException("Match error.");
    }

    @NotNull
    public R match() {
        if (result != null) {
            return result;
        }
        if (defaultResult != null) {
            return defaultResult;
        }
        throw new RuntimeException("Match error.");
    }

    private boolean checkResult() {
        return result != null;
    }

    private void setResult(@NotNull R result) {
        this.result = result;
    }
}