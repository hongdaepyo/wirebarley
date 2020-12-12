var main = {
	init : function(){
		var _this = this;
		document.getElementById("submitBtn").addEventListener("click", function(){
			_this.submitBtn_click();
		});
		
		document.getElementById("countrySelect").addEventListener("change", function(){
			_this.clearField();
			_this.countrySelect_change(this);
		});
		
		document.getElementById("countrySelect").dispatchEvent(new Event("change"));
	},
	submitBtn_click : function(){
		var amount = document.getElementById("sendAmount").value;
		var rate = document.getElementById("exchangeRate").textContent;
		rate = rate.replace(",", "");
		
		if (!this.checkSubmitValidation(amount)) {
			this.setSubmitErrorMsg();
			return;
		}
		
		var receivedAmount = amount * rate;
		var result = receivedAmount.toFixed(2).toString();
		result = result.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		
		this.setSubmitSuccessMsg(result);
	},
	countrySelect_change : function(elem){
		this.getCurrency(elem.value);
	},
	getCurrency : function(country){
		var _this = this;
		$.ajax({
			method: "POST",
			url: "/currency",
			data: {country: country},
			success: function(result){
				if (result.success) {
					_this.setExchangeData(result);
				} else {
					alert("환율 데이터를 가져오지 못 했습니다.");
				}
			},
			error: function(){
				alert("오류가 발생했습니다.");
			}
		})
	},
	setExchangeData : function(data){
		document.getElementById("exchangeRate").textContent = data.rate;
		document.getElementById("exchangeRateUnit").textContent = data.toCountry + "/" + data.fromCountry;
	},
	checkSubmitValidation : function(amount){
		var result = true;
		
		if(isNaN(amount)) { //숫자체크
			result = false;
		} else if (amount < 0 || amount > 10000) { //금액체크
			result = false;
		}
		
		return result;
	},
	setSubmitErrorMsg : function(){
		var msg = "송금액이 바르지 않습니다";
		document.getElementById("resultDiv").textContent = msg;
		document.getElementById("resultDiv").className = "error";
	},
	setSubmitSuccessMsg : function(money){
		var unit = document.getElementById("countrySelect").value;
		var msg = "수취금액은 " + money + " " + unit + "입니다.";
		
		document.getElementById("resultDiv").textContent = msg;
		document.getElementById("resultDiv").className = "success";
	},
	clearField : function(){
		document.getElementById("resultDiv").textContent = "";
		
		document.getElementById("exchangeRate").textContent = "";
		document.getElementById("exchangeRateUnit").textContent = "";
		
		document.getElementById("sendAmount").value = "";
	}
}

main.init();