<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $address = $_POST['propertyName'];
    $area = $_POST['propertyLocation'];
    $price = $_POST['propertyPrice'];
    $rooms = $_POST['propertyRooms'];

    $query = "INSERT INTO property (address, area, value, rooms) VALUES ('$address', '$area', '$price', '$rooms')";
    if (mysqli_query($conn, $query)) {
        header("Location: owner-dashboard.php");
        exit();
    } else {
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
}
?>