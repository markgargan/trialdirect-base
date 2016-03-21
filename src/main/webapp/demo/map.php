<!doctype html>
<html>
<body>
<?php
//require_once 'inc/base.config.php';
//require_once HTML_HEADER;

echo '
	<form method="get">
		<input name="search">
		<button>search</button>
	</form>
	';


$api_key = 'AIzaSyDPnVez_E7pNgjNPxNKWqy8tTsnlFpKJaw';
if ( isset($_GET) && !empty($_GET) ) {
	
	$finder = $_GET['search'];
	$finder = str_replace(' ', '+', $finder);
	if (empty($finder)) {$finder='77+Sir+John+Rogerson\'s+Quay';}

	$mapW = '100%';
	$mapH = '600';

	echo '
	<iframe
		width="'. $mapW .'"
		height="'. $mapH .'"
		frameborder="0" style="border:0"
		src="https://www.google.com/maps/embed/v1/place?key='.$api_key.'
		&q='.$finder.'">
	</iframe>
	';

}
//require_once HTML_FOOTER;
?>
</body>
</html>