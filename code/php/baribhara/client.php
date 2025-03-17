<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['login_submit'])) {
        // Sign-in logic
        $username = $_POST['login_username'];
        $password = $_POST['login_password'];

        $query = "SELECT * FROM client WHERE client_id='$username'";
        $result = mysqli_query($conn, $query);
        $user = mysqli_fetch_assoc($result);

        if ($user && password_verify($password, $user['password'])) {
            $_SESSION['username'] = $username;
            header("Location: client-dashboard.php");
            exit();
        } else {
            $error = "Invalid username or password!";
        }
    } elseif (isset($_POST['register_submit'])) {
        // Registration logic
        $username = $_POST['register_username'];
        $password = $_POST['register_password'];
        $confirm_password = $_POST['confirm_password'];

        if ($password !== $confirm_password) {
            $error = "Passwords do not match!";
        } else {
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);
            $query = "INSERT INTO client (client_id, password, registration_date) VALUES ('$username', '$hashed_password', NOW())";

            if (mysqli_query($conn, $query)) {
                $_SESSION['username'] = $username;
                header("Location: client.php"); // Redirect back to login form
                exit();
            } else {
                $error = "Registration failed. Please try again.";
            }
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Real Estate Management Login</title>
</head>
<body>
    <div class="overlay-text">BARIBHARA</div>
    <div class="container">
        <h1>Welcome</h1>
        <form id="form" method="POST" action="">
        
            <!-- Login Fields -->
            <div class="form-group login-fields">
                <input type="text" name="login_username" placeholder="Username" required>
                <input type="password" name="login_password" placeholder="Password" required>
            </div>

            <!-- Registration Fields -->
            <div class="form-group register-fields">
                <input type="text" name="register_username" placeholder="Username" required>
                <input type="password" name="register_password" placeholder="Enter Password" required>
                <input type="password" name="confirm_password" placeholder="Confirm Password" required>
            </div>

            <!-- Mode Switch -->
            <div class="switch" onclick="toggleMode()">
                <div class="switch-button">
                    <div class="slider"></div>
                    <span>Sign In</span>
                    <span>Register</span>
                </div>
            </div>

            <!-- Submit Buttons -->
            <button id="login-submit" class="button" type="submit" name="login_submit">Sign In</button>
            <button id="register-submit" class="button" type="submit" name="register_submit">Register</button>
        </form>
        
        <?php if (isset($error)): ?>
            <p class="error-message"><?= htmlspecialchars($error) ?></p>
        <?php endif; ?>
    </div>
    <div class="credits">
        Photo by <a href="https://unsplash.com/@kyddvisuals?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash" target="_blank">Dillon Kydd</a> on 
        <a href="https://unsplash.com/photos/gray-and-white-concrete-house-2keCPb73aQY?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash" target="_blank">Unsplash</a>
    </div>
    <div class="about-panel" onclick="toggleAboutPanel()">
        About this platform
    </div>
    <script src="scripts.js"></script>
</body>
</html>

