import org.junit.jupiter.api.Test

object Test1 {

    interface Notification

    data class Email(val sender: String, val title: String, val body: String) : Notification

    data class SMS(val caller: String, val message: String) : Notification

    data class VoiceRecording(val contactName: String, val link: String) : Notification

    @Test
    fun test() {
        println(matcher(VoiceRecording("Joe", "voicerecording.org/id/123")) {
            caseType<Email> { "You got an email from $sender with title: $title" }
            caseType<SMS> { "You got an SMS from $caller! Message: $message" }
            caseType<VoiceRecording> { "You received a Voice Recording from $contactName! Click the link to hear it: $link" }
            default("Match error.")
        }.match())
    }
}