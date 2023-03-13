var baseUrl = new URL(window.location.href).origin;
var menuData = document.getElementById('menu__item__data');
var menuMobileData = document.getElementById('menu__mobile__item__data');
var menuMobileTabData = document.getElementById('menu__mobile__tab__item__data');
var menuTitleCategoryData = document.getElementById('menu_title_category');
var menuTitleBrandData = document.getElementById('menu_title_brand');
var menuMobileBrandData = document.getElementById('menu__mobile__item__data__brand');

$(document).ready(function() {
	'use strict';

	showCategory();
	showBrand();

	menuData.innerHTML = ``;
	menuMobileData.innerHTML = ``;
	menuMobileTabData.innerHTML = ``;
	menuTitleCategoryData.innerHTML = ``;

	function showCategory() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/molla/layout/category"
		})
			.then(response => {
				response.data.forEach(item => {
					var itemMenu = document.createElement('li');
					itemMenu.innerHTML = `<a href="/molla/shop?category_id=` + item.id + `">` + item.name + `</a>`;
					
					menuData.appendChild(itemMenu);
					
					var itemMenuMobile = document.createElement('li');
					itemMenuMobile.innerHTML = `<a href="/molla/shop?category_id=` + item.id + `">` + item.name + `</a>`;
					
					menuMobileData.appendChild(itemMenuMobile);
					
					var itemMenuMobileTab = document.createElement('li');
					itemMenuMobileTab.innerHTML = `<a href="/molla/shop?category_id=` + item.id + `">` + item.name + `</a>`;
					
					menuMobileTabData.appendChild(itemMenuMobileTab);
					
					var itemMenuTitleCategory = document.createElement('li');
					itemMenuTitleCategory.innerHTML = `<a href="/molla/shop?category_id=` + item.id + `">` + item.name + `</a>`;
					
					menuTitleCategoryData.appendChild(itemMenuTitleCategory);
				});
			});
	}
	
	menuTitleBrandData.innerHTML = ``;
	menuMobileBrandData.innerHTML = ``;
	
	function showBrand() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/molla/layout/brand"
		})
			.then(response => {
				response.data.forEach(item => {
					var itemTitleBrand = document.createElement('li');
					itemTitleBrand.innerHTML = `<a href="/molla/shop?brand_id=` + item.id + `">` + item.name + `</a>`;
					
					menuTitleBrandData.appendChild(itemTitleBrand);
					
					var itemTitleMobileBrand = document.createElement('li');
					itemTitleMobileBrand.innerHTML = `<a href="/molla/shop?brand_id=` + item.id + `">` + item.name + `</a>`;
					
					menuMobileBrandData.appendChild(itemTitleMobileBrand);
				});
			});
	}
})