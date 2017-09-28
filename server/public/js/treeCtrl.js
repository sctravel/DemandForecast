(function (angular) {


    //// JavaScript Code ////
    function treeCtrl($scope,$log, $timeout,$http, toaster) {
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

        this.query = function () {
         
            var selectedDimensions = vm.treeInstanceDimensions.jstree(true).get_selected();
            for(var i = 0; i < selectedDimensions.length;i++){
                var dimension = selectedDimensions[i];
                if(dimension.indexOf("placeholder_")>-1){
                    selectedDimensions.splice(i,1);
                }
            }
            var selectedMeasures = vm.treeInstanceMeasures.jstree(true).get_selected();
            console.dir(selectedDimensions);
            console.dir(selectedMeasures);
        }
    }

    //// Angular Code ////

    angular.module('ngJsTreeDemo').controller('treeCtrl', treeCtrl);

})(angular);