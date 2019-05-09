package escalonador;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class Escalonador extends Thread{

	private int tempoAtual;
	private int tempoTotal;
	private static int numElemFila1;
	private static int numElemFila2;
	private static int PRIMEIRO=0;
	private int indiceElemento;
	private boolean escalonadorVago;

	private PrintWriter writer;
	
	//private static Random selecionaNumero = new Random();
	private static Random gerador = new Random();
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
		 * 
		 * Geração de Numeros Aleatorios (Parâmetros):		
		 * geraValores(semente, k, c, mod, MetodoGeracao)
		 * 
		 */
    	sequenciaAleatoria1 = geradorNumerosAleatorios.geraValores(3, 5, 0, 7, MetodoGeracao.MULTIPLICATIVO);
    	sequenciaAleatoria2 = geradorNumerosAleatorios.geraValores(3, 5, 0, 7, MetodoGeracao.MULTIPLICATIVO);
    	sequenciaAleatoria3 = geradorNumerosAleatorios.geraValores(3, 5, 0, 7, MetodoGeracao.MULTIPLICATIVO);
	}
	
    private static Runnable fila1 = new Runnable() {
        public void run() {
            try{
            	//sleep(1 + gerador.nextInt(12));
            	//sleep(sequenciaAleatoria1.remove(PRIMEIRO));
            	Escalonador.numElemFila1++;
            } catch (Exception e){}
 
        }
    };
 
    private static Runnable fila2 = new Runnable() {
        public void run() {
            try{
            	//sleep(1 + gerador.nextInt(4));
            	sleep(sequenciaAleatoria2.remove(PRIMEIRO));
            	Escalonador.numElemFila2++;
            } catch (Exception e){}
       }
    };
	
    @Override
	public void run() {

		while(this.tempoTotal > tempoAtual) {
	        new Thread(fila1).start();
	        new Thread(fila2).start();
			
			
			if(escalonadorVago) {
				escalonadorVago = false;
				Log();

				tempoAtual += 2 + gerador.nextInt(5);
				//tempoAtual += sequenciaAleatoria3.remove(PRIMEIRO);
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
		if(escalonadorVago) {
			System.out.println("Evento de saída, momento: " + tempoAtual);
		}else {
			System.out.println("Evento de chegada, momento: " + tempoAtual);
		}
		System.out.println("Elementos na fila 1: " + numElemFila1);
		System.out.println("Elementos na fila 2: " + numElemFila2);
		System.out.println("Elemento no serviço: " + indiceElemento);	
		System.out.println();
	}
	
	public static void main(String[] args) {
		int tempoDeEscalonamento = 100;
		Escalonador esc = new Escalonador(tempoDeEscalonamento);
		esc.start();
		
	}
		
}


