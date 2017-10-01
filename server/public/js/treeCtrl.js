(function (angular) {


    //// JavaScript Code ////
    function treeCtrl($scope,$log, $timeout,$http, toaster,$q) {
      $scope.data = {ready:false};
      $scope.gridOptions = {};
      $scope.gridOptions.columnDefs = [
            { name:'id'  },
            { name:'name',  pinnedLeft:true },
            { name:'age',  pinnedRight:true  },
            { name:'address.street'  },
            { name:'address.city'},
            { name:'address.state' },
            { name:'address.zip' },
            { name:'company' },
            { name:'email' },
            { name:'phone' },
            { name:'about' },
            { name:'friends[0].name', displayName:'1st friend'},
            { name:'friends[1].name', displayName:'2nd friend' },
            { name:'friends[2].name', displayName:'3rd friend' },
          ];

        $http.get('https://cdn.rawgit.com/angular-ui/ui-grid.info/gh-pages/data/500_complex.json').then(function getSuccess(response)  {
            for(i = 0; i < response.data.length; i++){
              response.data[i].registered = new Date(response.data[i].registered);
            }

            $scope.gridOptions.data = response.data;

        });
         $scope.msg = {};

   $scope.gridOptions.onRegisterApi = function(gridApi){
                  //set gridApi on scope
                  $scope.gridApi = gridApi;
                  gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
                  gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
                    $scope.msg.lastCellEdited = 'edited row id:' + rowEntity.id + ' Column:' + colDef.name + ' newValue:' + newValue + ' oldValue:' + oldValue ;
                    $scope.$apply();
                  });
                };
   $scope.saveRow = function( rowEntity ) {
     // create a fake promise - normally you'd use the promise returned by $http or $resource

     var promise = $q.defer();
     $scope.gridApi.rowEdit.setSavePromise( rowEntity, promise.promise );

     // fake a delay of 3 seconds whilst the save occurs, return error if gender is "male"
//     $interval( function() {
//       if (rowEntity.gender === 'male' ){
//         promise.reject();
//       } else {
//         promise.resolve();
//       }
//     }, 3000, 1);
   };
        var vm = this;
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
            var selectedDimensions = vm.treeInstanceDimensions.jstree(true).get_selected();
            for(var i = 0; i < selectedDimensions.length;i++){
                var dimension = selectedDimensions[i];
                if(dimension.indexOf("placeholder_")>-1){
                    selectedDimensions.splice(i,1);
                }
            }
            var selectedMeasures = vm.treeInstanceMeasures.jstree(true).get_selected();
            $scope.data.ready = true;
            console.dir(selectedDimensions);
            console.dir(selectedMeasures);
        }
    }

    //// Angular Code ////

    angular.module('ngJsTreeDemo').controller('treeCtrl', treeCtrl);

})(angular);