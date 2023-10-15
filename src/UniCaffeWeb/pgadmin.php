<?php
ini_set ( 'display_errors', 1 );
ini_set ( 'display_startup_erros', 1 );
error_reporting ( E_ALL );
function __autoload($classe) {
	if (file_exists ( 'classes/dao/' . $classe . '.php' )) {
		include_once 'classes/dao/' . $classe . '.php';
	}
	if (file_exists ( 'classes/model/' . $classe . '.php' )) {
		include_once 'classes/model/' . $classe . '.php';
	}
	if (file_exists ( 'classes/controller/' . $classe . '.php' )) {
		include_once 'classes/controller/' . $classe . '.php';
	}
	if (file_exists ( 'classes/util/' . $classe . '.php' )) {
		include_once 'classes/util/' . $classe . '.php';
	}
	if (file_exists ( 'classes/view/' . $classe . '.php' )) {
		include_once 'classes/view/' . $classe . '.php';
	}
}






$dao = new DAO ();

AdminPG::main ( $dao->getConexao () );



?>