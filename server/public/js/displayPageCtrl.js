angular.module('displayPageCtrl', []).controller('displayPageCtrl', function($scope,$http,toaster) {
        $scope.userViews = {};
        $scope.tableMeta = {};
        $scope.showViewList = true;
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
                    var level = data[prop][0];
                    var filters = getFilterString(userView.filters);
                    var url =  "/data/tables/YumSalesForecast/distinctValues?dimList=" + level + '&' + "dimType=" + prop + '&' + filters ;
                    $http.get(url).then(function getSuccess(response) {
                        for(var i = 0; i < response.data.length-3;i++){
                            newData.push({ id:response.data[i], parent:response.data[response.data.length-1], text: response.data[i], state: { opened: false }});
                        }
                       angular.copy(newData,treeData);
                    }, function getError(response) {
                      toaster.pop('error', 'get tables error', 'get /data/tebles error ');
                    });
              }
            }
        }

        var buildMeasureTreeData = function(data,treeData,newData){
            for(var i = 0; i < data.length;i++){
                 newData.push({ id:data[i].id, parent:'#', text: data[i].name , state: { opened: false }});
            }
            angular.copy(newData,treeData);
        }


        $http.get(url).then(function getSuccess(response) {
                console.log("in");
                toaster.pop('success', 'get tables', 'get /data/tables ok ');
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




        $scope.getTree = function(viewName){
             userView = JSON.parse($scope.userViews[viewName]);
             $scope.showViewList = false;
             sortDimensions(userView.dimensions);
             buildDimensionTreeData(userView.dimensions,vm.dimensionTreeData,[]);
             buildMeasureTreeData(userView.series,vm.measureTreeData,[]);


        };
});