<?php 
require "conn.php";
if(isset($_COOKIE['id'])) {
    $id = $_COOKIE['id'];
    //echo 'Welcome back, ' . $_COOKIE['id'] . '!';

    $mysqli_query = "SELECT * FROM `pet_list` where `id` = $id";
 //binding results to the query 
    $stmt = $conn->prepare("SELECT * FROM `pet_list` where `id` = $id");
    $stmt->execute();
    $stmt->bind_result($id, $pet_id, $pet_name, $gender, $age, $type, $image);
    $pets = array();
    while ($stmt->fetch()) {
    $temp = array();
    $temp['pet_name'] = $pet_name; 
    $temp['gender'] = $gender; 
    $temp['age'] = $age; 
    $temp['type'] = $type; 
    $temp['image'] = $image;
    array_push($pets, $temp);
}
 //displaying the result in json format 
//echo json_encode($pets);
} 
else {
$id = 2;
$pet_id = 1;
$mysqli_query = "SELECT 'heart_rate' FROM `heart`";
 //binding results to the query 
$stmt = $conn->prepare("SELECT * FROM `pet_list` where `id` = $id");
$stmt->execute();
$stmt->bind_result($id, $pet_id, $pet_name, $gender, $age, $type, $image);
$pets = array();
while ($stmt->fetch()) {
    $temp = array();
    $temp['pet_name'] = $pet_name; 
    $temp['gender'] = $gender; 
    $temp['age'] = $age; 
    $temp['type'] = $type; 
    $temp['image'] = $image;
    array_push($pets, $temp);
}
 //displaying the result in json format

$pets = json_encode($pets, JSON_NUMERIC_CHECK);
echo $pets;
}


?>