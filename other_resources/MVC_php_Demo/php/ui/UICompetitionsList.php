<style>
	.glyphicon { margin-right:5px; }
	.thumbnail
	{
		margin-bottom: 20px;
		padding: 0px;
		-webkit-border-radius: 0px;
		-moz-border-radius: 0px;
		border-radius: 0px;
	}

	.item.list-group-item
	{
		float: none;
		width: 100%;
		background-color: #fff;
		margin-bottom: 10px;
	}
	.item.list-group-item:nth-of-type(odd):hover,.item.list-group-item:hover
	{
		background: #428bca;
	}

	.item.list-group-item .list-group-image
	{
		margin-right: 10px;
	}
	.item.list-group-item .thumbnail
	{
		margin-bottom: 0px;
	}
	.item.list-group-item .caption
	{
		padding: 9px 9px 0px 9px;
	}
	.item.list-group-item:nth-of-type(odd)
	{
		background: #eeeeee;
	}

	.item.list-group-item:before, .item.list-group-item:after
	{
		display: table;
		content: " ";
	}

	.item.list-group-item img
	{
		float: left;
	}
	.item.list-group-item:after
	{
		clear: both;
	}
	.list-group-item-text
	{
		margin: 0 0 11px;
	}
	
	a {
		text-decoration: none;
		color: #000000;
	}
</style>

</br>
<div class="bs-callout" style="border-left-color:#00a8ff;"><div style="font-size:18px;color:#00a8ff;">Λίστα Διαθέσιμων Αγώνων</div><br/>Ακολουθεί η λίστα των διαθέσιμων αγώνων ταξινομημένοι με βάση την ημερομηνία Διεξαγωγής.<br/>Με τη χρήστη των ακόλουθων λιστών μπορείτε να φιλτράρετε τους αγώνες που εμφανίζοντε.</div>

<?php
	if(count($data) == 0)
		echo '<div class="error-message" id="add-info-error-message" style="padding-top:10px;"><div style="font-size:18px;color:red;">Δεν Υπάρχουν Αγώνες</div></br><p>Δεν υπάρχουν αγώνες για το Φιλτράρισμα που επιλέξατε. Παρακαλώ πολύ δοκιμάστε άλλα κριτήρια Φιλτραρίσματος.</p></div>';
?>
<br>

<div class="col-xs-12" style="padding-top:9px;height:60px;line-height:50px;border-style: solid;border-width: 1px;border-color: #e7e9ed;margin-bottom:35px;">
	<div class="col-xs-3">
		<select class="form-control" style="height:42px;" id="select_type">
		<option value="" selected>Όλοι οι Τύποι</option>
			<?php for($i = 0; $i < count($selector[0]); $i++) echo '<option value="'.$selector[0][$i].'">'.$selector[0][$i].'</option>'; ?>
		</select>
	</div>
	
	<div class="col-xs-3">
		<select class="form-control" style="height:42px;" id="select_location">
		<option value="" selected>Όλες οι Τοποθεσίες</option>
			<?php for($i = 0; $i < count($selector[1]); $i++) echo '<option value="'.$selector[1][$i].'">'.$selector[1][$i].'</option>'; ?>
		</select>
	</div>
	
	<div class="col-xs-3">
		<select class="form-control" style="height:42px;" id="select_date">
		<option value="" selected>Όλες οι Ημερομηνίες</option>
			<?php for($i = 0; $i < count($selector[2]); $i++) echo '<option value="'.$selector[2][$i].'">'.$selector[2][$i].'</option>'; ?>
		</select>
	</div>
	
	<div class="col-xs-3">
		<div style="height:42px;line-height:50px;">
			<button type="button" class="btn btn-success" href="javascript:void(0);" onclick="filter();" style="width:100%;height:100%;margin-bottom:10px;">Φιλτράρισμα</button>
		</div>
	</div>
</div>

<div id="products" class="row list-group">

<?php
	foreach ($data as $key => $value)
	{
			echo '
			<div class="item  col-xs-4 col-lg-4">
				<a href="/details/'.$value['id'].'" style="text-decoration: none;">
					<div class="thumbnail" style="padding:10px;">
						<img class="group list-group-image" src="'.$value['image'].'"/>
						<div class="caption">
							<h4 class="group inner list-group-item-heading"><br/>
								'.$value['title'].'</h4>
								<hr>
							<p class="group inner list-group-item-text">
								<div style="padding-bottom:5px;padding-top:0px;font-size:16px;"><div style="display:inline;font-weight:500;">Τοποθεσία:</div> '.$value['location'].'</div>
								<div style="padding-bottom:5px;padding-top:0px;font-size:16px;"><div style="display:inline;font-weight:500;">Τύπος:</div> '.$value['type_'].'</div>
								<div style="padding-bottom:5px;display:inline;padding-top:0px;font-size:16px;"><div style="display:inline;font-weight:500;">Διεξαγωγή:</div> '.$value['date_'].'</div>
								</p>
								<hr>
								
								<div style="padding-bottom:10px;">'.$value['free_positions'].' από τις '.$value['competitors_no'].' Θέσεις Ελεύθερες</div>
							<div class="progress">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="'.$value['progress'].'" aria-valuemin="0" aria-valuemax="100" style="width: '.$value['progress'].'%">
									<span class="sr-only"></span>
								</div>
								
								<span class="progress-type"></span>
								<span class="progress-completed"></span>
							</div>
								
						</div>
					</div>
				</a>
			</div>
			';
	}
?>

</div></br>

<script>
	function filter()
	{
		$('body').append($('<form/>')
		  .attr({'action': '/', 'method': 'post', 'id': 'replacer'})
		  .append($('<input/>').attr({'type': 'hidden', 'name': 'select_type', 'value': $('#select_type').val()}))
		  .append($('<input/>').attr({'type': 'hidden', 'name': 'select_location', 'value': $('#select_location').val()}))
		  .append($('<input/>').attr({'type': 'hidden', 'name': 'select_date', 'value': $('#select_date').val()}))
		).find('#replacer').submit();
	}
</script>
