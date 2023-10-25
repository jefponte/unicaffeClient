package br.edu.unilab.unicaffe.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import br.edu.unilab.unicaffe.controller.ClienteController;
import br.edu.unilab.unicaffe.registro.model.Perfil;

/**
 *
 * @author Jefferson Uchôa Ponte Classe responsável pelo inicialização do
 *         Unicaffé Cliente
 */

public class MainClient {

	private static RandomAccessFile randomAccessFile;

	/**
	 * Método que inicia o processo.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		
//		Perfil perfilBloqueio = new Perfil();
//		perfilBloqueio.setListaDeRegistros(Perfil.listaParaBloqueio());
//		perfilBloqueio.desfazer();
//		
//		try {
//			Process processo = Runtime.getRuntime().exec(" taskkill /f /im explorer.exe");
//			processo.waitFor();
//			Runtime.getRuntime().exec("explorer.exe");
//			System.out.println("Ressucitou explorer");
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
		File f = new File(".lock");
		FileLock lock = null;
		try {
			f.createNewFile();
			randomAccessFile = new RandomAccessFile(f, "rw");
			lock = randomAccessFile.getChannel().tryLock();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lock != null) {
			ClienteController controle = new ClienteController();
			controle.iniciaCliente();
		}
	}

}
