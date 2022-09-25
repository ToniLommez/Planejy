var userLoggedData = localStorage.getItem('user_login');

const formatDate = (date) => {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

onload = () => {
    if (userLoggedData) {
        var userLogged = JSON.parse(userLoggedData),
            userLoggedObj = userLogged.user_login;

        if (userLoggedObj[0].access != true) {
            location.href = '../index.html'
        }
    } else {
        location.href = '../index.html'
    }
}

function logout() {
    var users = {
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

// Dados iniciais

var db_postits_inicial = {
    "data": [{
        "id": 0,
        "title": "Lorem Ipsum",
        "start": "2022-01-01",
        "descricao": "Lorem IPsum.",
        "horario": "22:30",
        "color": "#A7C7E7"
    }, ]
}

var db = JSON.parse(localStorage.getItem('db_postit'));
if (!db) {
    db = db_postits_inicial
};

let eventDatabase = JSON.parse(localStorage.getItem('db_postit'))

if (!eventDatabase) {
    eventDatabase = db;
}

let data = {
    headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,listMonth'
    },
    initialDate: formatDate(new Date().getTime()),
    navLinks: false, // can click day/week names to navigate views
    businessHours: true, // display business hours
    editable: true,
    selectable: true,
    handleWindowResize: true,
    events: eventDatabase.data,
    windowResizeDelay: 0,
    navLinks: true,
    navLinkWeekClick: function(weekStart, jsEvent) {
        console.log('week start', weekStart.toISOString());
        console.log('coords', jsEvent.pageX, jsEvent.pageY);
    },
}

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, data);

    // calendar.getInitialDate();
    calendar.render();
});

// Menu de adição de notas

// Init

function init() {
    // Adiciona funções para tratar os eventos 

    $("#btnInsert").click(function() {
        // Verfica se o formulário está preenchido corretamente
        if (!$("#form-postit")[0].checkValidity()) {
            //displayMessage("Preencha o formulário corretamente.");
            return;
        }

        // Obtem os valores dos campos do formulário
        let campoNome = $("#inputNome").val();
        let campoDescricao = $("#inputDescricao").val();
        let campoDia = $("#inputDia").val();
        let campoHorario = $("#inputHorario").val();
        let campoCategoria = $("#inputCategoria").val();
        let postit = {
            nome: campoNome,
            descricao: campoDescricao,
            horario: campoHorario,
            start: campoDia,
            categoria: campoCategoria,
        };

        insertPostit(postit);

        // Limpa o formulario
        $("#form-postit")[0].reset();

        location.reload();

    });

    // Intercepta o click do botão Alterar
    /*     $("#btnUpdate").click(function() {
            // Obtem os valores dos campos do formulário
            let campoId = $("#inputId").val();
            if (campoId == "") {
                //displayMessage("Selecione um post-it para ser alterado.");
                return;
            }
            let campoDescricao = $("#inputDescricao").val();
            let campoUrgencia = $("#inputUrgencia").val();
            let campoDia = $("#inputDia").val();
            let campoHorario = $("#inputHorario").val();
            let campoCategoria = $("#inputCategoria").val();
            let postit = {
                descricao: campoDescricao,
                urgencia: campoUrgencia,
                dia: campoDia,
                horario: campoHorario,
                categoria: campoCategoria,
            };

            updatepostit(parseInt(campoId), postit);

            // Limpa o formulario
            $("#form-postit")[0].reset();
        }); */

    // Intercepta o click do botao Limpar Form

    $("#btnClear").click(function() {

        $("#form-postit")[0].reset();

    });

}

function addNotes() {

    let noteMenu = document.querySelector('.container')
    noteMenu.style.display = 'block';

    $('#btnCancel').click(function() {
        noteMenu.style.display = 'none';
        // Limpa o formulario
        $("#form-postit")[0].reset();
    });

    init();
}

function insertPostit(postit) {
    // Calcula novo Id a partir do último código existente no array (PODE GERAR ERRO SE A BASE ESTIVER VAZIA)
    let novoId = 1;
    if (db.data.length != 0)
        novoId = db.data[db.data.length - 1].id + 1;
    let novoPostit = {
        "id": novoId,
        "title": postit.nome,
        "description": postit.descricao,
        "start": postit.start,
        "horario": postit.horario,
        "color": postit.categoria,
        "textColor": 'black'
    };

    // Insere o novo objeto no array
    db.data.push(novoPostit);
    //displayMessage("Post-it criado com sucesso");

    // Atualiza os dados no Local Storage
    localStorage.setItem('db_postit', JSON.stringify(db));
}

function updatepostit(id, postit) {
    // Localiza o indice do objeto a ser alterado no array a partir do seu ID
    let index = db.data.map(obj => obj.id).indexOf(id);

    // Altera os dados do objeto no array
    db.data[index].descricao = postit.descricao,
        db.data[index].dia = postit.dia,
        db.data[index].horario = postit.horario,
        db.data[index].urgencia = postit.urgencia,
        db.data[index].categoria = postit.categoria

    //displayMessage("Post-it alterado com sucesso");

    // Atualiza os dados no Local Storage
    localStorage.setItem('db_postit', JSON.stringify(db));
}

function deletepostit(id) {
    // Filtra o array removendo o elemento com o id passado
    db.data = db.data.filter(function(element) { return element.id != id });

    //displayMessage("Post-it removido com sucesso");

    // Atualiza os dados no Local Storage
    localStorage.setItem('db_postit', JSON.stringify(db));
}

function openEventList() {

    // Abrindo a lista

    let noteList = document.querySelector('.tabelaEventos')

    noteList.style.display = 'block';

    // caso clique no botao de fechar...

    listarEventos();

    $('#btnClose').click(function() {

        noteList.style.display = 'none';

    });

}

function listarEventos() {

    // limpa a lista de contatos apresentados

    $("#table-events").empty();

    // Popula a tabela com os registros do banco de dados

    for (let index = 0; index < eventDatabase.data.length; index++) {
        const evento = eventDatabase.data[index];

        // Inclui o contato na tabela   

        $("#table-events").append(`<tr><td scope="row">${evento.title}</td>
                                            <td>${evento.description}</td>
                                            <td>${evento.start}</td>
                                            <td>${evento.horario}</td>
                                            <td><span onclick="function deleteButton () {deletepostit(${evento.id}); listarEventos()}; deleteButton(); location.reload();" id="btn-delete-event" class="material-icons">delete</span>
                                        </tr>`);

        // Colorindo o fundo da tabela de acordo com a categoria

    }

}