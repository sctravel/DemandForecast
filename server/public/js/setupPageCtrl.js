angular.module('setupPageCtrl', []).controller('setupPageCtrl', function($scope,$http,toaster) {

    $scope.measures = [];
    $scope.selectedMeasures = [];
    $scope.dimensions = [];
    $scope.selectedDimensions = [];
    $scope.availableDimensions = [];


    $scope.moveItem = function(item, from, to) {
        console.log('Move item   Item: '+item+' From:: '+from+' To:: '+to);
        //Here from is returned as blank and to as undefined
        var idx=from.indexOf(item);
        if (idx != -1) {
            from.splice(idx, 1);
            to.push(item);
        }
    };
    $scope.moveAll = function(from, to) {

        console.log('Move all  From:: '+from+' To:: '+to);
        //Here from is returned as blank and to as undefined

        angular.forEach(from, function(item) {
            to.push(item);
        });
        from.length = 0;
    };

    $scope.selection_nodeCB = function(e, data){
       var action = data.action;
       var level = data.node.id;

       if(action.slice(0,6) == 'select' ){
         $scope.availableDimensions = [];

        var levelUrl = '/data/tables/YumSalesForecast/distinctValues?dimList=' + level;
         $http.get(levelUrl).then(function getSuccess(response) {
         dataddd = response.data
                for(var i = 0; i < response.data.length-3; i++){
                   var dimension = {id : i, name : response.data[i]};
                   $scope.availableDimensions.push(dimension);

                }
                aaa =  $scope.availableDimensions;
         })

       }

    }



    vm = this;
    vm.dimensionTreeData = [];
    vm.filterTreeData = [];
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
    vm.filterTreeConfig = {
        core: {
            multiple: false,
            animation: true,
            error: function (error) {
                $log.error('treeCtrl: error from js tree - ' + angular.toJson(error));
            },
            children: true ,
            check_callback: true,
            themes: {"icons" : false},
            worker: true
        },


        plugins: [ 'checkbox','changed']
    };



    $http.get('/data/tables').then(function getSuccess(response) {
             console.log("in");
            toaster.pop('success', 'get tables', 'get /data/tables ok ');
            var i = 1;
            response.data[0].measureColumnNames.forEach(function(measure){
                $scope.measures.push({ id: i,  name: measure});
                i++;
            });
            response.data[0].dimensions.forEach(function(dimension){

                  vm.dimensionTreeData.push({ id:dimension.name, parent:'#', text: dimension.displayName, state: { opened: true }});
                  for(var i = 0; i <dimension.levels.length;i++ ){
                    vm.dimensionTreeData.push({ id:dimension.levels[i], parent:dimension.name, text: dimension.levels[i], state: { opened: false }});
                  }

                vm.filterTreeData.push({ id:dimension.name, parent:'#', text: dimension.displayName, state: { opened: true, disabled: true }});
                for(var i = 0; i <dimension.levels.length;i++ ){
                   vm.filterTreeData.push({ id:dimension.levels[i], parent:dimension.name, text: dimension.levels[i], state: { opened: false }});
                }
            })
                        a = response.data[0];
    }, function getError(response) {
        toaster.pop('error', 'get tables error', 'get /data/tebles error ');
    });
});