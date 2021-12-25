<?php if(!defined('include_const')) die(); ?>
<?php
	class database
	{
		private $conn;
		private $show_errors;
		
		private function db_error($error)
		{
			echo $error;
			die();
		}
		
		public function __construct()
		{
			require_once 'database_config.php';
			
			try
			{
				$this->conn = new PDO("mysql:host=$mysql_host;dbname=$mysql_database_name", $mysql_username, $mysql_password, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8;"));
				$this->run_query("SET SESSION sql_mode = 'STRICT_ALL_TABLES';", array());
				
				if(!$this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION)) //if error throw exception
					die('Database config failed');
			}
			catch(PDOException $e)
			{
				$this->db_error($e->getMessage());
			}
			
			unset($mysql_host);
			unset($mysql_username);
			unset($mysql_password);
			unset($mysql_database_name);
			
			unset($postgresql_host);
			unset($postgresql_username);
			unset($postgresql_password);
			unset($postgresql_database_name);
		}
		
		private function run_query($query_str, $parameters)
		{
			try
			{
				$result = $this->conn->prepare($query_str);
				$result->setFetchMode(PDO::FETCH_ASSOC);
				$result->execute($parameters);
				return $result;
			}
			catch(PDOException $e)
			{
				$this->db_error('function run_query('.$query_str.'):' .$e->getMessage());
				return NULL;
			}
		}
		
		private function fetch_query_data($query_result)
		{
			try
			{
				$result_data = $query_result->fetch();
				return $result_data;
			}
			catch(PDOException $e)
			{
				$this->db_error('function fetch_query_data:' .$e->getMessage());
				return NULL;
			}
		}
		
		//bellow are the public functions
		
		public function update($query_str, $parameters)
		{
			$this->run_query($query_str, $parameters);
		}
		
		public function delete($query_str, $parameters)
		{
			$this->run_query($query_str, $parameters);
		}
		
		public function insert($query_str, $parameters)
		{
			$this->run_query($query_str, $parameters);
		}
		
		public function select($query_str, $parameters)
		{
			$result = $this->run_query($query_str, $parameters);
			$output = array();
			
			while($mysql_array = $this->fetch_query_data($result))
				array_push($output, $mysql_array);
			
			return $output;
		}
	};
?>