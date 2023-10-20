const ratingInput = document.getElementById('rating');
const increaseRatingBtn = document.getElementById('increase-rating');
const decreaseRatingBtn = document.getElementById('decrease-rating');

let currentRating = 0;

function validateRating(value) {
    const floatValue = parseFloat(value);
    if (!isNaN(floatValue) && floatValue >= 0 && floatValue <= 5) {
        return parseFloat(floatValue.toFixed(1));
    }
    return null;
}

function updateRatingField() {
    ratingInput.value = currentRating.toFixed(1);
}

increaseRatingBtn.addEventListener('click', () => {
    if (currentRating < 5) {
        currentRating += 0.5;
        updateRatingField();
    }
});

decreaseRatingBtn.addEventListener('click', () => {
    if (currentRating > 0) {
        currentRating -= 0.5;
        updateRatingField();
    }
});

ratingInput.addEventListener('input', () => {
    const inputValue = ratingInput.value;
    const validatedValue = validateRating(inputValue);

    if (validatedValue !== null) {
        currentRating = validatedValue;
        updateRatingField();
    }
});


  function handleFiles(files) {
    const thumbnailsDiv = document.getElementById("thumbnails");

    // Limpa a área de miniaturas
    thumbnailsDiv.innerHTML = '';

    // Loop através das imagens carregadas
    for (let i = 0; i < files.length; i++) {
      const file = files[i];

      const thumbnail = document.createElement("div");
      thumbnail.className = "col-3";

      // Cria um elemento de imagem
      const image = document.createElement("img");
      image.className = "img-thumbnail imgthumbnail";
      image.src = URL.createObjectURL(file);
      console.log(image.className + file.name)

      image.onclick = function () {
        image.classList.add("destaque");
        document.getElementById("selectedImage").value = file.name;
      }

      thumbnail.appendChild(image);

      // Adiciona a miniatura à área de miniaturas
      thumbnailsDiv.appendChild(thumbnail);
    }
  }


    function handleFiles(files) {
      const thumbnailsDiv = document.getElementById("thumbnails");

      // Limpa a área de miniaturas
      thumbnailsDiv.innerHTML = "";

      // Loop através das imagens carregadas
      for (let i = 0; i < files.length; i++) {
        const file = files[i];

        const thumbnail = document.createElement("div");
        thumbnail.className = "col-3";

        // Cria um elemento de imagem
        const image = document.createElement("img");
        image.className = "img-thumbnail imgthumbnail";
        image.src = URL.createObjectURL(file);
        console.log(image.className + file.name)

        image.onclick = function () {
          image.classList.add("destaque");
          document.getElementById("selectedImage").value = file.name;
        }

        thumbnail.appendChild(image);

        // Adiciona a miniatura à área de miniaturas
        thumbnailsDiv.appendChild(thumbnail);
      }
    }