angular.module('displayPageCtrl', []).controller('displayPageCtrl', function($scope,$http,toaster) {
    $scope.userViews = {};

    var url = '/data/users/123321/userViews';
    $http.get(url).then(function getSuccess(response) {
            console.log("in");
            toaster.pop('success', 'get tables', 'get /data/tables ok ');
            $scope.userViews = response.data;
                        a = response;
    }, function getError(response) {
        toaster.pop('error', 'get tables error', 'get /data/tebles error ');
    });
});