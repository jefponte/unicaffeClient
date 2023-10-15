package br.edu.unilab.unicaffe.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Jefferson Uchôa Ponte Classe responsável pelo inicialização do
 *         Unicaffé Admin
 */
public class MainAdmin {
	/**
	 * recebe o comando
	 */
	private static Scanner scanner;
	/**
	 * Fluxo de conexão com o servidor
	 */
	private static Socket cliente;

	/**
	 * Método que inicia o processo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		do {

			System.out.println("UniCafeAdmin digite um comando: ");
			scanner = new Scanner(System.in);
			String mensagem = scanner.nextLine();
			if (mensagem.equals("sair"))
				break;

			try {
				cliente = new Socket("localhost", 27289);
				PrintStream printStream = new PrintStream(cliente.getOutputStream());
				BufferedReader buffereReader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				printStream.println("setStatus(3)");
				printStream.println(mensagem);
				String resposta = "";
				while ((resposta = buffereReader.readLine()) != null) {
					System.out.println(resposta);
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (true);

	}

}
