"use strict";
var $placePicker = null,
    $modal = null,
    n=null,
    btnClass="btn btn-secondary btn-sm",
    placePickerMap = null,
    marker = false,
    service = null,
    geocoder = null,
    result = null;
$.fn.PlacePicker = function (t) {
    n = {
        key : null,
        title : null,
        center: {lat: -34.397, lng: 150.644},
        zoom: 18,
        success: function () {}
    };
    var params = $.extend(n, t);
    $placePicker = this;
    $(this).wrap( "<div></div>" );
    $(this).closest("div").hover(function(){
        var left = 0;
        var top = 420;
        //var left = $(this).offset().left+$(this).width() - 40;
		//var top = $(this).offset().top+5;
        //var btn = $('<div class="placePickerUIButton" title="Pick location from map" style="position:absolute;top: '+top+'px;left: '+left+'px;z-index: 1045;"><div class="'+params.btnClass+'"><i class="icon-map-marker"></i></div></div>');
        //$(this).append(btn);
        //btn.click(function(){
            //if($("body").find("#step-1").length==0){
                //$("#pickup_country").append("<label for='order-form-address'>Select Your Delivery Address :</label><div class='row'><div class='col-md-12 col-xs-12' style='padding: 10px;position: absolute;'><div class='input-group input-group-sm' style='width: 100%;'> <input id ='useraddress' type='text' class='form-control pull-right autocomplete' placeholder='Search here or pick a location on map' style='border: 2px solid #3f98e4;font-weight: 600;color: #333;' autocomplete='off'></div><div class='col-md-12'><div id='placePickerMap' style='height:calc( 80vh );width:100%'></div></div></div></div>");
            //}
            result = null;
            //$modal = $(".placePicker");
            //$modal.modal("show");
            $(".placePicker").find(".address").html("");
            $(".placePicker").find(".address_content").hide();
            $(".placePicker").find(".autocomplete").val("");
            placePickerMap = null
            marker = false;
            if (!(typeof google === 'object' && typeof google.maps === 'object')) {
                $.getScript('https://maps.googleapis.com/maps/api/js?key='+params.key+'&libraries=places&region=IN&region=IN', function() {
                    initPlacePickerMap(params)
                });
            } else{
                initPlacePickerMap(params)
            }
            /*if(params.title!=null){
                $modal.find(".modal-title").html(params.title);
            }
            $(".close").click(function(){
                var place = $(".placePicker").find(".autocomplete");
                params.success(convertAddress(),place);
                $modal.modal("hide")
            });
            $modal.on('hidden.bs.modal', function () {
                $("body").find(".modal.placePicker").remove();
            })*/
        //});
    },
    function(){
        $("body").find(".placePickerUIButton").remove();
    });


};
function initPlacePickerMap(params) {
    service = new google.maps.Geocoder;
    geocoder= new google.maps.Geocoder;
    setTimeout(function() {
        var loc = new google.maps.LatLng(params.center.lat, params.center.lng);
        placePickerMap = new google.maps.Map(document.getElementById('placePickerMap'), {
            center: loc,
            zoom: params.zoom
        });
        service = new google.maps.places.PlacesService(placePickerMap);
        var autocomplete = new google.maps.places.Autocomplete(document.getElementsByClassName("autocomplete")[0]);
        google.maps.event.addListener(autocomplete, 'place_changed', function() {

            var place = autocomplete.getPlace();

            if (typeof place.address_components !== 'undefined') {
                placePickerMap.panTo(place.geometry.location)
                if(marker === false){
                    marker = new google.maps.Marker({
                        position: place.geometry.location,
                        map: placePickerMap,
                        draggable: true
                    });
                    google.maps.event.addListener(marker, 'dragend', function(event){
                        markerLocation();
                    });
                } else{
                    marker.setPosition(place.geometry.location);
                }
                markerLocation();
            }

        });

        google.maps.event.addListener(placePickerMap, 'load', function(event) {
            var clickedLocation = event.latLng;
            if(marker === false){
                marker = new google.maps.Marker({
                    position: clickedLocation,
                    map: placePickerMap,
                    draggable: true
                });
                google.maps.event.addListener(marker, 'dragend', function(event){
                    markerLocation();
                });
            } else{
                marker.setPosition(clickedLocation);
            }
            markerLocation();
        });
    }, 1000);
}
function markerLocation(){
    var currentLocation = marker.getPosition();
	
  	var str = currentLocation.toString();
	
	/*var cl = currentLocation.split(",");
	var lat = (cl[0]);
	var lon = (cl[1]); 
	alert("inlat");
	alert(lat);
	alert(lon);*/
 	var res = str.split(", ");
	var lat = res[0].replace("(","");
	var lon = res[1].replace(")","");
	
	document.getElementById('latitude').value=lat;
	document.getElementById('longitude').value=lon;
    geocoder.geocode({'location': currentLocation}, function(results, status) {
		
        if (status === 'OK') {
        if (results[0]) {
            result = results[0];
            var cont = "<h6 style='font-weight:600;font-size: 16px;padding-bottom: 10px;'>"+result.formatted_address+"</h6>";
            //var addr = "<h6 style='font-weight:600;font-size: 16px;padding-bottom: 10px;'>"+result.formatted_address+"</h6>";
            //var addr = "<input type='text' class='form-control pull-right autocomplete' placeholder='Search here or pick a location on map' style='border: 1px solid #dddddd;' autocomplete='off' value='"+result.formatted_address+"'>";
			$('#useraddress').val(result.formatted_address);
			
			
            $(result.address_components).each(function(key,value){
                cont += "<b>"+value.types.join(', ')+"</b> : "+value.long_name+"<br>";
            });
            $(".address").html(cont);
           // $(".useraddress").html(addr);
            //$(".address_content").show();
            }
        }
    });
}
function convertAddress(){
    var data=[];
    data["country"]="";
    data["administrative_area_level_2"]="";
    data["postal_code"]="";
    data["sublocality_level_1"]="";
    data["sublocality_level_2"]="";
    data["route"]="";
    data["locality"]="";
    data["formatted_address"] = result.formatted_address;
    $(result.address_components).each(function(i,address){
        $(address.types).each(function(j,type){
            if(type=="country"){
                data[type] = address.short_name;
            }else{
                data[type] = address.long_name;
            }
        });
    });
    return data;
}
