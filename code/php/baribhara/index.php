<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Real Estate Management</title>
    <style>
        body { 
            background-image: url('https://images.unsplash.com/photo-1565402170291-8491f14678db?q=80&w=2017&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
        }
        .overlay-title {
            position: absolute;
            top: 20%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 10vw; /* Autosize based on viewport width */
            font-weight: bold;
            color: rgba(255, 255, 255, 0.991);
            pointer-events: none;
            z-index: 1;
            text-transform: uppercase;
            text-align: center;
            white-space: nowrap;
        }
        .button-group a {
            text-decoration: none; /* Remove underline */
        }
    </style>
</head>
<body>
    <div class="overlay-title">BARIBHARA</div>
    <div class="container">
        <h1>Who are you?</h1>
        <div class="button-group">
            <a href="client-signin.php" class="button">Client</a>
            <a href="owner-signin.php" class="button">House Owner</a>
            <a href="admin-signin.php" class="button">Admin</a>
        </div>
    </div>
    <div class="credits">
        Photo by <a href="https://unsplash.com/@aviosly?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Avi Waxman</a> on <a href="https://unsplash.com/photos/white-and-red-houses-f9qZuKoZYoY?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
    </div>
    <div class="about-panel" onclick="toggleAboutPanel()">
        About this platform
    </div>
    <script src="scripts.js"></script>
</body>
</html>
