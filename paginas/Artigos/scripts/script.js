let userLoggedData = localStorage.getItem('user_login');

onload = () => {
    if (userLoggedData) {
        let userLogged = JSON.parse(userLoggedData),
            userLoggedObj = userLogged.user_login;

        if (userLoggedObj[0].access != true) {
            location.href = '../index.html'
            return;
        }
    } else {
        location.href = '../index.html'
        return;
    }

    loadArticles();
}

const logout = () => {
    let users = {
        'user_login': [
            {
            'firstname': '',
            'email': '', 
            'passwd': '',
            'access': false
            }
        ]
    }
    localStorage.setItem('user_login', JSON.stringify(users));
    location.href = '../index.html';
}

const loadArticles = () => {
    let tab = document.querySelector('.inner_content');

    let articles = '';

    for(let i = 0; i < ARTICLES.Articles.length; i++){
        let stars = ``;

        let j = 0;
        while(j < Math.round(ARTICLES.Articles[i].rating)){
            stars += '<span class="fa fa-lg fa-star yellow"></span>';
            j++;
        }

        while(j < 5){
            stars += '<span class="fa fa-lg fa-star black"></span>';
            j++;
        }

        let article = `
            <div class="col-sm-6 col-lg-12 col-xl-6" id="card_Control">
                <div class="card mb-6">
                    <div class="row">
                        <div class="col-auto">
                            <img src="${ARTICLES.Articles[i].image}" alt="Trendy Pants and Shoes" class="img-fluid rounded-start" id="img_Control" />
                        </div>
                        <div class="col-lg">
                            <div class="card-body">
                                <h5 class="card-title">${ARTICLES.Articles[i].title}</h5>
                                <p class="card-text">
                                    ${ARTICLES.Articles[i].brief}
                                </p>
                                <form action="../Artigos_inside/Artigos_inside.html">
                                    <input type="hidden" name="article_id" value="1">
                                    <input class="btn btn-primary" type="submit" value="Leia Mais">
                                </form>
                                <div class="stars">
                                    ${stars}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

        articles += article;
    }

    tab.innerHTML = articles;
}
