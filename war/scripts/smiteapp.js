window.onload = appOnLoad()

var movesID = new Array(4);
var moveList = [];
var moveLevels = [];
var moveTimes = [];
var moveCooldowns = [];
var start = "false";
var startTime = "0";


function appOnLoad() {
	fillGodNames();
}

$(document).ready(function() {
    hideStacks();
    hideEnemyStacks();
    $("#end").prop("disabled",true);
});


function hideStacks() {
	$('#items1Stacks').hide();
	$('#items2Stacks').hide();
	$('#items3Stacks').hide();
	$('#items4Stacks').hide();
	$('#items5Stacks').hide();
	$('#items6Stacks').hide();
}

function hideEnemyStacks() {
	$('#enemyItems1Stacks').hide();
	$('#enemyItems2Stacks').hide();
	$('#enemyItems3Stacks').hide();
	$('#enemyItems4Stacks').hide();
	$('#enemyItems5Stacks').hide();
	$('#enemyItems6Stacks').hide();
}


function loadGod(godName) {
	hideStacks();
	clearMoveList();
	loadItems(godName);
	loadStats();
	loadMoves();
	
}

function loadEnemyGod(godName) {
	clearMoveList();
	hideEnemyStacks();
	loadEnemyItems(godName);
	loadEnemyStats();

}

function itemChange(itemName) {
	clearMoveList();
	stackCheck(itemName);
	setTimeout(loadStats, 40);
	setTimeout(loadMoves, 40);
	
}

function enemyItemChange(itemName) {
	clearMoveList();
	stackCheck(itemName);
	setTimeout(loadEnemyStats, 40);
	
}

function stackChange() {
	clearMoveList();
	loadStats();
	loadMoves();
}

function enemyStackChange() {
	clearMoveList();
	loadEnemyStats();
}



function levelChange() {
	loadStats();
	calculateMoveListDamage();
}

function stackCheck(itemName) {
	$.ajax({
		url:'getItems?getBy=3&name=' + itemName.value,
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			if (data.items[0].stacks == "false") {
				var itemCombo = "#" + itemName.id + "Stacks";
				$(itemCombo).hide();
			} else {
				var itemCombo = Combo = "#" + itemName.id + "Stacks";
				$(itemCombo).show();
			}
		}
	});

}


function fillGodNames(){
	$.ajax({
		url:'getGods?getBy=2',
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			$('#gods').append($('<option>').text("-"));
			$('#enemyGods').append($('<option>').text("-"));
			$.each(data, function(i, value) {
				$('#gods').append($('<option>').text(value).attr('value', value));
				$('#enemyGods').append($('<option>').text(value).attr('value', value));
			});
		},
		complete: function() {
			$('#screenBlock').hide();
		}
		
	});
}

function loadItems(godName){
	$.ajax({
		url:'getItems?getBy=1&name=' + godName.value,
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			$('#items1').empty();
			$('#items2').empty();
			$('#items3').empty();
			$('#items4').empty();
			$('#items5').empty();
			$('#items6').empty();
			
			$('#items1').append($('<option>').text('-'));
			$('#items2').append($('<option>').text('-'));
			$('#items3').append($('<option>').text('-'));
			$('#items4').append($('<option>').text('-'));
			$('#items5').append($('<option>').text('-'));
			$('#items6').append($('<option>').text('-'));
				
			$.each(data.items, function(i, value) {
				$('#items1').append($('<option>').text(value.name).attr('value', value.name));
				$('#items2').append($('<option>').text(value.name).attr('value', value.name));
				$('#items3').append($('<option>').text(value.name).attr('value', value.name));
				$('#items4').append($('<option>').text(value.name).attr('value', value.name));
				$('#items5').append($('<option>').text(value.name).attr('value', value.name));
				$('#items6').append($('<option>').text(value.name).attr('value', value.name));
				
			});
		}
	});
}


