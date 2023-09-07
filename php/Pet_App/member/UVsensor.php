<?php
    require "./sensorconn.php";
    

    //$id=$_POST['id'];
    //$pet_id=$_POST['pet_id'];
    $id=2;
    $pet_id=1;

    // SQL 指令
    $sql1 = "SELECT UVindex FROM `sensor_uv` order by time desc limit 1";
    $sql2 = "SELECT tempture FROM `tempture` order by time desc limit 1";
    $a = mysqli_query($conn,$sql1);
    $b = mysqli_query($conn,$sql2);
    while($row = $a ->fetch_assoc())
    {
        //echo $row['UVindex'];
        $UVindex = $row['UVindex'];
    }
    while($row = $b ->fetch_assoc())
    {
        //echo $row['tempture'];
        $tempture = $row['tempture'];
    }
    
    $result = array();
    if($a) { // 當該指令執行有回傳
        $temp = array();
        $temp['UVindex'] = $UVindex; // 就逐項將回傳的東西放到陣列中
        $temp['tempture'] = $tempture;
        array_push($result, $temp);
    }

    // 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成 UNICODE
    echo json_encode($result, JSON_NUMERIC_CHECK);
    //$link -> close(); // 關閉資料庫連線

?>