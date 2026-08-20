package Entidades.Vector;

import Entidades.Estrutura_Linear;

public class Vector extends Estrutura_Linear {

    private  final Integer dim;
    private boolean transposto=false;

    public Vector(Double[] elements, Integer dim) {
        super(elements); // Passa o array para a classe pai
        if (elements.length != dim) {
            throw new IntegridadeVetorException("Número de elementos incorreto.");
        }
        this.dim = dim;
    }

    public Double[] getElements() {
        return elements;
    }

    public Integer getDim() {
        return dim;
    }

    public Double get(Integer linha){
        return elements[linha-1];
    }
    public  void set(Integer linha , Double valor){
        elements[linha]=valor;
    }

    public boolean isTransposto() {
        return transposto;
    }

    public void setTransposto(boolean transposto) {
        this.transposto = transposto;
    }

    public void vectorToString(){
        if(isTransposto()){
            for (int i = 0; i < this.getDim(); i++) {
                if(i==0){
                    System.out.print("[ "+ elements[i]);
                }else if(i==this.getDim()-1){
                    System.out.print(elements[i]+" ]");
                }else
                    System.out.print(" , "+elements[i]+" , ");
            }
        }else{
            for (int i = 0; i < this.getDim(); i++) {
                System.out.println("[ "+ elements[i] +"  ]");
            }
        }
    }
}
