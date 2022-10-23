let ghost = document.querySelector("#ghost");
      
let headerFloatie = document.querySelector(".floatie");
let headerFloatieSVG = document.querySelector(".floatie svg");
let permanentlyHideFloatie = false;

let isFiring = false;

let mouseX = 0;
let mouseY = 0;

let dX = 0;
let dY = 0;

let xPos = 0;
let yPos = 0;

let ghostTimeElapsed = 0;
let ghostVisibility = true;



function throttleEvent(event) {
    if (isFiring === false) {
        requestAnimationFrame(() => {
            setGhostPosition(event);
            isFiring = false;
        });
    }

    isFiring = true;
}

function setInitialGhostPosition() {
    if (permanentlyHideFloatie == true) {
        console.log("Ghost shouldn't be shown at all!");
        ghost.style.opacity = 0;
        return;
    }

    let initialXPos = localStorage.getItem("ghostInitialX");
    let initialYPos = localStorage.getItem("ghostInitialY");

    if (initialXPos) {
        ghost.style.setProperty("--ghostXPos", initialXPos + "px");
        xPos = Number(initialXPos);
        mouseX = Number(localStorage.getItem("ghostMouseX"));
    }

    if (initialYPos) {
        ghost.style.setProperty("--ghostYPos", initialYPos + "px");
        yPos = Number(initialYPos);
        mouseY = Number(localStorage.getItem("ghostMouseY"));
    }

    console.log(mouseX + " " + initialXPos + ", " + mouseY + " " + initialYPos);
}

setInitialGhostPosition();

function setGhostPosition(event) {
    if (permanentlyHideFloatie == false) {
        ghostTimeElapsed = 0;
        ghostVisibility = true;
        showFloatie();
    } else {
        hideFloatie();
    }

    mouseX = event.clientX;
    mouseY = event.clientY;
}

function moveGhost() {
    if (permanentlyHideFloatie == false) {
        dX = mouseX - xPos;
        dY = mouseY - yPos;

        xPos += (dX / 100);
        yPos += (dY / 100);

        ghost.style.setProperty("--ghostXPos", xPos + "px");
        ghost.style.setProperty("--ghostYPos", yPos + "px");

        localStorage.setItem("ghostInitialX", xPos);
        localStorage.setItem("ghostInitialY", yPos);

        localStorage.setItem("ghostMouseX", xPos);
        localStorage.setItem("ghostMouseY", yPos);

        ghostTimeElapsed++;


        if ((ghostTimeElapsed > 300) && (ghostVisibility == true)) {
            hideFloatie();
            ghostVisibility = false;
        }
    }

    requestAnimationFrame(moveGhost);
}

moveGhost();

function showFloatie() {
    ghost.style.opacity = 1;
}

function hideFloatie() {
    ghost.style.opacity = 0;
}

headerFloatie.addEventListener("click", toggleFloatieVisibility, false);

function getFloatieVisbility() {
    let floatieStatus = localStorage.getItem("floatieVisbility");
    
    if (floatieStatus == "show") {
        permanentlyHideFloatie = false;
        headerFloatieSVG.classList.add("enabled");
    } else if (floatieStatus == "hide") {
        permanentlyHideFloatie = true;
        headerFloatieSVG.classList.remove("enabled");
    } else {
        permanentlyHideFloatie = false;
        headerFloatieSVG.classList.remove("enabled");
    }
}

getFloatieVisbility();

function kickOffFloatie() {
    if (permanentlyHideFloatie == true) {
        hideFloatie();
    } else {
        document.body.addEventListener("mousemove", throttleEvent, false);
        headerFloatieSVG.classList.add("enabled");
        showFloatie();
    }
}

kickOffFloatie();

function toggleFloatieVisibility(event) {
    let floatieStatus = localStorage.getItem("floatieVisbility");
    
    if (floatieStatus == undefined) {
        console.log("No stored value!");
        localStorage.setItem("floatieVisbility", "hide");
        permanentlyHideFloatie = true;
        
        headerFloatieSVG.classList.remove("enabled");
    }
    
    if (floatieStatus == "show") {
        localStorage.setItem("floatieVisbility", "hide");
        permanentlyHideFloatie = true;
        
        headerFloatieSVG.classList.remove("enabled");
    }
    
    if (floatieStatus == "hide") {
        localStorage.setItem("floatieVisbility", "show");
        permanentlyHideFloatie = false;
        
        kickOffFloatie();
        
        headerFloatieSVG.classList.add("enabled");
    }

    console.log(permanentlyHideFloatie);
}