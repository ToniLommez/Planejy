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
    getPro();
    
//     if (userLoggedData && usersRegistered) {
//         let users = JSON.parse(usersRegistered),
//             userLogged = JSON.parse(userLoggedData),
//             userLoggedObj = userLogged.user_login;

//         usersObj = users.user_registered

//         Object.keys(usersObj).forEach( function(id) {
//             if (usersObj[id].email == userLoggedObj[0].email) {
//                 id_input.value = id
//                 name_input.value = usersObj[id].firstname + ' ' + usersObj[id].lastname
//                 email_input.value = usersObj[id].email
//                 phone_input.value = usersObj[id].phone
//                 age_input.value = usersObj[id].age
//             }
//         })
//     }

//     else {
//         location.href = '../index.html'
//     }
}

const logout = () => {
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

const getPro = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:5678/profissional/all/', true);

    xhr.onload = () => {
        loadPro(JSON.parse(xhr.responseText));
    }

    xhr.onerror = () => {
        alert('erro ao recuperar profissionais ;-;');
    }

    xhr.send();
}

const loadPro = (pros) => { //fix when pro doesnt have x social media
    console.log(pros);

    let cards = '';

    for(let i = 0; i < pros.Profissional.length; i++){
        cards += `<div class="col-xs-12 col-sm-6 col-lg-4 col-xl-3 pro-margin">
                      <!-- Card-->
                      <div class="card shadow border-0 rounded">
                          <div class="card-body p-0"><img src="images/pro-${pros.Profissional[i].registro}.jpg" alt="" class="w-100 card-img-top">
                              <div class="p-4">
                                  <h5 class="mb-0 nome">${pros.Profissional[i].nome}</h5>
                                  <p class="small text-muted preco">${pros.Profissional[i].preco}/h</p>
                                  <p class="small text-muted area">${pros.Profissional[i].servico}</p>
                                  <ul class="social mb-0 list-inline mt-3">
                                      <li class="list-inline-item m-0"><a href="${pros.Profissional[i].facebook}" class="social-link"><i class="fa fa-facebook-f"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="${pros.Profissional[i].twitter}" class="social-link"><i class="fa fa-twitter"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="${pros.Profissional[i].instagram}" class="social-link"><i class="fa fa-instagram"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="${pros.Profissional[i].linkedin}" class="social-link"><i class="fa fa-linkedin"></i></a></li>
                                  </ul>
                                  <div class="stars">
                                      <div class="rating">
                                          <input type="radio" name="rating" value="pro-1-5" id="pro-1-5"><label for="pro-1-5"><img src="images/star0.png" class="img-star"></label>
                                          <input type="radio" name="rating" value="pro-1-4" id="pro-1-4"><label for="pro-1-4"><img src="images/star0.png" class="img-star"></label>
                                          <input type="radio" name="rating" value="pro-1-3" id="pro-1-3"><label for="pro-1-3"><img src="images/star1.png" class="img-star"></label>
                                          <input type="radio" name="rating" value="pro-1-2" id="pro-1-2"><label for="pro-1-2"><img src="images/star1.png" class="img-star"></label>
                                          <input type="radio" name="rating" value="pro-1-1" id="pro-1-1"><label for="pro-1-1"><img src="images/star1.png" class="img-star"></label>
                                      </div>
                                      <p class="small text-muted n_votos">3.2/5 - 30 avaliacoes</p>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                `
    }

    document.querySelector('.cards-box').innerHTML = cards;
}