<?php
    require "./sensorconn.php";
    

    //$id=$_POST['id'];
    //$pet_id=$_POST['pet_id'];

    $id=2;
    $pet_id=1;

    // SQL 指令
    $stmt = $conn->prepare("SELECT ax,ay,az FROM `activity` order by time desc limit 1");
    $stmt->execute();
    $stmt->bind_result($ax,$ay,$az);
    $result = array();
    while ($stmt->fetch()) { // 當該指令執行有回傳
        $temp = array();
    
        $temp['ax'] = $ax; // 就逐項將回傳的東西放到陣列中
        $temp['ay'] = $ay;
        $temp['az'] = $az;
        array_push($result, $temp);
    }

    // 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成 UNICODE
    echo json_encode($result, JSON_NUMERIC_CHECK);
    //$link -> close(); // 關閉資料庫連線

?>