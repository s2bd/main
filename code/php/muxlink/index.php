<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A modern URL shortener for easy-to-remember links.">
    <meta name="author" content="MuxAI">
    <meta name="keywords" content="url shortener, short link generator, generate short url, simple url shortener">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <title>Mux8 - ShortLink Generator</title>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-DC1F2F5B5C"></script>
    <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());

    gtag('config', 'G-DC1F2F5B5C');
    </script>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>MUX8 Link Service</h1>
        <form method="POST">
            <input type="url" name="url" placeholder="Enter URL here" required>
            <br>
            <button type="submit">🔗 Shorten URL！</button>
        </form>
        <div class="short-link">
            <?php
            function generateShortCode($length = 1) {
                $chars = 'abcdefghijklmnopqrstuvwxyz0123456789';
                return substr(str_shuffle($chars), 0, $length);
            }

            if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                $url = $_POST['url'];
                if (filter_var($url, FILTER_VALIDATE_URL)) {
                    $length = 1;
                    $shortCode = generateShortCode($length);

                    while (is_dir($shortCode)) {
                        $length++;
                        $shortCode = generateShortCode($length);
                    }

                    mkdir($shortCode);
                    $redirectFile = "<html><meta http-equiv=\"refresh\" content=\"0.1;url={$url}\" /></html>";
                    file_put_contents("$shortCode/index.html", $redirectFile);

                    echo "Short link created: <a href='$shortCode' target='_blank'><span id='mux8-url'>http://" . $_SERVER['HTTP_HOST'] . "/$shortCode</span></a><button id='copyBtn' onclick='copyURL()'><i class='fas fa-copy'></i> Copy URL</button>";
                } else {
                    echo "<p style='color: red;'>Invalid URL provided.</p>";
                }
            }
            ?>
        </div>
    </div>
    <br>
   <div class="projects-grid" id="projectsGrid"></div>
    <div class="about-panel" onclick="toggleAboutPanel()">About this platform</div>
    <div class="photo-credits">
        Photo by <a href="https://unsplash.com/@simon_berger?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Simon Berger</a> on <a href="https://unsplash.com/photos/landscape-photography-of-mountains-twukN12EN7c?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
    </div>
    
<script>
function toggleAboutPanel() {
    const aboutPanel = document.querySelector('.about-panel');
    if (!aboutPanel.classList.contains('expanded')) {
        aboutPanel.innerHTML = `
            About this platform - Mux8
            <br><br>
            Made by <a href="https://apps.mux8.com/" target="_blank">MuxAI</a> © 2025
        `;
        aboutPanel.classList.add('expanded');
    } else {
        aboutPanel.innerHTML = 'About this platform';
        aboutPanel.classList.remove('expanded');
    }
}

async function fetchProjects() {
    const response = await fetch('https://cdn.mux8.com/projects.json');
    const projects = await response.json();
    console.log(projects);
    const grid = document.getElementById('projectsGrid');
    
    projects.forEach(project => {
        const projectItem = document.createElement('div');
        projectItem.className = 'project-item';

        const img = document.createElement('img');
        img.src = project.project_icon;
        img.alt = project.project_name;
        projectItem.appendChild(img);

        const tooltip = document.createElement('div');
        tooltip.className = 'tooltip';
        tooltip.innerHTML = `
            <strong>${project.project_name}</strong>
            <p>${project.project_info}</p>
            <div class="project-categories">
                ${project.project_category.map(cat => `<span>${cat}</span>`).join('')}
            </div>
            <a href="${project.project_url}" target="_blank" class="visit-button">Visit ↗</a>
        `;
        projectItem.appendChild(tooltip);
        
        projectItem.addEventListener('mouseenter', () => tooltip.style.display = 'block');
        projectItem.addEventListener('mouseleave', () => tooltip.style.display = 'none');
        
        grid.appendChild(projectItem);
    });
}

fetchProjects();

function copyURL() {
    const mux8Url = document.getElementById('mux8-url');
    const copyBtn = document.getElementById('copyBtn');
    const textToCopy = mux8Url.innerText;
    if (!textToCopy) return;
    navigator.clipboard.writeText(textToCopy).then(() => {
        copyBtn.innerHTML = "<i class='fas fa-check'></i>";
        setTimeout(() => {
            copyBtn.innerHTML = "<i class='fas fa-copy'></i>";
        }, 2000);
    }).catch(err => {
        console.log("Error copying URL: " + err);
    });
}
</script>
</body>
</html>