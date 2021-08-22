package br.com.zupacademy.guilherme.mercadolivre.emailsending;

public enum EmailSubject {
    QUESTION("Someone has made a question about your product"),
    OPINION("Someone has posted an opinion about your product"),
    CHECKOUT("Someone bought one of your product"),
    SUCCESSFUL_TRANSACTION("The transaction was successful for the product"),
    FAILED_TRANSACTION("The transaction failed for the product");

    private final String label;

    private EmailSubject(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
