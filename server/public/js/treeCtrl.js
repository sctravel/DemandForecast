(function (angular) {


    //// JavaScript Code ////
    function treeCtrl($scope,$log, $timeout,$http, toaster,$q) {
      $scope.data = {ready:false};
      $scope.gridOptions = {};
      $scope.gridOptions.columnDefs = [];
      $scope.msg = {};

      $scope.changeList = {};
      $scope.changeKeys = [];

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
                         $scope.changeKeys.push(currChange.key);
                         $scope.changeList[currChange.key] = currChange;

                     }
                     $scope.$apply();
                  });
                };

   $scope.save = function(){
        if($scope.changeKeys.length == 0){
            alert("No change will be submitted");
        }else{
            //$http.post to backend
        }

   }








//   $scope.saveRow = function() {
     // create a fake promise - normally you'd use the promise returned by $http or $resource
//     console.log("in save");
//
//     var promise = $q.defer();
//     $scope.gridApi.rowEdit.setSavePromise( rowEntity, promise.promise );
//
     // fake a delay of 3 seconds whilst the save occurs, return error if gender is "male"
//     $interval( function() {
//       if (rowEntity.gender === 'male' ){
//         promise.reject();
//       } else {
//         promise.resolve();
//       }
//     }, 3000, 1);
//   };
         vm = this;
        var newId = 1;
        vm.ignoreChanges = false;
        vm.newNode = {};
        vm.originalData = [];
        vm.measures = [];
        vm.treeData = [];
        vm.measureData = [];
        angular.copy(vm.originalData, vm.treeData);
        angular.copy(vm.measures, vm.measureData);
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
            
            plugins: [ 'checkbox']
        };
        var init = function () {
            $http.get('/data/tables').then(function getSuccess(response) {
               toaster.pop('success', 'get tables', 'get /data/tebles ok ');
               var dimensions = response.data[0].dimensions;

               for(var index in dimensions){
                   var levels="";
                   for(var i = 0; i < dimensions[index].levels.length;i++){

                        levels = levels + ( i==0 ?"":",")  + dimensions[index].levels[i];
                   }

                   vm.treeData.push({ id: dimensions[index].name, parent: '#', text: dimensions[index].name, state: { opened: false } });
                   var levelUrl = '/data/tables/YumSalesForecast/distinctValues?dimList=' + dimensions[index].levels[0];
                   levelUrl = levelUrl + "&dimType=" + dimensions[index].name + "&dimLevel=" + dimensions[index].levels[0] + "&Levels=" + levels ;
                   console.log(levelUrl);

                   $http.get(levelUrl).then(function getSuccess(response) {
                               var dimType = response.data[response.data.length-1];
                               var dimLevel = response.data[response.data.length-2];
                               var levels = response.data[response.data.length-3];

                               for(var i = 0; i <response.data.length-3;i++ ){
                                   var curr_id = response.data[i] +"_" + dimLevel+ "_" + levels;
                                   vm.treeData.push({ id:curr_id, parent:dimType, text: response.data[i], state: { opened: false } });
                                   vm.treeData.push({ id: "placeholder_" + response.data[i] , parent:curr_id, text: "", state: { opened : false }, li_attr :{class: "hidden"} });
                               }

                               }, function getError(response) {

                                   toaster.pop('error', 'get tables error', 'get /data/tebles error ');

                               });
                }
               response.data[0].measureColumnNames.forEach(function(measure){
                   vm.measureData.push({ id: measure, parent: '#', text: measure, state: { opened: false } });
               })
           }, function getError(response) {
               toaster.pop('error', 'get tables error', 'get /data/tebles error ');

           });

        };
        init();
        $scope.before_openCB = function(node,selected){
                var curr_node =  selected.node;
                var infos = curr_node.id.split("_");
                if(infos.length > 1 && curr_node.children.length == 1){
                //    curr_node.children.splice(-1,1);
                    var curr_level = infos[1];
                    var curr_filter = infos[0];
                    var all_levels = infos[2].split(",");
                    if(all_levels.indexOf(curr_level) +1 < all_levels.length){
                        var next_level = all_levels[all_levels.indexOf(curr_level)+1];
                                                    var url ='/data/tables/YumSalesForecast/distinctValues?dimList=' + next_level + '&filter=' + curr_level + "='" + curr_filter + "'" + "&dimType=" + curr_node.id + "&dimLevel=" + next_level + "&Levels=" + all_levels;
                                                    $http.get(url).then(function getSuccess(response) {
                                                                                   var dimType = response.data[response.data.length-1];
                                                                                   var dimLevel = response.data[response.data.length-2];
                                                                                   var levels = response.data[response.data.length-3];
                                                                                   for(var i = response.data.length-4; i >=0; i-- ){
                                                                                       var curr_id = response.data[i] +"_" + dimLevel+ "_" + levels;
                                                                                       if(i == response.data.length-4){
                                                                                            vm.treeData.push({ id:curr_id, parent:dimType, text: response.data[i], state: { opened: false },li_attr :{class: "jstree-last"} });
                                                                                       }else{
                                                                                            vm.treeData.push({ id:curr_id, parent:dimType, text: response.data[i], state: { opened: false }});
                                                                                       }

                                                                                       vm.treeData.push({ id: "placeholder_" + response.data[i] , parent:curr_id, text: "", state: { opened : false }, li_attr :{class: "hidden"} });
                                                                                   }

                                                                           }, function getError(response) {

                                                                               toaster.pop('error', 'get tables error', 'get /data/tebles error ');

                                                                           });

                    }



                }
        }
        $scope.open_nodeCB = function(node,selected){
        }

        $scope.query = function () {
         console.log("in");
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
            var filter_infos = {};
            for(var i=0; i < selectedDimensions.length;i++){
                 selected_node = treeInstance.get_node(selectedDimensions[i]);
                 if(selected_node.id.indexOf("_") < 0){
                    continue;
                 }
                 infos = selected_node.id.split("_");
                 dimension_name = infos[0];
                 dimension_level = infos[1];
                 level_struc = infos[2].split(",");
                 dimension_path =  level_struc.slice(0,level_struc.indexOf(dimension_level)+1);
                 for(var j = 0; j < dimension_path.length;j++){
                    if(dimlist.indexOf(dimension_path[j]) > -1) continue;
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

             for(var prop in filter_infos){

                            var tmp = "";
                            var vals = filter_infos[prop];

                            for(var v in vals){
                                tmp = tmp + prop + "=" + '"' + vals[v] + '"' + " or ";
                            }
                            tmp= tmp.substring(0,tmp.length-4);
                            tmp = "(" + tmp + ")";
                            filter = filter + tmp + " and ";
                        }
            filter= filter.substring(0,filter.length-5);

            url = "/data/tables/YumSalesForecast/query?dimList=" + dimlist + "&measureList=" + measureList + "&filter=" + filter;
            console.log(url);
            $http.get(url).then(function getSuccess(response)  {
             console.dir(response.data);
                for(i = 0; i < response.data.length; i++){
                  response.data[i].registered = new Date(response.data[i].registered);
                }

                $scope.gridOptions.data = response.data;

            });
            $scope.data.ready = true;

        }
    }

    //// Angular Code ////

    angular.module('ngJsTreeDemo').controller('treeCtrl', treeCtrl);

})(angular);