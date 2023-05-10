<?php

require "conn.php";
$account = $_POST["account"];
$password = $_POST["password"];
//$account = 'tes@gmail.com';
//$password = 'test';

if($conn) {
    $result = $conn -> query("SELECT `id` FROM `members` WHERE `e-mail` = '$account';");
    while ($row = $result->fetch_assoc()) // 當該指令執行有回傳
    {
        $output[] = $row; // 就逐項將回傳的東西放到陣列中
    }    
    print(json_encode($output, JSON_UNESCAPED_UNICODE)."\n");

$mysqli_query = "SELECT * FROM members where `e-mail` like '$account' and password like '$password'";

$result = mysqli_query($conn,$mysqli_query);
if(mysqli_num_rows($result)>0){
//print("Login success");

$row = mysqli_fetch_assoc($result); 
$name =$row["name"]; 
$id = $row["id"];
}
Print("Login Successful. Welcome ".$name);
/*
$sql= "SELECT id from members where `e-mail` = '$account' and `password` = '$password'";
$getid = mysqli_query($conn,$sql);
$row = mysqli_fetch_assoc($getid);
$id = $row["id"];
setcookie('id', $id, time() + 3600); // 有效期為 1 小時
print($id);*/

}
else{
print("Login not succes"); 
}

?>