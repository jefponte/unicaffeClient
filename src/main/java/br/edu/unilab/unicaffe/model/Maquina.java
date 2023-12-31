package br.edu.unilab.unicaffe.model;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @author Jefferson Uchôa Ponte Classe que representa a máquina, um PC do
 *         laboratóriod e informática, com informações de laboratório, acesso e
 *         usuário.
 * 
 */
public class Maquina {
	/**
	 * Id da máquina no banco de dados.
	 */
	private int id;
	/**
	 * Nome da máquina.
	 */
	private String nome;
	/**
	 * Endereço de IP da máquina.
	 */
	private String ip;
	/**
	 * Endereço MAC da máquina.
	 */
	private String enderecoMac;
	/**
	 * Estado atual da máquina. poderá ser: preenchido de acordo com as constantes
	 * 
	 * STATUS_DISPONIVEL STATUS_OCUPADA STATUS_DESCONECTADA STATUS_ADMIN
	 * STATUS_UPDATE
	 * 
	 */
	private int status;
	/**
	 * Esse objeto possui informações do acesso atual da máquina, com informação de
	 * tempo e usuário atual.
	 */
	private Acesso acesso;

	/**
	 * Informa se a máquina já foi cadastrada na base de dados.
	 */
	private boolean cadastrada;
	/**
	 * Versão do UniCaffé instalado na máquina.
	 */
	private String versao;

	// kantu tempu ki bu misti.
	/**
	 * @return informação de estado de acesso, tempo de uso e usuário atual.
	 */
	public Acesso getAcesso() {
		return this.acesso;
	}

	/**
	 * Atribui um acesso a essa máquina.
	 * 
	 * @param acesso
	 */
	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	/**
	 * Constroi objeto máquina.
	 */

	public Maquina() {

		this.acesso = new Acesso();
		this.setVersao("1.0");
		this.setNome("Não Listado");
		this.getAcesso().setStatus(Acesso.STATUS_DISPONIVEL);
	}

	/**
	 * Descobre o endereço MAC da máquina e atribui ao atributo enderecoMac deste
	 * objeto.
	 */
	public void preencheComMaquinaLocal() {
		this.enderecoMac = "Não Informado";
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			this.ip = ia.getHostAddress().toString();
			this.nome = ia.getHostName().toString();

			NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
			byte[] mac = ni.getHardwareAddress();

			if (mac == null) {
				return;
			}
			String macAddress = "";
			for (int i = 0; i < mac.length; i++) {
				macAddress += (String.format("%02X-", mac[i]));
			}

			if (macAddress != null) {
				if (macAddress.length() > 1) {
					this.enderecoMac = macAddress.substring(0, macAddress.length() - 1);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return o nome da máquina.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Atribui o nome da máquina.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * 
	 * @return ip da máquina.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Atribui o ip da máquina.
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * @return Endereço MAc da máquina.
	 */
	public String getEnderecoMac() {
		return enderecoMac;
	}

	/**
	 * 
	 * @param enderecoMac
	 */
	public void setEnderecoMac(String enderecoMac) {
		this.enderecoMac = enderecoMac;
	}

	/**
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {

		return "Nome: " + this.nome + " | ip: " + this.ip + " | MAC: " + this.enderecoMac + " | Status: " + this.status;

	}

	/**
	 * Constante para ser usada no status da máquina, representa máquina disponível.
	 */
	public static final int STATUS_DISPONIVEL = 0;
	/**
	 * Máquina ocupada.
	 */
	public static final int STATUS_OCUPADA = 1;
	/**
	 * Máquina desconectada.
	 */
	public static final int STATUS_DESCONECTADA = 2;
	/**
	 * Máquina Adm.
	 */
	public static final int STATUS_ADMIN = 3;
	/**
	 * Máquina em atualização.
	 */
	public static final int STATUS_UPDATE = 4;
	/**
	 * Máquina em atualização do atualizador.
	 */
	public static final int STATUS_UPDATE_ATUALIZADOR = 5;

	/**
	 * @param status
	 * @return Retorna o status da máquina no formato String para exibição.
	 */
	public static String statusString(int status) {
		String strStatus = "";

		switch (status) {
		case STATUS_DESCONECTADA:
			strStatus = "Desconectada";
			break;

		case STATUS_OCUPADA:
			strStatus = "Ocupada";
			break;
		case STATUS_DISPONIVEL:
			strStatus = "Disponível";
			break;
		case STATUS_ADMIN:
			strStatus = "Administrador";
			break;
		case STATUS_UPDATE:
			strStatus = "Atualizando";
			break;
		default:
			break;
		}
		return strStatus;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCadastrada() {
		return cadastrada;
	}

	/**
	 * 
	 * @param cadastrada
	 */
	public void setCadastrada(boolean cadastrada) {
		this.cadastrada = cadastrada;
	}

	/**
	 * 
	 * @return
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * 
	 * @param versao
	 */
	public void setVersao(String versao) {
		this.versao = versao;
	}
}
