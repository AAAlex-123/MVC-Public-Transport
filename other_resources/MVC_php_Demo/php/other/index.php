<?php
	setlocale(LC_ALL, 'el_GR.UTF-8');
	
	require_once 'database_engine.php';
	require_once 'view.php';
	require_once 'model.php';
	require_once 'controller.php';
	
	$database = new database();
	
	$server_params = explode('/', $_SERVER['REQUEST_URI']);
	
	if($server_params[1] == 'login')
	{
		$page_now = 'login';
		
		require_once 'header.php';
		
		echo '<br/>Υπο Κατασκευή<br/>';
		
		require_once 'footer.php';
		
	}
	else if($server_params[1] == 'register')
	{
		$page_now = 'register';
		
		require_once 'header.php';
		
		echo '<br/>Υπο Κατασκευή<br/>';
		
		require_once 'footer.php';
	}
	else if($server_params[1] == 'details' && isset($server_params[2]))
	{
		$page_now = 'details';
		
		require_once 'header.php';
		
		$model = new MCompetitionsList($database);
		$controller = new CCompetitionsDetails($model, $server_params[2]);
		$view = new VCompetitionsDetails($controller);
		echo $view->output();
		
		require_once 'footer.php';
	}
	else if($server_params[1] == 'add-competition')
	{
		$page_now = 'add-competition';
		
		$model = new MCompetitionsList($database);
		$controller = new CInsertCompetition($model);
		$view = new VInsertCompetition($controller);
		
		if(count($_POST) > 0)
			echo $view->send_competition($_POST);
		else
		{
			require_once 'header.php';
			echo $view->output();
			require_once 'footer.php';
		}
	}
	else
	{
		$page_now = 'home';
		
		require_once 'header.php';
		
		$model = new MCompetitionsList($database);
		
		//let's deal with the posted params
		$select_type = isset($_POST['select_type']) ? $_POST['select_type'] : '';
		$select_location = isset($_POST['select_location']) ? $_POST['select_location'] : '';
		$select_date = isset($_POST['select_date']) ? $_POST['select_date'] : '';
		//
		
		$controller = new CCompetitionsList($model, $select_type, $select_location, $select_date);
		$view = new VCompetitionsList($controller);
		echo $view->output();
		
		require_once 'footer.php';
	}
?>