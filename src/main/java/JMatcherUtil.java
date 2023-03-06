import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class JMatcherUtil {

    @NotNull
    public static <I, R> JMatcher<I, R> create(@NotNull I input, @NotNull Consumer<JMatcher<I, R>> consumer) {
        JMatcher<I, R> matcher = new JMatcher<>(input);
        consumer.accept(matcher);
        return matcher;
    }
}