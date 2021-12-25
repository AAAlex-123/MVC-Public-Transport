<?php
class CCompetitionsList
{
    private $model, $select_type, $select_location, $select_date;
	
    public function __construct($model, $select_type, $select_location, $select_date)
	{
        $this->model = $model;
		$this->select_type = $select_type;
		$this->select_location = $select_location;
		$this->select_date = $select_date;
		
		if($this->select_date != '')//cinde date is string we need to parse it
		{
			$this->select_date = DateTime::createFromFormat('d/m/Y H:i', $this->select_date);
			
			if($this->select_date) $this->select_date = $this->select_date->getTimestamp();
			else $this->select_date = '';
		}
    }
	
	public function get_competitions_array()
	{
		$data = $this->model->competitions_array($this->select_type, $this->select_location, $this->select_date);
		
		$selector[0] = array();
		$selector[1] = array();
		$selector[2] = array();
		
		for($i = 0; $i < count($data); $i++)
		{
			$data[$i]['progress'] = 100-(int)(100*($data[$i]['competitors_no']-(int)$data[$i]['competitors_no_filled'])/(int)$data[$i]['competitors_no']);//how many % of positions are filled
			$data[$i]['free_positions'] = (int)$data[$i]['competitors_no']-(int)$data[$i]['competitors_no_filled'];//# of free positions
			$data[$i]['date_'] = date('d/m/Y H:i', $data[$i]['date_']);
			
			if (!in_array($data[$i]['type_'], $selector[0])) array_push($selector[0], $data[$i]['type_']);
			if (!in_array($data[$i]['location'], $selector[1])) array_push($selector[1], $data[$i]['location']);
			if (!in_array($data[$i]['date_'], $selector[2])) array_push($selector[2], $data[$i]['date_']);
		}
		
		//we will now store 3 arrays.
		//1. the unique competition types
		//2. the unqiue competition lcoations
		//3. the unqiue competition start dates
		
		return array($data, $selector);
	}
}

class CCompetitionsDetails
{
    private $model;
	private $competition_id;
	
    public function __construct($model, $competition_id)
	{
        $this->model = $model;
		$this->competition_id = $competition_id;
    }
	
	public function get_competition_details()
	{
		//this array will be empty if no such competition is found
		$data = $this->model->competition_details((int)$this->competition_id);
		
		//let's do some stuff here
		if($data != array())//if array not empty
		{
			$data['creation_time'] = date('d/m/Y H:i', $data['creation_time']);
			$data['date_'] = date('d/m/Y H:i', $data['date_']);
			$data['registration_last_day'] = date('d/m/Y H:i', $data['registration_last_day']);
		}
		
		return $data;
	}
}

class CInsertCompetition
{
    private $model;
	
    public function __construct($model)
	{
        $this->model = $model;
    }
	
	public function insert_competition($data)
	{
		//title, location, summary and rules are strings so are checked if they are empty only
		if(
			count($data) == 11 && 
			isset($data['title']) && !empty($data['title']) && 
			isset($data['type_']) && !empty($data['type_']) && 
			isset($data['race_in_km']) && !empty($data['race_in_km']) && 
			isset($data['location']) && !empty($data['location']) && 
			isset($data['date_']) && !empty($data['date_']) && 
			isset($data['duration_limit']) && !empty($data['duration_limit']) && 
			isset($data['competitors_no']) && !empty($data['competitors_no']) &&
			isset($data['registration_last_day']) && !empty($data['registration_last_day']) &&
			isset($data['cost']) && !empty($data['cost']) && 
			isset($data['summary']) && !empty($data['summary']) && 
			isset($data['rules']) && !empty($data['rules'])
		) {
			
			$data['date_'] = DateTime::createFromFormat('d/m/Y H:i', $data['date_']);
			$data['registration_last_day'] = DateTime::createFromFormat('d/m/Y H:i', $data['registration_last_day']);
			
			if(
				($data['type_'] == 'Ομαλός Δρόμος' || $data['type_'] == 'Χωματόδρομος' || $data['type_'] == 'Δρόμος Μετ\' Εμποδίων') &&//type should be one of the available options
				is_numeric($data['race_in_km']) && //race distance should be a float
				$data['date_'] &&//if the timestamp length is not zero
				is_numeric($data['duration_limit']) && 
				is_numeric($data['competitors_no']) && 
				$data['registration_last_day'] &&
				is_numeric($data['cost'])
			) {
				///////////////
				//here we use one of the available images for now
				$data['creator_id'] = 0;//for now
				$data['image'] = '../assets/images/'.rand(1, 6).'.png';//empty image for now
				///////////////
				
				$data['date_'] = (int)$data['date_']->getTimestamp();
				$data['registration_last_day'] = (int)$data['registration_last_day']->getTimestamp();
				
				$data['race_in_km'] = (float)$data['race_in_km'];
				$data['duration_limit'] = (float)$data['duration_limit'];
				$data['competitors_no'] = (int)$data['competitors_no'];
				$data['cost'] = (float)$data['cost'];
				
				$data['creation_time'] = time();//the current timestamp
				$data['competitors_no_filled'] = 0;//there are 0 runners registed when we add this
				
				$this->model->insert_data($data);
				return 'OK';//to signify successfull insert
				
			}
		}
		
		return "";//insert failed
	}
}
?>