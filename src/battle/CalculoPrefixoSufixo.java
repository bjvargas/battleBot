package battle;

import java.util.ArrayList;

public class CalculoPrefixoSufixo {

	public int maiorPrefixoSufixo(String palavra) {

		ArrayList<String> esquerda = new ArrayList<String>();

		for (int i = 0; i < palavra.length(); i++) {

			char pegaCharDaString;
			String converteCharParaString;

			pegaCharDaString = palavra.charAt(i);
			converteCharParaString = String.valueOf(pegaCharDaString);
			esquerda.add(converteCharParaString);
		}

		int total = 0;
		int contador = palavra.length() - 1;
		for (int i = 0; i < contador; i++) {
			if (esquerda.get(contador).equals(esquerda.get(total))) {
				total++;
			}
			contador--;
		}

		if (total == 0) {
			total = -1;
		}
		System.out.println(esquerda);
		System.out.println(total);
				
		return total;

	}

	public String maiorQtdLetrasRepetidas(String palavra) {
		ArrayList<String> arranjoLetras = new ArrayList<String>();

		for (int i = 0; i < palavra.length(); i++) {
			char charPalavra;
			String charConvertidaString;

			charPalavra = palavra.charAt(i);
			charConvertidaString = String.valueOf(charPalavra);
			arranjoLetras.add(charConvertidaString);
		}

		int max = 0;
		String maisContado = null;

		int contador = 1;
		for (int i = 1; i < arranjoLetras.size(); i++) {
			if (arranjoLetras.get(i).equals(arranjoLetras.get(i - 1))) {
				contador++;
			} else {
				if (contador > max) {
					max = contador;
					maisContado = arranjoLetras.get(i - 1);
				}
				contador = 1;
			}
		}
		if (contador > max) {
			max = contador;
			maisContado = arranjoLetras.get(arranjoLetras.size() - 1);
		}

		return maisContado;
	}

	public void maiorQtdLetrasRepetidasaDANET(String palavra) {
		char[] array = palavra.toCharArray();
		int count = 1;
		int max = 0;
		char maxChar = 0;
		for (int i = 1; i < array.length; i++) { // Start from 1 since we want to compare it with the char in index 0
			if (array[i] == array[i - 1]) {
				count++;
			} else {
				if (count > max) { // Record current run length, is it the maximum?
					max = count;
					maxChar = array[i - 1];
				}
				count = 1; // Reset the count
			}
		}
		if (count > max) {
			max = count; // This is to account for the last run
			maxChar = array[array.length - 1];
		}
		System.out.println("Longest run: " + max + ", for the character " + maxChar);

	}
	
	public boolean verificarPalindromo(String palavra) {
		boolean ehPalindromo = false;
		
		ArrayList<String> esquerda = new ArrayList<String>();
		ArrayList<String> direita = new ArrayList<String>();

		for (int i = 0; i < palavra.length(); i++) {
			char pegaCharDaString;
			String converteCharParaString;

			pegaCharDaString = palavra.charAt(i);
			converteCharParaString = String.valueOf(pegaCharDaString);
			esquerda.add(converteCharParaString);
		}
		
		for (int i = palavra.length() -1; i >= 0; i--) {
			char pegaCharDaString;
			String converteCharParaString;

			pegaCharDaString = palavra.charAt(i);
			converteCharParaString = String.valueOf(pegaCharDaString);
			direita.add(converteCharParaString);
		}

		String esq = String.join("", esquerda);
		String dir = String.join("", direita);
		
		if(esq.equals(dir)) {
			ehPalindromo = true;
		}	
				
		return ehPalindromo;

	}
}
