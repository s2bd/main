<?php
session_start();
include("dbconnect.php");

// Ensure the user is logged in
if (!isset($_SESSION['username'])) {
    echo json_encode(['success' => false, 'message' => 'User not logged in. Please log in to continue.']);
    exit();
}

// Get the data from the AJAX request
$clientId = $_POST['client_id'];
$propertyId = $_POST['property_id'];

// Validate the input
if (empty($clientId) || empty($propertyId)) {
    echo json_encode(['success' => false, 'message' => 'Missing required data: client_id or property_id is empty.']);
    exit();
}

// Insert into the payment table
$query = "INSERT INTO payment (client_id, property_id, payment_type, status, discount, approved_by)
          VALUES ('$clientId', '$propertyId', 'cash', 'unpaid', 0, NULL)";

if (mysqli_query($conn, $query)) {
    // Fetch the latest transaction_id for this client and property
    $fetchTransactionIdQuery = "
        SELECT transaction_id 
        FROM payment 
        WHERE client_id = '$clientId' AND property_id = '$propertyId'
        ORDER BY transaction_id DESC
        LIMIT 1";

    $result = mysqli_query($conn, $fetchTransactionIdQuery);

    if ($result && mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $transactionId = $row['transaction_id'];

        // Now insert into client_rents_property
        $startDate = date('Y-m-d'); // Today's date
        $endDate = date('Y-m-d', strtotime('+1 month')); // Example end date (1 month later)
        $insertLeaseQuery = "INSERT INTO client_rents_property (client_id, property_id, start_date, end_date, payment_id)
                             VALUES ('$clientId', '$propertyId', '$startDate', '$endDate', '$transactionId')";

        if (mysqli_query($conn, $insertLeaseQuery)) {
            echo json_encode(['success' => true, 'message' => 'Payment and lease recorded successfully.']);
        } else {
            // Detailed error message for lease record insertion failure
            $leaseError = mysqli_error($conn);
            echo json_encode(['success' => false, 'message' => 'Failed to insert lease record. Error: ' . $leaseError]);
        }
} else {
    // Detailed error message for payment record insertion failure
    $paymentError = mysqli_error($conn);
    echo json_encode(['success' => false, 'message' => 'Failed to insert payment record. Error: ' . $paymentError]);
}
?>
