let userLoggedData = localStorage.getItem('user_login');

const formatDate = (date) => {
    let d = new Date(date),
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
        let userLogged = JSON.parse(userLoggedData),
            userLoggedObj = userLogged.user_login;

        if (userLoggedObj[0].access != true) {
            location.href = '../../index.html'
        }
    } else {
        location.href = '../../index.html'
    }
}

//function called by Calendario.html
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
    location.href = '../../index.html';
}

// Dados iniciais

let db_postits_inicial = {
    "data": [{
        "id": 0,
        "title": "Lorem Ipsum",
        "start": "2022-01-01",
        "descricao": "Lorem IPsum.",
        "horario": "22:30",
        "color": "#A7C7E7",
        "categoria": "Dia-a-dia"
    }, ]
}

let db = JSON.parse(localStorage.getItem('db_postit'));
if (!db) {
    db = db_postits_inicial
};

let eventDatabase = JSON.parse(localStorage.getItem('db_postit'));

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
    eventClick: function(info){
        updateNotes(info.event);
    },
    navLinkWeekClick: function(weekStart, jsEvent) {
        console.log('week start', weekStart.toISOString());
        console.log('coords', jsEvent.pageX, jsEvent.pageY);
    },
    eventDrop: function(info) {
        for (let i = 0; i < db.data.length; i++) {
            if (db.data[i].id == info.event.id) {
                db.data[i].start = formatDate(info.event.start);
            }
        }

        localStorage.setItem('db_postit', JSON.stringify(db));
    }
}

const initCalendar = () => {
    let calendarEl = document.getElementById('calendar');

    let calendar = new FullCalendar.Calendar(calendarEl, data);

    return calendar;
}

let calendar = initCalendar();
calendar.eventDragging = true;
calendar.render();


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

        let categ = document.getElementById('inputCategoria').querySelectorAll('option');
        let categName;

        for(let i = 0; i < categ.length; i++){
            if(categ[i].value === campoCategoria){
                categName = categ[i].textContent;
                i = categ.length; //break
            }
        }

        let postit = {
            nome: campoNome,
            descricao: campoDescricao,
            horario: campoHorario,
            start: campoDia,
            color: campoCategoria,
            categoria: categName
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

    let noteMenu = document.querySelector('.addNotes');
    noteMenu.style.display = 'block';

    $('#btnCancel').click(function() {
        noteMenu.style.display = 'none';
        // Limpa o formulario
        $("#form-postit")[0].reset();
    });

    init();
}

const updateNotes = (note) => {
    let noteMenu = document.querySelector('.updateNotes');
    noteMenu.style.display = 'block';

    let inputName = document.getElementById('inputNome2');
    let inputDay = document.getElementById('inputDia2');
    let inputDescription = document.getElementById('inputDescricao2');
    let inputHour = document.getElementById('inputHorario2');
    let inputCategory = document.getElementById('inputCategoria2');


    // == fill current note values == //

    inputName.value = note.title;
    inputDay.value = note.startStr;
    inputDescription.value = note.extendedProps.description;
    inputHour.value = note.extendedProps.horario;
    inputCategory.value = note.backgroundColor;
    
    
    // == defining behavior of buttons == //
    
    document.getElementById('btnCancel2').onclick = () => {
        noteMenu.style.display = 'none';
    }
    
    document.getElementById('btnInsert2').onclick = () => {
        if(inputName.value == '' || inputCategory.value == '' || inputDay.value == ''){
            alert('Campos obrigatórios não podem estar vazios!');
        }else{
            let categ = document.getElementById('inputCategoria').querySelectorAll('option');
            let categName;

            for(let i = 0; i < categ.length; i++){
                if(categ[i].value === inputCategory.value){
                    categName = categ[i].textContent;
                    i = categ.length; //break
                }
            }
            
            db.data[note.id].title = inputName.value;
            db.data[note.id].description = inputDescription.value;
            db.data[note.id].horario = inputHour.value;
            db.data[note.id].start = inputDay.value;
            db.data[note.id].color = inputCategory.value;
            db.data[note.id].categoria = categName;

            localStorage.setItem('db_postit', JSON.stringify(db));
            
            noteMenu.style.display = 'none';
            
            location.reload();
        }
        
    }
    
    document.getElementById('btnClear2').onclick = () => {
        inputName.value = null;
        inputDay.value = null;
        inputDescription.value = null;
        inputHour.value = null;
        inputCategory.value = null;
    }
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
        "color": postit.color,
        "categoria": postit.categoria,
        "textColor": 'black'
    };

    // Insere o novo objeto no array
    db.data.push(novoPostit);
    //displayMessage("Post-it criado com sucesso");

    // Atualiza os dados no Local Storage
    localStorage.setItem('db_postit', JSON.stringify(db));
}

// function updatepostit(id, postit) {
//     // Localiza o indice do objeto a ser alterado no array a partir do seu ID
//     let index = db.data.map(obj => obj.id).indexOf(id);

//     // Altera os dados do objeto no array
//     db.data[index].descricao = postit.descricao,
//         db.data[index].dia = postit.dia,
//         db.data[index].horario = postit.horario,
//         db.data[index].urgencia = postit.urgencia,
//         db.data[index].categoria = postit.categoria

//     //displayMessage("Post-it alterado com sucesso");

//     // Atualiza os dados no Local Storage
//     localStorage.setItem('db_postit', JSON.stringify(db));
// }

function deletepostit(id) {
    // Filtra o array removendo o elemento com o id passado
    db.data = db.data.filter(function(element) { return element.id != id });

    //displayMessage("Post-it removido com sucesso");

    // Atualiza os dados no Local Storage
    localStorage.setItem('db_postit', JSON.stringify(db));
}

function openEventList() {

    // Abrindo a lista

    let noteList = document.querySelector('.tabelaEventos');

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


let bufferArray = [];
let code = ['f', '4', '4', '3', '6', 'f'];

window.addEventListener('keyup', (e) => {
    const key = e.key.toLowerCase();
    bufferArray.push(key);

    if(bufferArray.length === code.length){
        if(bufferArray.every(function(element, index) {
            return element === code[index];
        })){
            console.log('taskmaster'); //redirect
        }
    }else if(bufferArray[bufferArray.length - 1] != code[bufferArray.length - 1]){
        bufferArray = [];
    }
});