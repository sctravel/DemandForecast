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
    $scope.timeGrain = "";
    $scope.from="";
    $scope.to="";



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
                if( $scope.availableDimensions.length > 0){
                     $scope.availableDimensions =[];
                }
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
        console.log("Saving User views");
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
        $scope.view.timeGrain = $scope.timeGrain;

        if($scope.view.relativeOrFix == "relativeToday"){
            if( $scope.view.timeGrain == "daily"){

                var f = new Date();
                f.setDate(f.getDate() + ($scope.view.From - 0));
                $scope.view.From= formatDate(f);
                var t = new Date();
                t.setDate(t.getDate() + ($scope.view.To - 0));
                $scope.view.To= formatDate(t);

            }else if( $scope.view.timeGrain == "weekly"){

                var f = new Date();
                f.setDate(f.getDate() + ($scope.view.From * 7));
                $scope.view.From= formatDate(f);
                var t = new Date();

                t.setDate(t.getDate() + ($scope.view.To * 7));
                $scope.view.To= formatDate(t);
            }else if( $scope.view.timeGrain == "monthly"){
                var f = new Date();
                f.setMonth(f.getMonth() + ($scope.view.From - 0));
                $scope.view.From= formatDate(f);
                var t = new Date();
                t.setMonth(t.getMonth() + ($scope.view.To - 0));
                $scope.view.To= formatDate(t);

            }else if($scope.view.timeGrain == "quarterly"){
                  var f = new Date();
                f.setMonth(f.getMonth() + ($scope.view.From * 3));
                $scope.view.From= formatDate(f);
                var t = new Date();
                t.setMonth(t.getMonth() + ($scope.view.To * 3));
                $scope.view.To= formatDate(t);
            }else if($scope.view.timeGrain == "yearly"){
                   var f = new Date();

                    f.setYear(f.getFullYear() + ($scope.view.From -0));
                    $scope.view.From= formatDate(f);
                    var t = new Date();
                    t.setYear(t.getFullYear() + ($scope.view.To -0));
                    $scope.view.To= formatDate(t);
            }

        }
        var data = JSON.stringify($scope.view);
        $http.post(url, data).then(function(response) {
           alert("userview successfully saved ");
           window.location.href="/home/listUserView.html";
          }, function(response) {
           alert("job failed");
        });
    }


    formatDate = function(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    vm = this;
    vm.dimensionTreeData = [];
    vm.filterTreeData = [];
    function reloadDimensionTree(){
         vm.treeConfig.version ++;
    }
    function reloadFilterTree(){
             vm.filterTreeConfig.version ++;
        }
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
            console.log("Successfully get all tables before");
            var i = 1;
            response.data[0].measureColumnNames.forEach(function(measure){
                $scope.measures.push({ id: i,  name: measure});
                console.log("Measure:"+measure)
                i++;
            });
            response.data[0].dimensions.forEach(function(dimension){


                  vm.dimensionTreeData.push({ id:dimension.name, parent:'#', text: dimension.displayName, state: { opened: true }});
                    for(var i = 0; i <dimension.levels.length;i++ ){
                    vm.dimensionTreeData.push({ id:dimension.levels[i], parent:dimension.name, text: dimension.levels[i], state: { opened: false }});

                  }

                  reloadDimensionTree();


                vm.filterTreeData.push({ id:dimension.name, parent:'#', text: dimension.displayName, state: { opened: true, disabled: true }});
                for(var i = 0; i <dimension.levels.length;i++ ){
                    vm.filterTreeData.push({ id:dimension.levels[i], parent:dimension.name, text: dimension.levels[i], state: { opened: false }});
                }
                reloadFilterTree();
            })

    }, function getError(response) {
        toaster.pop('error', 'get tables error', 'get /data/tebles error ');
    });
    console.log("end of setupPageCtrl")
});