const ghost = document.getElementById('ghost');

let mouseX = 0;
let mouseY = 0;

let ghostX = 0;
let ghostY = 0;

let x = 0;
let y = 0;

let visible = false;

let animation = null;

const hide = () => {
    ghost.style.opacity = 0;
    visible = false;
    document.body.removeEventListener('mousemove', move);
    cancelAnimationFrame(animation);
}

const show = () => {
    document.body.addEventListener('mousemove', setPosition);

    ghost.style.opacity = 1;
    visible = true;
    move();
}

const setPosition = e => {
    mouseX = e.clientX;
    mouseY = e.clientY;
}

const move = () => {
    x = mouseX - ghostX;
    y = mouseY - ghostY;

    ghostX += x / 100;
    ghostY += y / 100;

    ghost.style.setProperty('--ghostXPos', ghostX + 'px');
    ghost.style.setProperty('--ghostYPos', ghostY + 'px');

    animation = requestAnimationFrame(move);
}
