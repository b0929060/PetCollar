<?php
require "conn.php";
session_start();
//require "validateData.php";
// 獲取 COOKIE
if(isset($_SESSION['id'])) {
    $id = $_SESSION['id'];
    //echo 'Welcome back, ' . $_SESSION['id'] . '!';
} else {
    //echo '請重新登入';
}
?>