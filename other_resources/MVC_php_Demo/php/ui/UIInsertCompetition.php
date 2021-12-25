<br/>

	<div class="row">
		
		<div class="col-xs-12" style="padding-bottom:20px;">
			<div class="col-xs-12">
				<div class="bs-callout" style="border-left-color:#00a8ff;"><div style="font-size:18px;color:#00a8ff;">Καταχώρηση Νέου Αγώνα</div><br/>Παρακαλώ συμπληρώστε όλα τα ακόλουθα πεδία προκειμένου να καταχωρήσετε ένα νέο αγώνα δρόμου στη βάση δεδομένων.<br/>Μόλις ολοκληρώσετε την εισαγωγή πληροφοριών πατήστε το κουμπί "Αποθήκευση Πληροφοριών".</div>
				
				<div class="error-message hidden" id="add-info-error-message" style="padding-top:10px;"></div>

			</div>
		</div>
		
		<div class="col-xs-12" style="padding-bottom:20px;">
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="title" placeholder="Τίτλος Αγώνα"></div>
			</div>
			
			<div class="col-xs-4">
				<select class="form-control" style="height:42px;" id="type_">
				<option value="" selected disabled>Τύπος Αγώνα</option>
				<option value="Αγώνας Δρόμου">Αγώνας Δρόμου</option>
				<option value="Αγώνας Βουνού">Αγώνας Βουνού</option>
				<option value="Αγώνας Εντός Σταδίου">Αγώνας Εντός Σταδίου</option>
				</select>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="race_in_km" placeholder="Χιλιομετρική Απόσταση"></div>
			</div>
			
		</div>
		
		<div class="col-xs-12" style="padding-bottom:20px;">
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="location" placeholder="Τοποθεσία Aγώνα"></div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="date_" placeholder="Ημερομηνία Διεξαγωγής (ΗΗ/ΜΜ/ΕΕΕΕ ΩΩ:ΛΛ)"></div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="duration_limit" placeholder="Χρονικός Περιορισμός"></div>
			</div>
			
		</div>
		
		<div class="col-xs-12" style="padding-bottom:15px;">
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="competitors_no" placeholder="Μέγιστος Αριθμός Συμμετεχόντων"></div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="registration_last_day" placeholder="Προθεσμία Εγγραφής (ΗΗ/ΜΜ/ΕΕΕΕ ΩΩ:ΛΛ)"></div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group"><input type="text" class="form-control" id="cost" placeholder="Κόστος Συμμετοχής"></div>
			</div>
			
		</div>
		
		<div class="col-xs-12" style="padding-bottom:30px;">
			<div class="col-xs-12">
				<textarea class="form-control" rows="5" placeholder="Περιγραφή Αγώνα" id="summary"></textarea>
			</div>
		</div>
		
		<div class="col-xs-12" style="padding-bottom:40px;">
			<div class="col-xs-12">
				<textarea class="form-control" rows="5" placeholder="Κανονισμοί Αγώνα" id="rules"></textarea>
			</div>
		</div>
		
		<div class="col-xs-12" style="padding-bottom:20px;">
			<div class="col-xs-12">
				<button type="button" class="btn btn-success" style="float:right;" href="javascript:void(0);" onclick="save_data();">Αποθήκευση Πληροφοριών</button>
			</div>
		</div>
	</div>
<br/>

<script>
	function save_data()
	{
		var json_array = {};
		json_array["title"] = $('#title').val();
		json_array["type_"] = $('#type_').val();
		json_array["race_in_km"] = $('#race_in_km').val();
		json_array["location"] = $('#location').val();
		json_array["date_"] = $('#date_').val();
		json_array["duration_limit"] = $('#duration_limit').val();
		json_array["competitors_no"] = $('#competitors_no').val();
		json_array["registration_last_day"] = $('#registration_last_day').val();
		json_array["cost"] = $('#cost').val();
		json_array["summary"] = $('#summary').val();
		json_array["rules"] = $('#rules').val();
		
		if(json_array["title"] &&json_array["type_"] && json_array["race_in_km"] && json_array["location"] && json_array["date_"] && json_array["duration_limit"] && json_array["competitors_no"] && json_array["registration_last_day"] && json_array["cost"] && json_array["summary"] && json_array["rules"])
			$.post("/add-competition/?_=" + new Date().getTime(), json_array, function(data, status)
			{
				if($('#add-info-error-message').hasClass('hidden')) $("#add-info-error-message").removeClass('hidden');
				
				if(status == "success" && data == "OK")
				{
					$("#add-info-error-message").css("border-left-color","#1cb841");
					$("#add-info-error-message").html('<div style="font-size:18px;color:#1cb841;">Επιτυχής αποθήκευση!</div></br><p>Οι πληροφορίες αποθηκεύτηκαν επιτυχώς στο σύστημά μας!</p>');
					
					$('#title').val('');
					$('#type_').prop('selectedIndex',0);
					$('#race_in_km').val('');
					$('#location').val('');
					$('#date_').val('');
					$('#duration_limit').val('');
					$('#competitors_no').val('');
					$('#registration_last_day').val('');
					$('#cost').val('');
					$('#summary').val('');
					$('#rules').val('');
				}
				else
				{
					$("#add-info-error-message").css("border-left-color","red");
					$("#add-info-error-message").html('<div style="font-size:18px;color:red;">Σφάλμα κατα την αποθήκευση</div></br><p>Παρακαλώ εισάγατε ορθά όλες τις πληροφορίες που ζητούνται.</p>');
				}
			}
			, "text");
		else
		{
			if($('#add-info-error-message').hasClass('hidden')) $("#add-info-error-message").removeClass('hidden');
					$("#add-info-error-message").css("border-left-color","red");
			$("#add-info-error-message").html('<div style="font-size:18px;color:red;">Σφάλμα κατα την αποθήκευση</div></br><p>Παρακαλώ εισάγατε ορθά όλες τις πληροφορίες που ζητούνται.</p>');
		}
	}
</script>