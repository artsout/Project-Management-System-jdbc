package Entidades;

public class Estrutura_Linear {
    protected final Double[] elements;

    public Estrutura_Linear(Double[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Os elementos não podem ser nulos.");
        }
        this.elements = elements;
    }

    public Double[] getElements() {
        return elements;
    }
}
