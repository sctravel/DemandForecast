angular.module('seriesSelectorCtrl', []).controller('seriesSelectorCtrl', function($scope,$http,toaster) {


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


           $scope.selectedMeasures = [];
           $scope.measures = [];
           $http.get('/data/tables').then(function getSuccess(response) {
                          toaster.pop('success', 'get tables', 'get /data/tebles ok ');
                           var i = 1;

                          response.data[0].measureColumnNames.forEach(function(measure){

                             $scope.measures.push({ id: i,  name: measure});
                             i++;
                          })
                          a = $scope.measures;
                      }, function getError(response) {
                          toaster.pop('error', 'get tables error', 'get /data/tebles error ');

                      });

  });