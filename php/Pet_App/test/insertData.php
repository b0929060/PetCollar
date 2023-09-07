<?php
require "conn.php";
$name = $_POST["name"];
$account = $_POST["account"];
$password = $_POST["password"];
//$name = "Ken";
//$account = "B0929051@cgu.edu.tw";
//$password = "0904";

$search = $conn->query("SELECT *FROM members where  `e-mail` = '$account'");
$compare = $search->fetch_assoc();

if(!$compare){
    $mysqli_query = "INSERT INTO members VALUES (NULL,'$name','$account','$password');";



$result = mysqli_query($conn,$mysqli_query);

//RESET the AUTOINCREMENT
$result = mysqli_query($conn,"SET @num := 0;");
$result = mysqli_query($conn,"UPDATE members SET id = @num := (@num+1)");
$result = mysqli_query($conn,"ALTER TABLE members AUTO_INCREMENT =1");
if($result){
    print("Your have registered!"); 
    }
    else{
    print("NOT Successful"); 
    }
}
else{
    print("This e-mial has already been used!!");
}


?>