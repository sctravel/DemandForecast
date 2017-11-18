angular.module('setupPageCtrl', []).controller('setupPageCtrl', function($scope,$http,toaster) {
    $scope.view = {};
    $scope.measures = [];
    $scope.toMoveMeasures = [];
    $scope.selectedMeasures=[];
    $scope.view["dimensions"] = {};
    $scope.selectedDimensions = [];
    $scope.availableDimensions = [];
    $scope.filters = {};
    $scope.timeRangePos=[1,2,3,4,5];
    $scope.timeRangeNeg=[-1,-2,-3,-4,-5];


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
                for(var i = 0; i < response.data.length-3; i++){
                   var dimension = {id : i, name : response.data[i]};
                   $scope.availableDimensions.push(dimension);

                }
         })

       }

    }

    $scope.addFilters = function(){

       var filterDimension = vm.filterTree.jstree(true).get_selected();
       if( $scope.filters[filterDimension] == undefined){
             $scope.filters[filterDimension] = [];
       }
       for( var i = 0; i < $scope.availableDimensionsSelected.length; i++){
                  $scope.filters[filterDimension].push($scope.availableDimensionsSelected[i].name[0]);
       }

//       if($scope.availableDimensionsSelected.length >1){
//            filters = filters + filterDimension + " in " + "(";
//            for( var i = 0; i < $scope.availableDimensionsSelected.length; i++){
//               filters = filters +  $scope.availableDimensionsSelected[i].name + ",";
//            }
//            filters = filters.substring(0,filters.length-1);
//            filters = filters + ")";
//       }else {
//            filters = vm.filterTree.jstree(true).get_selected() + " = " + $scope.availableDimensionsSelected[0].name;
//       }
    }

    $scope.save = function(){
    console.log("save");
        $scope.view["series"] =  $scope.selectedMeasures;
        dimensionsTreeInstance = vm.dimensionsTree.jstree(true);
        treeSelected = dimensionsTreeInstance.get_selected();
        for(var i = 0; i < treeSelected.length; i++){
           if(dimensionsTreeInstance.get_node(treeSelected[i]).parent == "#") {
                 if($scope.view.dimensions[treeSelected[i]] ==undefined){
                    $scope.view.dimensions[treeSelected[i]] = [];
                 }else{
                    continue;
                 };
           }else{
                if( $scope.view.dimensions[dimensionsTreeInstance.get_node(treeSelected[i]).parent] == undefined) {
                    $scope.view.dimensions[dimensionsTreeInstance.get_node(treeSelected[i]).parent] =[];
                }

                $scope.view.dimensions[dimensionsTreeInstance.get_node(treeSelected[i]).parent].push(treeSelected[i]);
           }
        }
        $scope.view.filters = $scope.filters;
        var userId = 123321;
        var url = '/data/users' + '/'+ userId + '/userViews' + '/' + $scope.view.name;
        console.log(url);
        var data = JSON.stringify($scope.view);
        $http.post(url, data).then(function(response) {
           alert("userview successfully saved ");
          }, function(response) {
           alert("job failed");
        });



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