function loadEnemyItems(godName){
	$.ajax({
		url:'getItems?getBy=1&name=' + godName.value,
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			$('#enemyItems1').empty();
			$('#enemyItems2').empty();
			$('#enemyItems3').empty();
			$('#enemyItems4').empty();
			$('#enemyItems5').empty();
			$('#enemyItems6').empty();
			
			$('#enemyItems1').append($('<option>').text('-'));
			$('#enemyItems2').append($('<option>').text('-'));
			$('#enemyItems3').append($('<option>').text('-'));
			$('#enemyItems4').append($('<option>').text('-'));
			$('#enemyItems5').append($('<option>').text('-'));
			$('#enemyItems6').append($('<option>').text('-'));
				
			$.each(data.items, function(i, value) {
				$('#enemyItems1').append($('<option>').text(value.name).attr('value', value.name));
				$('#enemyItems2').append($('<option>').text(value.name).attr('value', value.name));
				$('#enemyItems3').append($('<option>').text(value.name).attr('value', value.name));
				$('#enemyItems4').append($('<option>').text(value.name).attr('value', value.name));
				$('#enemyItems5').append($('<option>').text(value.name).attr('value', value.name));
				$('#enemyItems6').append($('<option>').text(value.name).attr('value', value.name));
				
			});
		}
	});
}



function loadStats(){
	
	
	var url = 'getGods?getBy=4&name=' + $('select[name=godName]').val() + "&level=" + $('select[name=godLevel]').val();
	
	if ($('select[name=item1]').val() != '-'){
		url = url + "&i1=" + $('select[name=item1]').val();
	}
	
	if ($('select[name=item2]').val() != '-'){
		url = url + "&i2=" + $('select[name=item2]').val();
	}
	
	if ($('select[name=item3]').val() != '-'){
		url = url + "&i3=" + $('select[name=item3]').val();
	}
	
	if ($('select[name=item4]').val() != '-'){
		url = url + "&i4=" + $('select[name=item4]').val();
	}
	
	if ($('select[name=item5]').val() != '-'){
		url = url + "&i5=" + $('select[name=item5]').val();
	}
	
	if ($('select[name=item6]').val() != '-'){
		url = url + "&i6=" + $('select[name=item6]').val();
	}
	
	
	
	
	
	if ($('#items1Stacks').is(":visible")) {
		url = url + "&i1s=" + $('#items1Stacks').val();
	}
	
	if ($('#items2Stacks').is(":visible")) {
		url = url + "&i2s=" + $('#items2Stacks').val();
	}
	
	if ($('#items3Stacks').is(":visible")) {
		url = url + "&i3s=" + $('#items3Stacks').val();
	}
	
	if ($('#items4Stacks').is(":visible")) {
		url = url + "&i4s=" + $('#items4Stacks').val();
	}
	
	if ($('#items5Stacks').is(":visible")) {
		url = url + "&i5s=" + $('#items5Stacks').val();
	}
	
	if ($('#items6Stacks').is(":visible")) {
		url = url + "&i6s=" + $('#items6Stacks').val();
	}
	

	
	
	
	$.ajax({
		url: url,
		type:'POST',
		dataType: 'json',
		success: function(data) {

			var god = data.gods[0];
			
			document.getElementById("godPantheon").innerHTML = 'Pantheon: ' + god.pantheon;
			document.getElementById("godClass").innerHTML = 'Class: ' + god.godClass;
			
			if (god.type) {
				document.getElementById("godType").innerHTML = 'Type: Physical';
			} else {
				document.getElementById("godType").innerHTML = 'Type: Magical';
			}
			
			document.getElementById("godHealth").innerHTML = 'Health: ' + god.baseHealth;
			document.getElementById("godMana").innerHTML = 'Mana: ' + god.baseMana;
			document.getElementById("godPower").innerHTML = 'Power: ' + god.power;
			document.getElementById("godSpeed").innerHTML = 'Speed: ' + god.speed;
			document.getElementById("godRange").innerHTML = 'Range: ' + god.range;
			document.getElementById("godAPS").innerHTML = 'Attacks Per Second: ' + god.baseAttacksPerSecond;
			document.getElementById("godBasicDam").innerHTML = 'Basic Attack Damage: ' + god.baseAttackDamage;
			document.getElementById("godDPS").innerHTML = 'Basic Attack DPS: ' + god.basicDPS;
			document.getElementById("godAPS").innerHTML = 'Attacks Per Second: ' + god.baseAttacksPerSecond;
			document.getElementById("godPhysProt").innerHTML = 'Physical Protection: ' + god.basePhysicalProtection;
			document.getElementById("godMagProt").innerHTML = 'Magical Protection: ' + god.baseMagicalProtection;
			document.getElementById("godPen").innerHTML = 'Penetration: ' + god.penetration;
			document.getElementById("godCrit").innerHTML = 'Crit Chance: ' + god.critChance;
			document.getElementById("godCDR").innerHTML = 'Cooldown Reduction: ' + god.cooldownReduction + '%';
			

		}
	});
}


