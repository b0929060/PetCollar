<?php 
require "conn.php";
if(isset($_COOKIE['id'])) {
    $id = $_COOKIE['id'];
    //echo 'Welcome back, ' . $_COOKIE['id'] . '!';
} else {
    echo '請重新登入';
} 
$mysqli_query = "SELECT * FROM `pet_list` where `id` = $id";
 //binding results to the query 
$stmt = $conn->prepare("SELECT * FROM `pet_list` where `id` = $id");
$stmt->execute();
$stmt->bind_result($id, $pet_id, $pet_name, $gender, $age, $type, $image);
$Petlist = array();
while ($stmt->fetch()) {
    $temp['pet_name'] = $pet_name; 
    $temp['gender'] = $gender; 
    $temp['age'] = $age; 
    $temp['type'] = $type; 
    $temp['image'] = $image;
    array_push($Petlist, $temp);
}
 //displaying the result in json format 
 echo json_encode($Petlist);
 