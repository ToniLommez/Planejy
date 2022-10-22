const setStarEvents = () => {
    document.querySelectorAll('.star').forEach((star, index, array) => {
        star.nextSibling.addEventListener('mouseenter', () => {
            const id = star.getAttribute('id').split('-');
            const starId = +id[2];
            
            for(let i = 0; i < 5; i++){
                const img = array[index - starId + i].nextSibling.childNodes[0];

                if(i <= starId){
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
            const id = star.getAttribute('id').split('-');
            const starId = +id[2];
            
            for(let i = 0; i < 5; i++){
                const img = array[index - starId + i].nextSibling.childNodes[0];

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
            const id = star.getAttribute('id').split('-');
            const proId = +id[1];
            const starId = +id[2];
            
            for(let i = 0; i < 5; i++){
                const img = array[index - starId + i].nextSibling.childNodes[0];
                
                if(i <= starId){
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

            rate(proId, starId + 1);
        });
    });
}

const rate = (proId, rating) => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:5678/profissional/avaliar/${user.token}/${proId}/${rating}`, true);

    xhr.onload = () => {
        updateRating(proId);
    }

    xhr.onerror = () => {
        alert('Ocorreu um erro ao avaliar ;-;');
    }

    xhr.send('');
}

const updateRating = proId => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:5678/profissional/${proId}/${user.token}`);

    xhr.onload = () => {
        const response = JSON.parse(xhr.responseText).Profissional[0];

        document.querySelector(`#pro-nota-${response.registro}`).innerHTML = `${Number(response.nota).toFixed(1)}/5 - ${response.numNotas} avaliações`;

        document.querySelectorAll(`*[id^="pro-${proId}"]`).forEach((e, i) => {
            const img = e.nextSibling.childNodes[0];

            if(i <= +response.notaUsuario - 1){
                img.src = 'images/star1.png';
                img.classList.add('clicked');
                img.style.opacity = 1;
            }else if(i <= Math.round(+response.nota) - 1){
                img.src = 'images/star1.png';
                img.classList.add('starActive');
                img.style.opacity = .4;
            }else{
                img.src = 'images/star0.png';
                img.style.opacity = .4;
                img.classList.remove('starActive');
                img.classList.remove('clicked');
            }
        });
    }

    xhr.onerror = () => { 
        location.reload();
    }

    xhr.send();
}