function loadEnemyStats(){
	
	var url = 'getGods?getBy=4&name=' + $('select[name=enemyGodName]').val() + "&level=" + $('select[name=enemyGodLevel]').val();
	
	if ($('select[name=enemyItem1]').val() != '-'){
		url = url + "&i1=" + $('select[name=enemyItem1]').val();
	}
	
	if ($('select[name=enemyItem2]').val() != '-'){
		url = url + "&i2=" + $('select[name=enemyItem2]').val();
	}
	
	if ($('select[name=enemyItem3]').val() != '-'){
		url = url + "&i3=" + $('select[name=enemyItem3]').val();
	}
	
	if ($('select[name=enemyItem4]').val() != '-'){
		url = url + "&i4=" + $('select[name=enemyItem4]').val();
	}
	
	if ($('select[name=enemyItem5]').val() != '-'){
		url = url + "&i5=" + $('select[name=enemyItem5]').val();
	}
	
	if ($('select[name=enemyItem6]').val() != '-'){
		url = url + "&i6=" + $('select[name=enemyItem6]').val();
	}
	
	
	
	
	if ($('#enemyItems1Stacks').is(":visible")) {
		url = url + "&i1s=" + $('#enemyItems1Stacks').val();
	}
	
	if ($('#enemyItems2Stacks').is(":visible")) {
		url = url + "&i2s=" + $('#enemyItems2Stacks').val();
	}
	
	if ($('#enemyItems3Stacks').is(":visible")) {
		url = url + "&i3s=" + $('#enemyItems3Stacks').val();
	}
	
	if ($('#enemyItems4Stacks').is(":visible")) {
		url = url + "&i4s=" + $('#enemyItems4Stacks').val();
	}
	
	if ($('#enemyItems5Stacks').is(":visible")) {
		url = url + "&i5s=" + $('#enemyItems5Stacks').val();
	}
	
	if ($('#enemyItems6Stacks').is(":visible")) {
		url = url + "&i6s=" + $('#enemyItems6Stacks').val();
	}
	
	

	
	$.ajax({
		url: url,
		type:'POST',
		dataType: 'json',
		success: function(data) {

			var god = data.gods[0];
			
			document.getElementById("enemyGodPantheon").innerHTML = 'Pantheon: ' + god.pantheon;
			document.getElementById("enemyGodClass").innerHTML = 'Class: ' + god.godClass;
			
			if (god.type) {
				document.getElementById("enemyGodType").innerHTML = 'Type: Physical';
			} else {
				document.getElementById("enemyGodType").innerHTML = 'Type: Magical';
			}
			
			document.getElementById("enemyGodHealth").innerHTML = 'Health: ' + god.baseHealth;
			document.getElementById("enemyGodMana").innerHTML = 'Mana: ' + god.baseMana;
			document.getElementById("enemyGodPower").innerHTML = 'Power: ' + god.power;
			document.getElementById("enemyGodSpeed").innerHTML = 'Speed: ' + god.speed;
			document.getElementById("enemyGodRange").innerHTML = 'Range: ' + god.range;
			document.getElementById("enemyGodAPS").innerHTML = 'Attacks Per Second: ' + god.baseAttacksPerSecond;
			document.getElementById("enemyGodPhysProt").innerHTML = 'Physical Protection: ' + god.basePhysicalProtection;
			document.getElementById("enemyGodMagProt").innerHTML = 'Magical Protection: ' + god.baseMagicalProtection;
			document.getElementById("enemyGodPen").innerHTML = 'Penetration: ' + god.penetration;
			document.getElementById("enemyGodCrit").innerHTML = 'Crit Chance: ' + god.critChance;
			

		}
	});
}

