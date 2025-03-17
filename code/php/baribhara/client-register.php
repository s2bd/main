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
        $query = "SELECT * FROM client WHERE client_id = '$username' LIMIT 1";
        $result = mysqli_query($conn, $query);

        if (mysqli_num_rows($result) > 0) {
            // Username already exists
            $error = "Username already taken. Please choose another one.";
        } else {
            // Hash the password
            $hashed_password = password_hash($password, PASSWORD_BCRYPT);

            // Insert new user into the database
            $insert_query = "INSERT INTO client (client_id, password) VALUES ('$username', '$hashed_password')";
            if (mysqli_query($conn, $insert_query)) {
                // Successful registration, redirect to login page or login section
                header('Location: client-signin.php');
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
    <title>Real Estate Management - Client Register</title>
    <style>
    body { 
        background-image: url('https://images.unsplash.com/photo-1605146769289-440113cc3d00?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHx8MA%3D%3D');
    }
    .form-group input {
        margin-bottom: 10px;
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
                <br>
                <input type="password" name="register_password" placeholder="Enter Password" required>
                <br>
                <input type="password" name="confirm_password" placeholder="Confirm Password" required>
            </div>

            <!-- Submit Buttons -->
            <button id="register-submit" class="button" type="submit" name="register_submit">Register</button>
        </form>
        
        <?php if (isset($error)): ?>
            <p class="error-message"><?= htmlspecialchars($error) ?></p>
        <?php endif; ?>
        <br />
        <center>Already existing client? <a href="client-signin.php">Sign in</a></center>
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
