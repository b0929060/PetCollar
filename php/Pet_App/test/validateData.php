<?php

require "conn.php";
$account = 'tes@gmail.com';
$password = 'test';



$mysqli_query = "SELECT * FROM members where `e-mail` like '$account' and password like '$password'";

$result = mysqli_query($conn,$mysqli_query);

if(mysqli_num_rows($result)>0){
//print("Login success");
$row = mysqli_fetch_assoc($result); 
$name =$row["name"]; 
Print("Login Successful..Welcome ".$name);
print("\n");

$sql= "SELECT id from members where `e-mail` = '$account' and `password` = '$password'";
$getid = mysqli_query($conn,$sql);
$row = mysqli_fetch_assoc($getid);
$id = $row["id"];
print($id);
setcookie('id', $id, time() + 3600); // 有效期為 1 小時


}
else{
print("Login not succes"); 

}

?>