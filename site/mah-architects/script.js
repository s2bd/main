// Burger Menu
document.getElementById("burger").addEventListener("click", () => {
  document.getElementById("mobileMenu").classList.toggle("active");
});

// Sticky Navbar Show/Hide on Scroll
let lastScroll = 0;
const navbar = document.getElementById("navbar");
window.addEventListener("scroll", () => {
  const currentScroll = window.pageYOffset;

  if (currentScroll <= 0) {
    navbar.style.top = "0";
    navbar.classList.remove("scrolled-up");
    navbar.classList.remove("scrolled-down");
  } else if (currentScroll > lastScroll) {
    navbar.style.top = "-100px";
    navbar.classList.add("scrolled-down");
    navbar.classList.remove("scrolled-up");
  } else {
    navbar.style.top = "0";
    navbar.classList.add("scrolled-up");
    navbar.classList.remove("scrolled-down");
  }

  lastScroll = currentScroll;
});

// Hero Slider
const images = [
  'images/project_images/canada/220 ruggle render 11.jpg',
  'images/project_images/bangladesh/Apartment Building at Banani, Dhaka.jpg',
  'images/project_images/bangladesh/Apartment Building at Gulshan-2, Dhaka.jpg'
];

const texts = [
  {
    title: 'We are the architectural specialists you need!',
    subtitle: 'MAH Architects Inc., previously known as A&A, was established in 2018 in Ontario, Canada. The firm has completed numerous projects and offers architectural services for various building types, including residential, commercial, retail, industrial, institutional, restaurant and café, healthcare, and hospitality facilities such as hotels and motels.'
  },
  {
    title: 'Creating spaces that inspire communities.',
    subtitle: 'Project X: Modern Living in Downtown'
  },
  {
    title: 'Innovative designs for modern living.',
    subtitle: 'Project Y: Sustainable Architecture'
  }
];

let currentIndex = 0;

const heroImage = document.querySelector('.hero-image');
const mainTitle = document.querySelector('.main-title');
const subtitle = document.querySelector('.subtitle');
const counter = document.querySelector('.counter');



function updateContent() {
  heroImage.classList.remove('fade-in-zoom-out');
  heroImage.classList.add('fade-out-zoom-in');

  mainTitle.classList.remove('text-fade-in-slide-up');
  subtitle.classList.remove('text-fade-in-slide-up');
  mainTitle.classList.add('text-fade-out-slide-down');
  subtitle.classList.add('text-fade-out-slide-down');

  setTimeout(() => {
    heroImage.src = images[currentIndex];
    heroImage.classList.remove('fade-out-zoom-in');
    heroImage.classList.add('fade-in-zoom-out');

    mainTitle.textContent = texts[currentIndex].title;
    subtitle.textContent = texts[currentIndex].subtitle;

    mainTitle.classList.remove('text-fade-out-slide-down');
    subtitle.classList.remove('text-fade-out-slide-down');
    mainTitle.classList.add('text-fade-in-slide-up');
    subtitle.classList.add('text-fade-in-slide-up');

    counter.textContent = `0${currentIndex + 1} / 0${images.length}`;

    updateArrowStates();
  }, 500);
}

// Initial load
updateContent();


