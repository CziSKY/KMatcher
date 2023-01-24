interface Notification {
}

record Email(String sender, String title, String body) implements Notification {
}

record SMS(String caller, String message) implements Notification {
}

record VoiceRecording(String contactName, String link) implements Notification {
}

public class Main {
}
