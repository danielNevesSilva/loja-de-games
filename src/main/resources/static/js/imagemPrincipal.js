document.addEventListener('DOMContentLoaded', function() {
    const mainImagesDiv = document.getElementById('mainImages');
    const imagesInput = document.getElementById('images');
    const mainImageInput = document.getElementById('mainImage'); // Campo oculto para armazenar a imagem principal

    // Inicialize o valor do campo oculto como vazio
    mainImageInput.value = '';

    // Ouça o evento de mudança no campo de seleção de imagens
    imagesInput.addEventListener('change', updateMainImageOptions);

    function updateMainImageOptions() {
        // Limpe todas as opções existentes no div de imagens principais
        mainImagesDiv.innerHTML = '';

        // Adicione opções para cada imagem carregada
        for (let i = 0; i < imagesInput.files.length; i++) {
            const fileName = imagesInput.files[i].name;

            // Crie um checkbox para cada imagem
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = 'mainImages[]'; // Use um array para permitir seleção múltipla
            checkbox.value = fileName; // Valor associado ao checkbox
            checkbox.id = 'imageCheckbox' + i; // Defina um ID exclusivo para o checkbox

            // Crie uma label para o checkbox
            const label = document.createElement('label');
            label.htmlFor = 'imageCheckbox' + i;
            label.appendChild(document.createTextNode(fileName));

            // Crie uma quebra de linha
            const br = document.createElement('br');

            // Adicione um evento de clique para atualizar o campo oculto quando um checkbox for marcado
            checkbox.addEventListener('click', function() {
                if (checkbox.checked) {
                    mainImageInput.value = fileName; // Atualize o campo oculto com o caminho da imagem selecionada
                } else {
                    mainImageInput.value = ''; // Limpe o campo oculto se o checkbox for desmarcado
                }
            });

            // Adicione o checkbox, a label e a quebra de linha ao div de imagens principais
            mainImagesDiv.appendChild(checkbox);
            mainImagesDiv.appendChild(label);
            mainImagesDiv.appendChild(br);
        }
    }
});