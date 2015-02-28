Ext.namespace("Thunder");
Ext.namespace('Thunder.Converts');
function getUnitId() {
}
Thunder.unit = 10000;
function formatFloat(value, digits) {
	digits = digits == null ? 2 : digits;
	var baseValue = Math.pow(10, digits);
	value = Math.round(parseFloat(value) * baseValue) / baseValue;
	value = value.toString();
	if (value.toString().indexOf(".") > 0) {
		digits = digits
				- value.substr(value.toString().indexOf(".") + 1).length;
		if (digits > 0) {
			for (var i = 0; i < digits; i++) {
				value += "0";
			}
		}
	}
	return value;
}
function format(v, formatString) {
	var parterns = formatString.split(".");
	v = formatFloat(v,parterns[1].length);
	var numbers = v.toString().split(".");
	var lparterns = parterns[0].split("");
	var lparternsbak = parterns[0].split("");
	var lnumbers = numbers[0].split("");
	var lkeep = "";
	var rkeep = "";
	// 得到左侧要替换的部分
	var lplaces = [];
	for (var i = 0; i < lparterns.length; i++) {
		var parternchar = lparterns[i];
		if (parternchar == "#" || parternchar == "0") {
			lplaces.push(i);
		}
	}
	// 替换左侧，左侧有数字才要替换，以避免v = .99型的数字而产生错误
	if (lnumbers[0] && lnumbers[0].length > 0) {
		var numberIndex = lnumbers.length - 1;
		var replaced = 0;
		for (var i = lplaces.length - 1; i >= 0; i--) {
			replaced++; // 被替换的字符数量
			var place = lplaces[i];
			lparterns[place] = lnumbers[numberIndex];

			if (numberIndex == 0) {
				break;
			}
			numberIndex--;
		}
		// 处理以#为第一个格式（#前可能有非0的其他串也在此范围）的格式串，对于以#开头的格式串，将不会截取数字串，要补齐
		var lstartIdx = lplaces[0];

		if (lparternsbak[lstartIdx] == "#") {
			if (lnumbers.length > replaced) {
				var idx = lnumbers.length - replaced;
				for (var i = 0; i < idx; i++) {
					lkeep += lnumbers[i];
				}

				lparterns[lstartIdx] = lkeep + lparterns[lstartIdx];
			}
		}
	}
	var rparterns = formatFloat("0."+numbers[1],parterns[1].length);
	// 替换右侧
	
//	if (parterns[1] && parterns[1].length > 0) {
//		var rparterns = parterns[1].split("");
//		var rparternsbak = parterns[1].split("");
//
//		if (numbers[1] && numbers[1].length > 0) {
//			var rnumbers = numbers[1].split("");
//			// 得到右侧将要替换的部分
//			var rplaces = [];
//			for (var i = 0; i < rparterns.length; i++) {
//				var parternchar = rparterns[i];
//				if (parternchar == "#" || parternchar == "0") {
//					rplaces.push(i);
//				}
//			}
//			var replaced = 0;
//			for (var i = 0; i < rplaces.length; i++) {
//				replaced++; // 被替换的字符数量
//				var place = rplaces[i];
//				rparterns[place] = rnumbers[i];
//
//				if (i == rnumbers.length - 1) {
//					break;
//				}
//			}
//			// 处理以#结束的（#后有非0的串也在此范围）
//			var rlastIdx = rplaces[rplaces.length - 1];
//			if (rparternsbak[rlastIdx] == "#") {
//				for (var i = replaced - 1; i < rnumbers.length; i++) {
//					rkeep += rnumbers[i];
//				}
//				rparterns[rlastIdx] += rkeep;
//			}
//		}
//	}

	for (var i = 0; i < lparterns.length; i++) {
		if (lparterns[i] == "#") {
			lparterns[i] = "";
		}
	}
	var result = lparterns.join("");
	if (parterns[1]) {
		for (var i = 0; i < rparterns.length; i++) {
			if (rparterns[i] == "#") {
				rparterns[i] = "";
			}
		}
		rparterns.split(".").length==1?result:(result += "." + rparterns.split(".")[1]);
		//result += "." + (?rparterns.split(".")[0]:rparterns.split(".")[1]);
	}

	// 第一位不能为,号
	if (result.substring(0, 1) == ",") {
		result = result.substring(1);
	}

	// 最后一位也不能为,号
	if (result.substring(result.length - 1) == ",") {
		result = result.substring(0, result.length);
	}
	return result;
}

function anylog(a, b) {
	var c = (Math.log(b)) / (Math.log(a))
	var d = Math.round(1000000 * c) / 1000000
	return d;
}
// 计划监测预警状态（grid renderer）
Thunder.alterType = function(value) {
	return '<img src="../images/portlet/' + value + '.gif">';
}

// 个计 万计切换
Thunder.changeUnit = function(value) {
	var rpattern=".";
	for(var i=0;i<Thunder.acc;i++){
		rpattern+="0";
	}
	if (value == "" || value == "null")
		return "";
	else if (value.toString().indexOf("%") > 0)
		return value;
	else{
		var group = parseInt(parseInt(value/Thunder.unit).toString().length/3)+1;
		var lpattern="";
		for(var j=0;j<group;j++){
			lpattern+="###,"
		}
		lpattern = lpattern.substring(0, lpattern.length-1);
		//alert(value+"==="+Thunder.unit+"==="+lpattern+rpattern);
		return format(value/Thunder.unit, lpattern+rpattern);
	}
}

Thunder.Converts.emptyNumberConvert = function(v){
	 return v !== undefined && v !== null && v !== '' ?
                    parseInt(String(v).replace(Ext.data.Types.stripRe, ''), 10) : null;
}

Thunder.Converts.emptyFloat2pixConvert = function(v){
	 return v !== undefined && v !== null && v !== '' ?
                    parseFloat(parseFloat(String(v).replace(Ext.data.Types.stripRe, '')).toFixed(2)) : null;
}
