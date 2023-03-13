var baseUrl = new URL(window.location.href).origin;
var bodyQuickView = document.getElementById('bodyQuickView');
const bodyQuickImage = document.getElementById('bodyQuickImage');

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function quickView(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/molla/product-detail/quick-view/" + id
	})
		.then(function(response) {
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyQuickView.innerHTML = ``;
			var priceDiscount;
			if (response.data.discount <= 0) {
				price = `<h3 class="product-price"><span class="new-price">` + formatVND(response.data.price, ' VND') + `</span></h3>`;
			} else {
				price = `<h3 class="product-price"><span class="new-price">`
					+ formatVND(response.data.price - (response.data.price * response.data.discount * 0.01), ' VND') +
					`</span><span class="old-price"><del>` + formatVND(response.data.price, ' VND') + `<del></span></h3>`;
			}
			wrapper.innerHTML = `
				<h2 class="product-title">`+ response.data.name + `</h2>` + price;

			bodyQuickView.append(wrapper);


			bodyQuickImage.innerHTML = ``;
			var wrapperImage = document.createElement('a');
			wrapperImage.href = '#image0';
			wrapperImage.classList.add("carousel-dot", "active");
			wrapperImage.innerHTML = `<img src="/uploads/` + response.data.banner + `" alt="...">`;
			bodyQuickImage.appendChild(wrapperImage);
		});
	viewImages(id);
}

const bodyQuickImageDetail = document.getElementById('bodyQuickImageDetail');
function viewImages(id) {
	bodyQuickImageDetail.innerHTML = ``;
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/molla/product-detail/quick-view/view-image/" + id
	})
		.then(response => {
			response.data.forEach(image => {
				var wrapperImage = document.createElement('a');
				wrapperImage.href = '#image' + image.id;
				wrapperImage.classList.add("carousel-dot");
				wrapperImage.innerHTML = `<img src="/uploads/` + image.name + `" alt="...">`;
				bodyQuickImage.appendChild(wrapperImage);

				var wrapperImageDetail = document.createElement('div');
				wrapperImageDetail.setAttribute('data-hash', 'image' + image.id);
				wrapperImageDetail.classList.add("intro-slide");
				wrapperImageDetail.innerHTML = `<img src="/uploads/` + image.name + `" alt="...">
											<a class="btn-fullscreen"> 
												<i class="icon-arrows"></i>
											</a>
											`;
				bodyQuickImageDetail.appendChild(wrapperImageDetail);
			});
		});
}