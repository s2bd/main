// Carousel functionality
let carouselIndex = 0;
const carouselImgs = document.querySelectorAll('.carousel-img');
const totalImgs = carouselImgs.length;
const carouselCaptions = [
  "Modern Duplex", // Example caption for first image
  "Historic Museum", // Example caption for second image
  "Custom Residence", // Example caption for third image
  "Public Gathering Space", // Example caption for fourth image
  "Urban Living" // Example caption for fifth image
];

const captionBox = document.querySelector('.carousel-caption-text');

function carousel() {
  carouselImgs.forEach((img, index) => {
    img.classList.remove('active');
  });

  carouselIndex = (carouselIndex + 1) % totalImgs;
  carouselImgs[carouselIndex].classList.add('active');
  captionBox.textContent = carouselCaptions[carouselIndex]; // Update caption based on active image
}

setInterval(carousel, 4000);

// Shrinking Titles on Scroll
const titles = document.querySelectorAll('h2');

window.addEventListener('scroll', () => {
  titles.forEach(title => {
    if (window.scrollY > 100) {
      title.style.fontSize = '36px';
    } else {
      title.style.fontSize = '48px';
    }
  });
});


// Navbar scroll tracker with dynamic width + horizontal movement
const sections = document.querySelectorAll('section');
const navLinks = document.querySelectorAll('.navbar a');
const navHighlight = document.querySelector('.nav-highlight');

function updateHighlight() {
  let currentId = '';
  sections.forEach(section => {
    const top = section.offsetTop;
    const height = section.offsetHeight;
    if (pageYOffset >= top - height / 3) {
      currentId = section.getAttribute('id');
    }
  });

  navLinks.forEach(link => link.classList.remove('active'));

  const activeLink = [...navLinks].find(link => link.getAttribute('href') === `#${currentId}`);
  if (activeLink) {
    activeLink.classList.add('active');
    const linkRect = activeLink.getBoundingClientRect();
    const parentRect = activeLink.parentElement.getBoundingClientRect();

    navHighlight.style.width = `${linkRect.width}px`;
    navHighlight.style.transform = `translateX(${linkRect.left - parentRect.left}px)`;
  }
}

window.addEventListener('scroll', updateHighlight);
window.addEventListener('load', updateHighlight);

