<?php
/**
 * Classe com telas voltadas para tratamento com usuário. 
 * @author Jefferson Uchôa Ponte
 *
 */
class UsuarioView {
    /**
     * Formulário de login de usuário. 
     * @param boolean $erro
     * @param string $msg_erro
     */
	public function mostraFormularioLogin($erro = false, $msg_erro = "") {
		echo '<div class="tela fundo-cinza1">
     <div class="duas colunas no-meio">
            
            <div class="linha fundo-branco com-bordas">
                <div class="conteudo">';
		
		if ($erro){
			echo '     
                    <div class="alerta-erro">
                       <div class="icone icone-fire ix16"></div>
                       <div class="titulo-alerta">' . $msg_erro . '</div>
                       <div class="subtitulo-alerta">Favor verificar novamente.</div>
                    </div>';
		}
		echo '<form method="post" action="" class="formulario-organizado">

                       <label for="idTextLogin">
                           Login
                           <input type="text" name="login" id="idTextLogin" class="doze" placeholder="Digite seu Usuário"/>
                        </label>
                        <label for="idTextSenha">
                            Senha
                            <input type="password" name="senha" id="idTextSenha" class="doze" placeholder="Digite sua Senha" />
                        </label>
                       <button type="submit" name="formlogin" class="botao b-primario doze"><span class="icone-redo2"></span> Entrar </button>                
                    </form>
                   
                </div>
            </div>
            
     </div>
</div>';
	}

}