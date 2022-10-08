const user = JSON.parse(sessionStorage.getItem('user'));

onload = () => {
    if(!user){
        location.href = '../../index.html';
        return;
    }

    getPro();
}

const logout = () => {
    sessionStorage.removeItem('user');
    location.href = '../index.html';
}

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

const loadPro = (pros) => { //to-do: fix when pro doesnt have x social media
    let cards = '';

    for (let i = 0; i < pros.Profissional.length; i++) {
        cards += `<div class="col-xs-12 col-sm-6 col-lg-4 col-xl-3 pro-margin">
                      <!-- Card-->
                      <div class="card shadow border-0 rounded">
                          <div class="card-body p-0"><img src="images/pro-${pros.Profissional[i].registro}.jpg" alt="" class="w-100 card-img-top">
                              <div class="p-4">
                                  <h5 class="mb-0 nome">${pros.Profissional[i].nome}</h5>
                                  <p class="small text-muted preco">${pros.Profissional[i].preco}/h</p>
                                  <p class="small text-muted area">${pros.Profissional[i].servico}</p>
                                  <ul class="social mb-0 list-inline mt-3">
                                      <li class="list-inline-item m-0"><a href="https://${pros.Profissional[i].facebook}" class="social-link"><i class="fa fa-facebook-f"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${pros.Profissional[i].twitter}" class="social-link"><i class="fa fa-twitter"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${pros.Profissional[i].instagram}" class="social-link"><i class="fa fa-instagram"></i></a></li>
                                      <li class="list-inline-item m-0"><a href="https://${pros.Profissional[i].linkedin}" class="social-link"><i class="fa fa-linkedin"></i></a></li>
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