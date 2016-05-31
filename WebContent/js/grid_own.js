Ext.require
([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.chart.*'
]);

Ext.define
('CTData', {
    extend: 'Ext.data.Model',
    idProperty: 'taskId',
    fields: 
    [
        {name: 'ctId', type: 'int'},
        {name: 'tst_machine', type: 'string'},
        {name: 'traffic_model', type: 'string'},
        {name: 'software', type: 'string'},
        {name: 'product', type: 'string'},
        {name: 'version', type: 'string'}
    ]
});

Ext.onReady(function(){
	
	
/*createGridPanel(mockedData);*/

/*Ext.get('button').on('click',function(){
  var data={
          result:[{
            "ctId":"1",
            "tst_machine":"SPARC SAMid T5220",
            "traffic_model":"CoreNetwork141",
            "software":"LSV234",
            "product":"PGClassic",
            "version":"MA70GA"
          }]
        };*/
  		
  
		Ext.Ajax.request({//ajax request pass the predefined params
    		url:"/cso/statistics",
      		params:{
      			action:"1"  //search will pass to B/E
            },
    		method: 'GET',
    		success: function(result, request){
    			var data = Ext.decode(result.responseText);
                //createGridPanel(data);
    			//Ext.getCmp('result').getStore().loadData(data.result);
/*    			createGridPanel(data);
    			createChart(data);*/
    			
    			createGridPanel(data);
    			createPieChart();
    		},
    		failure: function(result, request){//if ajax request failure
    			alert("Server Response Error");
    		}
    	}); 
/*});*/
		
		
		
		
		
		
/*		function createStore(data)
		{

					store.on('groupchange', function(){
				  		//alert('groupchange');
				  		
					    var dataArr=Ext.getCmp('result').getStore().getGroups();
					    var data=[];
					    console.log(dataArr);
					    Ext.Array.each(dataArr,function(value,index){
					        data[index]={
					            name:value.name,
					            value:value.children.length
					        };
					    });
					    console.log(data);
						var tmpStore = Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					        data: data
						});
						
						console.log(tmpStore);
						Ext.getCmp('chart').bindStore(tmpStore);
			  
			  		});
  			  //createGridPanel(store);
  			  
			  
		}*/
		
		
		  ///create grid panel
		  function createGridPanel(data){
			  
			    //create grid store
				var store = Ext.create('Ext.data.Store', {
				    storeId:'resultStore',
				    model: 'CTData',
				    //fields:['ctId', 'tst_machine', 'traffic_model','software','product','version'],
				    groupField:'traffic_model',
				    data:data.result,
				    
				    listeners:{
				          groupchange:function(){
				        	  
/*							    var dataArr=Ext.getCmp('result').getStore().getGroups();
							    var data=[];
							    console.log(dataArr);
							    Ext.Array.each(dataArr,function(value,index){
							        data[index]={
							            name:value.name,
							            value:value.children.length
							        };
							    });*/
							    //console.log(data);
								var tmpStore = Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							        data: pieData()
								});
								
								console.log(tmpStore);
				        	  
				                //Ext.getCmp('chart').getStore().loadData(pieData());
				                
								Ext.getCmp('chart').bindStore(tmpStore);
				                
				               	}
				      			}
			
					});
			  
			  
  
			  var grid = Ext.create('Ext.grid.Panel',{
			    	id:'result',
			    	title: 'CT DATA SUMMARY',
			        store: store,
			    	//features: [{ftype:'grouping'}],
	
			        dockedItems: [{dock: 'top', xtype: 'toolbar',
		            	items: [{
		                tooltip: 'Refresh data from server',
		                text: 'Refresh Grid',
		                handler: function(){
		                		Ext.Ajax.request({//ajax request pass the predefined params
		                		url:"/cso/statistics",
		                  		params:{action:"1"},
		                		method: 'GET',
		                		success: function(result, request){
		                			data = Ext.decode(result.responseText);
		                			
		                			Ext.getCmp('result').getStore().loadData(data.result);},
		                		failure: function(result, request){//if ajax request failure
		                			alert(result);}
		                	});
		                }}]
			        }],
			        features: [{
			            id: 'group',
			            ftype: 'groupingsummary',
			            groupHeaderTpl: '{name}',
			            hideGroupedHeader: true,
			            enableGroupingMenu: true,
			            startCollapsed: true
			        }],			        
			        columns: [{ text: 'ID',  dataIndex: 'ctId', summaryType: 'count', 
			            summaryRenderer: function(value, summaryData, dataIndex) {
			                return ((value === 0 || value > 1) ? '( Count: ' + value + ' )' : '(Count: 1)');
			            }},
			            { text: 'Machine', dataIndex: 'tst_machine', width: 150},
			            { text: 'Model', dataIndex: 'traffic_model', width: 150},
			          	{ text: 'Software',  dataIndex: 'software' },
			            { text: 'Product', dataIndex: 'product'},
			            { text: 'Version', dataIndex: 'version'}],
			        height: 600,
			        width: 550,
			        renderTo: Ext.get('resultPanel')});
		  	}

			function pieData(){
				var dataArr=Ext.getCmp('result').getStore().getGroups();
			    var data=[];
			    Ext.Array.each(dataArr,function(value,index){
			        data[index]={
			            name:value.name,
			            value:value.children.length
			        };
			    });
			    return data;
			}
		  
			function createPieChart(){

				var store = Ext.create('Ext.data.Store', {
				storeId: 'storePie',	
			    fields: ['name', 'value'],
			        data: pieData()
				});
				
				var chart = Ext.create('Ext.chart.Chart', {
				    //renderTo: Ext.get('pie'),
					id:'chart',
				    width: 550,
				    height: 550,
				    animate: true,
				    store: store,
		            shadow: true,
		            legend: {
		                position: 'right'
		            },
		            theme: 'Base:gradients',
				    series: [{
				        type: 'pie',
				        angleField: 'value',
				        showInLegend: true,
				        tips: {
				            trackMouse: true,
				            width: 140,
				            height: 28,
				            renderer: function(storeItem, item) {
				                // calculate and display percentage on hover
			                    var total = 0;
			                    store.each(function(rec) {
			                        total += rec.get('value');
			                    });
				            	
			                    console.log(total);
			                    
			                    
				                this.setTitle(storeItem.get('name') + ': ' + Math.round(storeItem.get('value')/total*100)+'%');
				                
				                
				                console.log(store);
				                //store.loadData([],false);
				                
				               },

				        },
				        highlight: {
				            segment: {
				                margin: 10
				            }
				        },
				        
				        label: {
				            field: 'name',
				            display: 'rotate',
				            contrast: true,
				            font: '18px Arial'
				        }
				        }],
				        
/*			        listeners: {
			            afterrender: function(storeItem, item) {
			            	// calculate and display percentage on hover
			            	
			            	store.loadData([],false);
			            	console.log(store);
			            	alert('clean data');}
				        }*/
		            
					});
				
				
			    var panel1 = Ext.create('widget.panel', {
			        width: 550,
			        height: 600,
			        title: 'Pie Chart for Statistics',
			        //renderTo: Ext.getBody(),
			        renderTo:Ext.get('PieChart'),
			        layout: 'fit',
			        tbar: [{
			            enableToggle: true,
			            pressed: false,
			            text: 'Donut',
			            toggleHandler: function(btn, pressed) {
			                chart.series.first().donut = pressed ? 35 : false;
			                chart.refresh();
			            }
			        }],
			        items: chart
			    }); 
				
		  }

});