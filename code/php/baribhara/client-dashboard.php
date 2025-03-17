<?php
session_start();
include("dbconnect.php");

// Check if the user is logged in and their username is valid
if (!isset($_SESSION['username'])) {
    // If no session exists, redirect to login page
    header("Location: client-signin.php");
    exit();
}

// Get the logged-in username from the session
$username = $_SESSION['username'];

// Validate the username exists in the client table
$query = "SELECT * FROM client WHERE client_id = '$username'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) == 0) {
    // If the username doesn't exist in the client table, redirect to unauthorized page
    header("Location: 404.php");
    exit();
}

// Initialize search query and sorting options
$searchQuery = "";
$sortQuery = "";

// Handle search query
if (isset($_POST['search'])) {
    $searchTerm = mysqli_real_escape_string($conn, $_POST['searchTerm']);
    $searchQuery = "WHERE address LIKE '%$searchTerm%' OR value LIKE '%$searchTerm%' OR rooms LIKE '%$searchTerm%'";
}

// Handle sorting
if (isset($_POST['sortOption'])) {
    $sortOption = $_POST['sortOption'];
    switch ($sortOption) {
        case 'rating':
            $sortQuery = "ORDER BY rating DESC";
            break;
        case 'price':
            $sortQuery = "ORDER BY value DESC";
            break;
        case 'area':
            $sortQuery = "ORDER BY area DESC";
            break;
        default:
            $sortQuery = "";
    }
}

// Query properties based on search and sort
$query = "SELECT * FROM property $searchQuery $sortQuery";
$result = mysqli_query($conn, $query);

// Handle booked properties view
$showBookedProperties = isset($_POST['viewBookedProperties']) ? true : false;

// Query booked properties if the option is selected
$bookedPropertiesQuery = "";
if ($showBookedProperties) {
    $bookedPropertiesQuery = "SELECT * FROM property p
                              JOIN client_rents_property c ON p.property_id = c.property_id
                              WHERE c.client_id = '$username' AND c.payment_approved = 1";
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="client_styles.css">
    <title>Client Dashboard</title>
</head>
<body>
<div class="dashboard">
    <div class="search-panel">
        <h1 class="welcome-heading">Welcome, <?php echo htmlspecialchars($username); ?> (Client)</h1>
        <form method="POST" action="">
            <input type="text" name="searchTerm" placeholder="Search for a property..." required>
            <button type="submit" name="search" class="search-button">Search</button>
            <select name="sortOption" onchange="this.form.submit()">
                <option value="">Sort by</option>
                <option value="rating" <?php if(isset($_POST['sortOption']) && $_POST['sortOption'] == 'rating') echo 'selected'; ?>>Rating</option>
                <option value="price" <?php if(isset($_POST['sortOption']) && $_POST['sortOption'] == 'price') echo 'selected'; ?>>Price</option>
                <option value="area" <?php if(isset($_POST['sortOption']) && $_POST['sortOption'] == 'area') echo 'selected'; ?>>Area</option>
            </select>
        </form>
    </div>

    <!-- Booked Properties Button -->
    <form method="POST" action="">
        <button type="submit" name="viewBookedProperties" class="search-button">View Booked Properties</button>
    </form>

    <!-- Logout Button -->
    <form method="POST" action="logout.php">
        <button type="submit" class="logout-button">Logout</button>
    </form>

    <div class="search-results">
        <table>
            <thead>
                <tr>
                    <th>Property ID</th>
                    <th>Address</th>
                    <th>Price</th>
                    <th>Area</th>
                    <th>Rooms</th>
                    <th>Rating</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <?php
                if ($showBookedProperties) {
                    $result = mysqli_query($conn, $bookedPropertiesQuery);
                    if ($result && mysqli_num_rows($result) > 0) {
                        while ($property = mysqli_fetch_assoc($result)) {
                            echo "<tr>";
                            echo "<td>" . htmlspecialchars($property['property_id']) . "</td>";
                            echo "<td>" . htmlspecialchars($property['address']) . "</td>";
                            echo "<td>$" . number_format($property['value']) . "/month</td>";
                            echo "<td>" . htmlspecialchars($property['area']) . " sq ft</td>";
                            echo "<td>" . htmlspecialchars($property['rooms']) . "</td>";
                            echo "<td>" . htmlspecialchars($property['rating']) . "</td>";
                            echo "<td>Booked</td>";
                            echo "</tr>";
                        }
                    } else {
                        echo "<tr><td colspan='7'>No booked properties found.</td></tr>";
                    }
                } else {
                    if ($result && mysqli_num_rows($result) > 0) {
                        while ($property = mysqli_fetch_assoc($result)) {
                            echo "<tr>";
                            echo "<td>" . htmlspecialchars($property['property_id']) . "</td>";
                            echo "<td>" . htmlspecialchars($property['address']) . "</td>";
                            echo "<td>$" . number_format($property['value']) . "/month</td>";
                            echo "<td>" . htmlspecialchars($property['area']) . " sq ft</td>";
                            echo "<td>" . htmlspecialchars($property['rooms']) . "</td>";
                            echo "<td>" . htmlspecialchars($property['rating']) . "</td>";
                            echo "<td><button class='view-details' onclick='showPaymentPopup(" . $property['property_id'] . ")'>Book Now</button></td>";
                            echo "</tr>";
                        }
                    } else {
                        echo "<tr><td colspan='7'>No properties found.</td></tr>";
                    }
                }
                ?>
            </tbody>
        </table>
    </div>
</div>

<!-- Payment Popup -->
<div class="popup-overlay" id="paymentPopup">
    <div class="popup-content">
        <h2>Proceed with Payment</h2>
        <p id="popupPropertyDetails"></p>
        <button id="confirmPaymentBtn" onclick="confirmPayment()">Confirm Payment</button>
        <button onclick="closePaymentPopup()">Cancel</button>
    </div>
</div>

<script>
    let selectedPropertyId;

    function showPaymentPopup(propertyId) {
        selectedPropertyId = propertyId;
        const propertyDetails = document.querySelector(`#property_${propertyId}`);
        document.getElementById('popupPropertyDetails').innerText = `Property ID: ${propertyId}`;
        document.getElementById('paymentPopup').classList.add('active');
    }

    function closePaymentPopup() {
        document.getElementById('paymentPopup').classList.remove('active');
    }

    function confirmPayment() {
        // Get the client_id (logged-in user) from the session
        const clientId = "<?php echo $_SESSION['username']; ?>";
        const propertyId = selectedPropertyId;

        // Create a new FormData object to send the POST data
        const formData = new FormData();
        formData.append('client_id', clientId);
        formData.append('property_id', propertyId);

        // Send an AJAX request to the server
        fetch('process_payment.php', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Submitted successfully, now waiting for admin approval!');
                closePaymentPopup(); // Close the popup after successful payment
            } else {
                alert('Failed to confirm payment: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while processing the payment.');
        });
}

</script>
</body>
</html>
