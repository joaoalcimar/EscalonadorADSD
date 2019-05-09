package escalonador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Escalonador extends Thread{

	private int tempoAtual;
	private int tempoTotal;
	private static int numElemFila1;
	private static int numElemFila2;
	private int indiceElemento;
	private boolean escalonadorVago;
	private static int indice1 = 0;
    private static int indice2 = 0;
    private static int indice3 = 0;
	
	//private static Random selecionaNumero = new Random();
	private static GeradorNumerosAleatorios geradorNumerosAleatorios = new GeradorNumerosAleatorios();
	private static List<Integer> sequenciaAleatoria1;
	private static List<Integer> sequenciaAleatoria2;
	private static List<Integer> sequenciaAleatoria3;

	public Escalonador(int tempoTotal) {
		this.tempoTotal = tempoTotal;
		this.escalonadorVago = true;
		this.indiceElemento = 1;
		this.tempoAtual = 0;
		Escalonador.numElemFila1 = 0;
		Escalonador.numElemFila2 = 0;

		/*
		 * Geração de Numeros Aleatorios (Parâmetros):		
		 * geraValores(semente, k, c, mod, MetodoGeracao)
		 */
    	sequenciaAleatoria1 = geradorNumerosAleatorios.geraValores(12, 7, 0, 11, MetodoGeracao.ADITIVO);
    	sequenciaAleatoria2 = geradorNumerosAleatorios.geraValores(4, 1, 1, 4, MetodoGeracao.MISTO);
    	sequenciaAleatoria3 = geradorNumerosAleatorios.geraValores(5, 1, 2, 5, MetodoGeracao.MISTO);
    	//sequenciaAleatoria3 = geradorNumerosAleatorios.geraValores(6, 1, 2, 5, MetodoGeracao.MISTO);
	}
	
    private static Runnable fila1 = new Runnable() {
        public void run() {
            try{
            	//sleep(1 + gerador.nextInt(12));
            	//sleep(sequenciaAleatoria1.get(indice1));
				      if (indice1 < sequenciaAleatoria1.size()-1) {
					      indice1++;	
				      } else {
					      indice1 = 0;
				    }
            	Escalonador.numElemFila1++;
            } catch (Exception e){}
        }
    };
 
    private static Runnable fila2 = new Runnable() {
        public void run() {
            try{
            	//sleep(1 + gerador.nextInt(4));
            	sleep(sequenciaAleatoria2.get(indice2));
				if (indice2 < sequenciaAleatoria2.size()-1) {
					indice2++;	
				} else {
					indice2 = 0;
				}
				
            	Escalonador.numElemFila2++;
            } catch (Exception e){}
       }
    };
	
    private void limparDados() {
		FileOutputStream arquivoLimpo = null;
		final PrintStream printStream;
		try {
		   arquivoLimpo = new FileOutputStream("Saida_Simulacao.txt", false);
		   printStream = new PrintStream(arquivoLimpo);
		   // Redirecionamento de System.out para Arquivo de Saida
		   System.setOut(printStream);
		} catch(final FileNotFoundException e) {
			e.printStackTrace();
	    }
		// Fechando Stream de Saida
		try {
			if(arquivoLimpo != null) {
			   arquivoLimpo.close();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
    }
      
    @Override
	public void run() {
    	limparDados();
    	
		while(this.tempoTotal > tempoAtual) {
	        new Thread(fila1).start();
	        new Thread(fila2).start();	
			
			if(escalonadorVago) {
				escalonadorVago = false;
				Log();

				//tempoAtual += 2 + gerador.nextInt(5);
				tempoAtual += 1 + sequenciaAleatoria3.get(indice3);
				if (indice3 < sequenciaAleatoria3.size()-1) {
					indice3++;	
				} else {
					indice3 = 0;
				}
				
				if(numElemFila1 != 0) {
					numElemFila1--;
					escalonadorVago = true;
				}else if (numElemFila2 != 0){
					numElemFila2--;
					escalonadorVago = true;
				}
				Log();
				this.indiceElemento++;
			}
			
		}	
	}
	
	public void Log() {
		FileOutputStream arquivoSaida = null;
		final PrintStream printStream;
		try {
		   arquivoSaida = new FileOutputStream("Saida_Simulacao.txt", true);
		   printStream = new PrintStream(arquivoSaida);
		   // Redirecionamento de System.out para Arquivo de Saida
		   System.setOut(printStream);
		} catch(final FileNotFoundException e) {
			e.printStackTrace();
	    }
		
		if(escalonadorVago) {
			System.out.println("Evento de saída, momento: " + tempoAtual);
		}else {
			System.out.println("Evento de chegada, momento: " + tempoAtual);
		}
		System.out.println("Elementos na fila 1: " + numElemFila1);
		System.out.println("Elementos na fila 2: " + numElemFila2);
		System.out.println("Elemento no serviço: " + indiceElemento);	
		System.out.println();
		
		// Fechando Stream de Saida
		try {
			if(arquivoSaida != null) {
			   arquivoSaida.close();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int tempoDeEscalonamento = 100;

		System.out.println("--------------- Inicio da Simulação ---------------");
		System.out.println();
		System.out.println("############## Parânmetros Utilizados ##############");
		System.out.println("Tempo em Segundos: " + tempoDeEscalonamento);
		System.out.println("Metodo para GNA: " + MetodoGeracao.MULTIPLICATIVO);
		
		Escalonador esc = new Escalonador(tempoDeEscalonamento);
		esc.start();
		
		System.out.println("Saida (Arquivo com os eventos): Saida_Simulacao.txt");
		System.out.println("#####################################################");

		System.out.println();
		System.out.println("---------- Simulação Finalizada com Êxito ----------");
	}
		
}