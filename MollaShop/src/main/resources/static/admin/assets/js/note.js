





dragArea = document.querySelector('.drag-area'),
	input = document.querySelector('.drag-area input'),
	select = document.querySelector('.drag-area .select'),
	showImage = document.querySelector('.showImage');

/** CLICK LISTENER , from: 550 , from: 50*/
select.addEventListener('click', () => input.click());

/* INPUT CHANGE EVENT */


/** SHOW IMAGES */


/* DELETE IMAGE 
			<div class="image">
				<span onclick="delImage(${index})">&times;</span>
				<img src="${URL.createObjectURL(curr)}" />
			</div>*/


/* DRAG & DROP */
dragArea.addEventListener('dragover', e => {
	e.preventDefault()
	dragArea.classList.add('dragover')
})

dragArea.addEventListener('dragleave', e => {
	e.preventDefault()
	dragArea.classList.remove('dragover')
});

dragArea.addEventListener('drop', e => {
	e.preventDefault()
	dragArea.classList.remove('dragover');

	let file = e.dataTransfer.files;
	for (let i = 0; i < file.length; i++) {
		if (file[i].type.split("/")[0] != 'image') continue;

		if (!files.some(e => e.name == file[i].name)) files.push(file[i])
	}
	showImages();
});