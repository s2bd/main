<?php
session_start();
include("dbconnect.php");

// Check if the user is logged in and their username is valid
if (!isset($_SESSION['username'])) {
    // If no session exists, redirect to login page
    header("Location: owner-signin.php");
    exit();
}

// Get the logged-in username from the session
$username = $_SESSION['username'];

// Fetch properties from the database
$query = "SELECT * FROM property";
$result = mysqli_query($conn, $query);

$listings = [];
if ($result && mysqli_num_rows($result) > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        $listings[] = [
            "id" => $row['property_id'],
            "area" => $row['area'],
            "location" => $row['address'],
            "price" => "$" . number_format($row['value']) . "/month",
            "rooms" => $row['rooms']
        ];
    }
}

// Fetch owner information from the database
$query = "SELECT * FROM owner WHERE owner_id = '$username'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $ownerInfo = mysqli_fetch_assoc($result);
} else {
    // If the username doesn't exist in the owner table, redirect to unauthorized page
    header("Location: 404.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Owner Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .dashboard {
            display: flex;
            margin-top: 30px;
        }
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            height: 100vh;
            box-sizing: border-box;
        }
        .sidebar h2 {
            margin-bottom: 20px;
            font-size: 1.5em;
        }
        .sidebar ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar li {
            margin: 10px 0;
        }
        .sidebar a {
            color: #ecf0f1;
            text-decoration: none;
            font-size: 1.1em;
        }
        .sidebar a:hover {
            text-decoration: underline;
        }
        .content {
            flex-grow: 1;
            padding: 20px;
        }
        h1 {
            color: #2c3e50;
            font-size: 2em;
        }
        .table-container {
            display: none; /* Initially hidden */
            margin-top: 20px;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table thead {
            background-color: #2c3e50;
            color: #ecf0f1;
        }
        table th, table td {
            padding: 15px 20px; /* Adjusted padding for a more spacious look */
            text-align: center;
            border: 1px solid #ddd;
        }
        table tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            color: #fff;
            font-size: 0.9em;
        }
        .btn-view {
            background-color: #2ecc71; /* Green for view button */
        }
        .btn-edit {
            background-color: #3498db;
        }
        .btn-delete {
            background-color: #e74c3c;
        }
        .actions {
            display: flex;
            gap: 10px;
            justify-content: center;
        }

        /* Profile Section Styling */
        .profile-container {
            display: none; /* Initially hidden */
            margin-top: 20px;
            padding: 20px; /* Padding adjusted */
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            width: auto; /* Container will hug the content */
            max-width: 800px; /* Set a max width to prevent it from becoming too wide */
            margin-left: auto;
            margin-right: auto;
        }
        .profile-container h2 {
            margin-bottom: 20px;
            font-size: 1.8em;
            color: #2c3e50;
        }
        .profile-container p {
            margin: 15px 0;
            font-size: 1.1em;
            color: #333;
            text-align: left; /* Left-align the text */
        }
        .profile-container p strong {
            color: #2c3e50;
        }

        /* Modal styling */
        .overlay {
            display: none; /* Initially hidden */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1000;
            justify-content: center;
            align-items: center;
        }
        .modal {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            width: 60%;
            max-width: 600px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
        .modal h2 {
            margin-bottom: 20px;
        }
        .modal input, .modal select, .modal textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .modal button {
            padding: 8px 15px;
            background-color: #2c3e50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: auto;
            margin-top: 10px;
            font-size: 1em;
        }
        .modal button:hover {
            background-color: #34495e;
        }
        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: transparent;
            border: none;
            font-size: 20px;
            color: #999;
            cursor: pointer;
        }
        .close-btn:hover {
            color: #333;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <aside class="sidebar">
        <h2>Navigation</h2>
        <ul>
            <li><a href="javascript:void(0);" onclick="showListings()">My Listings</a></li>
            <li><a href="javascript:void(0);" onclick="openModal()">List Property</a></li>
            <li><a href="javascript:void(0);" onclick="showProfile()">My Profile</a></li> <!-- Profile link -->
            <li><a href="logout.php">Logout</a></li>
        </ul>
    </aside>
    <main class="content">
        <h1 id="welcomeMessage">Welcome, <?php echo htmlspecialchars($username); ?> (Owner)</h1>

        <!-- Table container -->
        <div class="table-container" id="listingsTable">
            <h2>Your Listings</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Area (sq meter)</th> <!-- Updated column -->
                        <th>Location</th>
                        <th>Price</th>
                        <th>Rooms</th> <!-- Updated column -->
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
    <?php foreach ($listings as $listing): ?>
        <tr data-id="<?php echo htmlspecialchars($listing['id']); ?>">
            <td><?php echo htmlspecialchars($listing['id']); ?></td>
            <td class="property-area"><?php echo htmlspecialchars($listing['area']); ?> m²</td>
            <td class="property-location"><?php echo htmlspecialchars($listing['location']); ?></td>
            <td class="property-price"><?php echo htmlspecialchars($listing['price']); ?></td>
            <td class="property-rooms"><?php echo htmlspecialchars($listing['rooms']); ?></td>
            <td class="actions">
                <a href="#" class="btn btn-view" onclick='viewPropertyDetails(<?php echo json_encode($listing); ?>)'>View</a>
                <a href="#" class="btn btn-edit" onclick='editPropertyDetails(<?php echo json_encode($listing); ?>)'>Edit</a>
                <a href="#" class="btn btn-delete" onclick='deleteProperty(<?php echo json_encode($listing); ?>)'>Delete</a>
            </td>
        </tr>
    <?php endforeach; ?>
</tbody>
            </table>
        </div>


        <!-- Profile container -->
        <div class="profile-container" id="profileInfo">
            <h2>Your Profile</h2>
            <p><strong>ID:</strong> <?php echo htmlspecialchars($ownerInfo['owner_id']); ?></p>
            <p><strong>Name:</strong> <?php echo htmlspecialchars($ownerInfo['name']); ?></p>
            <p><strong>Phone:</strong> <?php echo htmlspecialchars($ownerInfo['phone']); ?></p>
            <p><strong>Email:</strong> <?php echo htmlspecialchars($ownerInfo['email']); ?></p>
            <p><strong>Address:</strong> <?php echo htmlspecialchars($ownerInfo['address']); ?></p>
            <p><strong>Listings:</strong> <?php echo count($listings); ?></p> <!-- Display number of listings -->
            <button onclick="openEditProfileModal()">Edit Profile</button>
        </div>
    </main>
</div>

<!-- Modal (overlay) for Listing Property -->
<div class="overlay" id="propertyModal">
    <div class="modal">
        <button class="close-btn" onclick="closeModal()">&times;</button>
        <h2>List Your Property</h2>
        <form action="add-property.php" method="POST">
            <label for="propertyName">Address</label>
            <input type="text" id="propertyName" name="propertyName" required>

            <label for="propertyLocation">Area</label>
            <input type="text" id="propertyLocation" name="propertyLocation" required>

            <label for="propertyPrice">Price</label>
            <input type="text" id="propertyPrice" name="propertyPrice" required>

            <label for="propertyRooms">Rooms</label>
            <input type="text" id="propertyRooms" name="propertyRooms" required>

            <button type="submit">Submit</button>
        </form>
    </div>
</div>

<!-- Property Details Modal (Overlay) -->
<div class="overlay" id="propertyDetailsModal">
    <div class="modal">
        <button class="close-btn" onclick="closeDetailsModal()">&times;</button>
        <h2>Property Details</h2>
        <p><strong>ID:</strong> <span id="propertyDetailId"></span></p>
        <p><strong>Address:</strong> <span id="propertyDetailAddress"></span></p>
        <p><strong>Area:</strong> <span id="propertyDetailArea"></span></p>
        <p><strong>Price:</strong> <span id="propertyDetailPrice"></span></p>
        <p><strong>Rooms:</strong> <span id="propertyDetailRooms"></span></p>
        <button onclick="closeDetailsModal()">Close</button>
    </div>
</div>

<!-- Edit Property Modal (Overlay) -->
<div class="overlay" id="editPropertyModal">
    <div class="modal">
        <button class="close-btn" onclick="closeEditModal()">&times;</button>
        <h2>Edit Property</h2>
        <form id="editPropertyForm">
            <label for="editPropertyId">Property ID (Read-only)</label>
            <input type="text" id="editPropertyId" name="propertyId" readonly>

            <label for="editPropertyArea">Area</label>
            <input type="text" id="editPropertyArea" name="propertyArea" required>

            <label for="editPropertyLocation">Location</label>
            <input type="text" id="editPropertyLocation" name="propertyLocation" required>

            <label for="editPropertyPrice">Price</label>
            <input type="text" id="editPropertyPrice" name="propertyPrice" required>

            <label for="editPropertyRooms">Rooms</label>
            <input type="text" id="editPropertyRooms" name="propertyRooms" required>

            <button type="submit">Save Changes</button>
        </form>
    </div>
</div>

<script>
    // Function to open the edit modal and populate data
    function editPropertyDetails(property) {
        // Populate modal fields with property details
        document.getElementById("editPropertyId").value = property.id;
        document.getElementById("editPropertyArea").value = property.area;
        document.getElementById("editPropertyLocation").value = property.location;
        document.getElementById("editPropertyPrice").value = property.price.replace('$', '').replace('/month', ''); // Remove formatting
        document.getElementById("editPropertyRooms").value = property.rooms;

        // Show the modal
        document.getElementById("editPropertyModal").style.display = "flex";
    }

    // Function to close the edit modal
    function closeEditModal() {
        document.getElementById("editPropertyModal").style.display = "none";
    }

    // Function to handle form submission
    document.getElementById("editPropertyForm").addEventListener("submit", function(event) {
        event.preventDefault();

        // Gather updated property details
        const updatedProperty = {
            propertyId: document.getElementById("editPropertyId").value,
            propertyArea: document.getElementById("editPropertyArea").value,
            propertyLocation: document.getElementById("editPropertyLocation").value,
            propertyPrice: document.getElementById("editPropertyPrice").value,
            propertyRooms: document.getElementById("editPropertyRooms").value,
        };

        // Send updatedProperty to the server for saving
        fetch("edit-property.php", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams(updatedProperty).toString(),
        })
        .then(response => response.text())
        .then(data => {
            if (data === "success") {
                // Update the table with the new values
                const row = document.querySelector(`tr[data-id='${updatedProperty.propertyId}']`);
                row.querySelector(".property-area").textContent = updatedProperty.propertyArea + " m²";
                row.querySelector(".property-location").textContent = updatedProperty.propertyLocation;
                row.querySelector(".property-price").textContent = "$" + parseFloat(updatedProperty.propertyPrice).toLocaleString() + "/month";
                row.querySelector(".property-rooms").textContent = updatedProperty.propertyRooms;

                // Close the modal after submitting
                closeEditModal();
            } else {
                console.error("Error:", data);
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });
    });
</script>

<script>
    // Function to open the property details modal and populate data
    function viewPropertyDetails(property) {
        fetch("get-property-details.php", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ propertyId: property.id }).toString(),
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("Error:", data.error);
            } else {
                // Populate modal with property details
                document.getElementById("propertyDetailId").textContent = data.property_id;
                document.getElementById("propertyDetailAddress").textContent = data.address;
                document.getElementById("propertyDetailArea").textContent = data.area + " m²";
                document.getElementById("propertyDetailPrice").textContent = "$" + parseFloat(data.value).toLocaleString() + "/month";
                document.getElementById("propertyDetailRooms").textContent = data.rooms;

                // Show the modal
                document.getElementById("propertyDetailsModal").style.display = "flex";
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });
    }

    // Function to close the property details modal
    function closeDetailsModal() {
        document.getElementById("propertyDetailsModal").style.display = "none";
    }
