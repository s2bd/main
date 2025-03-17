<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $ownerId = $_SESSION['username'];
    $name = $_POST['name'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $address = $_POST['address'];

    $query = "UPDATE owner SET name = '$name', phone = '$phone', email = '$email', address = '$address' WHERE owner_id = '$ownerId'";
    if (mysqli_query($conn, $query)) {
        echo "success";
    } else {
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
}
?>