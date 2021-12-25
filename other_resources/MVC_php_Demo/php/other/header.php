<?php if(!defined('include_const')) die(); ?>
<?php header('Content-Type: text/html; charset=utf-8'); ?><!DOCTYPE html>
<html lang="el">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=1200"/>
		<meta name="description" content="Λίστα Αγώνων Δρόμου">
		<title>Λίστα Αγώνων Δρόμου</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
		
		<link href="https://fonts.googleapis.com/css?family=Roboto:400,500,700&amp;subset=greek" rel="stylesheet">
		
		
		<link href="../../assets/style.css" rel="stylesheet">
		<link href="../../../assets/custom-navbar.css" rel="stylesheet">
		
	</head>
	<body style="background-color:#fafafa;">
		<div class="logo_container">
			<div class="container">
				<div class="row">
					<a href="/"><div class="top_logo"></div></a>
				</div>
			</div>
		</div>
		
		<nav class="navbar navbar-default navbar-static-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li <?php if($page_now == 'home') echo 'class="active"'; ?>><a href="/">Λίστα Διαθέσιμων Αγώνων</a></li>
						<li <?php if($page_now == 'add-competition') echo 'class="active"'; ?>><a href="/add-competition">Καταχώρηση Νέου Αγώνα</a></li>
						<?php /*<li <?php if($page_now == 'competitions') echo 'class="active"'; ?>><a href="/competitions">Λίστα Διαθέσιμων Αγώνων</a></li>*/ ?>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li <?php if($page_now == 'login') echo 'class="active"'; ?>><a href="/login">Σύνδεση</a></li>
						<li <?php if($page_now == 'register') echo 'class="active"'; ?>><a href="/register">Εγγραφή</a></li>
					</ul>
				</div>
			</div>
		</nav>
		
		<div class="container">