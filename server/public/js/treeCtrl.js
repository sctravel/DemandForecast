(function (angular) {
    'use strict';

    //// JavaScript Code ////
    function treeCtrl($log, $timeout,$http, toaster) {
        var vm = this;
        var newId = 1;
        vm.ignoreChanges = false;
        vm.newNode = {};
        vm.originalData = [     
            { id: 'location', parent: '#', text: '地域', state: { opened: false } },
            { id: 'liaoning', parent: 'location', text: '辽宁', state: { opened: false } },
            { id: 'shenyang',   parent: 'liaoning', text: '沈阳', state: { opened: false } },
            { id: 'dalian', parent: 'liaoning', text: '大连', state: { opened: false } },
            { id: 'anshan', parent: 'liaoning', text: '鞍山', state: { opened: false } },
            { id: 'zhejiang', parent: 'location', text: '浙江', state: { opened: false } },
            { id: 'hangzhou', parent: 'zhejiang', text: '杭州', state: { opened: false } },
            { id: 'ningbo', parent: 'zhejiang', text: '宁波', state: { opened: false } },
            { id: 'haining', parent: 'zhejiang', text: '海宁', state: { opened: false } }
        ];

        vm.measures = [
                { id: 'forcasting', parent: '#', text: '预测', state: { opened: false } },
                { id: 'sum', parent: '#', text: '总和', state: { opened: false } }

        ];


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
                check_callback: true,
                worker: true
            },
            types: {
                default: {
                    icon: 'glyphicon glyphicon-flash'
                },
                star: {
                    icon: 'glyphicon glyphicon-star'
                },
                cloud: {
                    icon: 'glyphicon glyphicon-cloud'
                }
            },
            multiple: true,
            
            plugins: ['types', 'checkbox']
        };
        var init = function () {
            $http.get('/data/tables', config).then(successCallback, errorCallback);
        };
        this.readyCB = function () {
            $timeout(function () {
                vm.ignoreChanges = false;
             //   toaster.pop('success', 'JS Tree Ready', 'Js Tree issued the ready event')
            });
        };
        this.query = function () {
         
            var selectedDimensions = vm.treeInstanceDimensions.jstree(true).get_selected();
            var selectedMeasures = vm.treeInstanceMeasures.jstree(true).get_selected();
            console.dir(selectedDimensions);
            console.dir(selectedMeasures);
        }
    }

    //// Angular Code ////

    angular.module('ngJsTreeDemo').controller('treeCtrl', treeCtrl);

})(angular);