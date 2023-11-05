package br.edu.unilab.unicaffe.bloqueio.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import br.edu.unilab.unicaffe.util.Log;

/**
 *
 * Classe gerencia listas de bloqueios de processos.
 *
 * @author Jefferson Uchôa Ponte
 *
 *
 */
public class PerfilBloqueio {
	/**
	 * Lista com os processos que não serão bloqueados.
	 */
	private Collection<Processo> listaDeProcessosAceitos;
	/**
	 * Lista com os processo em execução pelo sistema operacional.
	 */
	private Collection<Processo> processosAtivos;

	/**
	 * Lista com os processos que foram mortos.
	 */
	private Collection<Processo> listaDeBloqueados;

	/**
	 *
	 * @return Collection listaDeBloqueados
	 */
	public Collection<Processo> getListaDeBloqueados() {
		return listaDeBloqueados;
	}

	/**
	 *
	 * @param listaDeBloqueados
	 */
	public void setListaDeBloqueados(Collection<Processo> listaDeBloqueados) {
		this.listaDeBloqueados = listaDeBloqueados;
	}

	/**
	 *
	 * @return a lista de processos que não serão bloqueados.
	 */
	public Collection<Processo> getListaDeAceitos() {
		return this.listaDeProcessosAceitos;
	}

	/**
	 *
	 * @return a lista de processos em execução pelo sistema operacional.
	 */
	public Collection<Processo> getProcessosAtivos() {
		return this.processosAtivos;
	}

	/**
	 * Constroi o objeto PerfilBloqueio.
	 */
	public PerfilBloqueio() {
		this.processosAtivos = new Vector<Processo>();
		this.listaDeProcessosAceitos = new Vector<Processo>();
		this.listaDeBloqueados = new Vector<Processo>();
	}

	/**
	 * Adiciona processo na lista de processos aceitos.
	 *
	 * @param processo
	 */
	public void addProcesso(Processo processo) {
		this.listaDeProcessosAceitos.add(processo);

	}

	/**
	 * Define uma lista de processos aceitos.
	 *
	 * @param processos
	 */
	public void setListaDeProcessosAceitos(Collection<Processo> processos) {
		this.listaDeProcessosAceitos = processos;
	}

	/**
	 *
	 * @return processos aceitos do windows 10
	 *
	 */
	public Collection<Processo> buscaAceitosWindows10() {
		Collection<Processo> lista = new Vector<Processo>();

		return lista;
	}

	/**
	 * Alimenta lista de processos aceitos.
	 */

	public void buscaAceitos() {
		this.buscaDeArquivo();
	}