function loadMoves() {
	
	
	var url = 'getGods?getBy=4&name=' + $('select[name=godName]').val() + "&level=" + $('select[name=godLevel]').val();
	if ($('select[name=item1]').val() != '-'){
		url = url + "&i1=" + $('select[name=item1]').val();
	}
	
	if ($('select[name=item2]').val() != '-'){
		url = url + "&i2=" + $('select[name=item2]').val();
	}
	
	if ($('select[name=item3]').val() != '-'){
		url = url + "&i3=" + $('select[name=item3]').val();
	}
	
	if ($('select[name=item4]').val() != '-'){
		url = url + "&i4=" + $('select[name=item4]').val();
	}
	
	if ($('select[name=item5]').val() != '-'){
		url = url + "&i5=" + $('select[name=item5]').val();
	}
	
	if ($('select[name=item6]').val() != '-'){
		url = url + "&i6=" + $('select[name=item6]').val();
	}
	
	
	if ($('#items1Stacks').is(":visible")) {
		url = url + "&i1s=" + $('#items1Stacks').val();
	}
	
	if ($('#items2Stacks').is(":visible")) {
		url = url + "&i2s=" + $('#items2Stacks').val();
	}
	
	if ($('#items3Stacks').is(":visible")) {
		url = url + "&i3s=" + $('#items3Stacks').val();
	}
	
	if ($('#items4Stacks').is(":visible")) {
		url = url + "&i4s=" + $('#items4Stacks').val();
	}
	
	if ($('#items5Stacks').is(":visible")) {
		url = url + "&i5s=" + $('#items5Stacks').val();
	}
	
	if ($('#items6Stacks').is(":visible")) {
		url = url + "&i6s=" + $('#items6Stacks').val();
	}
	
	

	$.ajax({
		url: url,
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			$('#move1').empty();
			$('#move2').empty();
			$('#move3').empty();
			$('#move4').empty();
			
			$('#move1dmg').empty();
			$('#move2dmg').empty();
			$('#move3dmg').empty();
			$('#move4dmg').empty();
			
			$('#move1').append(data.moves[0].moveName);
			$('#move2').append(data.moves[1].moveName);
			$('#move3').append(data.moves[2].moveName);
			$('#move4').append(data.moves[3].moveName);
			
			movesID[0] = data.moves[0].moveID;
			movesID[1] = data.moves[1].moveID;
			movesID[2] = data.moves[2].moveID;
			movesID[3] = data.moves[3].moveID;
			
			var level = parseInt($('select[name=move1level]').val());
			
			var dmgInc = parseInt(data.moves[0].damageIncrease);
			var powScal = parseInt(data.moves[0].powerScaling);
			var move1dmg = parseInt(data.moves[0].damage);
			var power = parseInt(data.gods[0].power);

			move1dmg = move1dmg + (dmgInc * (level-1));
			move1dmg = move1dmg + (powScal / 100 * power);
			move1dmg = Math.round(move1dmg);
			
			
			level = parseInt($('select[name=move2level]').val());	
			dmgInc = parseInt(data.moves[1].damageIncrease);
			powScal = parseInt(data.moves[1].powerScaling);
			var move2dmg = parseInt(data.moves[1].damage);

			move2dmg = move2dmg + (dmgInc * (level-1));
			move2dmg = move2dmg + (powScal / 100 * power);
			move2dmg = Math.round(move2dmg);
			
			level = parseInt($('select[name=move3level]').val());	
			if (data.moves[2].moveName == "Fleeting Breath") {
				dmgInc = parseInt(data.moves[2].tickDamageIncrease);
				powScal = parseInt(data.moves[2].tickScaling);
				var move3dmg = parseInt(data.moves[2].tickDamage);

				
			} else {	
				dmgInc = parseInt(data.moves[2].damageIncrease);
				powScal = parseInt(data.moves[2].powerScaling);
				var move3dmg = parseInt(data.moves[2].damage);

			}
			
			move3dmg = move3dmg + (dmgInc * (level-1));
			move3dmg = move3dmg + (powScal / 100 * power);
			move3dmg = Math.round(move3dmg);
			
			level = parseInt($('select[name=move4level]').val());	
			if (data.moves[3].moveName == "Empty the Crypts") {
				dmgInc = parseInt(data.moves[3].tickDamageIncrease);
				powScal = parseInt(data.moves[3].tickScaling);
				var move4dmg = parseInt(data.moves[3].tickDamage);
			} else {
				dmgInc = parseInt(data.moves[3].damageIncrease);
				powScal = parseInt(data.moves[3].powerScaling);
				var move4dmg = parseInt(data.moves[3].damage);
			}

			move4dmg = move4dmg + (dmgInc * (level-1));
			move4dmg = move4dmg + (powScal / 100 * power);
			move4dmg = Math.round(move4dmg);
					
			
			$('#move1dmg').append(move1dmg);
			$('#move2dmg').append(move2dmg);
			$('#move3dmg').append(move3dmg);
			$('#move4dmg').append(move4dmg);
			
			
			$('#move1button').text(data.moves[0].moveName);
			$('#move2button').text(data.moves[1].moveName);
			$('#move3button').text(data.moves[2].moveName);
			$('#move4button').text(data.moves[3].moveName);
			
			moveCooldowns = [];
			moveCooldowns.push(data.moves[0].cooldown * (1 -(data.gods[0].cooldownReduction / 100)));
			moveCooldowns.push(data.moves[1].cooldown * (1 -(data.gods[0].cooldownReduction / 100)));
			moveCooldowns.push(data.moves[2].cooldown * (1 -(data.gods[0].cooldownReduction / 100)));
			moveCooldowns.push(data.moves[3].cooldown * (1 -(data.gods[0].cooldownReduction / 100)));
			var apsButton = 1 / data.gods[0].baseAttacksPerSecond
			moveCooldowns.push(apsButton);
			moveCooldowns.push("3");
			moveCooldowns.push("40");
			
			loadMoveInfo(data);
			
		}
	});
}


