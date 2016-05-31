<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/format.css" />
<script language="javascript" src="js/jquery-1.8.1.js" type="text/javascript"></script>

<!-- <style type="text/css">
.test{width:600px;height:100px;margin-top:10px;}
</style> -->

<script type="text/javascript">
$(document).ready(function() {
	var findDivision="div#test";
	
	$(findDivision+" :not([name=checkbox])").attr("disabled", "disabled");
	
	$("#enabled").click(function(){
		$(findDivision+" :checkbox").removeAttr("disabled");
	});
	
	$("#disabled").click(function(){
		$(findDivision+" *").attr("disabled", "disabled");
		$("div#test :checkbox").each(function(){
			if($(this).attr("checked"))
				{
					$(this).removeAttr("checked");
				}
		});//this function here to uncheck checkboxes if they were checked in the previous operations.

	});	
	 
	$(".singleActivated").change(function(){
		if($(this).attr("checked"))
			{
				$(this).parent().parent().next().children().removeAttr("disabled");
				$(this).parent().parent().next().children("select").children().removeAttr("disabled");
			}
		else
			{
				$(this).parent().parent().next().children().attr("disabled", "disabled");
				$(this).parent().parent().next().children("select").children().attr("disabled", "disabled");
			}
		
	});
}); 

</script>


<title>CT/ST Management</title>
</head>
<body>
<h1 style="text-align: center; text-indent: 45px;">System Test Data Mart</h1>

<form id="Search" name="Search" method="post" action="ctDataCtr?mode=1">


<table width="100%" border="0" align="center" class="STYLE2">
  <tr>
    <td align="center">
	<table width="60%" border="0">
  		<tr bgcolor="#DCE8F3">
			<td colspan="5" align="center"><strong>Search Options</strong></td>
  		</tr>
  		<tr>
    		<td>
    		<label>
    		    <input type="radio" name="RadioGroup1" value="1" id="enabled" />
    		    Search by condition    		</label>			</td>
			<td>
    		<label>
    		    <input type="radio" name="RadioGroup1" value="2" id="disabled" checked="checked"/>
    		    List All data    		</label>			</td>
			<td>    			
    			<label><input type="checkbox" name="CT" value="CT" />CT</label>
    			<label><input type="checkbox" name="ST" value="ST" />ST</label>    		</td>
  		    <td><input type="submit" name="Submit" value="Search" /></td>
  		</tr>
	</table>   
	
	
<div class="test" id="test">
	<table width="100%" border="0">
    	<tr bgcolor="#DCE8F3"><td colspan="7" align="center"><strong>Optional Conditions</strong></td></tr>
    	<tr bgcolor="#F0F0F0">
      	<td width="99">
	  		<label><input type="checkbox" name="chbtraffic" value="chbtraffic" class="singleActivated"/>Traffic Module</label>		</td>
      	<td width="99">
			<select name="TrafficModule">
        		<option value="CoreNetwork141">CoreNetwork141</option>
        		<option value="HLR141">HLR141</option>
        <option value="HLR141_CAI3G">HLR141_CAI3G</option>
        <option value="FAI_FAM">FAI_FAM</option>
        <option value="Edifact_MA61_CDP21">Edifact_MA61_CDP21</option>
        <option value="ICS50_FD1">ICS50_FD1</option>
        <option value="ICS50_Layered">ICS50_Layered</option>
        <option value="IMT4_UP_BWR15">IMT4_UP_BWR15</option>
        <option value="IMT4_UP_BWR16">IMT4_UP_BWR16</option>
        <option value="IMT4_UP_BWR17">IMT4_UP_BWR17</option>
        <option value="IMT4_UP_BWR17SP4">IMT4_UP_BWR17SP4</option>
	<option value="IMT4_UP_BWR19SP4">IMT4_UP_BWR19SP4</option>
        <option value="MiO20">MiO20</option>
        <option value="MiO30">MiO30</option>
        <option value="MTAS31">MTAS31</option>
        <option value="PAS">PAS</option>
        <option value="RR_BWR15">RR_BWR15</option>
        <option value="BCS">BCS</option>
        <option value="CoreNetwork12B">CoreNetwork12B</option>
        <option value="CS">CS</option>
        <option value="CS50_2">CS50_2</option>
        <option value="CS50_3">CS50_3</option>
        <option value="HLR_NPlusONE">HLR_NPlusONE</option>
        <option value="HLR135_CAI3G">HLR135_CAI3G</option>
        <option value="ICS50_FD1">ICS50_FD1</option>
        <option value="ICS50_Layered">ICS50_Layered</option>
        <option value="MMTel12A">MMTel12A</option>
        <option value="MMTel30">MMTel30</option>
        <option value="SAPC">SAPC</option>
        <option value="UDR_Redundancy_CAI">UDR_Redundancy_CAI</option>
        <option value="UDR_Redundancy_CAI3G">UDR_Redundancy_CAI3G</option>
        <option value="XCAI_traffic">XCAI_traffic</option>
      	</select>		</td>
      	<td width="77">
      		<label><input type="checkbox" name="chbhdware" value="chbhdware" class="singleActivated"/>Platform</label></td>
        <td width="78"><select name="Platform">
