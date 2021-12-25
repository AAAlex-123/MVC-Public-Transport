<style>		
@import url(http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);

.page-header{
  text-align: center;    
}

.bs-callout {
	-moz-border-bottom-colors: none;
	-moz-border-left-colors: none;
	-moz-border-right-colors: none;
	-moz-border-top-colors: none;
	border-color: #eee;
	border-image: none;
	border-radius: 3px;
	border-style: solid;
	border-width: 1px 1px 1px 5px;
	margin-bottom: 5px;
	padding: 20px;
}
.bs-callout:last-child {
	margin-bottom: 0px;
}
.bs-callout h4 {
	margin-bottom: 10px;
	margin-top: 0;
}

.bs-callout-danger {
	border-left-color: #00a8ff;
}

.bs-callout-danger h4{
	color: #00a8ff;
}

.competitions .list-group-item:first-child, .competitions .list-group-item:last-child{
  border-radius:0;
}

</style>
<div class="container">
	<div class="competitions">
		<header class="page-header">
		<div class="col-xs-12 alert alert-info" style="border-radius:0;background-color:#00a8ff;color:#FFFFFF;"><h1 class="page-title"><?php echo $data['title']; ?></h1></div>
		<small> <i class="fa fa-clock-o"></i>&nbsp;&nbsp;Καταχωρήθηκε: <time><?php echo $data['creation_time']; ?></time></small>
	  </header>
	<div class="row">
	  <div class="col-xs-12 col-xs-offset-0">
		<div class="panel panel-default">
		  <div class="panel-heading competitions-heading" style="background-color:#f9f9f9;">
			<div class="row">
			  <div class="col-xs-12"><br/>
				<div class="col-xs-6">
					<br/><br/>
					<center><img class="img-circle img-responsive" alt="" src="<?php echo $data['image']; ?>" style="vertical-align: middle;"></center>
				</div>
				<div class="col-xs-6">
				  <ul class="list-group">
					<li class="list-group-item"><b>Τύπος Αγώνα: </b><?php echo $data['type_']; ?></li>
					<li class="list-group-item"><b>Απόσταση: </b><?php echo $data['race_in_km']; ?></li>
					<li class="list-group-item"><b>Τοποθεσία: </b><?php echo $data['location']; ?></li>
					<li class="list-group-item"><b>Διεξαγωγή: </b><?php echo $data['date_']; ?></li>
					<li class="list-group-item"><b>Χρονικό Όριο: </b><?php echo $data['duration_limit']; ?> Ώρες</li>
					<li class="list-group-item"><b>Συμμετέχοντες: </b><?php echo $data['competitors_no']; ?> Δρομείς</li>
					<li class="list-group-item"><b>Προθεσμία Εγγραφής: </b><?php echo $data['registration_last_day']; ?></li>
					<li class="list-group-item"><b>Κόστος Συμμετοχής: </b><?php echo $data['cost']; ?> ΕΥΡΟ</li>
				  </ul>
				</div>
			  </div>
			</div>
		  </div><br/>
		  <div class="bs-callout bs-callout-danger">
			<h4>Σύνοψη Αγώνα</h4><br/>
			<p style="white-space: pre-wrap;"><?php echo $data['summary']; ?></p>
			<br/>
		  </div><br/>
		  <div class="bs-callout bs-callout-danger">
			<h4>Κανονισμοί Αγώνα</h4><br/>
			<p style="white-space: pre-wrap;"><?php echo $data['rules']; ?></p>
			<br/>
		  </div>
		</div>
	  </div>
	</div>
	</div>
</div>
</br>