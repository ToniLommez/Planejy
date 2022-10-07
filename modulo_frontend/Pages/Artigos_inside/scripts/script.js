// let id_input = document.querySelector('#inputId'),
//     name_input = document.querySelector('#inputNome'),
//     phone_input = document.querySelector('#inputTelefone'),
//     email_input = document.querySelector('#inputEmail'),
//     age_input = document.querySelector('#inputIdade'),
//     btn_save = document.querySelector('#btnSave'),
//     userLoggedData = localStorage.getItem('user_login'),
//     usersRegistered = localStorage.getItem('users'),
//     usersObj = '';

onload = () => {
    getArticles();
    // if (userLoggedData && usersRegistered) {
    //     let users = JSON.parse(usersRegistered),
    //         userLogged = JSON.parse(userLoggedData),
    //         userLoggedObj = userLogged.user_login;

    //     usersObj = users.user_registered

    //     Object.keys(usersObj).forEach( function(id) {
    //         if (usersObj[id].email == userLoggedObj[0].email) {
    //             id_input.value = id
    //             name_input.value = usersObj[id].firstname + ' ' + usersObj[id].lastname
    //             email_input.value = usersObj[id].email
    //             phone_input.value = usersObj[id].phone
    //             age_input.value = usersObj[id].age
    //         }
    //     })
    // }

    // else {
    //     location.href = '../index.html'
    // }
}

function logout() {
    let users = {
        'user_login': [{
            'firstname': '',
            'email': '', 
            'passwd': '',
            'access': false
        }]
    }
    localStorage.setItem('user_login', JSON.stringify(users));
    location.href = '../index.html';
}

// name_input.addEventListener('blur', () => {
//     first_name = name_input.value.split(" ")[0]
//     last_name = name_input.value.split(" ")[1]

//     first_name = first_name.toLowerCase();
//     first_name = first_name[0].toUpperCase() + first_name.slice(1);

//     last_name = last_name.toLowerCase();
//     last_name = last_name[0].toUpperCase() + last_name.slice(1);

//     name_input.value = first_name + ' ' + last_name
// })

// email_input.addEventListener('input', () => {
//     email_input.value = email_input.value.toLowerCase();
// })

// phone_input.addEventListener('input', () => {
//     phone_input.value = phone_input.value.replace(/\D/g, "")
// })

// age_input.addEventListener('input', () => {
//     age_input.value = age_input.value.replace(/\D/g, "")
// })

// btn_save.addEventListener('click', () => {
//     usersObj[id_input.value].firstname = name_input.value.split(" ")[0]
//     usersObj[id_input.value].lastname = name_input.value.split(" ")[1]
//     usersObj[id_input.value].email = email_input.value
//     usersObj[id_input.value].phone = phone_input.value
//     usersObj[id_input.value].age = age_input.value


//     localStorage.setItem('users', JSON.stringify({'user_registered': usersObj}))
// })

const getArticleId = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const articleId = urlParams.get('article_id');
    
    return articleId;
}

const updatePage = (html) => {
    console.log(html);
    document.querySelector('.card-header').innerHTML = `<h1>${html.titulo}</h1>`;
    document.querySelector('.card-body').innerHTML = html.conteudo;
    document.querySelector('.autor').innerHTML = html.autor;
    document.getElementById('imagem').setAttribute('src', `../../Artigos/images/article-${html.chave}.png`); // ---------- not working
}

const getArticles = () => {
    let xhr = new XMLHttpRequest();
    let id = getArticleId();
    xhr.open("GET", `http://localhost:5678/articles/${id}`, true);

    xhr.onload = () => {
        updatePage(JSON.parse(xhr.responseText).Articles[0]);
    }

    xhr.onerror = () => {
        alert('erro ao carregar artigo ;-;');
    }

    xhr.send();
}