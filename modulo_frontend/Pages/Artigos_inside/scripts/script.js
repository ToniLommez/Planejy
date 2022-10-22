const user = JSON.parse(sessionStorage.getItem('user'));

onload = () => {
    if (!user) {
        location.href = '../../index.html';
        return;
    }

    viewTime();
    getArticles();
}

const logout = () => {
    sessionStorage.removeItem('user');
    location.href = '../../index.html';
}

const viewTime = () => {
    let times = 0;
    
    const id = getArticleId();
    let xhr = new XMLHttpRequest();

    xhr.onerror = () => { /* do nothing at last */ }
    
    xhr.onload = () => { /* do nothing once more */ }
    
    const interval = setInterval(() => {
        xhr.open('POST', `http://localhost:5678/articles/tempo/${user.token}/${id}`, true);
        xhr.send('');
        times++;

        if(times === 3) clearInterval(interval);
    }, 180000);
}

const getArticleId = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const articleId = urlParams.get('article_id');

    return articleId;
}

const updatePage = html => {
    let date = new Date(html.dataFabricacao); //for some reason new Date is parsing the date 1 day behind (no clue why that is)
    date.setDate(date.getDate() + 1); //this line adds that one day back

    document.querySelector('.card-header').innerHTML = `<h1>${html.titulo}</h1>`;
    document.querySelector('.card-body').innerHTML = html.conteudo;
    document.querySelectorAll('.autor').forEach(autor => autor.innerHTML = html.autor);
    document.querySelectorAll('.controle-imagem').forEach(imagem => imagem.setAttribute('src', `../Artigos/images/article-${html.chave}.png`));
    document.querySelectorAll('.controle-imagem').forEach(imagem => imagem.setAttribute('alt', html.imagem_alt));
    document.querySelectorAll('.dataFabricacao').forEach(data => data.innerHTML = date.toLocaleDateString('pt-BR'));
    loadStars(html);
}

const getArticles = () => {
    const id = getArticleId();
    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:5678/articles/receive/${id}/${user.token}`, true);

    xhr.onload = () => {
        updatePage(JSON.parse(xhr.responseText).Articles[0]);
    }

    xhr.onerror = () => {
        alert('erro ao carregar artigo ;-;');
    }

    xhr.send();
}