# KMatcher

Recently I'm studying [CS61A](https://github.com/CziSKY/CS61A), And I got a new intuitive understanding of Higher Order Function from it. So I coded this kindergarten Pattern Matching implementation in Kotlin.

```kotlin
interface Notification

data class Email(val sender: String, val title: String, val body: String) : Notification

data class SMS(val caller: String, val message: String) : Notification

data class VoiceRecording(val contactName: String, val link: String) : Notification

fun print() {
    println(matcher(VoiceRecording("Joe", "voicerecording.org/id/123")) {
        case<Email> { "You got an email from $sender with title: $title" }
        case<SMS> { "You got an SMS from $caller! Message: $message" }
        case<VoiceRecording> { "You received a Voice Recording from $contactName! Click the link to hear it: $link" }
        default("Match error.")
    }.match())
    // "You received a Voice Recording from Joe! Click the link to hear it: voicerecording.org/id/123"
}
```

And the java implementation (JMatcher.java):

```java
public void print() {
    int num = 20;
    var result = JMatcherUtil.matcher(num, m -> {
        m.casePredicate(i -> i > 0 && i <= 10, i -> "Small");
        m.caseType(Integer.class, i -> i > 10 && i <= 100, i -> "Medium");
        m.caseType(Double.class, d -> d > 100, d -> "Large");
        m.caseDefault("Unknown");
    }).match(String.class);
    // "Medium"
}
```