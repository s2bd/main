<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $propertyId = $_POST['propertyId'];

    $query = "DELETE FROM property WHERE property_id = '$propertyId'";
    if (mysqli_query($conn, $query)) {
        echo "success";
    } else {
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
}
?>