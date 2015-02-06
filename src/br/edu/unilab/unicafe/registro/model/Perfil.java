package br.edu.unilab.unicafe.registro.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Perfil {
	private int id;
	private ArrayList<Registro> listaDeRegistros;
	private String nome;
	private String descricao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Registro> getListaDeRegistros() {
		return listaDeRegistros;
	}
	public void setListaDeRegistros(ArrayList<Registro> listaDeRegistros) {
		this.listaDeRegistros = listaDeRegistros;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	


	public void executar(){
		Process process;
		for (Registro registro : listaDeRegistros) {
			try {

				process = Runtime.getRuntime().exec(registro.toString());
				System.out.println(registro);
				Scanner leitor = new Scanner(process.getInputStream());
				while (leitor.hasNextLine()) {
					System.out.println(leitor.nextLine());
				}
			} catch (IOException e) {
				System.out.println("Errou");
			}
			
		}
	}
	public void desfazer(){
		Process process;
		for (Registro registro : listaDeRegistros) {
			try {

				process = Runtime.getRuntime().exec(registro.toStringDesfazer());
				System.out.println(registro.toStringDesfazer());
				Scanner leitor = new Scanner(process.getInputStream());
				while (leitor.hasNextLine()) {
					System.out.println(leitor.nextLine());
				}
			} catch (IOException e) {
				System.out.println("Errou");
			}
			
		}
	}

	/**
	 * Retorna uma lista de bloqueios necess�rios em um bloqueio de tela. 
	 * Ideal para ser chamado no in�cio da aplica��o. 
	 * 
	 * @return
	 */
	public static ArrayList<Registro> listaParaBloqueio(){
		
		
		ArrayList<Registro> lista = new ArrayList<Registro>();
		
		lista.add(new Registro("HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System",	 "HideFastUserSwitching", Registro.REG_DWORD, "1", "0", "Remove Op��o para Trocar de Usu�rio no Ctrl+ALT+DEL"));
		lista.add(new Registro("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System",	 "DisableTaskMgr", Registro.REG_DWORD, "1", "0", "Remove Op��o para Iniciar o Gerenciador de Tarefas no Ctrl+ALT+DEL"));
		lista.add(new Registro("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoLogOff", Registro.REG_DWORD, "1", "0", "Remove Op��o para Logoff no Ctrl+ALT+DEL"));
		lista.add(new Registro("HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableLockWorkstation", Registro.REG_DWORD, "1", "0", "Remove Op��o para Desligar no Ctrl+ALT+DEL"));
		lista.add(new Registro("HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableChangePassword", Registro.REG_DWORD, "1", "0", "Remove Op��o para Desligar no Ctrl+ALT+DEL"));
		return lista;
		
		
	}
}