function loadMoveInfo(data) {
	$('#move1info').empty();
	$('#move2info').empty();
	$('#move3info').empty();
	$('#move4info').empty();
	
	if (data.moves[1].moveID == 2) {
		$('#move2info').append('<label for="move2inf">Corpses:</label><input id="move2inf" type="number" value="0" style="width:35px">');
	} else if (data.moves[1].moveID == 6) {
		$('#move2info').append('<label for="move2inf">Mirror Charge:</label><input id="move2inf" type="number" value="0" style="width:35px">');
	}
	
	if (data.moves[2].moveID == 3) {
		$('#move3info').append('<label for="move2inf">Enemy Heal?</label><input id="move3inf" type="checkbox">');
	}
	
	if (data.moves[3].moveID == 4) {
		$('#move4info').append('<label for="move2inf">Estimated Hits:</label><input id="move4inf" type="number" value="0" style="width:30px">');
	} else if (data.moves[3].moveID == 8) {
		$('#move4info').append('<label for="move2inf">Hits:</label><input id="move4inf" type="number" value="0" style="width:30px">');
	}
	
	
	
}


function addMove(move) {
	var cooldown;
	if ($('select[name=godName]').val() == '-') {
		alert("No God Selected");
	} else if ($('select[name=enemyGodName]').val() == '-') {
		alert("No Enemy God Selected");
	} else {
		if (start == "false") {
			$("#end").prop("disabled",false);
			start = "true";
			startTime = new Date().getTime();
		}
		
		var i = move.id.substring(4, 5);
		$("#" + move.id).prop("disabled",true);
		
		
		
		if (move.id == "move1button") {
			moveList.push(movesID[0]);
			moveLevels.push($('select[name=move1level]').val())
			moveInfo.push("0");
			cooldown = 0;
		} else if (move.id =="move2button") {
			moveList.push(movesID[1]);
			moveLevels.push($('select[name=move2level]').val());
			moveInfo.push($('#move2inf').val());
			cooldown = 1;
		} else if (move.id =="move3button") {
			moveList.push(movesID[2]);
			moveLevels.push($('select[name=move3level]').val());
			cooldown = 2;
			if ( $('input[id="move3inf"]').is(':checked') ) {
				moveInfo.push("true");
			} else {
				moveInfo.push("false");
			}
			
		} else if (move.id =="move4button") {
			moveList.push(movesID[3]);
			moveLevels.push($('select[name=move4level]').val());
			moveInfo.push($('#move4inf').val());
			cooldown = 3;
		} else if (move.id =="basicAttack") {
			moveList.push("999");
			moveLevels.push("1");
			moveInfo.push("0");
			cooldown = 4;
		} else if (move.id =="mysticalMail") {
			moveList.push("1000");
			moveLevels.push("1");
			moveInfo.push("0");
		} else if (move.id =="polynomicon") {
			moveList.push("1001");
			moveLevels.push("1");
			moveInfo.push("0");
			cooldown = 5;
		} else if (move.id =="qins") {
			moveList.push("1002");
			moveLevels.push("1");
			moveInfo.push("0");
			cooldown = 4;
		} else if (move.id =="soulReaver") {
			moveList.push("1003");
			moveLevels.push("1");
			moveInfo.push("0");
			cooldown = 6;
		}
		
		
		setTimeout(function(){
			$("#" + move.id).prop("disabled",false);
		}, (moveCooldowns[cooldown])*1000);
		
		moveTimes.push(new Date().getTime());
		
	}
	
}

