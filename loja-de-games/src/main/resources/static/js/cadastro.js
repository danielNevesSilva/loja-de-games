function validarSenhas() {
    const senha1 = document.getElementById('password');
    const senha2 = document.getElementById('confirmPassword');

    if (senha1.value === senha2.value) {
        senha2.setCustomValidity(''); // Senhas conferem
    } else {
        senha2.setCustomValidity('As senhas não conferem');
    }
}

function formatarCPF(campo) {
    var cpf = campo.value.replace(/\D/g, ''); // Remove todos os caracteres não numéricos

    if (cpf.length > 11) {
        cpf = cpf.slice(0, 11);
    }

    cpf = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');

    campo.value = cpf;

}

function validateCPF(cpf) {

   cpf = cpf.replace(/[^\d]/g, ''); // Remove caracteres não numéricos


  // Verificar se o CPF possui 11 dígitos
  if (cpf.length !== 11) {
    return false;
  }

  // Verificar se todos os dígitos são iguais
  const areAllDigitsEqual = cpf.split('').every(digit => digit === cpf[0]);
  if (areAllDigitsEqual) {
    return false;
  }

  // Calcular o primeiro dígito verificador
  let sum = 0;
  for (let i = 0; i < 9; i++) {
    sum += parseInt(cpf.charAt(i)) * (10 - i);
  }
  let firstDigit = 11 - (sum % 11);
  if (firstDigit >= 10) {
    firstDigit = 0;
  }

  // Verificar o primeiro dígito verificador
  if (parseInt(cpf.charAt(9)) !== firstDigit) {
    return false;
  }

  // Calcular o segundo dígito verificador
  sum = 0;
  for (let i = 0; i < 10; i++) {
    sum += parseInt(cpf.charAt(i)) * (11 - i);
  }
  let secondDigit = 11 - (sum % 11);
  if (secondDigit >= 10) {
    secondDigit = 0;
  }

  // Verificar o segundo dígito verificador
  if (parseInt(cpf.charAt(10)) !== secondDigit) {
    return false;
  }

  return true;
}

function validarCPF(cpfValue) {
    const cpfField = document.getElementById('cpf');
    const cpfErrorSpan = cpfField.nextElementSibling;

    if (!validateCPF(cpfValue)) {
        cpfErrorSpan.textContent = 'Cpf inválido';
        cpfField.setCustomValidity('Cpf inválido');
    } else {
        cpfErrorSpan.textContent = '';
        cpfField.setCustomValidity('');
    }
}

function alterarStatus(id, statusAtual) {
console.log("Função alterarStatus chamada");
    var novoStatus = statusAtual === 'Ativo' ? 'Inativo' : 'Ativo';
    var url = `/funcionario/alterarStatusFuncionario?id=${id}&status=${novoStatus}`;

    // Fazer uma solicitação AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // A solicitação foi bem-sucedida, atualize a página
                window.location.reload();
            } else {
                // Trate o erro, se necessário
                console.error('Erro ao alterar o status do funcionário');
            }
        }
    };
    xhr.open('POST', url, true);
    xhr.send();
}
