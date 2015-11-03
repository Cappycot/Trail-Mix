<!DOCTYPE html>
<html>
<head>
<script
src="http://maps.googleapis.com/maps/api/js">
</script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<?php
      require_once 'medoo.php';
      
      $database = new medoo([
        // required
        'database_type' => 'mysql',
        'database_name' => 'traylz',
        'server' => 'localhost',
        'username' => 'root',
        'password' => '',
        'charset' => 'utf8',
      ]);
      $id = $_GET['id'];
      $datas = $database->select("trails", "*", ["id" => $id]);
      echo "<script> var lngsString = " . $datas[0]["inputlngs"] . "; var latsString =" . $datas[0]["inputlats"] . ";";
    ?>
    <?php echo "</script>"?>
    <style>
    .nav-pills {
    margin: 0 auto;
    padding: 0;
    width: 400px;
    }
    .nav-pills>li>a {
        border-radius: 10px;
    }
    body {
      font-family: arial;
    }
    </style>
<script>
var x=new google.maps.LatLng(52.395715,lngsString[0]);
var stavanger=new google.maps.LatLng(58.983991,5.734863);
var amsterdam=new google.maps.LatLng(52.395715,4.888916);
var london=new google.maps.LatLng(51.508742,-0.120850);
LatLngs = [];
for (var i = 0, I = lngsString.length; i < I; ++i)
{
  LatLngs[i] = new google.maps.LatLng(latsString[i],lngsString[i]);
}

function initialize()
{
var mapProp = {
  center:LatLngs[0],
  zoom:11,
  mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  
var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

var myTrip=[stavanger,amsterdam,london];
var flightPath=new google.maps.Polyline({
  path:LatLngs,
  strokeColor:"#0000FF",
  strokeOpacity:0.8,
  strokeWeight:2
  });

flightPath.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
 <style>
.nav-pills {
    margin: 0 auto;
    padding: 0;
    width: 400px;
}
.nav-pills>li>a {
    border-radius: 10px;
}
  </style>
<body>
<ul class="nav nav-pills centered-pills">
  <li role="presentation"><a href="NewTrail.php">Make a New Trail</a></li>
  <li role="presentation"><a href="ListTrails.php">Browse Trails</a></li>
  <li role="presentation"><a href="uses.php">Uses</a></li>
  <li role="presentation"><a href="about.php">About</a></li>
  </ul>
<div id="googleMap" style="width:1000px;height:400px;"></div>
</body>
</html>
