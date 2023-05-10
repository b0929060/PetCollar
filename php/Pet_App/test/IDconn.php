<?php
require "conn.php";
// 獲取 COOKIE
if(isset($_COOKIE['id'])) {
    $id = $_COOKIE['id'];
    //echo 'Welcome back, ' . $_COOKIE['id'] . '!';
} else {
    echo '請重新登入';
}
?>