const email = document.getElementById('email');
const passwd = document.getElementById('passwd');

onload = () => {
    sessionStorage.removeItem('user');
}

document.getElementById('login').addEventListener('submit', e => {e.preventDefault()});

document.getElementById('btn_submit').onclick = () => {
    if(email.checkValidity() && passwd.checkValidity()){
        postUser(email.value, passwd.value);
    }
}

const generateToken = n => {
    const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';

    for(let i = 0; i < n; i++){
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }

    return result;
}

const postUser = (e, p) => {
    let token = generateToken(40);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:5678/usuario/login/${e}/${token}`, true);

    xhr.onload = () => {
        let user = {
            id: JSON.parse(xhr.responseText).Usuario[0].id,
            token: token
        }

        sessionStorage.setItem('user', JSON.stringify(user));
        location.href = '/Pages/Calendario/Calendario.html';
    }

    xhr.onerror = () => {
        alert('erro ao efetuar login ;-;');
    }

    xhr.send(p);
}