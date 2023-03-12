var baseUrl = new URL(window.location.href).origin;
$(document).ready(function() {
	'use strict';

	showCategory();

	function showCategory() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/molla/layout/category"
		})
			.then(response => {
				console.log(response)
				response.data.forEach(item => {
					console.log(item)
				});
			});
	}
})