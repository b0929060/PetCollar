<?php 
require "conn.php";
//require "validateData.php";
//echo $id;
//session_start();
$id = $_POST["id"];
//$id = 2;
if($id != -1) {
    //echo $id;

    $mysqli_query = "SELECT * FROM `pet_list` where `id` = $id";
 //binding results to the query 
    $stmt = $conn->prepare("SELECT * FROM `pet_list` where `id` = $id");
    $stmt->execute();
    $stmt->bind_result($id, $pet_id, $pet_name, $gender, $age, $type, $image);
    $pets = array();
    while ($stmt->fetch()) {
    $temp = array();
    $temp['pet_id'] = $pet_id;
    $temp['pet_name'] = $pet_name; 
    $temp['gender'] = $gender; 
    $temp['age'] = $age; 
    $temp['type'] = $type; 
    $temp['image'] = $image;
    array_push($pets, $temp);
}
 //displaying the result in json format 
//echo json_encode($pets);
$pets = json_encode($pets, JSON_NUMERIC_CHECK);
echo $pets;
}
//測試用 
else {
$id = 1;
//echo $id;
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

$pets = json_encode($pets, JSON_NUMERIC_CHECK);
echo $pets;
}

?>