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
        location.href = '../index.html'
        return;
    }

    getUserInfo();
}

const logout = () => {
    sessionStorage.removeItem('user');
    location.href = '../index.html';
}

const getUserInfo = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:5678/usuario/get/${user.token}`, true);

    xhr.onload = () => {
        console.log(console.log(xhr.responseText));
        loadUserInfo(JSON.parse(xhr.responseText));
    }

    xhr.onerror = () => {
        alert('Ocorreu um erro ao carregar informações ;-;');
    }

    xhr.send();
}

const loadUserInfo = (info) => {
    console.log(info)
}

gender_input.oninput = () => {
    if(gender_input.value.length > 1){
        gender_input.value = gender_input.value.substring(0, 1);
    }

    gender_input.value = gender_input.value.toUpperCase();
};

btn_save.onclick = () => {

};
