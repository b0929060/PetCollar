<?php 
require "conn.php";
//echo $id;
//session_start();
$collar_id = $_POST["collar_id"];
$id = 2;
if($id != -1) {
    //echo $id;

    $sql = "DELETE FROM `collar_pet` WHERE collar_id = $collar_id;";
 //binding results to the query 
    $result = mysqli_query($conn,$sql);
    
}


?>