var baseUrl = new URL(window.location.href).origin;

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function updateValueDisplay(id) {
	const inputNumber = document.querySelector('#inputNumberCart' + id);
	const qty = inputNumber.value;

	axios.post(baseUrl + '/molla/cart/update?id=' + id + "&sst=" + qty)
		.then(function(response) {
			let totailCart = 0;
			response.data.forEach(item => {
				let price = 0;
				if (item.discount > 0) {
					totailCart += (item.price - (item.price * item.discount * 0.01)) * item.quantity;
					price = (item.price - (item.price * item.discount * 0.01)) * item.quantity;
				} else {
					totailCart += item.price * item.quantity;
					price = item.price * item.quantity;
				}
				document.getElementById('viewTotailPrice' + id).innerHTML = formatVND(price, ' VND');
			});
			document.getElementById('totailViewCart').innerHTML = formatVND(totailCart, ' VND');
		})
}