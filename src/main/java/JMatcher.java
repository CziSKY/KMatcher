import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class JMatcher<R, O> {

    @NotNull
    O value;

    @NotNull
    ArrayList<JMatcherBranch<R, ?>> branches = new ArrayList<>();

    JMatcher<R, O> add(Predicate<O> block, Function<O, R> result) {
        branches.add(new JMatcherBranch<>(value, block.test(value), result));
        return this;
    }

    @SuppressWarnings("unchecked")
    <T> JMatcher<R, O> addType(Predicate<T> block, Function<T, R> result) {
        try {
            T cast = (T) value;
            branches.add(new JMatcherBranch<>(cast, block.test(cast), result));
        } catch (Exception ignored) {
        }
        return this;
    }

    JMatcher<R, O> other(R result) {
        branches.add(new JMatcherBranch<>(value, true, o -> result));
        return this;
    }

    Optional<R> match() {
        return branches.stream()
                .map(JMatcherBranch::get)
                .filter(Objects::nonNull)
                .findAny();
    }
}

@AllArgsConstructor
class JMatcherBranch<R, O> {

    O value;
    boolean bool;
    Function<O, R> result;

    @Nullable
    public R get() {
        if (bool) {
            return result.apply(value);
        } else {
            return null;
        }
    }
}