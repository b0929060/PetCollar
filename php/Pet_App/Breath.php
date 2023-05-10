<?php
    require "../conn.php";
    $link = mysqli_connect("localhost", "root", "root", "pet_db");
    $link -> set_charset("UTF8"); // 設定語系避免亂碼

    $id=$_POST['id'];
    $pet_id=$_POST['pet_id'];

    $id=2;
    $pet_id=1;

    // SQL 指令
    $result = $link -> query("SELECT id,pet_id,breath_rate FROM `breath` WHERE `id` like '$id' and `pet_id` like '$pet_id' order by time desc limit 1");
    while ($row = $result->fetch_assoc()) // 當該指令執行有回傳
    {
        $output[] = $row; // 就逐項將回傳的東西放到陣列中
    }

    // 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成 UNICODE
    print(json_encode($output, JSON_UNESCAPED_UNICODE));
    $link -> close(); // 關閉資料庫連線

?>