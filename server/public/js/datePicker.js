
(function (angular) {
        //// JavaScript Code ////
       angular.module("date", [])
           .directive("datepicker", function () {
           return {
               restrict: "A",
               link: function (scope, el, attr) {
                   el.datepicker({
                                   dateFormat: 'yy-mm-dd'
                               });
               }
           };
       })
           .controller("dateCtrl", function ($scope) {
       });
})(angular);