</script>

<script>
    // Function to toggle visibility of listings table and hide the welcome message
    function showListings() {
        var tableContainer = document.getElementById("listingsTable");
        var welcomeMessage = document.getElementById("welcomeMessage");
        
        // Toggle table visibility
        if (tableContainer.style.display === "none" || tableContainer.style.display === "") {
            tableContainer.style.display = "block";
            welcomeMessage.style.display = "none"; // Hide the welcome message
        } else {
            tableContainer.style.display = "none";
            welcomeMessage.style.display = "block"; // Show the welcome message again if table is hidden
        }

        // Hide profile section if visible
        document.getElementById("profileInfo").style.display = "none";
    }

    // Function to open the modal (overlay)
    function openModal() {
        document.getElementById("propertyModal").style.display = "flex";
    }

    // Function to close the modal (overlay)
    function closeModal() {
        document.getElementById("propertyModal").style.display = "none";
    }

    // Function to display profile information and hide other sections
    function showProfile() {
        document.getElementById("profileInfo").style.display = "block";
        document.getElementById("listingsTable").style.display = "none"; // Hide listings table
        document.getElementById("welcomeMessage").style.display = "none"; // Hide welcome message
    }
</script>

<script>
    // Function to delete a property
    function deleteProperty(property) {
        if (confirm("Are you sure you want to delete this property?")) {
            fetch("delete-property.php", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({ propertyId: property.id }).toString(),
            })
            .then(response => response.text())
            .then(data => {
                if (data === "success") {
                    // Remove the row from the table
                    const row = document.querySelector(`tr[data-id='${property.id}']`);
                    row.remove();
                } else {
                    console.error("Error:", data);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        }
    }
</script>

<script>
    // Function to open the edit profile modal
    function openEditProfileModal() {
        document.getElementById("editProfileModal").style.display = "flex";
    }

    // Function to close the edit profile modal
    function closeEditProfileModal() {
        document.getElementById("editProfileModal").style.display = "none";
    }

    // Function to handle profile form submission
    document.getElementById("editProfileForm").addEventListener("submit", function(event) {
        event.preventDefault();

        // Gather updated profile details
        const updatedProfile = {
            name: document.getElementById("editProfileName").value,
            phone: document.getElementById("editProfilePhone").value,
            email: document.getElementById("editProfileEmail").value,
            address: document.getElementById("editProfileAddress").value,
        };

        // Send updatedProfile to the server for saving
        fetch("update-profile.php", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams(updatedProfile).toString(),
        })
        .then(response => response.text())
        .then(data => {
            if (data === "success") {
                // Update the profile section with the new values
                document.querySelector("#profileInfo p:nth-child(2)").textContent = "Name: " + updatedProfile.name;
                document.querySelector("#profileInfo p:nth-child(3)").textContent = "Phone: " + updatedProfile.phone;
                document.querySelector("#profileInfo p:nth-child(4)").textContent = "Email: " + updatedProfile.email;
                document.querySelector("#profileInfo p:nth-child(5)").textContent = "Address: " + updatedProfile.address;

                // Close the modal after submitting
                closeEditProfileModal();
            } else {
                console.error("Error:", data);
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });
    });
</script>
</body>
</html>