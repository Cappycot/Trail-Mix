<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>New Trail</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
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
      $datas = $database->select("trails", "*");
    ?>
  </head>
  <body>
  <ul class="nav nav-pills centered-pills">
  <li role="presentation"><a href="NewTrail.php">Make a New Trail</a></li>
  <li role="presentation"><a href="ListTrails.php">Browse Trails</a></li>
  <li role="presentation"><a href="uses.php">Uses</a></li>
  <li role="presentation"><a href="about.php">About</a></li>
  </ul>
    <table class="table table-hover">
      <thead>
          <tr>
              <th>Name</th>
              <th>Trail Type</th>
              <th>Difficulty</th>
          </tr>
      </thead>
      <tbody class ="table-striped">
              <?php foreach($datas as $data){
              echo '<tr><td><a href="/ShowTrail.php?id=' . $data["id"] . '">' . $data["name"] . "</a></td><td>" . $data["trailtype"] . "</td><td>" . $data["difficulty"] . "</td></tr>";
              } ?>
      </tbody>
    </table>
  </body>
</html>