<?php 
require "conn.php";
//echo $id;
//session_start();
//$id = $_POST["id"];
$id = 2;
if($id != -1) {
    //echo $id;

    $sql = "SELECT collar_pet.collar_id, collar_pet.pet_id,pet_name, gender, age, type, image
    from ((SELECT collar_id FROM `member_collar` WHERE id = $id) as a
    inner join collar_pet on a.collar_id = collar_pet.collar_id)
    inner join pet_list on collar_pet.pet_id = pet_list.pet_id";
 //binding results to the query 
    $a = mysqli_query($conn,$sql);
    $result = [];
    $output = [];
    while($row = $a ->fetch_array())
    {
        //$temp = array();
        $temp['collar_id'] = $row['collar_id'];
        $temp['pet_id'] = $row['pet_id'];
        $temp['pet_name'] = $row['pet_name'];
        $temp['image'] = $row['image'];
        $output [] = $temp;
        $result []= $temp;
    }
    /*
    if($output){
        $output = json_encode($output, JSON_NUMERIC_CHECK);
        echo $output;
    }
    */
    

    $sql2 = "SELECT a.collar_id
    from (SELECT collar_id FROM `member_collar` WHERE id = $id) as a
    left join collar_pet on a.collar_id = collar_pet.collar_id
    where collar_pet.pet_id is NULL;";
    $b = mysqli_query($conn,$sql2);
    $output2 = [];
    while($row = $b ->fetch_array())
    {
        $temp2['collar_id'] = $row['collar_id'];
        $temp2['pet_id'] = -1;
        $temp2['pet_name'] = -1;
        $temp2['image'] = -1;
        $output2 [] = $temp2;
        $result [] = $temp2;
    }
    /*
    if($output2){
        $output2 = json_encode($output2, JSON_NUMERIC_CHECK);
        echo $output2;
    }
    */


    if($result){
        $result = json_encode($result, JSON_NUMERIC_CHECK);
        echo $result;
    }else{
        $result['collar_id'] = -1;
        $result['pet_id'] = -1;
        $result['pet_name'] = -1;
        $result['image'] = -1;
        $result = json_encode($result, JSON_NUMERIC_CHECK);
        echo $result;
    }
}


?>