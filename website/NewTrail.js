// This example creates an interactive map which constructs a
// polyline based on user clicks. Note that the polyline only appears
// once its path property contains two LatLng coordinates.

var poly;
var map;
var markers = [];
var inputlats = [];
var inputlngs = [];
var userlat = 39.85;
var userlng = -98.5;
function initialize() {
  getLocation();
  var mapOptions = {
    zoom: 4,
    center: new google.maps.LatLng(userlat, userlng)
  };

  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

  var polyOptions = {
    strokeColor: '#000000',
    strokeOpacity: 1.0,
    strokeWeight: 3
  };
  poly = new google.maps.Polyline(polyOptions);
  poly.setMap(map);

  // Add a listener for the click event
  google.maps.event.addListener(map, 'click', addLatLng);
}

//$( document ).ready(function(){})
/**
 * Handles click events on a map, and adds a new point to the Polyline.
 * @param {google.maps.MouseEvent} event
 */
function addLatLng(event) {

  var path = poly.getPath();

  // Because path is an MVCArray, we can simply append a new coordinate
  // and it will automatically appear.
  path.push(event.latLng);

  // Add a new marker at the new plotted point on the polyline.
  var marker = new google.maps.Marker({
    position: event.latLng,
    title: '#' + path.getLength(),
    map: map
  });
  marker.setDraggable(true);
  markers.push(marker);
  google.maps.event.addListener(marker, 'click', function() {
  marker.setMap(null);
    for (var i = 0, I = markers.length; i < I && markers[i] != marker; ++i);
    markers.splice(i, 1);
    path.removeAt(i);
    }
  );

  google.maps.event.addListener(marker, 'drag', function() {
    for (var i = 0, I = markers.length; i < I && markers[i] != marker; ++i);
    path.setAt(i, marker.getPosition());
    }
  );
	
}
function getLocation() {
    navigator.geolocation.getCurrentPosition(foundLocation, noLocation);
    function foundLocation(position) {
	  $( "#DoesItWork" ).html( "Hell yeah!! Geolocation works!!!" );
    userlat = position.coords.latitude;
    userlng = position.coords.longitude;
	  initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    map.setCenter(initialLocation);
	  map.setZoom(14);
      }

    function noLocation() {
      $( "#DoesItWork" ).html( "Geolocation isn't working, bro!!" );
      userlat = 30.0;
      userlng = -70;
    }
}

google.maps.event.addDomListener(window, 'load', initialize);
function submitstuff()
{
  for (var i = 0, I = markers.length; i < I; ++i)
  {
    inputlats[i] = markers[i].getPosition().lat();
    inputlngs[i] = markers[i].getPosition().lng();
  }
  var trailtype = $( "#trailtype" ).val();
  var difficulty = $( "#difficulty" ).val();
  var name = $( "#name" ).val();
  $.ajax({
    method: "POST",
    url: "NewTrail.php",
    data: {
      inputlats: JSON.stringify(inputlats),
      inputlngs: JSON.stringify(inputlngs),
      trailtype: trailtype,
      difficulty: difficulty,
      name: name
    }
  })
  .done(function() {
    alert( "Data Saved: ");
  })
}