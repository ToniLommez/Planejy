const back_box = document.querySelector('.back-box'),
    user_firstname = document.querySelector('#firstname'),
    user_email = document.querySelector('#email'),
    user_confirmemail = document.querySelector('#confirmemail'),
    user_nick = document.querySelector('#nickname'),
    user_birthday = document.querySelector('#birthDate'),
    user_passwd = document.querySelector('#passwd'),
    user_confirmpasswd = document.querySelector('#confirmpasswd'),
    form_element = document.querySelector('#register'),
    show_passwd = document.querySelector('#show-passwd'),
    hide_passwd = document.querySelector('#hide-passwd');

let btn_submit = document.querySelector('.btn-submit');

// Back Box Event Listener
back_box.addEventListener('click', () => {
    location.href = '../../index.html';
})

user_confirmemail.addEventListener('blur', () => {
    if (user_confirmemail.value != user_email.value) {
        user_confirmemail.style.backgroundColor = '#f1343499';
        valid_email = false;
        btn_submit.setAttribute('disabled', 'true');
    } else {
        user_confirmemail.style.backgroundColor = '#fff';
        valid_email = true;
        if (valid_email && valid_passwd) {
            btn_submit.removeAttribute('disabled');
        }
    }
});

// User Confirm Password Event Listener
user_confirmpasswd.addEventListener('blur', () => {
    if (user_confirmpasswd.value != user_passwd.value) {
        user_confirmpasswd.style.backgroundColor = '#f1343499';
        valid_passwd = false;
        btn_submit.setAttribute('disabled', 'true');
    } else {
        user_confirmpasswd.style.backgroundColor = '#fff';
        valid_passwd = true;
        if (valid_email && valid_passwd) {
            btn_submit.removeAttribute('disabled');
        }
    }
});

show_passwd.addEventListener('click', (event) => {
    user_confirmpasswd.setAttribute('type', 'text');
    show_passwd.style.display = 'none';
    hide_passwd.style.display = 'block';
    event.preventDefault();
});

hide_passwd.addEventListener('click', (event) => {
    user_confirmpasswd.setAttribute('type', 'password');
    show_passwd.style.display = 'block';
    hide_passwd.style.display = 'none';
    event.preventDefault();
});

// Register
form_element.addEventListener('submit', e => {
    e.preventDefault();
});

btn_submit.onclick = () => {
    let user = {
        name: user_firstname.value,
        email: user_email.value,
        nick: user_nick.value,
        birth: user_birthday.value,
        password: user_passwd.value,
        gender: 'n'
    }

    postUser(user);
}

const userToSql = user => {
    return `INSERT INTO planejy.usuario (nome, nick, senha, email)
            VALUES ('${user.name}', '${user.nick}', '${user.password}', '${user.email}')`;
}

const postUser = (user) => {
    let sql = userToSql(user);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:5678/usuario/registrar/:nome/:nick/:senha/:email', true);

    xhr.onload = () => {
        location.href = '../../index.html';
    }

    xhr.onerror = () => {
        alert('erro ao criar conta ;-;');
    }

    xhr.send(sql);
}