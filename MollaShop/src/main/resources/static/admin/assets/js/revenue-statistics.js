var dataRevenue = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
var year = document.getElementById('yearRevenue').value;

demotest();

function demotest() {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/revenue/revenue-year/2023"
	})
		.then(response => {
			response.data.forEach(item => {
				console.log('item->' + item + '-parseInt(item[0] - 1)->' + parseInt(item[0] - 1)
					+ '-parseInt(item[1])-' + parseInt(item[1]))
				dataRevenue[parseInt(item[0] - 1)] = parseInt(item[1]);
			});
		});

	dataRevenue.forEach(item => {
		console.log(item)
	});
}

document.addEventListener("DOMContentLoaded", () => {

	echarts.init(document.querySelector("#revenueStatisticsYear")).setOption({
		xAxis: {
			type: 'category',
			data: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
				"Tháng 10", "Tháng 11", "Tháng 12"]
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			data: dataRevenue,
			type: 'line',
			smooth: true
		}],
		noData: {
			text: 'Không có dữ liệu...'
		}
	});
});