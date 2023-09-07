<?php 
require "conn.php";
//echo $id;
//session_start();
$collar_id = $_POST["collar_id"];
$pet_id = $_POST["pet_id"];
$id = 2;
if($id != -1) {
    //echo $id;

    $sql = "INSERT INTO `collar_pet` (`collar_id`, `pet_id`) VALUES ('$collar_id', '$pet_id');";
 //binding results to the query 
    $result = mysqli_query($conn,$sql);
    
}


?>