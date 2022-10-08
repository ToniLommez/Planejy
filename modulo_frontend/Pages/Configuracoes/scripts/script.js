const user = JSON.parse(sessionStorage.getItem('user'));

var id_input = document.querySelector('#inputId'),
    email_input = document.querySelector('#inputEmail'),
    name_input = document.querySelector('#inputNome'),
    birth_input = document.querySelector('#inputNascimento'),
    gender_input = document.querySelector('#inputGenero'),
    nick_input = document.querySelector('#inputNick'),
    btn_save = document.querySelector('#btnSave'),
    userLoggedData = localStorage.getItem('user_login'),
    usersRegistered = localStorage.getItem('users'),
    usersObj = '';


onload = () => {
    if(!user) {
        location.href = '../index.html'
    }
}

function logout() {
    sessionStorage.removeItem('user');
    location.href = '../index.html';
}

name_input.addEventListener('blur', () => {
    first_name = name_input.value.split(" ")[0]
    last_name = name_input.value.split(" ")[1]

    first_name = first_name.toLowerCase();
    first_name = first_name[0].toUpperCase() + first_name.slice(1);

    last_name = last_name.toLowerCase();
    last_name = last_name[0].toUpperCase() + last_name.slice(1);

    name_input.value = first_name + ' ' + last_name
})

email_input.addEventListener('input', () => {
    email_input.value = email_input.value.toLowerCase();
})

birth_input.addEventListener('input', () => {
    birth_input.value = birth_input.value.replace(/\D/g, "");
})

gender_input.addEventListener('input', () => {
    gender_input.value = gender_input.value.toLowerCase();
})

btn_save.addEventListener('click', () => {
    usersObj[id_input.value].firstname = name_input.value.split(" ")[0]
    usersObj[id_input.value].lastname = name_input.value.split(" ")[1]
    usersObj[id_input.value].email = email_input.value
    usersObj[id_input.value].birth = birth_input.value
    usersObj[id_input.value].gender = gender_input.value
    usersObj[id_input.value].nick = nick_input.value


    localStorage.setItem('users', JSON.stringify({'user_registered': usersObj}))
})
