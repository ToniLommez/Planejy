const user = JSON.parse(sessionStorage.getItem('user'));

const id_input = document.querySelector('#inputId'),
      email_input = document.querySelector('#inputEmail'),
      name_input = document.querySelector('#inputNome'),
      birth_input = document.querySelector('#inputNascimento'),
      gender_input = document.querySelector('#inputGenero'),
      nick_input = document.querySelector('#inputNick'),
      btn_save = document.querySelector('#btnSave');


onload = () => {
    if(!user) {
        location.href = '../../index.html'
        return;
    }

    getUserInfo();
}

const logout = () => {
    sessionStorage.removeItem('user');
    location.href = '../../index.html';
}

gender_input.oninput = () => {
    if(gender_input.value.length > 1){
        gender_input.value = gender_input.value.substring(0, 1);
    }

    gender_input.value = gender_input.value.toUpperCase();
};

btn_save.onclick = () => {
    updateUserInfo();
};

const getUserInfo = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:5678/usuario/get/${user.token}`, true);

    xhr.onload = () => {
        loadUserInfo(JSON.parse(xhr.responseText).Usuario[0]);
    }

    xhr.onerror = () => {
        alert('Ocorreu um erro ao carregar informações ;-;');
    }

    xhr.send();
}

const loadUserInfo = info => {
    console.log(info)
    
    id_input.value = info.id;
    email_input.value = info.email;
    name_input.value = info.nome;
    birth_input.value = info.nascimento;
    nick_input.value = info.nick;
    gender_input.value = info.genero.toUpperCase();
}

const infoToString = () => {
    let info = '';

    if(email_input.value.length > 0 && 
       name_input.value.length > 0 && 
       birth_input.value.length > 0 && 
       gender_input.value.length > 0 && 
       nick_input.value.length > 0){

        info = `${email_input.value};${name_input.value};${birth_input.value};${nick_input.value};${gender_input.value}`;
    }

    return info;
}

const updateUserInfo = () => {
    const info = infoToString();
    if(info.length === 0){
        alert('Preencha cada um dos campos corretamente!');
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:5678/usuario/Atualizar/${user.token}/${user.id}`, true);

    xhr.onload = () => {
        console.log(xhr.responseText);
        alert('Dados salvos com sucesso!');
    }

    xhr.onerror = () => {
        alert('Ocorreu um erro ao salvar informações ;-;');
    }

    xhr.send(info);
}