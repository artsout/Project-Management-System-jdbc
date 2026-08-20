package Services;

import Entidades.Estrutura_Linear;
import Entidades.Matrix.Matrix;
import Entidades.Vector.Vector;

public class LinearAlgebra {



    public Estrutura_Linear transpose(Estrutura_Linear estruturaLinear) {
       if(estruturaLinear==null){
           throw new IllegalArgumentException("Estrutura n pode ser nula ou vetor ou matriz");
       }

        if (estruturaLinear.getClass() == Matrix.class) {
            Matrix original = (Matrix) estruturaLinear;

            //Precisa cirar outro elements pq no Construtor da matriz ele ja recebe um elements normal e se vc pegar o original as linhas e colunas n se invertem
            Double[] elementosTranspostos = new Double[original.getRows() * original.getCols()];//mantendo o padrao col*row=elementos.length

            int k = 0;//adiciona da mesma forma do construtor de matriz
            for (int j = 0; j < original.getCols(); j++) {//inverte o for dessa forma invertendo linha e coluna//se usa o k pq se fosse o i esperaria terminar a coluna(se passando j ciclos sem mudar o elemento)
                for (int i = 0; i < original.getRows(); i++) {
                    elementosTranspostos[k++] = original.get(i+1, j+1);//se fosse o j ele ficaria em um ciclo de 0 ate cols
                }
            }
                return new Matrix(original.getCols(), original.getRows(), elementosTranspostos);

        }else if (estruturaLinear.getClass() == Vector.class) {
            Vector vector = new Vector(estruturaLinear.getElements(), ((Vector) estruturaLinear).getDim());
            vector.setTransposto(true);

            return vector;
        }
            return null;
    }



    public  Estrutura_Linear sum(Estrutura_Linear estruturaLinear1,Estrutura_Linear estruturaLinear2){
        if(verificadorDeEstruturasIguaisAMatrix(estruturaLinear1,estruturaLinear2)){
            if(!verificadorDeDimensoes(estruturaLinear1,estruturaLinear2)){
                throw new OperationsException("Soma com dimenssoes diferentes n e permitido");
            }
            Matrix matrix=(Matrix) estruturaLinear1;
            Matrix matrix2 =(Matrix) estruturaLinear2;

            Double[] elementosSum = new Double[matrix.getCols()* matrix.getRows()];
            int k=0;
                for (int i = 0; i < matrix.getRows(); i++) {
                    for (int j = 0; j < matrix.getCols(); j++) {
                      elementosSum[k++] = matrix.get(i+1,j+1)+matrix2.get(i+1,j+1);
                    }
                }
            return new Matrix(matrix.getRows(),matrix.getCols(),elementosSum);

        }else{
            if(!verificadorDeDimensoes(estruturaLinear1,estruturaLinear2)){
                throw new OperationsException("Soma com dimenssoes diferentes n e permitido");
            }
            Vector vector = (Vector) estruturaLinear1;
            Vector vector2 = (Vector) estruturaLinear2;
            Double[] elementosSumVector =new Double[vector.getDim()];
            int s =0;
            for (int i = 0; i < vector2.getDim(); i++) {
              elementosSumVector[s++] =  vector.get(i+1)+vector2.get(i+1);
            }

            return  new Vector(elementosSumVector,vector.getDim());
        }
    }


    public Estrutura_Linear times(Estrutura_Linear estruturaLinear1 , Estrutura_Linear estruturaLinear2){
        if(verificadorDeEstruturasIguaisAMatrix(estruturaLinear1,estruturaLinear2)){
            if(!verificadorDeDimensoes(estruturaLinear1,estruturaLinear2)){
                throw new OperationsException("Times com dimenssoes diferentes n e permitido");
            }
            Matrix matrix=(Matrix) estruturaLinear1;
            Matrix matrix2 =(Matrix) estruturaLinear2;
            Double[] elementosTimesMatriz = new  Double[matrix.getCols()* matrix.getRows()];
            int k=0;
            for (int i = 0; i < matrix.getRows(); i++) {
                for (int j = 0; j < matrix.getCols(); j++) {
                   elementosTimesMatriz[k++] = matrix.get(i+1,j+1) * matrix2.get(i+1,j+1);
                }
            }
            return new Matrix(matrix.getRows(),matrix.getCols(),elementosTimesMatriz);
        }else{
            if(!verificadorDeDimensoes(estruturaLinear1,estruturaLinear2)){
                throw new OperationsException("Times com dimenssoes diferentes n e permitido");
            }
            Vector vector = (Vector) estruturaLinear1;
            Vector vector2 = (Vector) estruturaLinear2;
            Double[] elementosTimesVector=new Double[vector.getDim()];
            int s =0;
            for (int i = 0; i < vector.getDim(); i++) {
              elementosTimesVector[s++] = vector.get(i+1)*vector2.get(i+1);
            }
            return new Vector(elementosTimesVector,vector.getDim());
        }
    }

