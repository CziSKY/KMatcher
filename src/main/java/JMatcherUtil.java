import java.util.function.Function;

public class JMatcherUtil {

    public static <I, R> JMatcher<I, R> matcher(I input, Function<JMatcher<I, R>, JMatcher<I, R>> func) {
        JMatcher<I, R> matcher = new JMatcher<>(input);
        return func.apply(matcher);
    }
}