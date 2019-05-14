package escalonador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class Escalonador extends Thread{

	private int tempoTotal;
	private int numElemFila1;
	private int numElemFila2;	
    private int proxChegada1;
    private int proxChegada2;
    private int proxAtendimento;
    private boolean servidorLivre;
    private int tempoAtual;
    private String elementoEmAtendimento;
	private int numElemServico;
	private boolean isReceive;
	
    private static Random rand = new Random();
      
	private GeradorNumerosAleatorios geradorNumerosAleatorios1 = new GeradorNumerosAleatorios();
	private GeradorNumerosAleatorios geradorNumerosAleatorios2 = new GeradorNumerosAleatorios();
	private GeradorNumerosAleatorios geradorNumerosAleatorios3 = new GeradorNumerosAleatorios();
	private List<Integer> sequenciaAleatoria1;
	private List<Integer> sequenciaAleatoria2;
	private List<Integer> sequenciaAleatoria3;
	private int indice1;
    private int indice2;
    private int indice3;

	public Escalonador(int tempoTotal) {
		this.tempoTotal = tempoTotal;
		this.numElemFila1 = 0;
		this.numElemFila2 = 0;
	    this.proxChegada1 = 1 + rand.nextInt(12);
	    this.proxChegada2 =  1 + rand.nextInt(5);
	    this.proxAtendimento = 0;
		this.servidorLivre = true;
		this.tempoAtual = 0;
		this.elementoEmAtendimento = "Sem elemento";
		this.numElemServico = 0;
		this.isReceive = false;
		
		this.indice1 = 0;
	    this.indice2 = 0;
	    this.indice3 = 0;
		/*
		 * Geração de Numeros Aleatorios (Parâmetros):		
		 * geraValores(semente, k, c, mod, MetodoGeracao)
		 */
		this.sequenciaAleatoria1 = geradorNumerosAleatorios1.geraValores(12, 7, 0, 11, MetodoGeracao.MULTIPLICATIVO);
    	this.sequenciaAleatoria2 = geradorNumerosAleatorios2.geraValores(4, 1, 1, 4, MetodoGeracao.MISTO);
    	this.sequenciaAleatoria3 = geradorNumerosAleatorios3.geraValores(5, 1, 2, 5, MetodoGeracao.MISTO);
	}
	
    @Override
	public void run() {
    	limparDados();
    	
    	/*
    	 * Código para produzir arquivo de saida tipo texto
    	 */
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
			
		this.isReceive = true;
		log();	
    	for (int i = 0; i < this.tempoTotal; i++) {
    		tempoSimulacao();
    		escalonador();
		}
    	
		// Fechando Stream de Saida
		try {
			if(arquivoSaida != null) {
			   arquivoSaida.close();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}
    
    private void escalonador() {
        if (this.proxChegada1 < 1) {
            if (this.servidorLivre) {
            	iniciaAtendimento('1');
            } else {
                this.numElemFila1 += 1;
                this.isReceive=true;
                log();
				escalonaProximaChegada('1');            	
            }
        	
        }

        if( this.proxChegada2 < 1) {
        	if (this.servidorLivre) {
                iniciaAtendimento('2');
        	} else {
        		this.numElemFila2 += 1;
        		this.isReceive=true;
                log();
                escalonaProximaChegada('2');
        	}
        }
            

        if (this.proxAtendimento < 1) {
            if (!this.servidorLivre) {
            	finalizaAtendimento();
            }
            
            if (this.numElemFila1 > 0) {
            	iniciaAtendimento('1');
            } else if (this.numElemFila2 > 0) {
            	iniciaAtendimento('2');
            } else {
            	this.servidorLivre = true;        	
            }
        }
        
	}
    
	private void iniciaAtendimento(char fila) {
		this.numElemServico++;
        if (fila == '1') {
            if (this.numElemFila1 > 0) {
            	this.numElemFila1 -= 1;
            }
            this.elementoEmAtendimento = "Elemento da classe 1";     	
        } else {
            if (this.numElemFila2 > 0) {
            	this.numElemFila2 -= 1;
            }
            this.elementoEmAtendimento = "Elemento da classe 2";     	
        }

        this.servidorLivre = false;
        
        this.proxAtendimento += 1 + sequenciaAleatoria3.get(indice3);
        //this.proxAtendimento = 2 + rand.nextInt(5);
		if (indice3 < sequenciaAleatoria3.size()-1) {
			indice3++;	
		} else {
			indice3 = 0;
		}
		
        log();
	}
	
	private void finalizaAtendimento() {
		this.numElemServico--;
	    this.servidorLivre = true;
	    this.isReceive=false;
	}

	private void escalonaProximaChegada(char fila) {
        if (fila == '1') {
            this.proxChegada1 = sequenciaAleatoria1.get(indice1);
            //this.proxChegada1 = 1 + rand.nextInt(12);
			if (indice1 < sequenciaAleatoria1.size()-1) {
				indice1++;	
			} else {
				indice1 = 0;
			}
        } else {
            this.proxChegada2 = sequenciaAleatoria2.get(indice2);
            //this.proxChegada2 = 1 + rand.nextInt(4);
			if (indice2 < sequenciaAleatoria2.size()-1) {
				indice2++;	
			} else {
				indice2 = 0;
			}
        }
	}

	private void tempoSimulacao() {
        this.tempoAtual += 1;
        if(this.proxChegada1 > 0) {
            this.proxChegada1 -= 1;
        }
        if (this.proxChegada2 > 0) {
        	this.proxChegada2 -= 1;
        }
        if (this.proxAtendimento > 0) {
        	this.proxAtendimento -= 1;        	
        }	
	}
	
    private void limparDados() {
		FileOutputStream arquivoLimpo = null;
		final PrintStream printStream;
		
		// Abrindo Stream de Saida
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
      
	public void log() {	
		if (numElemServico==0) {
			this.elementoEmAtendimento = "Sem elemento";
			System.out.println("Evento de hibernação, momento: " + tempoAtual + " segundos");
		} else {
			if(!this.isReceive) {
				System.out.println("Evento de saída, momento: " + tempoAtual + " segundos");
			} else {
				System.out.println("Evento de chegada, momento: " + tempoAtual + " segundos");
			}			
		}
		System.out.println("Elementos na fila 1: " + numElemFila1);
		System.out.println("Elementos na fila 2: " + numElemFila2);
		System.out.println("Elemento no serviço: " + numElemServico + " (" + this.elementoEmAtendimento + ")");	
		System.out.println();	
	}
	
	public static void main(String[] args) {
		int tempoDeEscalonamento = 100;
		Escalonador esc = new Escalonador(tempoDeEscalonamento);

		System.out.println("--------------- Inicio da Simulação ---------------");
		System.out.println();
		System.out.println("############## Parânmetros Utilizados ##############");
		System.out.println("Tempo em Segundos: " + tempoDeEscalonamento);
		System.out.println("Metodos para GNA: -" + MetodoGeracao.MULTIPLICATIVO);
		System.out.println("                  -" + MetodoGeracao.MISTO);
	
		esc.run();
	}
		
}