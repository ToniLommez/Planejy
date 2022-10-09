const back_box = document.querySelector('.back-box'),
      new_passwd = document.querySelector('.new-passwd'),
      confirm_passwd = document.querySelector('.confirm-passwd'),
      reset_form = document.querySelector('.reset-form'),
      form_button = document.querySelector('.form-button'),
      information = document.querySelector('.information'),
      show_passwd = document.querySelector('#show-passwd'),
      hide_passwd = document.querySelector('#hide-passwd'),
      show_confirmpwd = document.querySelector('#show-confirmpwd'),
      hide_confirmpwd = document.querySelector('#hide-confirmpwd'),
      tmp = JSON.parse(sessionStorage.getItem('tmp'));

let valid_passwd = true;

onload = () => {
    if(!tmp) location.href = '../../index.html';
}

back_box.addEventListener('click', () => {
    history.back();
});

confirm_passwd.addEventListener('input', () => {
    if (confirm_passwd.value != new_passwd.value) {
        confirm_passwd.style.backgroundColor = '#f1343499';
        valid_passwd = false;
        form_button.setAttribute('disabled', 'true');
        information.innerText = 'As senhas não se coincidem.'
    } else {
        confirm_passwd.style.backgroundColor = '#fff';
        valid_passwd = true;
        form_button.removeAttribute('disabled');
        information.innerText = ''
    }
});

reset_form.addEventListener('submit', e => {
    e.preventDefault();

    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'url', true);

    xhr.onload = () => {
        alert('Senha alterada com sucesso!');
        location.href = '../../index.html';
    }

    xhr.onerror = () => {
        alert('Ocorreu algum erro ao alterar a senha ;-;');
    }

    xhr.send(tmp)
});

show_passwd.addEventListener('click', e => {
    e.preventDefault();
    new_passwd.setAttribute('type', 'text');
    show_passwd.style.display = 'none';
    hide_passwd.style.display = 'block';
});

show_passwd.addEventListener('vmousedown', e => {
    e.preventDefault();
    new_passwd.setAttribute('type', 'text');
    show_passwd.style.display = 'none';
    hide_passwd.style.display = 'block';
});

hide_passwd.addEventListener('click', e => {
    e.preventDefault();
    new_passwd.setAttribute('type', 'password');
    show_passwd.style.display = 'block';
    hide_passwd.style.display = 'none';
});

show_confirmpwd.addEventListener('click', e => {
    e.preventDefault();
    confirm_passwd.setAttribute('type', 'text');
    show_confirmpwd.style.display = 'none';
    hide_confirmpwd.style.display = 'block';
});

show_confirmpwd.addEventListener('vmousedown', e => {
    e.preventDefault();
    confirm_passwd.setAttribute('type', 'text');
    show_confirmpwd.style.display = 'none';
    hide_confirmpwd.style.display = 'block';
});

hide_confirmpwd.addEventListener('click', e => {
    e.preventDefault();
    confirm_passwd.setAttribute('type', 'password');
    show_confirmpwd.style.display = 'block';
    hide_confirmpwd.style.display = 'none';
});