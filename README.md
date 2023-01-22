# KMatcher

Kindergarten Pattern Matching implementation in Kotlin for self-learning

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