// JavaScript Document
    //Arrays for data  
    var scenarioList= new Array();  
	var rnList= new Array();
	var pnList= new Array();
    //second selecct 
	var machineIndex;
    //var provinceIndex;  
    //function to show second scenario  
    function selectScenario()  
    {  
		
		scenarioList['SPARC']=['SALow','SAMid','2Nodes','SCHA'];
		scenarioList['X86']=['SALow','SAMid','2Nodes','SCHA','Cluster'];
		scenarioList['EBS']=['SALow','SAMid','2Nodes','SCHA_2X2','SCHA_2X4','SCHA_2X6','SCHA_2X8'];
		
		rnList['SPARC']=['SALow','SAMid','2Nodes','SCHA'];
		rnList['X86']=['SALow','SAMid','2Nodes','SCHA','Cluster'];
		rnList['EBS']=['SALow','SAMid','2Nodes','SCHA_2X2','SCHA_2X4','SCHA_2X6','SCHA_2X8'];
		
		pnList['SPARC']=['SALow','SAMid','2Nodes','SCHA'];
		pnList['X86']=['SALow','SAMid','2Nodes','SCHA','Cluster'];
		pnList['EBS']=['SALow','SAMid','2Nodes','SCHA_2X2','SCHA_2X4','SCHA_2X6','SCHA_2X8'];
		
          
        //get the first level
        machineIndex=document.newCTform.selMachine.value;  
        var op;  
        //clean all data
        document.newCTform.selScenario.options.length=1;  
        document.newCTform.selType.options.length=1;  
          
        //add value to list  
        for(var i in scenarioList[machineIndex])   
        {  
            op=new Option(scenarioList[machineIndex][i],scenarioList[machineIndex][i]);  
            document.newCTform.selScenario.options.add(op);  
        }  
    }  
      
    //select third level list
    function selectType()  
    {  

        scenarioList['SPARC']['SALow']=['T3-1','T5220','T2000'];  
        scenarioList['SPARC']['SAMid']=['T3-1','T5220','T2000'];  
        scenarioList['SPARC']['2Nodes']=['T3-1','T5220'];  
	scenarioList['SPARC']['SCHA']=['T5220'];
          
        scenarioList['X86']['SALow']=['HPBL460C','HPBL460C-G7','HPBL460C-G8-Low','HPBL460C-G8-Mid','VMWare']; 
        scenarioList['X86']['SAMid']=['HPBL460C','HPBL460C-G7','HPBL460C-G8-Low','HPBL460C-G8-Mid'];
        scenarioList['X86']['2Nodes']=['HPBL460C','HPBL460C-G7','HPBL460C-G8-Low','HPBL460C-G8-Mid'];  
        scenarioList['X86']['SCHA']=['HPBL460C','HPBL460C-G7','HPBL460C-G8-Low','HPBL460C-G8-Mid'];
        scenarioList['X86']['Cluster']=['VMWare'];
  
        scenarioList['EBS']['SALow']=['GEP3']; 
        scenarioList['EBS']['SAMid']=['GEP3'];
        scenarioList['EBS']['2Nodes']=['GEP3'];
        scenarioList['EBS']['SCHA_2X2']=['GEP3'];  
        scenarioList['EBS']['SCHA_2X4']=['GEP3'];
	scenarioList['EBS']['SCHA_2X6']=['GEP3'];
	scenarioList['EBS']['SCHA_2X8']=['GEP3'];
		
		  
        rnList['SPARC']['SALow']=['1'];  
        rnList['SPARC']['SAMid']=['1'];          
        rnList['SPARC']['2Nodes']= ['2'];
	rnList['SPARC']['SCHA']=['2']; 
	 
	rnList['X86']['SALow']=['1']; 
	rnList['X86']['SAMid']=['1'];
        rnList['X86']['2Nodes']=['2']; 
        rnList['X86']['SCHA'] = ['2'];
        rnList['X86']['Cluster']=['2']; 

        rnList['EBS']['SALow']=['1'];
        rnList['EBS']['SAMid']=['1'];
        rnList['EBS']['2Nodes']=['2'];
	rnList['EBS']['SCHA_2X2']=['2'];  
        rnList['EBS']['SCHA_2X4']=['2'];
	rnList['EBS']['SCHA_2X6']=['2'];
	rnList['EBS']['SCHA_2X8']=['2'];
		
	pnList['SPARC']['SALow']=['1'];  
        pnList['SPARC']['SAMid']=['1'];          
        pnList['SPARC']['2Nodes']= ['2'];
	pnList['SPARC']['SCHA']=['2','3','4','5','6','7','8']; 
		 
	pnList['X86']['SALow']=['1']; 
	pnList['X86']['SAMid']=['1'];
        pnList['X86']['2Nodes']=['2']; 
        pnList['X86']['SCHA'] = ['1','2','3','4','5','6','7','8'];
        pnList['X86']['Cluster']=['2','3','4']; 

        pnList['EBS']['SALow']=['1'];
        pnList['EBS']['SAMid']=['1'];
        pnList['EBS']['2Nodes']=['2'];
	pnList['EBS']['SCHA_2X2']=['2'];  
        pnList['EBS']['SCHA_2X4']=['4'];
	pnList['EBS']['SCHA_2X6']=['6'];
	pnList['EBS']['SCHA_2X8']=['8'];
		 
        //take value from secondary lis
        var scenarioIndex=document.newCTform.selScenario.value;
		  
        var oopp;  
        //clean list  
        document.newCTform.selType.options.length=1;  
        //write data to list 
        for(var j in scenarioList[machineIndex][scenarioIndex])  
        {  
            oopp=new Option(scenarioList[machineIndex][scenarioIndex][j],scenarioList[machineIndex][scenarioIndex][j]);  
            document.newCTform.selType.options.add(oopp);  
        }  
		
		var rn;
		document.newCTform.RN.options.length=1;  
        //write data to list  
        for(var k in rnList[machineIndex][scenarioIndex])  
        {  
            rn=new Option(rnList[machineIndex][scenarioIndex][k],rnList[machineIndex][scenarioIndex][k]);  
            document.newCTform.RN.options.add(rn);  
        } 
		
		var pn;
		document.newCTform.PN.options.length=1;  
        //write data to list  
        for(var l in pnList[machineIndex][scenarioIndex])  
        {  
            pn=new Option(pnList[machineIndex][scenarioIndex][l],pnList[machineIndex][scenarioIndex][l]);  
            document.newCTform.PN.options.add(pn);  
        }		
    }  
    
	function submitChk()
	{
		if(document.newCTform.selMachine.value=="")
		{
			alert("Sorry,please select hardware information about platform");
			document.newCTform.selMachine.focus();
			return false;
		}

		if(document.newCTform.selScenario.value=="")
		{
			alert("Sorry,please select scenario about platform");
			document.newCTform.selScenario.focus();
			return false;
		}
		
		if(document.newCTform.selType.value=="")
		{
			alert("Sorry,please select machine type about platform");
			document.newCTform.selType.focus();
			return false;
		}
		
		if(document.newCTform.RN.value=="")
		{
			alert("Sorry,please select RN number");
			document.newCTform.RN.focus();
			return false;
		}
		
		if(document.newCTform.PN.value=="")
		{
			alert("Sorry,please select PN number");
			document.newCTform.PN.focus();
			return false;
		}		
		

		if(document.newCTform.cpu_session_all.value=="")
		{
			alert("Sorry,please add cpu session file");
			document.newCTform.cpu_session_all.focus();
			return false;
		}
		
		if(document.newCTform.cso_session_local_all.value=="")
		{
			alert("Sorry,please add cso session file");
			document.newCTform.cso_session_local_all.focus();
			return false;
		}
		
		
		if(document.newCTform.team.value=="")
		{
			alert("Sorry,please provide team info");
			document.newCTform.team.focus();
			return false;
		}else if (document.newCTform.team.value.length>50)
		{
			alert("Team information should shorter than 50 chars!");
			document.newCTform.team.focus();
			return false;
		}
		
		if(document.newCTform.tester.value=="")
		{
			alert("Sorry,please indicate tester");
			document.newCTform.tester.focus();
			return false;
		}else if (document.newCTform.tester.value.length>10)
		{
			alert("Tester name should shorter than 10 chars!");
			document.newCTform.tester.focus();
			return false;
		}
		
		if(document.newCTform.init_cap.value=="")
		{
			alert("Sorry,please input initial capacity");
			document.newCTform.init_cap.focus();
			return false;
		}else if (document.newCTform.init_cap.value.length>25)
		{
			alert("Initial capacity should shorter than 25 chars!");
			document.newCTform.init_cap.focus();
			return false;
		}
		
		if(document.newCTform.deviation.value=="")
		{
			alert("Sorry,please input deviation");
			document.newCTform.deviation.focus();
			return false;
		}else if (document.newCTform.deviation.value.length>25)
		{
			alert("Deviation should shorter than 25 chars!");
			document.newCTform.deviation.focus();
			return false;
		}
		
	}
	
	function submitChkEdit()
	{
		if(document.newCTform.selMachine.value=="")
		{
			alert("Sorry,please select hardware information about platform");
			document.newCTform.selMachine.focus();
			return false;
		}

		if(document.newCTform.selScenario.value=="")
		{
			alert("Sorry,please select scenario about platform");
			document.newCTform.selScenario.focus();
			return false;
		}
		
		if(document.newCTform.selType.value=="")
		{
			alert("Sorry,please select machine type about platform");
			document.newCTform.selType.focus();
			return false;
		}
		
		if(document.newCTform.RN.value=="")
		{
			alert("Sorry,please select RN number");
			document.newCTform.RN.focus();
			return false;
		}
		
		if(document.newCTform.PN.value=="")
		{
			alert("Sorry,please select PN number");
			document.newCTform.PN.focus();
			return false;
		}		

		if(document.newCTform.team.value=="")
		{
			alert("Sorry,please provide team info");
			document.newCTform.team.focus();
			return false;
		}else if (document.newCTform.team.value.length>30)
		{
			alert("Team information should be less than 30 chars!");
			document.newCTform.team.focus();
			return false;
		}
	}
