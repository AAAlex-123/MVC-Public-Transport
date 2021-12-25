<?php
class VCompetitionsList
{
    private $controller;
	
    public function __construct($controller)
	{
        $this->controller = $controller;
    }
	
    public function output()
	{
		$data_tmp = $this->controller->get_competitions_array();
		$data = $data_tmp[0];
		$selector = $data_tmp[1];
		require_once 'ui/UICompetitionsList.php';
    }
}

class VCompetitionsDetails
{
    private $controller;
	
    public function __construct($controller)
	{
        $this->controller = $controller;
    }
	
    public function output()
	{
		$data = $this->controller->get_competition_details();
		
		if($data == array())
			require_once 'ui/UICompetitionNotFound.php';
		else
			require_once 'ui/UICompetitionsDetails.php';
	}	
}

class VInsertCompetition
{
    private $controller;
	
    public function __construct($controller)
	{
        $this->controller = $controller;
    }
	
	public function send_competition($data)
	{
		return $controller->insert_competition($data);
	}
	
    public function output()
	{
		require_once 'ui/UIInsertCompetition.php';
	}	
}

?>