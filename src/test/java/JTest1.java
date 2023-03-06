public class JTest1 {

    public static void main(String[] args) {
        JMatcher<Integer, String> test = JMatcherUtil.create(20, m -> {
            m.casePredicate(i -> i > 0 && i <= 10, i -> "Small");
            m.caseType(Integer.class, i -> i > 10 && i <= 100, i -> "Medium");
            m.caseType(Double.class, d -> d > 100, d -> "Large");
            m.caseDefault("Unknown");
        });
        System.out.println(test.match());
    }
}