<!--           <option value="SPARC SALow T3-1">SPARC SALow T3-1</option>
          <option value="SPARC SAMid T5220">SPARC SAMid T5220</option>
          <option value="SPARC 2Nodes T3-1">SPARC 2Nodes T3-1</option>
          <option value="SPARC 2Nodes T5220">SPARC 2Nodes T5220</option>          
          <option value="SPARC SCHA T5220">SPARC SCHA T5220</option>          
          <option value="X86 SALow HPBL460C">X86 SALow HPBL460C</option>
          <option value="X86 2Nodes HPBL460C">X86 2Nodes HPBL460C</option>
          <option value="X86 Cluster VMWare">X86 Cluster VMWare</option>
          <option value="X86 SALow VMWare">X86 SALow VMWare</option>         
          <option value="EBS 2Nodes GEP3">EBS 2Nodes GEP3</option>
          <option value="EBS 2X2 GEP3">EBS SCHA_2X2 GEP3</option>
          <option value="EBS 2X4 GEP3">EBS SCHA_2X4 GEP3</option> -->
          
          <option value="SPARC SALow">SPARC SALow</option>
          <option value="SPARC SAMid">SPARC SAMid</option>
          <option value="SPARC 2Nodes">SPARC 2Nodes</option>
          <option value="SPARC SCHA">SPARC SCHA</option>
          
          <option value="X86 SALow HPBL460C">X86 SALow HPBL460C</option>
          <option value="X86 SAMid HPBL460C">X86 SAMid HPBL460C</option>          
          <option value="X86 2Nodes HPBL460C">X86 2Nodes HPBL460C</option>
          <option value="X86 SCHA HPBL460C">X86 SCHA HPBL460C</option>
          
          <option value="EBS SALow GEP3">EBS SALow GEP3</option>
          <option value="EBS SAMid GEP3">EBS SAMid GEP3</option>
          <option value="EBS 2Nodes GEP3">EBS 2Nodes GEP3</option>
          <option value="EBS SCHA">EBS SCHA GEP3</option>
          
        </select></td>
      <td width="78"><label>
        <input type="checkbox" name="chbTeam" value="chbTeam" class="singleActivated"/>Team</label></td>
      <td width="79"><input type="text" name="team" /></td>
    </tr>
    <tr bgcolor="#F0F0F0">
      <td>
      	<label><input type="checkbox" name="chbTester" value="chbTester" class="singleActivated"/>Tester</label></td>
      <td><input type="text" name="Tester" /></td>
      <td>
      	<label><input type="checkbox" name="chbProduct" value="chbProduct" class="singleActivated"/>Product</label></td>
      <td><select name="Product">
        <option value="PGClassic">PG Classic</option>
        <option value="PM">PM</option>
      </select></td>
      <td>
      	<label><input type="checkbox" name="chbVersion" value="chbVersion" class="singleActivated"/>Version</label></td>
      <td><select name="Version">
        <option value="MA63GA">MA63GA</option>
        <option value="MA63CP1">MA63CP1</option>
        <option value="MA63CP2">MA63CP2</option>
        <option value="MA63CP3">MA63CP3</option>
        <option value="MA63CP4">MA63CP4</option>
        <option value="MA63CP5">MA63CP5</option>
        <option value="MA63CP6">MA63CP6</option>
        <option value="MA70GA<">MA70GA</option>
        <option value="MA70CP1">MA70CP1</option>
        <option value="MA70CP2">MA70CP2</option>
        <option value="MA70CP3">MA70CP3</option>
      </select></td>
    </tr>
    <tr bgcolor="#F0F0F0">
      <td>
       	<label><input type="checkbox" name="chbInterface" value="chbInterface" class="singleActivated"/>Interface</label></td>
      <td><select name="Interface">
        <option value="CAI">CAI</option>
        <option value="CAI3G">CAI3G</option>
        <option value="XCAI3G">XCAI3G</option>
      </select></td>
      <td colspan="2">&nbsp;</td>
      <td colspan="2">&nbsp;</td>
    </tr>
  </table>
</div>
	</td>
  </tr>
</table>

</form>
</body>
</html>
