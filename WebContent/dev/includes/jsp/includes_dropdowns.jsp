<!---
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
---->
		<div id="Battery_Finder">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
				  <label for="product_type">Product Type *:</label>
				  <select class="form-control yes" id="product_type">
					<option value="0">Select Product Type</option>
					<option value="Car Batteries" >Car Batteries</option>
					<option value="Inverter Batteries" >Inverter Batteries</option>
					<option value="Inverter" >Inverter</option>
					<option value="Inverter and Battery Combo" >Inverter & Battery Combo</option>
					<option value="Bike Batteries" >Bike Batteries</option>
					<option value="Bus Batteries" >Bus Batteries</option>
					<option value="Tractor Batteries" >Tractor Batteries</option>
					<option value="Truck Batteries" >Truck Batteries</option>
					<option value="Three Wheeler Batteries" >Three Wheeler Batteries</option>					
					<option value="Special Vehicle Batteries" >Special Vehicle Batteries</option>
					<option value="Genset Batteries" >Genset Batteries</option>
					<option value="Crane Batteries" >Crane Batteries</option>
					<option value="Roller Batteries" >Roller Batteries</option>
					<option value="Loader Batteries" >Loader Batteries</option>
					<option value="Dozer Batteries" >Dozer Batteries</option>
					<option value="Excavator Batteries" >Excavator Batteries</option>
					<option value="Tyre Handler Batteries" >Tyre Handler Batteries</option>
					<option value="Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
					<option value="Harvestor Batteries" >Harvestor Batteries</option>
					<option value="Generator Batteries" >Generator Batteries</option>
					<option value="Compactor Batteries" >Compactor Batteries</option>
					<option value="Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
					<option value="Forwarder Batteries" >Forwarder Batteries</option>
					<option value="Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
					<option value="Minibus Batteries" >Minibus Batteries</option>
					<option value="Dumper Batteries" >Dumper Batteries</option>
					<option value="Construction Equipment Batteries" >Construction Equipment Batteries</option>
					<option value="Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
				  </select>
				  <div id='product_type-error'style="display:none;"></div>
				</div>
			</div>
			
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="product_capacity_div" style="display:none;">
				<div class="form-group">
				  <label for="product_capacity">Capacity *:</label>
				  <select class="form-control yes" id="product_capacity">
					<option value="0">Select Capacity</option>
				  </select>
				  <div id='product_capacity-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="product_make_div">
				<div class="form-group">
				  <label for="product_make">Make *:</label>
				  <select class="form-control yes" id="product_make">
					<option value="0">Select Make</option>
				  </select>
				  <div id='product_make-error'style="display:none;"></div>
				</div>
			</div>
<!-- 			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  id="product_fuel_div">
				<div class="form-group">
				  <label for="product_fuel">Fuel Type *:</label>
				  <select class="form-control yes" id="product_fuel">
					<option value="0">Select Fuel Type</option>
				  </select>
				  <div id='product_fuel-error' style="display:none;"></div>
				</div>
			</div> -->
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  id="product_model_div">
				<div class="form-group">
				  <label for="product_model">Model *:</label>
				  <select class="form-control yes" id="product_model">
					<option value="0">Select Model</option>
				  </select>
				  <div id='product_model-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
				  <label for="product_brand">Brand :</label>
				  <select class="form-control yes" id="product_brand">
					<option value="0">Select Brand</option>
				  </select>
				  <div id='product_brand-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="product_inverter_capacity_div" style="display:none;">
				<div class="form-group">
				  <label for="product_inverter_capacity">Inverter Capacity *:</label>
				  <select class="form-control yes" id="product_inverter_capacity">
					<option value="0">Select Inverter Capacity</option>
				  </select>
				  <div id='product_inverter_capacity-error'style="display:none;"></div>
				</div>
			</div>
			
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="product_inverter_battery_div" style="display:none;">
				<div class="form-group">
				  <label for="product_inverter_battery">Inverter Battery *:</label>
				  <select class="form-control yes" id="product_inverter_battery">
					<option value="0">Select Inverter Battery</option>
				  </select>
				  <div id='product_inverter_battery-error'style="display:none;"></div>
				</div>
			</div>
		<!----	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
				  <label for="product_state">State *:</label>
				  <select class="form-control yes" id="product_state">
					<option value="0">Select State</option>
				  </select>
				  <div id='product_state-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
				  <label for="product_city">City *:</label>
				  <select class="form-control yes" id="product_city">
					<option value="0">Select City</option>
				  </select>
				  <div id='product_city-error'style="display:none;"></div>
				</div>
			</div> ---->
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="AskforMobileNumber()" class="button btn-cart" type="button" id="find_battery_btn">
						<span><i class="icon-basket"></i> Find Product</span>
					</button>	
				</div>
			</div>
		</div>
		