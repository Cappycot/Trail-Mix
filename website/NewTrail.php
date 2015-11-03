<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>New Trail</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <style>
      
      #map-canvas {
        height: 400px;
        margin: 0;
        padding: 10px
        margin-left: auto;
        margin-right: auto;
        display: block;
      }
      #name {
        width: 300px;
      }
      .nav-pills {
        margin: 0 auto;
        padding: 0;
        width: 400px;
        }
        .nav-pills>li>a {
          border-radius: 10px;
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="NewTrail.js"></script>
	<!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
  </head>
  <body>
  <ul class="nav nav-pills centered-pills">
  <li role="presentation"><a href="NewTrail.php">Make a New Trail</a></li>
  <li role="presentation"><a href="ListTrails.php">Browse Trails</a></li>
  <li role="presentation"><a href="uses.php">Uses</a></li>
  <li role="presentation"><a href="about.php">About</a></li>
  </ul>
  <div id="wrapper">
    <div id="map-canvas"></div>
    <?php
    if ($_SERVER["REQUEST_METHOD"] == "POST")
    {
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
      $database->insert("trails", [
        "name" => $_POST['name'],
        "trailtype" => $_POST['trailtype'],
        "difficulty" => $_POST['difficulty'],
        "inputlats" => $_POST['inputlats'],
        "inputlngs" => $_POST['inputlngs']
      ]);
      
    }
    ?>
    <div id="DoesItWork"></div>
    <br/>
    <!--<form action="">
      <fieldset>-->
        <select id="trailtype"  >
        <option value="">Choose Trail Type</option>
        <option value="multi">Multi-Use</option>
        <option value="walkrun">Walk/Run</option>
        <option value="bike">Bike</option>
        <option value="horse">Horse</option>
        </select>
        <br/>
        <br/>
        <select id="difficulty">
        <option value="">Choose Difficulty</option>
        <option value="easy">Easy</option>
        <option value="medium">Medium</option>
        <option value="hard">Hard</option>
        </select>
        <br/>
        <br/>
        <div class="form-group">
          <input class="form-control" id="name" name="name" placeholder="Name" type="text"/></input>
        </div>
        <div class="form-group">
          <button type="submit" class="btn btn-default" onclick="submitstuff();" >Submit Trail</button>
        </div>
      <!--</fieldset>
    </form>-->
    </div>
  </body>
</html>
