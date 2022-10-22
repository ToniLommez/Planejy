const user = JSON.parse(sessionStorage.getItem('user'));
let pros = {};

onload = () => {
    if(!user){
        location.href = '../../index.html';
        return;
    }
}

const logout = () => {
    sessionStorage.removeItem('user');
    location.href = '../index.html';
}

const getPro = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:5678/profissional/all/${user.token}`, true);

    xhr.onload = () => {
        loadPro(JSON.parse(xhr.responseText));
    }

    xhr.onerror = () => {
        alert('erro ao recuperar profissionais ;-;');
    }

    xhr.send();
}

const loadPro = profissionals => { //to-do: fix when pro doesnt have x social media
    pros = profissionals.Profissional;
    
    let cards = '';

    for (let i = 0; i < profissionals.Profissional.length; i++) {

        let ratings = '';

        let j = 0;
        while(j < Math.round(profissionals.Profissional[i].nota)){
            if(j < profissionals.Profissional[i].notaUsuario){
                ratings += `<input class="star" type="radio" name="rating" 
                             value="pro-${profissionals.Profissional[i].registro}-${j}" 
                             id="pro-${profissionals.Profissional[i].registro}-${j}">
                             <label class="starLabel" for="pro-${profissionals.Profissional[i].registro}-${j}">
                             <img src="images/star1.png" class="img-star starActive clicked"></label>`;
            }else{
                ratings += `<input class="star" type="radio" name="rating" 
                             value="pro-${profissionals.Profissional[i].registro}-${j}" 
                             id="pro-${profissionals.Profissional[i].registro}-${j}">
                             <label class="starLabel" for="pro-${profissionals.Profissional[i].registro}-${j}">
                             <img src="images/star1.png" class="img-star starActive"></label>`;
            }
            j++;
        }

        while(j < 5){
            if(j < profissionals.Profissional[i].notaUsuario){
                ratings += `<input class="star" type="radio" name="rating" 
                             value="pro-${profissionals.Profissional[i].registro}-${j}" 
                             id="pro-${profissionals.Profissional[i].registro}-${j}">
                             <label class="starLabel" for="pro-${profissionals.Profissional[i].registro}-${j}">
                             <img src="images/star1.png" class="img-star starActive clicked"></label>`;
            }else{
                ratings += `<input class="star" type="radio" name="rating" 
                             value="pro-${profissionals.Profissional[i].registro}-${j}" 
                             id="pro-${profissionals.Profissional[i].registro}-${j}">
                             <label class="starLabel" for="pro-${profissionals.Profissional[i].registro}-${j}">
                             <img src="images/star0.png" class="img-star"></label>`;
            }
            j++;
        }

        const categories = profissionals.Profissional[i].servico.split(',').join('<br>');
        const rating = Number(profissionals.Profissional[i].nota).toFixed(1);
        const brilhinho = profissionals.Profissional[i].brilhinho ? 'brilhinho' : '';

        cards += `<div class="col-xs-12 col-sm-6 col-lg-4 col-xl-3 pro-margin">
                      <!-- Card-->
                      <div class="card shadow border-0 rounded ${brilhinho}">
                          <div class="card-body p-0">
                              <img src="images/pro-${profissionals.Profissional[i].registro}.jpg" alt="" class="w-100 card-img-top">
                              <div class="p-4">
                                  <h5 class="mb-0 nome">${profissionals.Profissional[i].nome}</h5>
                                  <p class="small text-muted preco">${profissionals.Profissional[i].preco}/h</p>
                                  <p class="small text-muted area">${categories}</p>${categories.indexOf('<br>') === -1 ? '<br>' : ''}
                                  <ul class="social mb-0 list-inline mt-3">
                                      <li class="list-inline-item m-0"><a href="https://${profissionals.Profissional[i].facebook}" class="social-link"><i class="fa fa-facebook-f"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${profissionals.Profissional[i].twitter}" class="social-link"><i class="fa fa-twitter"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${profissionals.Profissional[i].instagram}" class="social-link"><i class="fa fa-instagram"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${profissionals.Profissional[i].linkedin}" class="social-link"><i class="fa fa-linkedin"></i></a></li>
                                  </ul>
                                  <div class="stars">
                                      <div class="rating" id="pro-id-${profissionals.Profissional[i].registro}">
                                          ${ratings}
                                      </div>
                                      <p id="pro-nota-${profissionals.Profissional[i].registro}" class="small text-muted n_votos">${rating}/5 - ${profissionals.Profissional[i].numNotas} avaliações</p>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                `
    }

    document.querySelector('.cards-box').innerHTML = cards;
    document.querySelectorAll('.clicked').forEach(e => {e.src = 'images/star1.png'; e.style.opacity = 1;});

    setStarEvents();
}

getPro();
