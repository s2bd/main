function getRandomColor() {
    return `#${Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0')}`;
}

function generatePalette() {
    const mode = document.getElementById('mode').value;
    const palette = document.getElementById('palette');
    palette.innerHTML = '';
    let colors = [];

    let baseColor = getRandomColor();
    colors.push(baseColor);

    if (mode === 'complementary') {
        colors.push(getComplementaryColor(baseColor));
    } else if (mode === 'split-complementary') {
        colors.push(...getSplitComplementaryColors(baseColor));
    } else if (mode === 'analogous') {
        colors.push(...getAnalogousColors(baseColor));
    } else if (mode === 'triadic') {
        colors.push(...getTriadicColors(baseColor));
    } else if (mode === 'tetradic') {
        colors.push(...getTetradicColors(baseColor));
    } else if (mode === 'square') {
        colors.push(...getSquareColors(baseColor));
    } else if (mode === 'monochromatic') {
        colors.push(...getMonochromaticColors(baseColor));
    } else {
        while (colors.length < 5) {
            colors.push(getRandomColor());
        }
    }

    colors.forEach(color => {
        let colorBox = document.createElement('div');
        colorBox.className = 'color-box';
        colorBox.style.backgroundColor = color;
        colorBox.style.boxShadow = `0 0 15px ${color}`;

        let text = document.createElement('p');
        text.innerHTML = `
            HEX: ${color} <br>
            RGB: ${hexToRgb(color)} <br>
            CMYK: ${hexToCmyk(color)} <br>
            HSL: ${hexToHsl(color)} <br>
            HSV: ${hexToHsv(color)}
        `;
        colorBox.appendChild(text);

        palette.appendChild(colorBox);
    });
}

function toggleTheme() {
    document.body.classList.toggle('light');
}

function toggleChart() {
    const modal = document.getElementById("color-chart");
    modal.style.display = modal.style.display === "flex" ? "none" : "flex";
}

function updateColorBoxes(color) {
    const colorBoxes = document.querySelectorAll('.color-box');
    colorBoxes.forEach(box => {
        box.style.backgroundColor = color;
    });
}

function hexToRgb(hex) {
    let bigint = parseInt(hex.slice(1), 16);
    let r = (bigint >> 16) & 255;
    let g = (bigint >> 8) & 255;
    let b = bigint & 255;
    return `${r}, ${g}, ${b}`;
}

function hexToCmyk(hex) {
    let r, g, b;
    [r, g, b] = hexToRgb(hex).split(', ').map(Number);

    let k = 1 - Math.max(r / 255, g / 255, b / 255);
    let c = (1 - r / 255 - k) / (1 - k) || 0;
    let m = (1 - g / 255 - k) / (1 - k) || 0;
    let y = (1 - b / 255 - k) / (1 - k) || 0;

    return `${(c * 100).toFixed(1)}%, ${(m * 100).toFixed(1)}%, ${(y * 100).toFixed(1)}%, ${(k * 100).toFixed(1)}%`;
}

function hexToHsl(hex) {
    let r, g, b;
    [r, g, b] = hexToRgb(hex).split(', ').map(Number);

    r /= 255, g /= 255, b /= 255;
    let max = Math.max(r, g, b), min = Math.min(r, g, b);
    let h, s, l = (max + min) / 2;

    if (max === min) {
        h = s = 0; 
    } else {
        let d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch (max) {
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        h *= 60;
    }

    return `${Math.round(h)}°, ${(s * 100).toFixed(1)}%, ${(l * 100).toFixed(1)}%`;
}

function hexToHsv(hex) {
    let r, g, b;
    [r, g, b] = hexToRgb(hex).split(', ').map(Number);

    r /= 255, g /= 255, b /= 255;
    let max = Math.max(r, g, b), min = Math.min(r, g, b);
    let d = max - min;
    let h, s, v = max;

    s = max === 0 ? 0 : d / max;

    if (max === min) {
        h = 0; 
    } else {
        switch (max) {
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        h *= 60;
    }

    return `${Math.round(h)}°, ${(s * 100).toFixed(1)}%, ${(v * 100).toFixed(1)}%`;
}

function getComplementaryColor(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    let newH = (h + 180) % 360;
    return `hsl(${newH}, 100%, 50%)`;
}

function getSplitComplementaryColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    let first = (h + 150) % 360;
    let second = (h + 210) % 360;
    return [`hsl(${first}, 100%, 50%)`, `hsl(${second}, 100%, 50%)`];
}

function getAnalogousColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    return [
        `hsl(${(h - 30 + 360) % 360}, 100%, 50%)`,
        `hsl(${(h + 30) % 360}, 100%, 50%)`
    ];
}

function getTriadicColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    return [
        `hsl(${(h + 120) % 360}, 100%, 50%)`,
        `hsl(${(h + 240) % 360}, 100%, 50%)`
    ];
}

function getTetradicColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    return [
        `hsl(${(h + 90) % 360}, 100%, 50%)`,
        `hsl(${(h + 180) % 360}, 100%, 50%)`,
        `hsl(${(h + 270) % 360}, 100%, 50%)`
    ];
}

function getSquareColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    return [
        `hsl(${(h + 90) % 360}, 100%, 50%)`,
        `hsl(${(h + 180) % 360}, 100%, 50%)`,
        `hsl(${(h + 270) % 360}, 100%, 50%)`
    ];
}

function getMonochromaticColors(baseColor) {
    let hsl = hexToHsl(baseColor);
    let h = parseInt(hsl.split('°')[0]);
    return [
        `hsl(${h}, 100%, 70%)`,
        `hsl(${h}, 100%, 50%)`,
        `hsl(${h}, 100%, 30%)`
    ];
}

document.addEventListener("mousemove", (e) => {
    let x = (window.innerWidth / 2 - e.pageX) / 50;
    let y = (window.innerHeight / 2 - e.pageY) / 50;
    document.getElementById("palette").style.transform = `rotateY(${x}deg) rotateX(${y}deg)`;
});

generatePalette();
