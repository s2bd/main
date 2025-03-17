<?php
session_start();
include("dbconnect.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $propertyId = $_POST['propertyId'];

    $query = "SELECT * FROM property WHERE property_id = '$propertyId'";
    $result = mysqli_query($conn, $query);

    if ($result && mysqli_num_rows($result) > 0) {
        $property = mysqli_fetch_assoc($result);
        echo json_encode($property);
    } else {
        echo json_encode(["error" => "Property not found"]);
    }
}
?>