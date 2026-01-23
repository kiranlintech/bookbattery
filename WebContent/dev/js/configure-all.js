
var inverter_brand_store_val;
var inverter_capacity_store_val;
var inverter_battery_capacity_store_val;
var inverter_state_store_val;
var inverter_city_store_val;


	//####################### Start of Step's  
	$(document).ready(function () {
		var navListItems = $('div.setup-panel div a'),
		allWells = $('.setup-content'),
		allnextBtn_1 = $('.configure-your-inverter-nextBtn-1');
		allnextBtn_2 = $('.configure-your-inverter-nextBtn-2');
		allnextBtn_3 = $('.configure-your-inverter-nextBtn-3');
		allnextBtn_4 = $('.configure-your-inverter-nextBtn-4');

		allWells.hide();
		navListItems.click(function (e) {
			e.preventDefault();
			var $target = $($(this).attr('href')),
					$item = $(this);

			if (!$item.hasClass('disabled')) {
				navListItems.removeClass('btn-primary').addClass('btn-default');
				$item.addClass('btn-primary');
				allWells.hide();
				$target.show();
				$target.find('input:eq(0)').focus();
			}
		});
		
		//####################### Step 1 Next Button

		allnextBtn_1.click(function(){
			var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");
			
			if (selectValidation("","inverter_brand","select") == false)
			{
				return;
			}
			else
			{
				nextStepWizard.removeAttr('disabled').trigger('click');
				$('html, body').animate({ scrollTop: $("#nav-configure-your-inverter-step-1").offset().top }, 1000);
				$("#inverter_capacity_table_selected").html("");
				$("#inverter_battery_capacity_table_selected").html("");
				
				var inverterbrand =$("#inverter_brand").val();
				inverter_brand_store_val=inverterbrand;
				$("#inverter_capacity").html("<option>Loading ... </option>");
				
				 $.ajax({					 
					type: "GET",
					 url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
					data: {inverterbrand:inverterbrand},
					success: function(data)
					{	
						$("#inverter_capacity").html(data).focus();
					}
				});
				var inverter_table_data;
				if(inverterbrand=="Amaron")
				{
					inverter_table_data ="<div><b>COMP</b>: Computer | <b>FN</b>: Fans | <b>TL</b>: Tube Lights | <b>TV</b>: Television (32&#8243;)</div><div class='table-responsive'><table width='100%' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tbody> <tr><th colspan='2'>Inverter Capacity</th><th>COMP</th><th>FN</th><th>TL</th><th>TV</th></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('400 VA');\" >400&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>3</td><td>0</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>1</td><td>1</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('650 VA');\" >650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('675 VA');\" >675&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>4</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>2</td><td>3</td><td>0</td></tr>  <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('825 VA');\" >825&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('880 VA');\" >880&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr>  <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('1400 VA');\" >1400&nbsp;VA</a> </td><td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr> </tbody> </table> </div> ";
				}
				else if(inverterbrand=="Exide")
				{
					inverter_table_data ="<div><b>COMP</b>: Computer | <b>FN</b>: Fans | <b>TL</b>: Tube Lights | <b>TV</b>: Television (32&#8243;)</div><div class='table-responsive'>  <table width='100%' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tbody> <tr><th colspan='2'>Inverter Capacity</th><th>COMP</th><th>FN</th><th>TL</th><th>TV</th></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('650 VA');\" >650&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>4</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>2</td><td>3</td><td>0</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('850 VA');\" >850&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('1450 VA');\" >1450&nbsp;VA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr> </tbody> </table> </div> ";
				}
				else if(inverterbrand=="Luminous")
				{
					inverter_table_data ="<div><b>COMP</b>: Computer | <b>FN</b>: Fans | <b>TL</b>: Tube Lights | <b>TV</b>: Television (32&#8243;)</div><div class='table-responsive'> <table width='100%' class='configure-your-inverter-table table table-condensed table-bordered table-striped'><tbody><tr><th colspan='2'>Inverter Capacity</th><th>COMP</th><th>FN</th><th>TL</th><th>TV</th></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('650 VA');\" >650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('700 VA');\" >700&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('850 VA');\" >850&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('900 VA');\" >900&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr><tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('1500 VA');\" >1500&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('1650 VA');\" >1650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('1700 VA');\" >1700&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr><tr><td><b>Option&nbsp;2</b></td><td>0</td><td>7</td><td>7</td><td>1</td></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('2 KVA');\" >2 KVA</a> <a href=\"javascript:Select_inverter_capacity('3.5 KVA');\" >3.5 KVA</a> </td><td><b>Option&nbsp;1</b></td><td>1</td><td>10</td><td>10</td><td>2</td></tr><tr><td><b>Option&nbsp;2</b></td><td>2</td><td>9</td><td>9</td><td>1</td></tr><tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr></tbody></table></div>";
				}
				else
				{
					inverter_table_data ="<div><b>COMP</b>: Computer | <b>FN</b>: Fans | <b>TL</b>: Tube Lights | <b>TV</b>: Television (32&#8243;)</div><div class='table-responsive'> <table width='100%' class='configure-your-inverter-table table table-condensed table-bordered table-striped'><tbody><tr><th colspan='2'>Inverter Capacity</th><th>COMP</th><th>FN</th><th>TL</th><th>TV</th></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('400 VA');\" >400&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>3</td><td>0</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>1</td><td>1</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('650 VA');\" >650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('675 VA');\" >675&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('700 VA');\" >700&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('825 VA');\" >825&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('850 VA');\" >850&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('880 VA');\" >880&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('900 VA');\" >900&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('1500 VA');\" >1500&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('1650 VA');\" >1650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('1700 VA');\" >1700&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>7</td><td>7</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('1400 VA');\" >1400&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity('1450 VA');\" >1450&nbsp;VA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity('2 KVA');\" >2 KVA</a> <a href=\"javascript:Select_inverter_capacity('3.5 KVA');\" >3.5 KVA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>10</td><td>10</td><td>2</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>2</td><td>9</td><td>9</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr>  </tbody></table></div>";
				}
			$("#inverter_capacity_table").html(inverter_table_data);
				
			}	
		});
		
		//####################### Step 2 Next Button
		allnextBtn_2.click(function(){
			var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");
			
			if (selectValidation("","inverter_capacity","select") == false)
			{
				return;
			}
			else
			{
				nextStepWizard.removeAttr('disabled').trigger('click');
				$('html, body').animate({ scrollTop: $("#nav-configure-your-inverter-step-1").offset().top }, 1000);
				$("#inverter_battery_capacity_table_selected").html("");
				
				
				var inverter_capacity =$("#inverter_capacity").val();
				var invertermake=inverter_brand_store_val;
				inverter_capacity_store_val=inverter_capacity;
				
				var inverter_battery_table_data;
				
				if(inverter_capacity=="400 VA")
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option>";

					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b> Battery Pcs</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td><td>3 hrs</td> <td>x 1</td> </tr> </table> </div>";
				}

				else if((inverter_capacity=="650 VA" || inverter_capacity=="675 VA" || inverter_capacity=="700 VA" || inverter_capacity=="825 VA"|| inverter_capacity=="850 VA" || inverter_capacity=="880 VA" || inverter_capacity=="900 VA") && (invertermake=="Amaron" || invertermake=="All" || invertermake=="All" || invertermake=="Luminous"))
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option>";

					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup</b></td> <td colspan='1'><b> Battery Pcs</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>4 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>4 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>4 hrs</td> <td>x 1</td> </tr> </table> </div>";
				}

				else if(inverter_capacity=="650 VA" && invertermake=="Exide")
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option>";
						
					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr><td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup</b></td> <td colspan='1'><b> Battery Pcs</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> </table> </div> ";
				}

				else if(inverter_capacity=="825 VA" || inverter_capacity=="880 VA"  || inverter_capacity=="1250 VA")
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option>";

					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup</b></td> <td colspan='1'><b> Battery Pcs</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr><tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Batter </a></td> <td>4 hrs</td> <td>x 1</td> </tr> </table> </div> ";
				}

				else if(inverter_capacity=="850 VA")
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='200 AH Tall Tubular Battery'>200 AH Tall Tubular Battery</option>";
					
					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup</b></td> <td colspan='1'><b> Battery Pcs</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>1 Hour</br>45 min</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>1 Hour</br>45 min</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 hrs</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('200 AH Tall Tubular Battery');\"> 200 AH Tall Tubular Batter</a></td> <td>4 hrs</td> <td>x 1</td> </tr> </table> </div> ";
				}

				else if(inverter_capacity=="1400 VA" || inverter_capacity=="1450 VA"  || inverter_capacity=="1500 VA" || inverter_capacity=="1650 VA" || inverter_capacity=="1700 VA" )
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option><option value='180 AH Tall Tubular Battery'>180 AH Tall Tubular Battery</option>";
					
					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b> Battery Pcs</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 hrs</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 hrs</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 hrs</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Battery</a></td> <td>4 hrs</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('180 AH Tall Tubular Battery');\"> 170 AH Current Battery</a></td> <td>4 hrs</br>15 min</td> <td>x 2</td> </tr> </table> </div> ";
				}
				
				else if(inverter_capacity=="2 KVA" || inverter_capacity=="3.5 KVA"  )
				{
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option><option value='180 AH Tall Tubular Battery'>180 AH Tall Tubular Battery</option>";
					
					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%' cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b> Battery Pcs</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 hrs</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 hrs</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 hrs</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 hrs</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 hrs</br>30 min</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Battery</a></td> <td>4 hrs</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery('180 AH Tall Tubular Battery');\"> 180 AH Tall Tubular Battery</a></td> <td>4 hrs</br>15 min</td> <td>x 4</td> </tr> </table> </div> ";
				}

				else if(inverter_capacity=="1450 VA" )
				{
					
					inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='200 AH Tall Tubular Battery'>200 AH Tall Tubular Battery</option>";
						
					inverter_battery_table_data ="<b>Suggested Inverter Battery for "+inverter_capacity+"</b><div class='table-responsive'> <table width='100%'  cellpadding='2' class='configure-your-inverter-table table table-condensed table-bordered table-striped'><tr > <td colspan='1' ><b>Backup</b></td> <td colspan='1' ><b>&nbsp;2&nbsp;&nbsp;Hours&nbsp;</b></td><td colspan='1'><b>3 Hours 30 min</b></td><td colspan='1'><b>4 Hours 30 min</b></td></tr><tr > <td >Tubular Battery</td> <td><a href=\"javascript:Select_inverter_battery('100 AH Tubular Battery');\" >100AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>100AH</a></td> <td ><a href=\"javascript:Select_inverter_battery('150 AH Tubular Battery');\" >150AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>150AH </a></td> <td > - </td><td > - </td></tr><tr > <td >Tall Tubular Battery</td> <td > - </td> <td ><a href=\"javascript:Select_inverter_battery('150 AH Tall Tubular Battery');\" >150AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>150AH </a></td> <td ><a href=\"javascript:Select_inverter_battery('200 AH Tall Tubular Battery');\" >200AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>200AH </a> </td><td > - </td></tr></table></div>";
				}
				else
				{
					
				}
				
					$("#inverter_battery_capacity").html(inverter_battery_options).focus();
					$("#inverter_battery_capacity_table").html(inverter_battery_table_data);
					$("input[name='configure-your-inverter-step-3-yes-no'][value='Yes']").prop('checked', true);
					$("input[name='configure-your-inverter-step-3-yes-no']:checked").change();
					
			}	
		});
		
		//####################### Step 3 Next Button

		allnextBtn_3.click(function(){
			var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");
	
			var configure_your_inverter_step_yes_no = $("input[name='configure-your-inverter-step-3-yes-no']:checked").val()
			if(configure_your_inverter_step_yes_no=="Yes" || configure_your_inverter_step_yes_no==undefined)
			{
				if (selectValidation("","inverter_battery_capacity","select") == false)
				{
					return;
				}
				else
				{
					var inverter_battery_capacity =$("#inverter_battery_capacity").val();
					inverter_battery_capacity_store_val=inverter_battery_capacity;
					nextStepWizard.removeAttr('disabled').trigger('click');
					$('html, body').animate({ scrollTop: $("#nav-configure-your-inverter-step-1").offset().top }, 1000);
					$("#product_state_conf").focus();
				}
			}
			else if(configure_your_inverter_step_yes_no=="No")
			{
				var inverter_battery_capacity =$("#inverter_battery_capacity").val();
				inverter_battery_capacity_store_val=inverter_battery_capacity;
				nextStepWizard.removeAttr('disabled').trigger('click');
				$('html, body').animate({ scrollTop: $("#nav-configure-your-inverter-step-1").offset().top }, 1000);
				$("#product_state_conf").focus();
			}
			else
			{
				alert("Opps !. Some thing went wrong. Please reload the Page.");
			}	
			
			inverter_state_store_val =$("#product_state").val();
			inverter_city_store_val =$("#product_city").val();
			
			var ctime=365*24*60*60*1000;
			setCookie("product_state_cookie", inverter_state_store_val,ctime);
			setCookie("product_city_cookie", inverter_city_store_val,ctime);
				
			var publicUrl =$("#publicUrl").val();
			var inverter_brand_store_val_URL;
			var inverter_capacity_store_val_URL;
			var inverter_battery_capacity_store_val_URL;
			var inverter_state_store_val_URL;
			var inverter_city_store_val_URL;
			
			inverter_brand_store_val_URL=inverter_brand_store_val.replace(/ /g, "+");
			inverter_capacity_store_val_URL=inverter_capacity_store_val.replace(/ /g, "+");
			inverter_battery_capacity_store_val_URL=inverter_battery_capacity_store_val.replace(/ /g, "+");
			inverter_state_store_val_URL=inverter_state_store_val.replace(/ /g, "-");
			inverter_city_store_val_URL=inverter_city_store_val.replace(/ /g, "+");
			
			$("#Loading_bar").show();
			var configure_your_inverter_step_yes_no = $("input[name='configure-your-inverter-step-3-yes-no']:checked").val()
			if(configure_your_inverter_step_yes_no=="Yes" || configure_your_inverter_step_yes_no==undefined)
			{
				window.location.href = publicUrl+'/bookbattery/Inverter-and-Battery-Combo/'+inverter_brand_store_val_URL+'/'+inverter_state_store_val_URL+'/'+inverter_city_store_val_URL+'/Inverter-Capacity='+inverter_capacity_store_val_URL+'/Battery-Capacity='+inverter_battery_capacity_store_val_URL+'/';
			}
			else if(configure_your_inverter_step_yes_no=="No")
			{
				window.location.href = publicUrl+'/bookbattery/Inverter/'+inverter_brand_store_val_URL+'/'+inverter_state_store_val_URL+'/'+inverter_city_store_val_URL+'/Inverter-Capacity='+inverter_capacity_store_val_URL+'/';
			}
			else
			{
				alert("Opps !. Some thing went wrong. Please reload the Page.");
			}
		});
				
		//####################### Step 4 Next Button	
				
		allnextBtn_4.click(function(){
			var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");
			
			if (selectValidation("","product_state_conf","select") == false)
			{
				return;
			}
			else
			{
				
			}
			
			if (selectValidation("","product_city_conf","select") == false)
			{
				return;
			}
			else
			{
				
			}
			
		});
		
		$('div.setup-panel div a.btn-primary').trigger('click');
		
		
	});	
	
	//####################### End of Step's  
	
	$(document).ready(function () {
		$("#inverter_brand").html("<option>Loading ....</option>");			
		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
			data: {product_type: "Inverter"},
			success: function(data)
			{	
				data= data.replace("Select&nbsp;Make","Select Inverter Brand");
				if(data.split("value").length<=4)
				{
					data= data.replace("<option style='' value='All'>All</option>","");
				}	
				else
				{}
				$("#inverter_brand").html(data).focus();
			}
		});
	});
	
	
	$(document).ready(function () {
	// Code to get City #################################################
		 
		$( "#product_state_conf" ).change(function() {
			product_state_conf=$("#product_state_conf").val();
			
			if (product_state_conf=="0" || product_state_conf=="default" || product_state_conf==""){
				$("#product_city_conf").html("<option>Select City</option>");
			}
			else{

				$("#product_city_conf").html("<option>Loading ... </option>");
				
				 $.ajax({					 
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
					data: {state:product_state_conf},
					success: function(data)
					{	
						$("#product_city_conf").html(data).focus();
					}
				});
			}

		});
	});

	
	function Select_inverter_capacity(selectValue)
	{
		$("#inverter_capacity").val(selectValue).change();
		
	}
	function Select_inverter_battery(selectValue){
		$("#inverter_battery_capacity").val(selectValue).change();
		
	}
	
	
	function order_form_Back(id)
	{
		$("#"+id+"").click();
	} 
	
	$("#inverter_capacity").change(function(e) 
	{
		$(".configure-your-inverter-nextBtn-2").focus();
		var MaxScroll = $(window).height()*0.8;
		var center = $(window).height()/2;
		var top = $(".configure-your-inverter-nextBtn-2").offset().top ;
		if (top > MaxScroll){
			if (screen.width <= 767)
			{
				$('body').animate({
					scrollTop:top-center
				},'slow');
			}
		}
		
		$("#inverter_capacity_table_selected").html("<p>Your Selected Inverter Capacity : "+$(this).val()+"<br><span style='font-size: 12px;  font-weight: 600; color: #949393;'>Click Next</span></p>");
		$("#configure-your-inverter-step-3-lable").html("<p>Would you like to buy Inverter Battery for "+$(this).val()+" Inverter ");
	})
	
	$("#inverter_battery_capacity").change(function() 
	{	
		$(".configure-your-inverter-nextBtn-3").focus();
		var MaxScroll = $(window).height()*0.8;
		var center = $(window).height()/2;
		var top = $(".configure-your-inverter-nextBtn-3").offset().top ;
		if (top > MaxScroll){
			if (screen.width <= 767)
			{
				$('body').animate({
					scrollTop:top-center
				},'slow');
			}
		}
		
		$("#inverter_battery_capacity_table_selected").html("<p>Your Selected Inverter Battery Capacity : "+$(this).val()+"<br><span style='font-size: 12px;  font-weight: 600; color: #949393;'>Click Next</span></p>");
	})
	
	$("#inverter_brand").change(function() 
	{
		$(".configure-your-inverter-nextBtn-1").focus();
	})
	$("#product_city_conf").change(function() 
	{
		$(".configure-your-inverter-nextBtn-4").focus();
	})
	

	
$(document).ready(function() {

    $('#configure-your-inverter-step-3-check input:radio').change(function() {
		var configure_your_inverter_step_yes_no = $("input[name='configure-your-inverter-step-3-yes-no']:checked").val()
		if(configure_your_inverter_step_yes_no=="Yes")
		{
			$('#configure-your-inverter-step-3-select').show();
			$('#configure-your-inverter-step-3-unselect').hide();
		}
		else if(configure_your_inverter_step_yes_no=="No")
		{
			$('#configure-your-inverter-step-3-select').hide();	
			$('#configure-your-inverter-step-3-unselect').show();
		}
		else
		{
			alert("Opps !. Some thing went wrong. Please reload the Page.");
		}
		
    });
});

