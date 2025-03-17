<?php
// Include database connection file
include('dbconnect.php');

if (isset($_POST['register_submit'])) {
    // Get the registration data from the form
    $username = $_POST['register_username'];
    $password = $_POST['register_password'];
    $confirm_password = $_POST['confirm_password'];

    // Sanitize inputs to prevent SQL injection
    $username = mysqli_real_escape_string($conn, $username);
    $password = mysqli_real_escape_string($conn, $password);
    $confirm_password = mysqli_real_escape_string($conn, $confirm_password);

    // Check if passwords match
    if ($password !== $confirm_password) {
        $error = "Passwords do not match.";
    } else {
        // Check if the username already exists
        $query = "SELECT * FROM admin WHERE admin_id = '$username' LIMIT 1";
        $result = mysqli_query($conn, $query);

        if (mysqli_num_rows($result) > 0) {
            // Username already exists
            $error = "Username already taken. Please choose another one.";
        } else {
            // Hash the password
            $hashed_password = password_hash($password, PASSWORD_BCRYPT);

            // Insert new user into the database
            $insert_query = "INSERT INTO admin (admin_id, password) VALUES ('$username', '$hashed_password')";
            if (mysqli_query($conn, $insert_query)) {
                // Successful registration, redirect to login page or login section
                header('Location: admin-signin.php');
                exit();
            } else {
                // Database insertion error
                $error = "Error during registration. Please try again later.";
            }
        }
    }

    // Close the database connection
    mysqli_close($conn);
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Real Estate Management - admin Register</title>
    <style>
    body { background-image: url('https://images.unsplash.com/photo-1558036117-15d82a90b9b1?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
        }
    </style>
</head>
<body>
    <div class="overlay-text">BARIBHARA</div>
    <div class="container">
        <h1>Welcome</h1>
        <form id="form" method="POST" action="">

            <!-- Registration Fields -->
            <div class="form-group">
                <input type="text" name="register_username" placeholder="Username" required>
                <input type="password" name="register_password" placeholder="Enter Password" required>
                <input type="password" name="confirm_password" placeholder="Confirm Password" required>
            </div>

            <!-- Submit Buttons -->
            <button id="register-submit" class="button" type="submit" name="register_submit">Register</button>
        </form>
        
        <?php if (isset($error)): ?>
            <p class="error-message"><?= htmlspecialchars($error) ?></p>
        <?php endif; ?>
        <br />
        <center>Already existing admin? <a href="admin-signin.php">Sign in</a></center>
    </div>
    <div class="credits">
        Photo by <a href="https://unsplash.com/@pbanselme?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Bailey Anselme</a> on <a href="https://unsplash.com/photos/brown-wooden-house-with-green-grass-field-Bkp3gLygyeA?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
    </div>
    <div class="about-panel" onclick="toggleAboutPanel()">
        About this platform
    </div>
    <script src="scripts.js"></script>
</body>
</html>

