var main = {
	init : function(){
		var _this = this;
		document.getElementById("submitBtn").addEventListener("click", function(){
			_this.submitBtn_click();
		});
		
		document.getElementById("countrySelect").addEventListener("change", function(){
			_this.countrySelect_change(this);
		});
	},
	submitBtn_click : function(){
		alert(1);
	},
	countrySelect_change : function(elem){
	},
	getCurrency : function(country){
		var _this = this;
		$.ajax({
			method: "POST",
			url: "/currency",
			data: {country: country},
			success: function(result){
				console.log(result);
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
	}
}

main.init();