    public Matrix times(Double escalar, Matrix b) {
        Double[] resultado =new Double[b.getRows()*b.getCols()];
        int k=0;
        for (int i = 0; i < b.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
            resultado[k++] = b.get(i+1,j+1)*escalar;
            }
        }
        return new Matrix(b.getRows(),b.getCols(),resultado);
    }


    public Vector times(Double escalar, Vector b) {
        Double[] resultado = new Double[b.getDim()];
        int s = 0;
        for (int i = 0; i < b.getDim(); i++) {
           resultado[s++] = escalar * b.get(i + 1);
        }
        return new Vector(resultado, b.getDim());
    }

    public Matrix dot(Matrix a ,Matrix b){
      if(!a.getCols().equals(b.getRows())){
          throw new OperationsException("Essa multiplicaçao n e valida, as colunas de a devem ser iguais as linhas de b");
      }
      Double[] elementosResultantes =new Double[a.getRows()*b.getCols()];
        int k=0;
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j <b.getCols() ; j++) {
                elementosResultantes[k++]= dotMatrixHelper(i,j,a,b);
            }
        }
        return  new Matrix(a.getRows(),b.getCols(),elementosResultantes);
    }


    public Matrix gauss(Matrix matrix){
        Double[] elementosGauss = new Double[matrix.getRows()*matrix.getCols()];


        return  new Matrix(matrix.getRows(),matrix.getRows(),elementosGauss);
    }



    public Boolean verificadorDeEstruturasIguaisAMatrix(Estrutura_Linear estruturaLinear1,Estrutura_Linear estruturaLinear2){

        if((estruturaLinear1.getClass() == Matrix.class &&  estruturaLinear2.getClass()== Matrix.class)){
            return true;
        }else if(estruturaLinear1.getClass()== Vector.class && estruturaLinear2.getClass()== Vector.class){
            return false;
        }else{
            throw new OperationsException("Operaçoes de mesmos tipos de estruturas apenas");
        }
    }




    public  Boolean verificadorDeDimensoes(Estrutura_Linear estruturaLinear1,Estrutura_Linear estruturaLinear2){
        if(verificadorDeEstruturasIguaisAMatrix(estruturaLinear1,estruturaLinear2)){
           Matrix matrix1=(Matrix) estruturaLinear1;
           Matrix matrix2=(Matrix) estruturaLinear2;

           return matrix1.getRows().equals(matrix2.getRows()) && matrix1.getCols().equals(matrix2.getCols());
        }else {
            Vector vector = (Vector) estruturaLinear1;
            Vector vector2 = (Vector) estruturaLinear2;

            return vector.getDim().equals(vector2.getDim());
        }
    }


    public  Double dotMatrixHelper(Integer linha,Integer coluna,Matrix matrix,Matrix matrix2){
        Double resultado=0.0;
        for (int i = 0; i < matrix2.getRows(); i++) {
               resultado+= matrix.get(linha+1,i+1)*matrix2.get(i+1,coluna+1);
        }
        return resultado;
    }

    public Matrix gaussMatrixHelper(Matrix matrix){
        boolean acesso=true;
        Double[] resultado =new Double[matrix.getRows()*matrix.getCols()];
       while(acesso){

           for (int j = 0; j < matrix.getCols(); j++) {
               if(matrix.get(1,j)==0){
                   gaussMetodoTrocaLinhas(matrix.getRows(),1,matrix);
               }
                Double val =
               if (matrix.get(matrix.getRows(), j) + matrix.get(1, j) == 0||matrix.get(matrix.getRows(), j)+(matrix.get(1, j)*val)==0) {

                   gaussMetodoSoma(1, matrix.getRows(),matrix, );
               }

           }
       }
        return new Matrix(matrix.getRows(), matrix.getCols(),resultado);
    }
    private void gaussMetodoTrocaLinhas(Integer linhaTrocada ,Integer linhaQueVaiTrocar,Matrix matrix){
        for (int j = 0; j < matrix.getCols(); j++) {
            Double valorOriginalQueVaiTrocar = matrix.get(linhaQueVaiTrocar, j);
            Double valorOriginalTrocada = matrix.get(linhaTrocada, j);

            matrix.set(linhaTrocada, j, valorOriginalQueVaiTrocar);
            matrix.set(linhaQueVaiTrocar, j, valorOriginalTrocada);
        }
    }
    private void gaussMetodoMultiplicaçao(Integer linha,Double val,Matrix matrix){
        if(val==0){
            throw new OperationsException("No metodo de gauss, na operaçao de multiplicaçao vc n pode multiplicar uma linha por zero");
        }
        double aux=0.0;

        for (int j = 0; j < matrix.getCols(); j++) {
            aux = matrix.get(linha,j)*val;
            matrix.set(linha,j,aux);
        }
    }

    private   void gaussMetodoSoma(Integer linhaSomada,Integer linhaQueSoma,Matrix matrix,Double fator){
        for (int j = 0; j < matrix.getCols(); j++) {
            Double valorAtual = matrix.get(linhaSomada, j);
            Double valorAdicionar = matrix.get(linhaQueSoma, j) * fator; // Multiplica antes de somar
            Double resultado=valorAtual+valorAdicionar;
            matrix.set(linhaSomada, j, resultado);
        }
    }
}

