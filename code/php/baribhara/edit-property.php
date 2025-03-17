<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $propertyId = $_POST['propertyId'];
    $area = $_POST['propertyArea'];
    $location = $_POST['propertyLocation'];
    $price = $_POST['propertyPrice'];
    $rooms = $_POST['propertyRooms'];

    $query = "UPDATE property SET area = '$area', address = '$location', value = '$price', rooms = '$rooms' WHERE property_id = '$propertyId'";
    if (mysqli_query($conn, $query)) {
        echo "success";
    } else {
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
}
?>