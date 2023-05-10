<?php
    require "conn.php";
    //$link = mysqli_connect("localhost", "root", "root", "pet_db");
    //$link -> set_charset("UTF8"); // 設定語系避免亂碼
    $id=$_POST["id"];
    //$id=2;

    // SQL 指令
    //$result = $link -> query("SELECT * FROM `pet_list` where id = '$id'");
    if($conn) {
        $result = $conn -> query("SELECT * FROM `pet_list` where id = '$id'");
        while ($row = $result->fetch_assoc()) // 當該指令執行有回傳
        {
            $output[] = $row; // 就逐項將回傳的東西放到陣列中
        }    
        print(json_encode($output, JSON_UNESCAPED_UNICODE)."\n");
    }
    // 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成 UNICODE
    $conn -> close(); // 關閉資料庫連線

?>