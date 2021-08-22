package br.com.zupacademy.guilherme.mercadolivre.emailsending;

public class EmailSendingFake {

    private String sender = "noreply@mercadolivre.com.br";
    private String receiver;
    private String subject;
    private String body;

    public EmailSendingFake(String receiver, String emailSubjectLabel, String body) {
        this.receiver = receiver;
        this.subject = emailSubjectLabel;
        this.body = body;
    }

    public static class EmailBuilder {

        private final String receiver;
        private String subject;
        private String body;

        public EmailBuilder(String receiver) {
            this.receiver = receiver;
        }

        public EmailBuilder setEmailSubjectLabel(String emailSubjectLabel) {
            this.subject = emailSubjectLabel;
            return this;
        }

        public EmailBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public EmailSendingFake build(){
            return new EmailSendingFake(receiver, subject, body);
        }
    }


    public void sendEmail() {
        System.out.println("[Sender: " + this.sender + "]");
        System.out.println("[Receiver: " + this.receiver + "]");
        System.out.println("[Subject: " + this.subject + "]");
        System.out.println("[Body: " + this.body + "]");
    }
}
