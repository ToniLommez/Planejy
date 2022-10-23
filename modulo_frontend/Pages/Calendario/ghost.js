const ghost = document.getElementById('ghost');

let mouseX = 0;
let mouseY = 0;

let ghostX = 0;
let ghostY = 0;

const hide = () => {
    ghost.style.opacity = 0;
}

const show = () => {
    ghost.style.opacity = 1;
}

document.body.addEventListener('mousemove', e => {
    requestAnimationFrame(() => {
        setPosition(e)
    });
});

const setPosition = e => {
    mouseX = e.clientX;
    mouseY = e.clientY;
    ghost.style.setProperty('--ghostXPos', mouseX + 'px');
    ghost.style.setProperty('--ghostYPos', mouseY + 'px');
}

show();