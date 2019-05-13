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
		if(this.sequenciaAleatoria.size()==0) {
			return 0;
		}
		int indexUltimoElemento = this.sequenciaAleatoria.size()-1;
		return this.sequenciaAleatoria.get(indexUltimoElemento);
	}
	
	/**
	 * Metodo que realiza a geração de um numero aleatorio
	 * dado um dos metodos listados		
	 * @param semente, k, c, mod, MetodoGeracao
	 */
	private void adicionarValorAleatorio(int x_atual, int k, int c, int mod) {
		int x_prox = 0; // Tambem conhecido por X(n+1)
		int indexUltimoElemento = this.sequenciaAleatoria.size()-1;

		switch (metodoGeracao) {
			case ADITIVO:
				/* Gera lista de numeros inicial */
				geraValores(this.semente, k, c, mod, MetodoGeracao.MISTO);
				indexUltimoElemento = this.sequenciaAleatoria.size()-1;
				
				/* Completa restante da lista de numeros */
				for (int i = 0; i <= mod; i++) {
					indexUltimoElemento = this.sequenciaAleatoria.size()-1;
					x_prox = (this.sequenciaAleatoria.get(indexUltimoElemento) + this.sequenciaAleatoria.get(i)) % mod;
					this.sequenciaAleatoria.add(x_prox);
				}
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
		if (k >= mod || c >= mod) {
			System.out.println("Os valores de 'k' ou 'c' não compativeis!");
			return this.sequenciaAleatoria;
		}
		
		this.semente = semente;
		this.k = k;
		this.c = c;
		this.mod= mod;
		this.metodoGeracao = metodoGeracao;
		
		/* Adicionando primeiro valor */
		if (metodoGeracao!=MetodoGeracao.ADITIVO) {
			this.sequenciaAleatoria.add(this.semente);	
		}
		
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
		/* Start of Test */
		System.out.println("-------- INICIO --------");
		GeradorNumerosAleatorios gerador = new GeradorNumerosAleatorios();
		
		System.out.println("-> Exemplos Ilustrativos");
		System.out.println("Gerador com Métodos de Congruência Aditivo");
		gerador.geraValores(7, 2, 3, 5, MetodoGeracao.ADITIVO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
		
		System.out.println("Gerador com Métodos de Congruência Multipicativo");
		gerador.geraValores(3, 5, 0, 7, MetodoGeracao.MULTIPLICATIVO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
		gerador.geraValores(12, 7, 0, 11, MetodoGeracao.MULTIPLICATIVO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
		
		System.out.println("Gerador com Métodos de Congruência Misto");
		gerador.geraValores(4, 1, 1, 4, MetodoGeracao.MISTO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();
		gerador.geraValores(5, 1, 2, 5, MetodoGeracao.MISTO);
		System.out.println( gerador.toString() );
		gerador.apagaValores();

	}
}
