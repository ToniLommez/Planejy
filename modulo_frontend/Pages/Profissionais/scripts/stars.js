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

                if(i <= starId){
                    if(img.classList.contains('starActive')){
                        img.src = 'images/star1.png';
                    }else{
                        img.src = 'images/star0.png';
                    }

                    img.style.opacity = .4;
                }
            }
        });
    });

    document.querySelectorAll('.star').forEach(star => {
        star.nextSibling.addEventListener('click', () => {
            const id = star.getAttribute('id').split('-');
            const proId = +id[1];
            const starId = +id[2] + 1;
            
            console.log(`proId - ${proId}\nstarId - ${starId}`);
        });
    });
}