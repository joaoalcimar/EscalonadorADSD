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

		switch (metodoGeracao) {
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
}
