import java.util.function.Function;
import java.util.function.Predicate;

public class JMatcher<I, R> {

    private final I input;

    private R result;
    private R defaultResult;

    public JMatcher(I input) {
        this.input = input;
    }

    public void casePredicate(Predicate<I> block, Function<I, R> result) {
        if (checkResult() || !block.test(input)) {
            return;
        }
        setResult(result.apply(input));
    }

    public <T> void caseType(Class<T> clazz, Predicate<T> block, Function<T, R> result) {
        if (checkResult()) {
            return;
        }
        T castedValue = clazz.cast(input);
        if (!block.test(castedValue)) {
            return;
        }
        setResult(result.apply(castedValue));
    }

    public void caseDefault(R result) {
        if (checkResult()) {
            return;
        }
        this.defaultResult = result;
    }

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

    private void setResult(R result) {
        this.result = result;
    }
}