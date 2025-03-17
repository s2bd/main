<?php
// Include database connection file
include('dbconnect.php');

if (isset($_POST['login_submit'])) {
    // Get the login data from the form
    $username = $_POST['login_username'];
    $password = $_POST['login_password'];

    // Sanitize inputs to prevent SQL injection
    $username = mysqli_real_escape_string($conn, $username);
    $password = mysqli_real_escape_string($conn, $password);

    // Query to check if the username exists in the owner table
    $query = "SELECT * FROM owner WHERE owner_id = '$username' LIMIT 1";
    $result = mysqli_query($conn, $query);

    if ($result && mysqli_num_rows($result) > 0) {
        // Fetch user data
        $user = mysqli_fetch_assoc($result);
        
        // Verify the password (assuming passwords are hashed in the database)
        if (password_verify($password, $user['password'])) {
            // Successful login, set session or cookies as needed
            session_start();
            $_SESSION['user_id'] = $user['owner_id'];
            $_SESSION['username'] = $user['owner_id']; // or any other user-related data

            // Redirect to a protected page (e.g., dashboard or home)
            header('Location: owner-dashboard.php');
            exit();
        } else {
            // Invalid password
            $error = "Invalid username or password.";
        }
    } else {
        // Username not found
        $error = "Invalid username or password.";
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
    <title>Real Estate Management - owner Login</title>
    <style>
    body { background-image: url('https://images.unsplash.com/photo-1560184897-ae75f418493e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
        }
    </style>
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

            <!-- Submit Buttons -->
            <button id="login-submit" class="button" type="submit" name="login_submit">Sign In</button>
        </form>
        
        <?php if (isset($error)): ?>
            <p class="error-message"><?= htmlspecialchars($error) ?></p>
        <?php endif; ?>
        <br />
    </div>
    <div class="credits">
        Photo by <a href="https://unsplash.com/@fromitaly?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Francesca Tosolini</a> on <a href="https://unsplash.com/photos/a-porch-with-two-chairs-and-a-table-on-it-XcVm8mn7NUM?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
    </div>
    <div class="about-panel" onclick="toggleAboutPanel()">
        About this platform
    </div>
    <script src="scripts.js"></script>
</body>
</html>

