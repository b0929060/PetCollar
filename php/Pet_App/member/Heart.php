<?php
    require "./conn.php";
    

    //$id=$_POST['id'];
    //$pet_id=$_POST['pet_id'];

    $id=2;
    $pet_id=1;

    // SQL 指令
    $stmt = $conn->prepare("SELECT heart_rate FROM `heart` WHERE `id` like '$id' and `pet_id` like '$pet_id' order by time desc limit 1");
    $stmt->execute();
    $stmt->bind_result($heart_rate);
    $result = array();
    while ($stmt->fetch()) { // 當該指令執行有回傳
    
        $result['heart_rate'] = $heart_rate; // 就逐項將回傳的東西放到陣列中
    }

    // 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成 UNICODE
    echo json_encode($result, JSON_NUMERIC_CHECK);
    //$link -> close(); // 關閉資料庫連線

?>