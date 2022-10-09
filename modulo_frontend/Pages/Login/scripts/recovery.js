const back_box = document.querySelector('.back-box'),
    code_form = document.querySelector('.code-form'),
    code_input = document.querySelector('.code-input'),
    form_input = document.querySelector('.form-input'),
    form_email = document.querySelector('.email-form'),
    request_email = document.querySelector('.request-email'),
    request_code = document.querySelector('.request-code');


let code = 0;

while (code < 0 || code < 100000) {
    code = Math.floor(Math.random() * 999999) - 100000;
}

console.log(code);

onload = () => {
    sessionStorage.removeItem('tmp');
    request_code.style.display = 'none';
    request_email.style.display = 'flex';
}

form_email.addEventListener('submit', e => {
    e.preventDefault();

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'url', true);

    xhr.onload = () => {
        request_email.style.display = 'none';
        request_code.style.display = 'flex';
    }

    xhr.onerror = () => {
        alert('Email não encontrado');
    }
});

code_form.addEventListener('submit', e => {
    e.preventDefault();

    if (+code_input.value === code) {
        sessionStorage.setItem('tmp', JSON.stringify({email: form_input.value, codigo: +code_input.value, pass: null}));
        location.href = 'reset-passwd.html';
    } else {
        alert("Código incorreto!");
    }
});

back_box.addEventListener('click', () => {
    history.back();
});