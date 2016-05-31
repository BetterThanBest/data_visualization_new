<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="js/select.js"></script>

<title>CT Data Updates</title>

</head>
<body>

<form action="uploadFile" method="post" enctype="multipart/form-data" name="newCTform" id="newCTform" onsubmit="return submitChk()">

<table width="100%" border="0">
    <tr>
      <td colspan="4" align="center"><h1>Add CT Data</h1></td>
    </tr>
    <tr>
      <td>
      <table width="75%" border="1" align="center"  cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" >
        <tr bgcolor="#CCCCCC">
          <td colspan="4" align="center">Hardware Settings:</td>
        </tr>
        <tr>

          <td><label>Platform</label></td>
          <td>
<!-- 		  <select name="platform">
		    <option value="SPARC 2Nodes T3-1">SPARC 2Nodes T3-1</option>
        	<option value="SPARC 2Nodes T5220">SPARC 2Nodes T5220</option>
        	<option value="SPARC SAMid T5220">SPARC SAMid T5220</option>
        	<option value="SPARC SALow T3-1">SPARC SALow T3-1</option>
        	<option value="SPARC SCHA T5220">SPARC SCHA T5220</option>
        	<option value="X86 2Nodes HPBL460C">X86 2Nodes HPBL460C</option>
        	<option value="X86 SALow HPBL460C">X86 SALow HPBL460C</option>
        	<option value="X86 Cluster VMWare">X86 Cluster VMWare</option>
        	<option value="X86 SALow VMWar">X86 SALow VMWare</option>
        	<option value="EBS 2X2 GEP3">EBS 2X2 GEP3</option>
        	<option value="EBS 2X4 GEP3">EBS 2X4 GEP3</option>        	
          </select> -->
			<select name="selMachine" id="select" onchange="selectScenario()"> 
				<option value=""selected="selected">--Select HDware--</option> 
      			<option value="SPARC">SPARC</option>  
      			<option value="X86">X86</option>  
      			<option value="EBS">EBS</option>  
  			</select>
			<select name="selScenario" id="select2" onchange="selectType()">  
    			<option value=""selected="selected">--Select Scenario--</option>  
  			</select> 
  			<select name="selType" id="select3">  
    			<option value=""selected="selected">--Select Type--</option>  
  			</select>       
          </td>
          <td><label>Storage Type</label></td>
          <td>
		  <select name="storageType">
		  	<option value="None" selected="selected">None</option>
		  	<option value="SE 3510">SE 3510</option>
			<option value="EMC CX3-20F">EMC CX3-20F</option>
			<option value="EMC AX4-5F">EMC AX4-5F</option>
			<option value="DellMD3620i">DellMD3620i</option>
                        <option value="DellMD3620f">DellMD3620f</option>
          </select>          
          </td>
        </tr>
        <tr>
          <td><label>Resource Node(RN)</label></td>
          <td>
          <select name="RN">
     		<option value="" selected="selected">--Select RN--</option>
          </select></td>
          <td><label>Processing Node(PN)</label></td>
          <td><select name="PN">
     		<option value="" selected="selected">--Select PN--</option>
          </select>
          </td>
        </tr>

      </table></td>
    </tr>
    <tr>
      <td><table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF">
        <tr bgcolor="#CCCCCC">
          <td colspan="4" align="center"><label>Software Settings</label></td>
          </tr>
        <tr>
          <td><label>Traffic Model</label></td>
          <td>
		  <select name="trafficModel">
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
        		<option value="MMTel30">MMTel30</option>
        		<option value="SAPC">SAPC</option>
        		<option value="UDR_Redundancy_CAI">UDR_Redundancy_CAI</option>
        		<option value="UDR_Redundancy_CAI3G">UDR_Redundancy_CAI3G</option>
        		<option value="XCAI_traffic">XCAI_traffic</option>
          </select>		  
          </td>
          <td><label>Interface</label></td>
          <td>
		  <select name="interface">
		    <option value="CAI">CAI</option>
        	<option value="CAI3G">CAI3G</option>
        	<option value="XCAI3G">XCAI3G</option>
          </select>		  
          </td>
        </tr>
        <tr>
          <td><label>NE DownStream Link</label></td>
          <td>
          	<!-- <input type="text" name="neLink" value="N/A"/> -->
          	<select name="neLink">
          		<option value="96 downstream link">96</option>
		    	<option value="128 downstream link">128</option>
        		<option value="256 downstream link" selected="selected">256</option>
        		<option value="512 downstream link">512</option>
          	</select>
          </td>
          <td><label>Proclog Level</label></td>
          <td><select name="proclogLv">
		    <option value="1">1</option>
        	<option value="2" selected="selected">2</option>
        	<option value="3">3</option>
          </select>          </td>
        </tr>
        <tr>
          <td><label>Simulator info</label></td>
          <td>
          <select name="simInfo">
          	<option value="resp">resp</option>
          	<option value="resp(cacheenable)">resp(cacheenable)</option>
          	<option value="LDAP">LDAP</option>
          	<option value="JavaLink">JavaLink</option>
          </select>          
          </td>
          <td><label>License</label></td>
          <td><input type="text" name="license" value="Full license"/></td>
        </tr>
        <tr>
          <td><label>Product</label></td>
          <td>
		  <select name="product">
		    <option value="PGClassic">PGClassic</option>
        	<option value="PGNGN">PGNGN</option>
        	<option value="PM">PM</option>
          </select>		  </td>
          <td><label>Version</label></td>
          <td>
		  <select name="version_1">
		  	<option value="MA60">MA60</option>
        	<option value="MA61">MA61</option>
        	<option value="MA62">MA62</option>
			<option value="MA63">MA63</option>
			<option value="MA70" selected="selected">MA70</option>
			<option value="MA71">MA71</option>
