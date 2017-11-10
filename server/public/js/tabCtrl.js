(function (angular) {
        //// JavaScript Code ////
        function tabCtrl() {
                 this.tab = 1;
                 this.setTab = function (tabId) {
                     this.tab = tabId;
                 };

                 this.isSet = function (tabId) {
                     return this.tab === tabId;
                 };
        }
        //// Angular Code ////
        angular.module('tabCtrl',[]).controller("tabCtrl",tabCtrl);
})(angular);

//(function () {
//    var app = angular.module('tabCtrl', []);
//
//    app.controller('tabCtrl', function () {
//        this.tab = 1;
//
//        this.setTab = function (tabId) {
//            this.tab = tabId;
//        };
//
//        this.isSet = function (tabId) {
//            return this.tab === tabId;
//        };
//    });
//})();