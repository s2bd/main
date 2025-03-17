<?php
session_start();
include("dbconnect.php");

// Check if the user is logged in and their username is valid
if (!isset($_SESSION['username'])) {
    header("Location: admin-signin.php");
    exit();
}

// Get the logged-in username from the session
$username = $_SESSION['username'];

// Validate the username exists in the admin table
$query = "SELECT * FROM admin WHERE admin_id = '$username'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) == 0) {
    header("Location: 404.php");
    exit();
}

// Handle POST actions from forms
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Remove property
    if (isset($_POST['remove_property'])) {
        $property_id = mysqli_real_escape_string($conn, $_POST['property_id']);
        
        // Check if the property exists before deleting
        $check_query = "SELECT * FROM property WHERE property_id='$property_id'";
        $check_result = mysqli_query($conn, $check_query);

        if ($check_result && mysqli_num_rows($check_result) > 0) {
            // Property exists, proceed with deletion
            $delete_query = "DELETE FROM property WHERE property_id='$property_id'";
            if (mysqli_query($conn, $delete_query)) {
                echo "<script>alert('Property removed successfully!');</script>";
            } else {
                echo "<script>alert('Error: Could not remove property. SQL Error: " . mysqli_error($conn) . "');</script>";
            }
        } else {
            echo "<script>alert('Error: Property ID not found.');</script>";
        }
    }


    // Add or remove clients
    if (isset($_POST['add_client'])) {
        $client_id = mysqli_real_escape_string($conn, $_POST['client_id']);
        $client_name = mysqli_real_escape_string($conn, $_POST['client_name']);
        $client_email = mysqli_real_escape_string($conn, $_POST['client_email']);

        $insert_query = "INSERT INTO client (client_id, name, email) 
                        VALUES ('$client_id', '$client_name', '$client_email')";
        if (mysqli_query($conn, $insert_query)) {
            echo "<script>alert('Client added successfully!');</script>";
        } else {
            echo "<script>alert('Error: Could not add client.');</script>";
        }
    }  elseif (isset($_POST['remove_client'])) {
        $client_id = mysqli_real_escape_string($conn, $_POST['client_id']);
        $delete_query = "DELETE FROM client WHERE client_id = '$client_id'";
        if (mysqli_query($conn, $delete_query)) {
            echo "<script>alert('Client removed successfully!');</script>";
        } else {
            echo "<script>alert('Error: Could not remove client.');</script>";
        }
    }


    // Edit property details
    if (isset($_POST['edit_property'])) {
        $property_id = $_POST['property_id'];
        $new_details = $_POST['details'];
        $update_query = "UPDATE properties SET details='$new_details' WHERE property_id='$property_id'";
        mysqli_query($conn, $update_query);
    }

    // Approve payment
    if (isset($_POST['approve_payment'])) {
        $payment_id = mysqli_real_escape_string($conn, $_POST['payment_id']);
        
        // Ensure the payment exists in the database
        $check_query = "SELECT * FROM payment WHERE transaction_id = '$payment_id'";
        $check_result = mysqli_query($conn, $check_query);
        
        if (mysqli_num_rows($check_result) > 0) {
            // Update the payment record
            $update_query = "UPDATE payment SET 
                                status='paid', 
                                approved_by='$username' 
                            WHERE transaction_id='$payment_id'";
            if (mysqli_query($conn, $update_query)) {
                echo "<script>alert('Payment approved successfully! (1/2)');</script>";
            } else {
                echo "<script>alert('Error: Could not approve payment. (1/2)');</script>";
            }
            $update_query2 = "UPDATE client_rents_property SET 
                                payment_approved = 1 
                            WHERE payment_id = '$payment_id'";
            if (mysqli_query($conn, $update_query2)) {
                echo "<script>alert('Payment approved successfully! (2/2)');</script>";
            } else {
                echo "<script>alert('Error: Could not approve payment. (2/2)');</script>";
            }         
        } else {
            echo "<script>alert('Payment ID not found.');</script>";
        }
    }

    // My Profile
    if (isset($_POST['update_profile'])) {
        $new_admin_id = mysqli_real_escape_string($conn, $_POST['new_admin_id']);
        $new_password = mysqli_real_escape_string($conn, $_POST['new_password']);
        $confirm_new_password = mysqli_real_escape_string($conn, $_POST['confirm_new_password']);

        // Check if passwords match
        if ($new_password !== $confirm_new_password) {
            echo "<script>alert('Passwords do not match.');</script>";
        } else {
            // Hash the new password
            $hashed_password = password_hash($new_password, PASSWORD_BCRYPT);

            // Update the admin record
            $update_query = "UPDATE admin SET admin_id='$new_admin_id', password='$hashed_password' WHERE admin_id='$username'";
            
            if (mysqli_query($conn, $update_query)) {
                // Update session username
                $_SESSION['username'] = $new_admin_id;

                echo "<script>alert('Profile updated successfully!');</script>";
            } else {
                echo "<script>alert('Error updating profile.');</script>";
            }
        }
    }



    // Logout mechanism
    if (isset($_POST['logout'])) {
        // Clear all session data
        $_SESSION = [];
        session_unset();
        session_destroy();
        header('Location: index.php');
        exit();
    }
    
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="admin_styles.css">
    <title>Admin Dashboard</title>
