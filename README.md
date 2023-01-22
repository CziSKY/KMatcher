# KMatcher

Recently I'm studying [CS61A](https://github.com/CziSKY/CS61A), And I got a new intuitive understanding of Higher Order Function from it. So I coded this kindergarten Pattern Matching implementation in Kotlin.

```kotlin
    fun print() {
        println(matcher(VoiceRecording("Joe", "voicerecording.org/id/123")) {
            case<Email> { "You got an email from $sender with title: $title" }
            case<SMS> { "You got an SMS from $caller! Message: $message" }
            case<VoiceRecording> { "You received a Voice Recording from $contactName! Click the link to hear it: $link" }
            default("Match error.")
        }.match())
    }
```