let userLoggedData = localStorage.getItem('user_login'); //remover futuramente
let calendar;

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

const noteToSql = (note, method) => {
    let sql = '';

    if(method === 'update'){
        sql = `UPDATE planejy.nota 
               SET titulo = '${note.titulo}', 
               dia = '${note.dia}',
               categoria = '${note.categoria}',
               descricao = '${note.descricao}',
               horario = '${note.horario}',
               cor = '${note.cor}'
               WHERE nota.chave = '${note.id}'`;
    }else if(method === 'insert'){
        sql = `INSERT INTO planejy.nota (id_usuario, titulo, dia, descricao, horario, categoria, cor)
               VALUES ('${note.id_usuario}', '${note.titulo}', '${note.dia}', '${note.descricao}', '${note.horario}', '${note.categoria}', '${note.cor}')`;
    }else if(method === 'delete'){
        sql = `DELETE FROM planejy.nota WHERE chave = '${note.id}'`;
    }

    return sql;
}

const postNotes = (tmpNote, method) => {
    let note = {};
    
    if(isNaN(tmpNote)){
        note = {
            id_usuario: 1,
            id: tmpNote.id,
            titulo: tmpNote.title,
            dia: tmpNote.start,
            descricao: tmpNote.description,
            horario: tmpNote.horario,
            categoria: tmpNote.categoria,
            cor: tmpNote.color
        };
    }else{
        note = {
            id: tmpNote
        };
    }

    let sql = noteToSql(note, method);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:5678/nota/post/1', true);

    xhr.onload = () => {
        db = {data:[]};
        getNotes();
    }

    xhr.onerror = () => {
        alert('erro ao salvar notas ;-;');
    }

    xhr.send(sql);
}

const getNotes = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:5678/nota/get/1', true);

    xhr.onload = () => {
        let tmp = JSON.parse(xhr.responseText);
        // db = {data:[]};

        for(let i = 0; i < tmp.Notas.length; i++){
            db.data.push(tmp.Notas[i]);
        }
        
        // console.log(db.data);
        data.events = db.data;
        calendar = initCalendar();
        calendar.eventDragging = true;
        calendar.render();
    }

    xhr.onerror = () => {
        alert('erro ao recuperar notas ;-;');
    }

    xhr.send();
}

const initCalendar = () => {
    let calendarEl = document.getElementById('calendar');

    let calendar = new FullCalendar.Calendar(calendarEl, data);

    return calendar;
}

let db = {data:[]};
getNotes();
// console.log(db.data)

const data = {
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
    events: db.data,
    windowResizeDelay: 0,
    navLinks: true,
    eventClick: function(info) {
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
                postNotes(db.data[i], 'update');
            }
        }
    }
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
    localStorage.setItem('user_login', JSON.stringify(users)); //remover futuramente
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

        for (let i = 0; i < categ.length; i++) {
            if (categ[i].value === campoCategoria) {
                categName = categ[i].textContent;
                i = categ.length; //break
            }
        }

        if(campoNome == '' || campoDescricao == '' || campoDia == '' || campoHorario == '' || campoCategoria == '' || categName == ''){
            alert('Campos obrigatórios não podem estar vazios!');
            return;
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

        document.querySelector('.addNotes').style.display = 'none';
        // location.reload();
    });

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
        if (inputName.value == '' || inputCategory.value == '' || inputDay.value == '' || inputDescription.value == '' || inputHour.value == '') {
            alert('Campos obrigatórios não podem estar vazios!');
        } else {
            let categ = document.getElementById('inputCategoria').querySelectorAll('option');
            let categName;

            for (let i = 0; i < categ.length; i++) {
                if (categ[i].value === inputCategory.value) {
                    categName = categ[i].textContent;
                    i = categ.length; //break
                }
            }

            let index = 0;
            for (; index < db.data.length; index++) {
                if (db.data[index].id == note.id) {
                    break;
                }
            }

            db.data[index].title = inputName.value;
            db.data[index].description = inputDescription.value;
            db.data[index].horario = inputHour.value;
            db.data[index].start = inputDay.value;
            db.data[index].color = inputCategory.value;
            db.data[index].categoria = categName;

            noteMenu.style.display = 'none';

            postNotes(db.data[index], 'update');
            noteMenu.style.display = 'none';
            db = {data:[]};
            getNotes();
            // location.reload();
        }
    }

    document.getElementById('btnClear2').onclick = () => {
        inputName.value = null;
        inputDay.value = null;
        inputDescription.value = null;
        inputHour.value = null;
        inputCategory.value = null;
    }

    document.getElementById('btnDelet2').onclick = () => {
        let index = 0;
        for (; index < db.data.length; index++) {
            if (db.data[index].id == note.id) {
                break;
            }
        }

        postNotes(db.data[index], 'delete');
        noteMenu.style.display = 'none';
        // db = {data:[]};
        // getNotes();
        // location.reload();
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

    postNotes(novoPostit, 'insert');
    // db = {data:[]};
    // getNotes();
}

function deletepostit(id) {
    // Filtra o array removendo o elemento com o id passado
    db.data = db.data.filter(function(element) { return element.id != id });
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

    for (let index = 0; index < db.data.length; index++) {
        const evento = db.data[index];

        // Inclui o contato na tabela   

        $("#table-events").append(`<tr><td scope="row">${evento.title}</td>
                                        <td>${evento.description}</td>
                                        <td>${evento.start}</td>
                                        <td>${evento.horario}</td>
                                        <td><span onclick="postNotes(${db.data[index].id}, 'delete'); listarEventos(); location.reload();" id="btn-delete-event" class="material-icons">delete</span>
                                    </tr>`);
    }
}

let bufferArray = [];
let code = ['f', '4', '4', '3', '6', 'f'];

window.addEventListener('keyup', (e) => {
    const key = e.key.toLowerCase();
    bufferArray.push(key);

    if (bufferArray.length === code.length) {
        if (bufferArray.every(function(element, index) {
                return element === code[index];
            })) {
            location.href = 'https://taskmaster.carolnigri.repl.co/';
        }
    } else if (bufferArray[bufferArray.length - 1] != code[bufferArray.length - 1]) {
        bufferArray = [];
    }
});