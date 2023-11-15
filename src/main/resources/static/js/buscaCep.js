document.addEventListener('DOMContentLoaded', function() {
    var cepInput = document.getElementById('cep');
    cepInput.addEventListener('blur', function() {
        var cepValue = cepInput.value.replace(/\D/g, '');
        if (cepValue.length === 8) {
            fetch(`https://viacep.com.br/ws/${cepValue}/json/`)
                .then(response => response.json())
                .then(data => {
                    if (!data.erro) {
                        document.getElementById('rua').value = data.logradouro;
                        document.getElementById('numero').focus();
                        document.getElementById('bairro').value = data.bairro;
                        document.getElementById('cidade').value = data.localidade;
                        document.getElementById('uf').value = data.uf;
                    } else {
                        alert('CEP não encontrado. Por favor, verifique o CEP inserido.');
                    }
                })
                .catch(error => console.error('Erro ao buscar endereço:', error));
        }
    });
});