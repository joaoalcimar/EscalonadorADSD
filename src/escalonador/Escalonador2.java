package escalonador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;

public class Escalonador extends Thread{

	private int tempoAtual;
	private int tempoTotal;
	private static int numElemFila1;
	private static int numElemFila2;
	private int indiceElemento;
	private boolean escalonadorVago;
	private PrintWriter writer;
	private static Random gerador = new Random();

	public Escalonador(int tempoTotal) {
		this.tempoTotal = tempoTotal;
		this.escalonadorVago = true;
		this.indiceElemento = 1;
		this.tempoAtual = 0;
		Escalonador.numElemFila1 = 0;
		Escalonador.numElemFila2 = 0;
		
	}
	
    private static Runnable fila1 = new Runnable() {
        public void run() {
            try{
            	Escalonador.numElemFila1++;
            } catch (Exception e){}
 
        }
    };
 
    private static Runnable fila2 = new Runnable() {
        public void run() {
            try{
            	sleep(1 + gerador.nextInt(4));
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

		// Abrindo Stream de Saida
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
			tempoAtual++;
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
		Escalonador esc = new Escalonador(tempoDeEscalonamento);
		esc.start();
		
	}
	
	
}