function calculateMoveListDamage() {
	var url = 'getDamage?startTime=' + startTime + '&name=' + $('select[name=godName]').val() + "&level=" + $('select[name=godLevel]').val();
	if ($('select[name=item1]').val() != '-'){
		url = url + "&i1=" + $('select[name=item1]').val();
	}
	
	if ($('select[name=item2]').val() != '-'){
		url = url + "&i2=" + $('select[name=item2]').val();
	}
	
	if ($('select[name=item3]').val() != '-'){
		url = url + "&i3=" + $('select[name=item3]').val();
	}
	
	if ($('select[name=item4]').val() != '-'){
		url = url + "&i4=" + $('select[name=item4]').val();
	}
	
	if ($('select[name=item5]').val() != '-'){
		url = url + "&i5=" + $('select[name=item5]').val();
	}
	
	if ($('select[name=item6]').val() != '-'){
		url = url + "&i6=" + $('select[name=item6]').val();
	}
	
	
	if ($('#items1Stacks').is(":visible")) {
		url = url + "&i1s=" + $('#items1Stacks').val();
	}
	
	if ($('#items2Stacks').is(":visible")) {
		url = url + "&i2s=" + $('#items2Stacks').val();
	}
	
	if ($('#items3Stacks').is(":visible")) {
		url = url + "&i3s=" + $('#items3Stacks').val();
	}
	
	if ($('#items4Stacks').is(":visible")) {
		url = url + "&i4s=" + $('#items4Stacks').val();
	}
	
	if ($('#items5Stacks').is(":visible")) {
		url = url + "&i5s=" + $('#items5Stacks').val();
	}
	
	if ($('#items6Stacks').is(":visible")) {
		url = url + "&i6s=" + $('#items6Stacks').val();
	}
	
	
	url = url + '&enemyName=' + $('select[name=enemyGodName]').val() + "&enemyLevel=" + $('select[name=enemyGodLevel]').val();
	
	if ($('select[name=enemyItem1]').val() != '-'){
		url = url + "&ei1=" + $('select[name=enemyItem1]').val();
	}
	
	if ($('select[name=enemyItem2]').val() != '-'){
		url = url + "&ei2=" + $('select[name=enemyItem2]').val();
	}
	
	if ($('select[name=enemyItem3]').val() != '-'){
		url = url + "&ei3=" + $('select[name=enemyItem3]').val();
	}
	
	if ($('select[name=enemyItem4]').val() != '-'){
		url = url + "&ei4=" + $('select[name=enemyItem4]').val();
	}
	
	if ($('select[name=enemyItem5]').val() != '-'){
		url = url + "&ei5=" + $('select[name=enemyItem5]').val();
	}
	
	if ($('select[name=enemyItem6]').val() != '-'){
		url = url + "&ei6=" + $('select[name=enemyItem6]').val();
	}
	
	
	
	
	if ($('#enemyItems1Stacks').is(":visible")) {
		url = url + "&ei1s=" + $('#enemyItems1Stacks').val();
	}
	
	if ($('#enemyItems2Stacks').is(":visible")) {
		url = url + "&ei2s=" + $('#enemyItems2Stacks').val();
	}
	
	if ($('#enemyItems3Stacks').is(":visible")) {
		url = url + "&ei3s=" + $('#enemyItems3Stacks').val();
	}
	
	if ($('#enemyItems4Stacks').is(":visible")) {
		url = url + "&ei4s=" + $('#enemyItems4Stacks').val();
	}
	
	if ($('#enemyItems5Stacks').is(":visible")) {
		url = url + "&ei5s=" + $('#enemyItems5Stacks').val();
	}
	
	if ($('#enemyItems6Stacks').is(":visible")) {
		url = url + "&ei6s=" + $('#enemyItems6Stacks').val();
	}
	
	$.each(moveList, function(i, val ) {
		url = url + '&moves=' + moveList[i];
		  return ( val !== moveList.length );
		});
	
	$.each(moveLevels, function(i, val ) {
		url = url + '&moveLevels=' + moveLevels[i];
		  return ( val !== moveList.length );
		});
	
	$.each(moveTimes, function(i, val ) {
		url = url + '&moveTimes=' + moveTimes[i];
		  return ( val !== moveList.length );
		});
	
	$.each(moveInfo, function(i, val ) {
		url = url + '&moveInfo=' + moveInfo[i];
		  return ( val !== moveList.length );
		});

	
	console.log(url);
	
	$.ajax({
		url: url,
		type:'POST',
		dataType: 'json',
		success: function( data ) {
			$('#attackList').html('');
			var table = getTable(data);
			$("#attackList").append(table);
		}
	});
}








