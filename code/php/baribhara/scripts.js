const container = document.querySelector('.container');

        function toggleMode() {
            container.classList.toggle('register-active');
        }
document.body.addEventListener('mousemove', (event) => {
    document.body.style.setProperty('--mouse-x', `${event.clientX}px`);
    document.body.style.setProperty('--mouse-y', `${event.clientY}px`);
});
function toggleAboutPanel() {
    const aboutPanel = document.querySelector('.about-panel');
    if (!aboutPanel.classList.contains('expanded')) {
        aboutPanel.innerHTML = `
            About this platform - by Group 3
            <br><br>
            Made by <a href="https://www.facebook.com/abrarahmed.avi77" target="_blank">Abrar Ahmed</a>, 
            <a href="https://www.facebook.com/" target="_blank">Fardin Noor</a>, 
            <a href="https://www.facebook.com/" target="_blank">Tahmid Mahdi</a>
            <br>As a group project for CSE370-16
        `;
        aboutPanel.classList.add('expanded');
    } else {
        aboutPanel.innerHTML = 'About this platform';
        aboutPanel.classList.remove('expanded');
    }
}