<option value="MA72">MA72</option>
<option value="MA80">MA80</option>
          </select>
          <select name="version_2">
		  	<option value="GA">GA</option>
        	<option value="CP1" selected="selected">CP1</option>
        	<option value="CP2" >CP2</option>
			<option value="CP3">CP3</option>
			<option value="CP4">CP4</option>
			<option value="CP5">CP5</option>
			<option value="CP6">CP6</option>
			<option value="CP7">CP7</option>
			<option value="CP8">CP8</option>
			<option value="CP9">CP9</option>
          </select>		  
          </td>
        </tr>
        <tr>
          <td><label>Initial Capacity</label></td>
          <td><input type="text" name="init_cap" value="N/A"/></td>
          <td><label>Deviation</label></td>
          <td><input type="text" name="deviation" value="N/A"/></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td><table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF">
        <tr bgcolor="#CCCCCC">
          <td colspan="4" align="center"><label>Execution Information</label></td>
          </tr>
        <tr>
          <td><label>Team Information</label></td>
          <td><input type="text" name="team" /></td>
          <td><label>Executor</label></td>
          <td><input type="text" name="tester" /></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td>
      <table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF">
        <tr bgcolor="#CCCCCC">
          <td colspan="2" align="center"><label>File Record Uploads</label></td>
          </tr>
        <tr>
          <td width="31%"><label>cpu session all</label></td>
          <td width="69%"><input type="file" name="cpu_session_all"/></td>
        </tr>
        <tr>
          <td><label>cso session local all</label></td>
          <td><input type="file" name="cso_session_local_all" /></td>
        </tr>
        <tr>
          <td><label>emaplugin cpu session all</label></td>
          <td><input type="file" name="emaplugin_cpu_session_all" /></td>
        </tr>
        <tr>
          <td><label>emaplugin mem session all</label></td>
          <td><input type="file" name="emaplugin_mem_session_all" /></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td>
      <table width="75%" border="0" align="center">
        <tr>
          <td colspan="2" align="center"><input type="submit" name="Submit" value="Submit" />
            <input type="reset" name="Submit2" value="Reset" />
          </td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
	
</form>

</body>
</html>
