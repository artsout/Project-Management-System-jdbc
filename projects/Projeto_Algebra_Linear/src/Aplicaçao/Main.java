package Aplicaçao;

import Entidades.Matrix.Matrix;
import Entidades.Vector.Vector;
import Services.LinearAlgebra;

public  class Main {
    static void main(String[] args) {
        LinearAlgebra ln = new LinearAlgebra();
        System.out.println("--- TESTANDO O VETOR ---");
        Double[] dadosVetor = {10.0, 20.0, 30.0};
        Vector v1 = new Vector(dadosVetor, 3);

        System.out.println("Vetor Original:");
        v1.vectorToString();

        Vector vTransposto = (Vector) ln.transpose(v1);
        System.out.println("\nVetor Transposto:");
        vTransposto.vectorToString();


        System.out.println("\n--- TESTANDO A MATRIZ (2x3) ---");
        // Array simples com 6 elementos que vão virar uma matriz de 2 linhas e 3 colunas
        Double[] dadosMatriz = {
                1.0, 2.0, 3.0,  // Linha 1
                4.0, 5.0, 6.0   // Linha 2
        };
        Matrix m1 = new Matrix(2, 3, dadosMatriz);

        System.out.println("Matriz Original (2x3):");
        m1.matrizToString();

        // Executa a transposição (deve virar uma matriz 3x2)
        Matrix mTransposta = (Matrix) ln.transpose(m1);

        System.out.println("\nMatriz Transposta (3x2):");
        mTransposta.matrizToString();

        // Testando o seu método get direto em uma posição
        System.out.println("\nElemento na Linha 3, Coluna 1 da transposta: " + mTransposta.get(3, 1));


        System.out.println("--- TESTANDO A MULTIPLICAÇÃO DE MATRIZES ---");


        Double[] dadosA = {
                1.0, 2.0, 3.0,  // Linha 1
                4.0, 5.0, 6.0,
                3.0, 6.0 ,9.0 // Linha 2
        };
        Matrix mA = new Matrix(3, 3, dadosA);


        Double[] dadosB = {
                7.0, 8.0,
                9.0, 1.0,
                2.0, 3.0
        };
        Matrix mB = new Matrix(3, 2, dadosB);

        System.out.println("Matriz A "+"("+ mA.getRows()+" X "+mA.getCols()+")"+" : ");
        mA.matrizToString();

        System.out.println("\nMatriz B "+"("+ mB.getRows()+ " X " + mB.getCols() +")"+" : ");
        mB.matrizToString();


        Matrix mResultado = ln.dot(mA, mB);

        System.out.println("\nResultado da Multiplicação mA . mB (2x2):");
        mResultado.matrizToString();

       }
}