function getTable(data) {
	  var table = "<table border='1' class='attackTable'>\n" +
	  		
	  		"<tr><th style='width:50px'>No.</th>\n" +
	  		"<th style='width:250px'>Attack</th>\n" +
	  		"<th style='width:110px'>Attack Damage</th>\n" +
	  		"<th style='width:140px'>Cumulative Damage</th>\n" +
			"<th style='width:70px'>Time</th>\n" +
			"<th style='width:100px'>Average DPS</th></tr>\n" +
	              getTableBody(data) +
	              "</table>";
	  return(table);
}

function getTableBody(data) {
	var body = "";
	for(var i=0; i < data.moves.length; i++) {
		body += "  <tr>";
		var row = data.moves[i];
		body += "<td>" + (i + 1) + "</td>";
		body += "<td>" + row.name + "</td>";
		body += "<td>" + row.damage + "</td>";
		body += "<td>" + row.cumDam + "</td>";
		body += "<td>" + row.time + "</td>";
		body += "<td>" + row.avgDPS + "</td>";
		
		body += "</tr>\n";
	}
	return(body);
}




function endCombo() {
	start = "false";
	calculateMoveListDamage();
	setTimeout(function() {
		$("#end").prop("disabled",true);
		moveList = [];
		moveLevels = [];
		moveTimes = [];
		moveInfo = [];
		
		$("#move1button").prop("disabled",false);
		$("#move2button").prop("disabled",false);
		$("#move3button").prop("disabled",false);
		$("#move4button").prop("disabled",false);
		$("#basicAttack").prop("disabled",false);
		
		
	}, 70);
}


function clearMoveList() {
	$("#attackList").empty();
	var table = "<table border='1' class='attackTable'>\n" +
		"<tr><th style='width:50px'>No.</th>\n" +
		"<th style='width:250px'>Attack</th>\n" +
		"<th style='width:110px'>Attack Damage</th>\n" +
		"<th style='width:140px'>Cumulative Damage</th>\n" +
		"<th style='width:70px'>Time</th>\n" +
		"<th style='width:100px'>Average DPS</th></tr>\n" +
        "</table>";
	$("#attackList").append(table);
	moveList = [];
	moveLevels = [];
	moveTimes = [];
	moveInfo = [];
}