</head>
<body>
<div class="dashboard">
    <aside class="sidebar">
        <h2>Navigation</h2>
        <ul>
            <li><a href="#listings">Manage Properties</a></li>
            <li><a href="#clients">Manage Clients</a></li>
            <li><a href="#payments">Manage Payments</a></li>
            <li><a href="#profile">My Profile</a></li>
            <form method="POST">
                <button class="sidebar" type="submit" name="logout">Logout</button>
            </form>
        </ul>
    </aside>

    <main class="content">
    <h1 id="default-heading">Welcome, <?php echo htmlspecialchars($username); ?> (Admin)</h1>

    <!-- Manage Properties Section -->
    <section id="listings">
        <h2>Manage Properties</h2>
        <form method="POST">
            <input type="text" name="property_id" placeholder="Property ID" required>
            <button type="submit" name="remove_property">Remove Property</button>
        </form>
    </section>

    <!-- Manage Clients Section -->
    <section id="clients">
    <h2>Manage Clients</h2>
        <form method="POST">
            <input type="text" name="client_id" placeholder="Client ID" required>
            <input type="text" name="client_name" placeholder="Client Name">
            <input type="email" name="client_email" placeholder="Client Email">
            <button type="submit" name="add_client">Add Client</button>
            <button type="submit" name="remove_client">Remove Client</button>
        </form>
    </section>


    <!-- Manage Payments Section -->
    <section id="payments">
        <h2>Manage Payments</h2>
        <form method="POST">
            <input type="text" name="payment_id" placeholder="Payment ID" required>
            <button type="submit" name="approve_payment">Approve Payment</button>
        </form>
    </section>

    <section id="profile">
        <h2>My Profile</h2>
        <form method="POST">
            <input type="text" name="new_admin_id" placeholder="New Username" value="<?php echo htmlspecialchars($username); ?>" required>
            <input type="password" name="new_password" placeholder="New Password" required>
            <input type="password" name="confirm_new_password" placeholder="Confirm New Password" required>
            <button type="submit" name="update_profile">Update Profile</button>
        </form>
    </section>

</main>

</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const sections = document.querySelectorAll("main.content section");
        const links = document.querySelectorAll(".sidebar ul li a");
        const defaultHeading = document.querySelector("main.content h1");

        // Hide all sections by default
        sections.forEach(section => section.style.display = "none");

        // Handle navigation clicks
        links.forEach(link => {
            link.addEventListener("click", function(e) {
                e.preventDefault();

                // Hide all sections and reset heading
                sections.forEach(section => section.style.display = "none");
                defaultHeading.style.display = "none";

                // Show the corresponding section
                const targetId = link.getAttribute("href").substring(1);
                const targetSection = document.querySelector(`#${targetId}`);
                if (targetSection) targetSection.style.display = "block";
            });
        });
    });
</script>

</body>
</html>