const setStarEvents = () => {
    document.querySelectorAll('.star').forEach((star, index, array) => {
        star.nextSibling.addEventListener('mouseenter', () => {
            for(let i = 0; i < 5; i++){
                const img = array[i].nextSibling.childNodes[1];

                if(i <= index){
                    img.src = 'images/star1.png';
                    img.style.opacity = 1;
                }else{
                    if(img.classList.contains('starActive')){
                        img.src = 'images/star1.png';
                        img.style.opacity = .4;
                    }else{
                        img.src = 'images/star0.png';
                        img.style.opacity = .4;
                    }
                }
            }
        });
    });

    document.querySelectorAll('.star').forEach((star, index, array) => {
        star.nextSibling.addEventListener('mouseleave', () => {
            
            for(let i = 0; i < 5; i++){
                const img = array[i].nextSibling.childNodes[1];
                
                if(img.classList.contains('clicked')){
                    img.src = 'images/star1.png';
                    img.style.opacity = 1;
                }else if(img.classList.contains('starActive')){
                    img.src = 'images/star1.png';
                    img.style.opacity = .4;
                }else{
                    img.src = 'images/star0.png';
                    img.style.opacity = .4;
                }
            }
        });
    });

    document.querySelectorAll('.star').forEach((star, index, array) => {
        star.nextSibling.addEventListener('click', () => {
            for(let i = 0; i < 5; i++){
                const img = array[i].nextSibling.childNodes[1];
                
                if(i <= index){
                    img.src = 'images/star1.png';
                    img.classList.add('clicked');
                }else{
                    if(img.classList.contains('starActive')){
                        img.src = 'images/star1.png';
                    }else{
                        img.src = 'images/star0.png';
                    }
                    
                    img.style.opacity = .4;
                    img.classList.remove('clicked');
                }
            }

            rate(index + 1);
        });
    });
}

const rate = rating => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:5678/articles/avaliar/${user.token}/${getArticleId()}/${rating}`, true);

    xhr.onload = () => {
        updateRating();
    }

    xhr.onerror = () => {
        alert('Ocorreu um erro ao avaliar ;-;');
    }

    xhr.send('');
}

const updateRating = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:5678/articles/receive/${getArticleId()}/${user.token}`);

    xhr.onload = () => {
        const response = JSON.parse(xhr.responseText).Articles[0];
        console.log(response)

        document.querySelector('.n_votos').innerHTML = `${Number(response.nota).toFixed(1)}/5 - ${response.numNotas} avaliações`;

        document.querySelectorAll('.star').forEach((e, i) => {
            const img = e.nextSibling.childNodes[1];

            if(img.classList.contains('clicked')){
                img.src = 'images/star1.png';
                img.style.opacity = 1;
            }else if(img.classList.contains('starActive')){
                img.src = 'images/star1.png';
                img.style.opacity = .4;
            }else{
                img.src = 'images/star0.png';
                img.style.opacity = .4;
            }
        });
    }

    xhr.onerror = () => { 
        location.reload();
    }

    xhr.send();
}

const loadStars = article => {
    ratings = document.querySelector('.rating');
    
    for(let i = 0; i < 5; i++){
        const img = i < +article.nota ? 1 : 0;

        ratings.innerHTML += `<input class="star" type="radio" name="rating" value="${i}" id="${i}"><label for="${i}">
                              <img src="images/star${img}.png" class="img-star"></label>`;
    }

    ratings.querySelectorAll('label').forEach((e, i) => {
        const img = e.childNodes[1];
        
        if(i >= article.notaUsuario && i >= article.nota){
            img.classList.remove('clicked');
            img.classList.remove('starActive');
            img.src = 'images/star0.png';
            img.style.opacity = .4;
        }else{
            if(i < article.nota){
                img.classList.add('starActive');
                img.src = 'images/star1.png';
                img.style.opacity = .4;
            }
            
            if(i < article.notaUsuario){
            img.classList.add('clicked');
            img.src = 'images/star1.png';
            img.style.opacity = 1;
            }
        }
    });

    document.querySelector('.n_votos').innerHTML = `${Number(article.nota).toFixed(1)}/5 - ${article.numNotas} avaliações`;
    setStarEvents();
}
