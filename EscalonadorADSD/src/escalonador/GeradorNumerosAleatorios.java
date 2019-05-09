package escalonador;

import java.util.ArrayList;
import java.util.List;

public class GeradorNumerosAleatorios {
	private List<Integer> sequenciaAleatoria;
	private int semente; // Tambem conhecido por Xn
	private int k;
	private int c;
	private int mod;
	
	private MetodoGeracao metodoGeracao;
	
	public GeradorNumerosAleatorios() {
		this.sequenciaAleatoria = new ArrayList<Integer>();
	}
	
	private int getUltimoElemento() {
		int indexUltimoElemento = this.sequenciaAleatoria.size()-1;
		return this.sequenciaAleatoria.get(indexUltimoElemento);
	}
	
	private void adicionarValorAleatorio(int x_atual, int k, int c, int mod) {
		int x_prox = 0; // Tambem conhecido por X(n+1)
		int indexUltimoElemento = this.sequenciaAleatoria.size()-1;

		switch (metodoGeracao) {
			case ADITIVO:
				//System.out.println("TESTE -> "+x_atual+" .... "+indexUltimoElemento );
				x_prox = (x_atual + this.sequenciaAleatoria.get(indexUltimoElemento)) % mod;		
				this.sequenciaAleatoria.add(x_prox);
				break;
			case MULTIPLICATIVO:
				x_prox = (k*x_atual) % mod;		
				this.sequenciaAleatoria.add(x_prox);
				break;
			case MISTO:
				x_prox = ((k*x_atual)+ c) % mod;		
				this.sequenciaAleatoria.add(x_prox);				
				break;
			default:
				System.out.println("Este não é um metodo válido!");
		}
	}
	
	public List<Integer> geraValores(int semente, int k, int c, int mod, MetodoGeracao metodoGeracao) {
		this.semente = semente;
		this.k = k;
		this.c = c;
		this.mod= mod;
		this.metodoGeracao = metodoGeracao;
		
		/* Adicionando primeiro valor */
		this.sequenciaAleatoria.add(this.semente);
		for (int i = 0; i < mod - 1; i++) {
			adicionarValorAleatorio(getUltimoElemento(), this.k, this.c, this.mod);
		}	
		return this.sequenciaAleatoria;
	}
	
	public void apagaValores(){
		this.sequenciaAleatoria.clear();
		this.semente = 0;
		this.k = 0;
		this.c = 0;
		this.mod= 0;
	}
	
	public String toString() {
		String listaNumerosAleatorios = "[";
		
		for (int i = 0; i < this.sequenciaAleatoria.size(); i++) {
			if (i==0) {
				listaNumerosAleatorios += this.sequenciaAleatoria.get(i);
			} else {
				listaNumerosAleatorios += ", "+ this.sequenciaAleatoria.get(i);	
			}
		}
		listaNumerosAleatorios += "]";	
		return listaNumerosAleatorios;
	}

	
	public static void main(String[] args) {
		/* Start */
		System.out.println("INICIO");
		GeradorNumerosAleatorios gerador = new GeradorNumerosAleatorios();
		
		System.out.println("Gerador com Métodos de Congruência Aditivo");
		gerador.geraValores(3, 5, 0, 7, MetodoGeracao.ADITIVO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
		
		System.out.println("Gerador com Métodos de Congruência Multipicativo");
		gerador.geraValores(3, 5, 0, 7, MetodoGeracao.MULTIPLICATIVO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();

		
		System.out.println("Gerador com Métodos de Congruência Misto");
		gerador.geraValores(3, 7, 1, 13, MetodoGeracao.MISTO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
	}
}
