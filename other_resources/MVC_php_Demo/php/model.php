<?php
class MCompetitionsList
{
    public $database;
	
    public function __construct($database)
	{
        $this->database = $database;
    }
	
	public function competitions_array($select_type, $select_location, $select_date)
	{
		return $this->database->select("SELECT id, image, title, type_, location, date_, competitors_no_filled, competitors_no FROM competitions WHERE type_ ".($select_type == '' ? '!' : '')."= :type_ AND location ".($select_location == '' ? '!' : '')."= :location AND date_ ".($select_date == '' ? '!' : '')."= :date_ ORDER BY date_ DESC", array('type_' => $select_type, 'location' => $select_location, 'date_' => $select_date));
	}
	
	public function competition_details($id)
	{
		return $this->database->select("SELECT id, title, image, type_, race_in_km, location, date_, duration_limit, competitors_no, registration_last_day, cost, rules, summary, creator_id, creation_time, competitors_no_filled FROM competitions WHERE id = :id", array('id' => $id))[0];
	}
	
	public function insert_data($safe_data)
	{
		$this->database->insert("INSERT INTO competitions (title, image, type_, race_in_km, location, date_, duration_limit, competitors_no, competitors_no_filled, registration_last_day, cost, rules, summary, creator_id, creation_time) VALUES (:title, :image, :type_, :race_in_km, :location, :date_, :duration_limit, :competitors_no, :competitors_no_filled, :registration_last_day, :cost, :rules, :summary, :creator_id, :creation_time)", $data_array);
	}
}
?>