	/**
	 * Busca lista de processos aceitos a partir de um arquivo.
	 */
	public void buscaDeArquivo() {
		File arquivoVerifica = new File("permitidos.txt");
		if (!arquivoVerifica.exists()) {
			FileWriter fw;
			try {
				fw = new FileWriter(arquivoVerifica, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(
						"[Cole aqui os processos do arquivo bloqueados.txt que deseja permitir. Um processo por linha. Não apague esta linha!] ");
				bw.newLine();
				bw.close();
				fw.close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {

			FileInputStream arquivo = new FileInputStream(arquivoVerifica);
			BufferedReader linhaArquivo = new BufferedReader(new InputStreamReader(arquivo));
			linhaArquivo.ready();
			while (linhaArquivo.ready()) {
				String linha = linhaArquivo.readLine();
				if (linha.isEmpty()) {
					continue;
				}

				if (linha.charAt(0) == '[') {
					continue;
				}

				boolean jaTem = false;
				String[] vDados = linha.split("[,]");

				for (Processo processoAceito : listaDeProcessosAceitos) {
					if (processoAceito.getExecutablePath().equals(vDados[0])
							&& processoAceito.getImagem().equals(vDados[1])) {
						jaTem = true;
						break;
					}
				}

				if (jaTem) {
					continue;
				}

				try {
					listaDeProcessosAceitos.add(new Processo(vDados[1], vDados[0]));
				} catch (ArrayIndexOutOfBoundsException fora) {
					System.out.println(linha);
				}
			}
			linhaArquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obter a lista de processos ativos no sistema operacional.
	 */
	public void buscaAtivos() {

		this.processosAtivos = new Vector<Processo>();
		Process process;
		Scanner leitor;

		try {

			process = Runtime.getRuntime().exec(" wmic process  get ProcessID, Name, ExecutablePath /FORMAT:CSV");
			leitor = new Scanner(process.getInputStream());

			while (leitor.hasNext()) {
				String linha = leitor.nextLine();
				if (linha.equals("")) {
					continue;
				}

				String[] vDados = linha.split("[,]");

				if (vDados[1].isEmpty()) {
					continue;
				}

				this.processosAtivos.add(new Processo(vDados[2], vDados[1], vDados[3]));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isAllowedPath(String path) {
		String[] allowedPaths = {
				"C:\\windows\\servic",
				"c:\\progra",
				"c:\\positivo",
				"c:\\windows\\winsxs\\",
				"c:\\windows\\microsoft",
				"c:\\users",
				"c:\\windows\\system32\\drivers",
				"c:\\windows\\systemapps",
				"c:\\windows\\servicing",
				"c:\\windows\\system32\\lsaiso",
				"c:\\windows\\system32\\oobe",
				"c:\\windows\\immersivecontrolpanel",
				"c:\\windows\\system32\\wbem",
				"c:\\windows\\system32\\mousocoreworker.exe",
				"C:\\Windows\\system32\\vmcompute.exe",
				"C:\\Windows\\System32\\vmwp.exe",
				"C:\\Windows\\System32\\wsl.exe",
				"C:\\xampp\\php\\php.exe",
				"C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
				"C:\\Windows\\system32\\conhost.exe",
				"C:\\xampp\\xampp-control.exe",
				"C:\\Windows\\SysWOW64\\OneDriveSetup.exe",
				"C:\\WINDOWS\\system32\\backgroundTaskHost.exe",
				"C:\\Windows\\System32\\RuntimeBroker.exe",
				"C:\\Windows\\System32\\smartscreen.exe",
				"c:\\windows\\system32\\taskhostw.exe",
				"C:\\Windows\\system32\\atieclxx.exe",
				"C:\\Windows\\system32\\sihost.exe",
				"C:\\Windows\\system32\\ApplicationFrameHost.exe",
				"C:\\Windows\\system32\\atieclxx.ex",
				"C:\\Windows\\system32\\atiesrxx.exe",
				"C:\\Windows\\SystemApps\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\MicrosoftEdgeCP.exe",
				"C:\\Windows\\SystemApps\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\MicrosoftEdge.exe",
				"C:\\WINDOWS\\system32\\MusNotification.exe",
				"C:\\WINDOWS\\system32\\mqsvc.exe",
				"C:\\WINDOWS\\system32\\AUDIODG.EXE",
				"C:\\WINDOWS\\system32\\fontdrvhost.exe",
				"C:\\WINDOWS\\system32\\browser_broker.exe",
				"C:\\WINDOWS\\system32\\MusNotificationUx.exe",
				"C:\\WINDOWS\\system32\\dashost.exe",
				"C:\\WINDOWS\\system32\\WLANExt.exe",
				"C:\\WINDOWS\\system32\\wwahost.exe",
				"C:\\Windows\\System32\\SystemSettingsBroker.exe",
				"C:\\WINDOWS\\system32\\BackgroundTransferHost.exe",
				"C:\\Windows\\System32\\MicrosoftEdgeCP.exe",
				"C:\\Windows\\System32\\SecurityHealthSystray.exe",
				"C:\\Windows\\system32\\ctfmon.exe",
				"C:\\Windows\\helppane.exe",
				"C:\\Windows\\System32\\W.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\Silverlight_x64.exe",
				"C:\\Windows\\SysWOW64\\REG.exe",
				"C:\\Windows\\system32\\PrintIsolationHost.exe",
				"C:\\Windows\\system32\\csrss.exe",
				"C:\\Windows\\system32\\wininit.exe",
				"C:\\Windows\\system32\\services.exe",
				"C:\\Windows\\system32\\winlogon.exe",
				"C:\\Windows\\system32\\lsass.exe",
				"C:\\Windows\\system32\\lsm.exe",
				"C:\\Windows\\system32\\svchost.exe",
				"C:\\Windows\\System32\\spoolsv.exe",
				"C:\\Windows\\system32\\IProsetMonitor.exe",
				"C:\\Windows\\system32\\taskhost.exe",
				"C:\\Windows\\system32\\Dwm.exe",
				"C:\\Windows\\Explorer.EXE",
				"C:\\Windows\\system32\\SearchIndexer.exe",
				"C:\\Windows\\System32\\igfxtray.exe",
				"C:\\Windows\\System32\\hkcmd.exe",
				"C:\\Windows\\System32\\igfxpers.exe",
				"C:\\Windows\\system32\\conhost.exe",
				"C:\\Windows\\system32\\notepad.exe",
				"C:\\Windows\\system32\\mspaint.exe",
				"C:\\Windows\\system32\\SearchProtocolHost.exe",
				"C:\\Windows\\system32\\SearchFilterHost.exe",
				"C:\\Windows\\system32\\igfxsrvc.exe",
				"C:\\Windows\\system32\\taskeng.exe",
				"C:\\Windows\\system32\\tasklist.exe",
				"C:\\Windows\\System32\\WUDFHost.exe",
				"C:\\Windows\\system32\\DllHost.exe",
				"C:\\Windows\\system32\\sppsvc.exe",
				"C:\\Windows\\System32\\rundll32.exe",
				"C:\\Windows\\SysWOW64\\explorer.exe",
				"C:\\Windows\\SysWOW64\\taskkill.exe",
				"C:\\Windows\\system32\\igfxCUIService.exe",
				"C:\\Windows\\SYSTEM32\\WISPTIS.EXE",
				"C:\\Windows\\SysWOW64\\DllHost.exe",
				"C:\\Windows\\system32\\mmc.exe",
				"C:\\Windows\\system32\\wuauclt.exe",
				"C:\\Windows\\splwow64.exe",
				"C:\\Windows\\system32\\unregmp2.exe",
				"C:\\Windows\\system32\\calc.exe",
				"C:\\Windows\\System32\\StkCSrv.exe",
				"C:\\Windows\\system32\\StikyNot.exe",
				"C:\\Windows\\system32\\consent.exe",
				"C:\\Windows\\System32\\GfxUI.exe",
				"C:\\Windows\\system32\\igfxEM.exe",
				"C:\\Windows\\system32\\igfxHK.exe",
				"C:\\Windows\\system32\\igfxTray.exe",
				"C:\\Windows\\system32\\SndVol.exe",
				"C:\\Windows\\SysWOW64\\cmd.exe",
				"C:\\Windows\\system32\\LogonUI.exe",
				"C:\\Windows\\system32\\vssvc.exe",
				"C:\\Windows\\system32\\mcbuilder.exe",
				"C:\\Windows\\system32\\defrag.exe",
				"C:\\Windows\\system32\\aitagent.EXE",
				"C:\\Windows\\SysWOW64\\unregmp2.exe",
				"C:\\Windows\\system32\\taskkill.exe",
				"C:\\Windows\\SysWOW64\\WerFault.exe",
				"C:\\Windows\\system32\\DXPServer.exe",
				"C:\\Windows\\system32\\REG.exe",
				"C:\\Windows\\System32\\msdtc.exe",
				"C:\\Windows\\SysWOW64\\rundll32.exe",
				"C:\\Windows\\system32\\net.exe",
				"C:\\Windows\\system32\\net1.exe",
				"C:\\Windows\\System32\\dinotify.exe",
				"C:\\Windows\\system32\\MpSigStub.exe",
				"C:\\Windows\\System32\\sdclt.exe",
				"C:\\Windows\\SysWOW64\\net.exe",
				"C:\\Windows\\system32\\cacls.exe",
				"C:\\Windows\\system32\\DrvInst.exe",
				"C:\\Windows\\system32\\makecab.exe",
				"C:\\Windows\\system32\\attrib.exe",
				"C:\\Windows\\SysWOW64\\notepad.exe",
				"C:\\Windows\\WindowsMobile\\wmdc.exe",
				"C:\\Windows\\system32\\cmd.exe",
				"C:\\Windows\\system32\\java.exe",
				"C:\\Windows\\SysWOW64\\Macromed\\Flash\\FlashPlayerPlugin_16_0_0_305.exe",
				"C:\\Windows\\system32\\Macromed\\Flash\\FlashUtil64_16_0_0_305_ActiveX.exe",
				"C:\\Octave\\Octave-4.2.0\\bin\\octave-gui.exe",
				"C:\\Windows\\SysWOW64\\Macromed\\Flash\\FlashPlayerPlugin_17_0_0_188.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\vstor_redist.exe",
				"Q:\\140066.ptb\\Office14\\WINWORDC.EXE",
				"Q:\\140066.ptb\\Office14\\EXCELC.EXE",
				"c:\\oraclexe\\app\\oracle\\product\\11.2.0\\server\\bin\\ORACLE.EXE",
				"C:\\oraclexe\\app\\oracle\\product\\11.2.0\\server\\BIN\\tnslsnr.exe",
				"C:\\Python27\\python.exe",
				"C:\\Windows\\system32\\Macromed\\Flash\\FlashUtil64_17_0_0_188_ActiveX.exe",
				"C:\\Windows\\system32\\DeviceDisplayObjectProvider.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.201.2218.0.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.203.205.0.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.201.2067.0.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.201.1937.0.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Base_Patch1.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta.exe",
				"C:\\Windows\\system32\\CompatTelRunner.exe",
				"C:\\Windows\\TEMP\\DFLocker.exe",
				"C:\\Windows\\system32\\compattel\\DiagTrackRunner.exe",
				"C:\\Windows\\system32\\gpscript.exe",
				"C:\\Windows\\system32\\GWX\\GWXConfigManager.exe",
				"C:\\Windows\\system32\\GWX\\GWX.exe",
				"C:\\Windows\\System32\\mobsync.exe",
				"c:\\59a0b9075bdf5a428ed9\\MpSigStub.exe",
				"Machine\\Scripts\\Startup\\OCS-NG-Windows-Agent-Setup.exe",
				"C:\\Windows\\system32\\sc.exe",
				"C:\\Windows\\System32\\sdiagnhost.exe",
				"C:\\Windows\\System32\\wsqmcons.exe",
				"C:\\Windows\\system32\\wermgr.exe",
				"C:\\Windows\\system32\\CompatTel\\WicaInventory.exe",
				"C:\\Windows\\system32\\Wat\\WatAdminSvc.exe",
				"C:\\Windows\\system32\\WerFault.exe",
				"C:\\Windows\\system32\\UI0Detect.exe",
				"C:\\Windows\\system32\\Macromed\\Flash\\FlashPlayerPlugin_18_0_0_209.exe",
				"C:\\Windows\\system32\\Macromed\\Flash\\FlashPlayerUpdateService.exe",
				"C:\\Windows\\SysWOW64\\Macromed\\Flash\\FlashPlayerPlugin_13_0_0_214.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.203.1791.0.exe",
				"C:\\Windows\\system32\\javaw.exe",
				"C:\\Windows\\system32\\taskmgr.exe",
				"C:\\Windows\\regedit.exe",
				"C:\\Windows\\SoftwareDistribution\\Download\\Install\\AM_Delta_Patch_1.255.107.0.exe",
				"C:\\Windows\\system32\\userinit.exe",
				"C:\\Windows\\system32\\fxssvc.exe",
				"C:\\Windows\\system32\\gpupdate.exe",
				"C:\\WINDOWS\\System32\\vds.exe",
				"executablepath",
		};

		String lowerPath = path.toLowerCase();

		for (String allowed : allowedPaths) {
			if (lowerPath.trim().startsWith(allowed.toLowerCase().trim())) {
				return true;
			}
			if (lowerPath.trim().endsWith(allowed.toLowerCase().trim())) {
				return true;
			}
			
		}
		return false;
	}

	/**
	 * Mata os processos ativos que não estiverem na lista dos permitidos. .
	 */
	public void comparaEMata() {

		boolean existeNaLista = false;
		Iterator<Processo> it = processosAtivos.iterator();// usando iterator para evitar erros de acessos simultâneos
		while (it.hasNext()) {
			Processo processoAtivo = it.next();
			existeNaLista = false;

			if (this.isAllowedPath(processoAtivo.getExecutablePath())) {
				continue;
			}

			for (Processo processoAceito : this.listaDeProcessosAceitos) {
				if (processoAtivo.equals(processoAceito)) {
					existeNaLista = true;
					break;

				}
			}
			if (existeNaLista) {

				continue;
			}

			try {
				Runtime.getRuntime().exec(" taskkill /PID \"" + processoAtivo.getProcessId() + "\" /F");
				System.out.println(processoAtivo.getExecutablePath() + " -> " + processoAtivo.getImagem());
				if (!this.existeNaLista(processoAtivo, this.listaDeBloqueados)) {
					this.listaDeBloqueados.add(processoAtivo);

				}
				new Log(processoAtivo.getExecutablePath() + "," + processoAtivo.getImagem(), "bloqueados.txt");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Exibe os processos ativos.
	 */
	public void mostraProcessos() {
		for (Processo p : this.processosAtivos) {
			System.out.println(p);
		}
	}

	public void mostraBloqueados() {
		for (Processo p : this.listaDeBloqueados) {
			System.out.println(p);
		}
	}

	/**
	 * @param processo
	 * @param lista
	 * @return
	 */
	public boolean existeNaLista(Processo processo, Collection<Processo> lista) {
		for (Processo processo2 : lista) {
			if (processo.getExecutablePath().toLowerCase().trim()
					.equals(processo2.getExecutablePath().toLowerCase().trim())
					&& processo.getImagem().toLowerCase().trim().equals(processo2.getImagem().toLowerCase().trim())) {
				return true;
			}
		}
		return false;
	}
}
