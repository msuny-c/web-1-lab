const url = '/fcgi-bin/area.jar'

document.addEventListener("DOMContentLoaded", function() {
	setInputValidity('y-text', -3, 3);
	setInputValidity('r-text', 2, 5);
	setScrollShadow("header", "shadow", 5);
	setFormListener();
});

function setFormListener() {
	$('#coordinates-form').on('submit', function(event) {
		event.preventDefault();
		updateCoordinates();
		$.ajax({
			url: url,
			method: 'GET',
			data: $(this).serialize(),
			success: function (data, _, response) {
				appendResult(JSON.parse(JSON.stringify(data)), response);
			},
		});
	})
}

function updateCoordinates() {
	let x = document.getElementById("x-select").value;
	let y = document.getElementById("y-text").value;
	let r = document.getElementById('r-text').value;
	let dot = document.getElementById("point");
	dot.setAttribute("cx", 150 + x * 100 / r)
	dot.setAttribute("cy", 150 - y * 100 / r)
	dot.setAttribute("visibility", "visible");
	document.querySelectorAll(".r").forEach(el => el.textContent = r)
	document.querySelectorAll(".-r").forEach(el => el.textContent = -r)
	document.querySelectorAll(".r-2").forEach(el => el.textContent = r/2)
	document.querySelectorAll(".-r-2").forEach(el => el.textContent = -r/2)
}

function setScrollShadow(selector, shadow, px) {
	window.addEventListener("scroll", function() {
		if (document.body.scrollTop > px || document.documentElement.scrollTop > px) {
		  document.querySelector(selector).classList.add(shadow);
		} else {
		  document.querySelector(selector).classList.remove(shadow)
		}
	});
}

function setInputValidity(input_id, min, max) {
	let input = document.getElementById(input_id);
	let name = input.getAttribute('name').toUpperCase()
	input.addEventListener('input', () => {
		if (!isFinite(input.value)) {
			input.setCustomValidity(name + ' должен быть числом!')
		} else if (input.value <= min || input.value >= max) {
			input.setCustomValidity(name + ' должен быть в пределах от ' + min + ' до ' + max + ' не включительно!');
		} else {
			input.setCustomValidity('');
		}
	})
}

function appendResult(data, response) {
	let date_header = response.getResponseHeader("date");
	let exec_time_header = response.getResponseHeader("fastcgi-exec-time")
	let tbody = document.querySelector('.result-table').querySelector('tbody');
	let x = document.createTextNode(parseFloat(data.x));
	let y = document.createTextNode(parseFloat(data.y)); 
	let r = document.createTextNode(parseFloat(data.r));
	let hitElement = document.createElement('span');
	switch (data.hit) {
		case 'true':
			hitElement.innerHTML = 'попадание'
			hitElement.classList.add('success-hit');
			break;
		case 'false':
			hitElement.innerHTML = 'промах';
			hitElement.classList.add('fail-hit');
			break;
	}
	let date = document.createTextNode(new Date(date_header).toLocaleString());
	let exec_time = document.createTextNode(exec_time_header + ' ns');
	let counter = document.createTextNode('');
	const rowData = [counter, x, y, r, hitElement, date, exec_time]
	let row = tbody.insertRow();
	for (let element of rowData) {
		let cell = row.insertCell();
		cell.appendChild(element);
	}
}
