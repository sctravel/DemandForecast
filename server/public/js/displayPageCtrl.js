angular.module('displayPageCtrl', []).controller('displayPageCtrl', function($scope,$http,toaster) {
        $scope.userViews = {};
        $scope.tableMeta = {};
        $scope.showViewList = true;
        $scope.title = "我的工作表";
        $scope.showTree = false;
        $scope.showDataGrid=false;
        $scope.data = {ready:false};
        $scope.gridOptions = {};
        $scope.gridOptions.columnDefs = [];
        $scope.msg = {};

        $scope.changeList = {};
        $scope.changeKeys = [];

        $scope.getTree = function(viewName){
            switchUI(viewName);
            userView = JSON.parse($scope.userViews[viewName]);
            $scope.showViewList = false;
            sortDimensions(userView.dimensions);
            buildDimensionTreeData(userView.dimensions,vm.dimensionTreeData,[]);
            buildMeasureTreeData(userView.series,vm.measureTreeData,[]);
        };

        $scope.before_openCB = function(node,selected){
            var curr_node =  selected.node;
            var infos = curr_node.id.split("_");
            if(infos.length > 1 && curr_node.children.length == 1){
                var curr_level = infos[1];
                var curr_filter = infos[0];
                var all_levels = infos[2].split(",");
                if(all_levels.indexOf(curr_level) +1 < all_levels.length){
                    var next_level = all_levels[all_levels.indexOf(curr_level)+1];
                    var filter = mergeFilters(userView.filters,curr_level,curr_filter);

                    var url ='/data/tables/YumSalesForecast/distinctValues?dimList=' + next_level + '&' + filter + "&dimType=" + curr_node.id + "&dimLevel=" + next_level + "&Levels=" + all_levels;
                    $http.get(url).then(function getSuccess(response) {
                        var dimType = response.data[response.data.length-1];
                        var dimLevel = response.data[response.data.length-2];
                        var levels = response.data[response.data.length-3];
                        for(var i = response.data.length-4; i >=0; i-- ){
                        var curr_id = response.data[i] +"_" + dimLevel+ "_" + levels;
                            if(i == response.data.length-4){
                                vm.dimensionTreeData.push({ id:curr_id, parent:dimType, text: response.data[i], state: { opened: false },li_attr :{class: "jstree-last"} });
                            } else {
                                vm.dimensionTreeData.push({ id:curr_id, parent:dimType, text: response.data[i], state: { opened: false }});
                            }
                            vm.dimensionTreeData.push({ id: "placeholder_" + response.data[i] , parent:curr_id, text: "", state: { opened : false }, li_attr :{class: "hidden"} });
                        }

                    }, function getError(response) {
                    toaster.pop('error', 'get tables error', 'get /data/tebles error ');

                    });

                }
            }
        };


        $scope.gridOptions.onRegisterApi = function(gridApi){
          //set gridApi on scope
        $scope.gridApi = gridApi;
        // gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
        gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
             var currChange ={};
             currChange.key = rowEntity.$$hashKey+ "_" + colDef.name;
             if($scope.changeKeys.indexOf(currChange.key) > -1) {
                 $scope.changeList[currChange.key].time = Math.floor(Date.now() / 1000);
                 $scope.changeList[currChange.key].newValue = newValue;
                 $scope.changeList[currChange.key].oldValue = oldValue;
             }else{
                 currChange.time = Math.floor(Date.now() / 1000);
                 currChange.column = colDef.name;
                 currChange.newValue = newValue;
                 currChange.oldValue = oldValue;
                 currChange.schema = $scope.gridOptions.columnDefs;
                 aaa = $scope.gridApi;
                 $scope.changeKeys.push(currChange.key);
                 $scope.changeList[currChange.key] = currChange;

             }
             changeList =  $scope.changeList;
             $scope.$apply();
            });
        };
        $scope.goToCreatePage = function(){
             window.location.href="/home/createUserView.html";
        }

        $scope.returnToList = function(){
            switchUI();
            location = window.location.href;
        }

        $scope.save = function(){
            if($scope.changeKeys.length == 0){
                alert("No change will be submitted");
            }else{
                //$http.post to backend
            }

        }


        $scope.query = function () {
         $scope.gridOptions.columnDefs=[];
          var queryUrl = "/data/tables/YumSalesForecast/query?";
            var treeInstance = vm.treeInstanceDimensions.jstree(true);
            var selectedDimensions = vm.treeInstanceDimensions.jstree(true).get_selected();
            for(var i = 0; i < selectedDimensions.length;i++){
                var dimension = selectedDimensions[i];
                if(dimension.indexOf("placeholder_")>-1){
                    selectedDimensions.splice(i,1);
                }
            }
            var selectedMeasures = vm.treeInstanceMeasures.jstree(true).get_selected();

            var dimlist = "";
            var filter ="";
            var measureList = "";
            filter_infos = {};
            $scope.gridOptions.columnDefs.push({name: "SalesDate"});
            for(var i=0; i < selectedDimensions.length;i++){
                 selected_node = treeInstance.get_node(selectedDimensions[i]);
                 if(selected_node.id.indexOf("_") < 0){
                    continue;
                 }

                 if (selected_node.parent.indexOf("_") > -1 && treeInstance.get_node(selected_node.parent).state.selected) continue;
                 infos = selected_node.id.split("_");
                 dimension_name = infos[0];
                 dimension_level = infos[1];
                 level_struc = infos[2].split(",");

                 dimension_path =  level_struc.slice(0,level_struc.indexOf(dimension_level)+1);
                 for(var j = 0; j < dimension_path.length;j++){
                     var index = dimlist.indexOf(dimension_path[j]);

                     var pre = index -1 ;

                    if( index > -1 && ((pre >=0 && dimlist[pre] == ",") || pre < 0)) continue;


                    dimlist = dimlist + dimension_path[j] + ",";
                   var gridOption = {name : dimension_path[j]};
                   $scope.gridOptions.columnDefs.push(gridOption);
                 }

                 if(filter_infos[dimension_level] == undefined){
                    filter_infos[dimension_level] =[];
                    filter_infos[dimension_level].push(dimension_name);
                 }else{
                    filter_infos[dimension_level].push(dimension_name);
                 }
         }

            dimlist = dimlist.substring(0,dimlist.length-1);

            for(var i = 0; i < selectedMeasures.length; i++) {
               measureList = measureList + selectedMeasures[i] + ",";
               var gridOption = {name : selectedMeasures[i]};
               $scope.gridOptions.columnDefs.push(gridOption);

            }


            measureList = measureList.substring(0,measureList.length-1);

            var tree_filters ={};
            angular.copy(userView.filters, tree_filters);

             for(var prop in filter_infos){
               if(tree_filters[prop]!=undefined){
                    for(var i = 0; i < filter_infos[prop].length; i++){
                        tree_filters[prop].push(filter_infos[prop][i]);
                    }
               }else{
                    tree_filters[prop]=[];
                    for(var i = 0; i < filter_infos[prop].length; i++){
                        tree_filters[prop].push(filter_infos[prop][i]);
                    }
               }
            }
            var filterString = getFilterString(tree_filters);
            filterString  = filterString.slice(0,filterString.length-1);
            var timeString = " and (SalesDate >= " +  "'" + userView.From + "'" + " and SalesDate <= " +  "'" + userView.To + "'" + " )";
            filterString  = filterString + timeString+ ")";
            console.log("filter=" + filterString);

            url = "/data/tables/YumSalesForecast/query?dimList=" + dimlist + "&measureList=" + measureList + "&"+ filterString + "&timeGrain="  + userView.timeGrain ;
            console.log(url);
            $http.get(url).then(function getSuccess(response)  {

                for(i = 0; i < response.data.length; i++){
                  response.data[i].registered = new Date(response.data[i].registered);
                }

                $scope.gridOptions.data = response.data;

            });
            $scope.data.ready = true;

        }


        userView={};
        vm = this;
        vm.dimensionTreeData = [];
        vm.measureTreeData = [];
        vm.treeConfig = {
                  core: {
                      multiple: true,
                      animation: true,
                      error: function (error) {
                          $log.error('treeCtrl: error from js tree - ' + angular.toJson(error));
                      },
                      children: true ,
                      check_callback: true,
                      themes: {"icons" : false},
                      worker: true
                  },

                  multiple: true,

                  plugins: [ 'checkbox','changed']
        };

        var url = '/data/users/123321/userViews';
        var urlMeta = '/data/tables/YumSalesForecast';
        var getDimensionLevels = function(dimension,tableMeta){
               for(var i = 0; i <tableMeta.dimensions.length; i++){
                        if(tableMeta.dimensions[i].name == dimension){
                            return tableMeta.dimensions[i].levels;
                    }
               }
               return null;
        }

        var sortDimensions = function(dimensions){
            for (var dimension in dimensions) {
                if (dimensions.hasOwnProperty(dimension)) {
                    var levels = getDimensionLevels(dimension, $scope.tableMeta);
                    dimensions[dimension].sort(function(a, b){
                         return levels.indexOf(a) - levels.indexOf(b);
                    });
                }
            }
        }

        var getFilterString = function(filters){
            var filterString = "filter=(";
            var filterStringParts = [];
            for(var prop in filters){
                if (filters.hasOwnProperty(prop)){
                    var tmp ="(";
                    for(var i = 0; i < filters[prop].length; i++){
                        tmp =  tmp + prop + "=" + '"'+  filters[prop][i] + '"' + " or "
                    }
                    tmp = tmp.slice(0,tmp.length-4);// remove " or "
                    tmp = tmp + ")";
                    filterStringParts.push(tmp);
                }
            }

            for(var i = 0; i < filterStringParts.length; i++){
                filterString = filterString + filterStringParts[i] +  (i == filterStringParts.length-1? "" : " and ");
            }
            filterString = filterString + ")";
            return filterString;
        }


        var buildDimensionTreeData = function(data,treeData,newData){
            for(var prop in data){
              if (data.hasOwnProperty(prop)){

                    newData.push({ id:prop, parent:'#', text: prop, state: { opened: false }});

                    var dim = data[prop][0];
                    var filters = getFilterString(userView.filters);
                    //get this shitty code because we don't know (KFC belong to Category, and what its next level is when user clicked on the node and loading children nodes)
                    var url =  "/data/tables/YumSalesForecast/distinctValues?dimList=" + dim + '&' + "dimType=" + prop + '&' + filters + "&Levels=" + data[prop] + "&dimLevel=" + dim;
                    $http.get(url).then(function getSuccess(response) {
                        for(var i = 0; i < response.data.length-3;i++){
                            var dimType = response.data[response.data.length-1];
                            var dimLevel = response.data[response.data.length-2];
                            var levels = response.data[response.data.length-3];
                            var curr_id = response.data[i] +"_" + dimLevel+ "_" + levels;
                            newData.push({ id:curr_id, parent:response.data[response.data.length-1], text: response.data[i], state: { opened: false }});
                            newData.push({ id: "placeholder_" + curr_id , parent:curr_id, text: "", state: { opened : false }, li_attr :{class: "hidden"} });
                        }
                       angular.copy(newData,treeData);

                    }, function getError(response) {
                      toaster.pop('error', 'get tables error', 'get /data/tebles error ');
                    });
              }
               //newData=[]; //try to get rig of wierd cache issue;
            }
        }



        var buildMeasureTreeData = function(data,treeData,newData){
            for(var i = 0; i < data.length;i++){
                 newData.push({ id:data[i].name, parent:'#', text: data[i].name , state: { opened: false }});
            }
            angular.copy(newData,treeData);
        }

        var mergeFilters = function(filters,curr_level,curr_filter){
            if(filters[curr_level] == undefined){
                filters[curr_level] =[];
                filters[curr_level].push(curr_filter);
            }
            return getFilterString(filters);

        }

        var switchUI = function(viewName){
            $scope.title = viewName;
            $scope.showViewList = !$scope.showViewList;
            $scope.showTree = !$scope.showTree;
            $scope.showDataGrid =!$scope.showDataGrid;
        }


        $http.get(url).then(function getSuccess(response) {
                toaster.pop('success', 'get userViews', 'get /data/tables ok ');
                $scope.userViews = response.data;
                a = response;


        }, function getError(response) {
            toaster.pop('error', 'get tables error', 'get /data/tebles error ');
        });

        //get table meta data
        $http.get(urlMeta).then(function getSuccess(response) {
                $scope.tableMeta = response.data;
        }, function getError(response) {
            toaster.pop('error', 'get tables error', 'get /data/tebles error ');
        });







});