package Entidades.Matrix;


import Entidades.Estrutura_Linear;

public class Matrix  extends Estrutura_Linear {
    private final Integer rows;
    private final Integer cols;
    private final Double[] elements;
    private final Double[][] Matriz;

    public Matrix(Integer rows,Integer cols,Double [] elements) {
        super(elements);

        if(rows * cols != elements.length){
            throw new IntegridadeMatrizException("O numero de elementos deve ser condizente ao de colunas vezes o numero de  linhas");
        }
        if(rows < 1 || cols < 1){
            throw new IntegridadeMatrizException("Uma Matiz deve ter no minimo row=1 e coluna=1");
        }
        this.elements=elements;
        this.rows = rows;
        this.cols = cols;
        this.Matriz=new Double[rows][cols];

        int k = 0;//se usa o k pq se fosse o i esperaria terminar a coluna(se passando j ciclos sem mudar o elemento)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {//se fosse o j ele ficaria em um ciclo de 0 ate cols
                Matriz[i][j]= elements[k];
                k++;
            }
        }
    }


    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }

    public Double[] getElements() {
        return elements;
    }

    public Double[][] getMatriz() {
        return Matriz;
    }
    public Double get(Integer linha , Integer coluna){
        if(linha>getRows() || coluna>getCols()){
            throw  new IntegridadeMatrizException("Vc passou dos limites da matriz");
        }
        return Matriz[linha-1][coluna-1];
    }
    public void set(Integer linha , Integer coluna,Double valor){
        Matriz[linha-1][coluna-1]=valor;
    }
    public void matrizToString(){
        for (int i = 0; i < this.getRows() ; i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if(j==0){
                    System.out.print("[ "+ Matriz[i][j] + " ");
                }else if(j==this.getCols()-1){
                    System.out.print(" " + Matriz[i][j]+" ]");
                }else{
                    System.out.print(" , " + Matriz[i][j] + " , ");
                }
            }
            System.out.println(" ");
        }
    }
}
