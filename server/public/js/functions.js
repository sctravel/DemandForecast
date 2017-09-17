function submitJob() {

    var dataset = $('#dataset').val();
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var clusterNum = $('#clusterNum').val();
    var metrics = $('#metrics').val();
    var tcCluster1 = 0;
    var tcCluster2 = 0;
    var tcCluster3 = 0;
    var tcClusters = [];
    var EventRange = ["1 ~ 36", "37 ~ 247", "286 ~ 599"];
    
    $.getJSON("/Content/retentionchart.json", function (json) {
  
        var options = json;
        $("#retentionchart").Retention(options);
    });
    Highcharts.chart('clusteringchart', {

        chart: {
            type: 'bubble',
            plotBorderWidth: 1,
            zoomType: 'xy'
        },

        title: {
            text: 'User Behavior Clustering'
        },

        xAxis: {
            gridLineWidth: 1
        },

        yAxis: {
            startOnTick: false,
            endOnTick: false
        },

        series: [{
            name: "Cluster0",
            data: [
                [0, 0, 336835],
                [1, 1, 57736],
                [2, 1, 19244],
                [2, 2, 14849],
                [3, 1, 16347],
                [3, 2, 10962],
                [3, 3, 7388],
                [4, 1, 18877],
                [4, 2, 13863],
                [4, 3, 7016],
                [4, 4, 4896],
                [5, 1, 17158],
                [5, 2, 14065],
                [5, 3, 3436],
                [6, 1, 14613]],

            marker: {
                fillColor: {
                    radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, 'rgba(255,255,255,1)'],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[1]).setOpacity(1).get('rgba')]
                    ]
                }
            }
        },
        {
            name: "Cluster1",
            data: [
                [11, 9, 1291],
                [12, 7, 4377],
                [12, 8, 6309],
                [12, 9, 4282],
                [13, 4, 264],
                [13, 5, 8117],
                [13, 6, 14801],
                [13, 7, 14287],
                [13, 8, 12892],
                [13, 9, 11518],
                [14, 3, 7754],
                [14, 4, 22685],
                [14, 5, 21615],
                [14, 6, 20203],
                [14, 7, 18296],
                [14, 8, 16324],
                [14, 9, 14595],
                [15, 2, 18888],
                [15, 3, 23601],
                [15, 4, 26480],
                [15, 5, 28244],
                [15, 6, 28826],
                [15, 7, 29057],
                [15, 8, 29409],
                [15, 9, 1181],
                [16, 1, 9195],
                [16, 2, 14724],
                [16, 3, 18703],
                [16, 4, 21041],
                [16, 5, 21991],
                [16, 6, 21773],
                [16, 7, 20760],
                [16, 8, 19520],
                [17, 1, 10283],
                [17, 2, 17942],
                [17, 3, 24204],
                [17, 4, 28669],
                [17, 5, 31844],
                [17, 6, 33241],
                [17, 7, 33185],
                [17, 8, 32666],
                [18, 1, 14128],
                [18, 2, 27518],
                [18, 3, 39442],
                [18, 4, 52814],
                [18, 5, 67441],
                [18, 6, 83646],
                [18, 7, 101687],
                [18, 8, 122005]
            ],
            marker: {
                fillColor: {
                    radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, 'rgba(255,255,255,1)'],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[3]).setOpacity(1).get('rgba')]
                    ]
                }
            }
        },
        {
            name: "Cluster2",
            data: [
                [15, 15, 4547],
                [16, 15, 8076],
                [16, 16, 6205],
                [17, 15, 26648],
                [17, 16, 22526],
                [17, 17, 18591],
                [18, 15, 232484],
                [18, 16, 205349],
                [18, 17, 225077],
                [18, 18, 1941015]
            ],
            marker: {
                fillColor: {
                    radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, 'rgba(255,255,255,1)'],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(1).get('rgba')]
                    ]
                }
            }
        },
        {
            name: "Cluster3",
            data: [
                [5, 3, 5570],
                [5, 4, 4733],
                [5, 5, 3630],
                [6, 2, 13522],
                [6, 3, 9781],
                [6, 4, 6699],
                [6, 5, 3722],
                [6, 6, 3091],
                [7, 1, 16279],
                [7, 2, 15153],
                [7, 3, 11550],
                [7, 4, 8392],
                [7, 5, 6114],
                [7, 6, 3370],
                [7, 7, 3054],
                [8, 1, 12848],
                [8, 2, 14029],
                [8, 3, 11989],
                [8, 4, 9600],
                [8, 5, 8227],
                [8, 6, 8075],
                [8, 7, 3423],
                [8, 8, 2935],
                [9, 1, 9079],
                [9, 2, 10110],
                [9, 3, 8576],
                [9, 4, 6721],
                [9, 5, 5034],
                [9, 6, 3752],
                [9, 7, 3017],
                [9, 8, 2373],
                [9, 9, 2374],
                [10, 1, 9431],
                [10, 2, 11190],
                [10, 3, 9922],
                [10, 4, 7897],
                [10, 5, 6083],
                [10, 6, 4564],
                [10, 7, 3442],
                [10, 8, 2618],
                [10, 9, 2035],
                [10, 10, 2069],
                [11, 1, 11559],
                [11, 2, 14780],
                [11, 3, 14185],
                [11, 4, 12156],
                [11, 5, 9698],
                [11, 6, 7757],
                [11, 7, 6006],
                [11, 8, 3798],
                [11, 9, 1468],
                [12, 1, 11676],
                [12, 2, 16540],
                [12, 3, 16933],
                [12, 4, 15490],
                [12, 5, 13399],
                [12, 6, 11060],
                [12, 7, 4809],
                [12, 8, 754],
                [13, 1, 11105],
                [13, 2, 17008],
                [13, 3, 18793],
                [13, 4, 18479],
                [13, 5, 9518],
                [13, 6, 1170],
                [14, 1, 12381],
                [14, 2, 20163],
                [14, 3, 13957],
                [15, 1, 11801]
            ],
            marker: {
                fillColor: {
                    radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, 'rgba(255,255,255,1)'],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[9]).setOpacity(1).get('rgba')]
                    ]
                }
            }
        },
        {
            name: "Cluster4",
            data: [
                [11, 10, 2084],
                [11, 11, 2100],
                [12, 10, 3148],
                [12, 11, 2330],
                [12, 12, 2411],
                [13, 10, 5502],
                [13, 11, 3857],
                [13, 12, 2776],
                [13, 13, 2838],
                [14, 10, 12102],
                [14, 11, 7263],
                [14, 12, 5122],
                [14, 13, 3614],
                [14, 14, 3476],
                [15, 9, 29559],
                [15, 10, 34216],
                [15, 11, 31283],
                [15, 12, 15052],
                [15, 13, 9236],
                [15, 14, 5586],
                [16, 9, 18519],
                [16, 10, 18033],
                [16, 11, 17779],
                [16, 12, 17408],
                [16, 13, 15272],
                [16, 14, 13403],
                [17, 9, 32216],
                [17, 10, 32472],
                [17, 11, 33175],
                [17, 12, 33373],
                [17, 13, 32129],
                [17, 14, 29661],
                [18, 9, 157872],
                [18, 10, 229435],
                [18, 11, 334426],
                [18, 12, 335414],
                [18, 13, 250273],
                [18, 14, 233618]

            ],
            marker: {
                fillColor: {
                    radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, 'rgba(255,255,255,1)'],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[4]).setOpacity(1).get('rgba')]
                    ]
                }
            }
        }
       ]

    });

    Highcharts.chart('callsbehaviorchart', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'User Behavior Chart'
        },
        xAxis: {
            categories: ['Call']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total Duration of Calls (Seconds)'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'stacked'
            }
        },
        series: [
            {
                name: 'Cluster2',
                data: [1411],
                color: '#90ed7d'
            },
            {
                name: 'Cluster3',
                data: [727],
                color: "#91e8e1"
            },
            {
                name: 'Cluster0',
                data: [18],
                color: '#434348'
            }, {
                name: 'Cluster1',
                data: [26],
                color: "#f7a35c"
            }, {
                name: 'Cluster4',
                data: [231],
                color: '#8085e9'
            }]
    });

    Highcharts.chart('msgbehaviorchart', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'User Behavior Chart'
        },
        xAxis: {
            categories: ['Message']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total Number of Messages'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'stacked'
            }
        },
        series: [
              {
                  name: 'Cluster4',
                  data: [13],
                  color: '#8085e9'
              },
               {
                   name: 'Cluster2',
                   data: [17],
                   color: '#90ed7d'
               },
                {
                    name: 'Cluster1',
                    data: [10],
                    color: "#f7a35c"
                },
            {
                name: 'Cluster3',
                data: [2],
                color: "#91e8e1"
            },
           
          
            {
                name: 'Cluster0',
                data: [11],
                color: '#434348'
            }
          
              ]
    });
   
//get pie chart json
    //$.getJSON("/content/piechart.json", function (json) {
    //    var pie_data = [];
    //    for (var i = 0; i < json.length; i++) {
    //        if (json[i].Cluster == 0) {
    //            tcCluster1 = tcCluster1 + json[i].UserCount;
    //            tcClusters[0] = tcCluster1;
    //        }
    //        if (json[i].Cluster == 1) {
    //            tcCluster2 = tcCluster2 + json[i].UserCount;
    //            tcClusters[1] = tcCluster2;
    //        }
    //        if (json[i].Cluster == 2) {
    //            tcCluster3 = tcCluster3 + json[i].UserCount;
    //            tcClusters[2] = tcCluster3;
    //        }
    //    }
    //    for (var i = 0; i < json.length; i++) {
    //         var temp = {};
    //         temp.label = "Event Count : " + json[i].EventCount;
    //         temp.value = json[i].UserCount;
    //         json[i].data = [];
    //         json[i].data.push(temp);
    //         delete json[i].EventCount;
    //         delete json[i].UserCount;
    //    }


//    json = json.sort(function (a, b) {
    //            return a.Date - b.Date || a.Cluster - b.Cluster;
    //        });


//  var piechart_data = [];

    //    for (var i = 0, j = 1; j < json.length; j++) {
    //        var tmp = {};
    //        tmp.Date = json[i].date;
    //        tmp.data = json[i].data;
    //        while ( j < json.length && json[j].Date == json[j].Date && json[i].Cluster == json[j].Cluster ) {
    //          tmp.data  = tmp.data.concat(json[j].data);
    //            j++;
    //        }

    //        piechart_data.push(tmp);
    //        i = j;
    //    }


     //for (var i = 0; i < piechart_data.length; i++) {
     //        $('#donut-chart-block').append(
     //          '<div class="col-lg-4">'
     //               + '<div class="panel panel-default">'
     //                   + '<div class="panel-heading">'
     //                       + '<h3 class="panel-title">'
     //                           + '<i class="fa fa-long-arrow-right fa-fw"></i>'+ 'Training Data Cluster : ' + (i+1)
     //                       + '</h3>'
     //                       + '<div>' + 'Total User Count : ' + tcClusters[i] + '</div>'
     //                       + '<div>' + 'Event Count Range : ' + EventRange[i] + '</div>'
     //                   + '</div>'
     //                   + '<div class="panel-body">'
     //                       + '<div id="morris-donut-chart-' + (i + 1) + '"></div>'
     //                           + '<div class="text-right">'
     //                               + '<a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>'
     //                           + '</div>'
     //                       + '</div>'
     //                   + '</div>'
     //          + '</div>'
     //      );
     //   }

    //    for (var i = 0; i < piechart_data.length; i++) {
    //        var color = [];
    //        if (i == 0) {
    //            color = ['#0B62A4', '#3980B5', '#679DC6', '#95BBD7', '#B0CCE1', '#095791', '#095085', '#083E67', '#052C48', '#042135'];
    //        } else if (i == 1) {
    //            color = ['#7a9ea8', '#7a9aa8', '#a8b8c6', '#99abbc', '#899fb2', '#7A92A3', '#6b859e', '#5f7990', '#546c81', '#3c4d5c'];
    //        } else if (i == 2) {
    //            color = ['#569e56', '#4da74d', '#02F202', '#44b044', '#3aba3a', '#31c331', '#02F202', '#418c41', '#479a47', '#02F202'];
    //            }
    //        Morris.Donut({
    //            element: 'morris-donut-chart-' + (i + 1),
    //            data: piechart_data[i].data,
    //            colors: color,
    //            resize: true
    //        });
    //    }
    ////a = piechart_data;
    ////alert('stop');
    //});
    ////data_first_day = [
    ////                {
    ////                    Date: '2016-11-01',
    ////                    ClusterNum:1,
    ////                    data: [{
    ////                            EventCount: 500,
    ////                            UserCount: 120
    ////                           },
    ////                            {
    ////                                EventCount: 900,
    ////                                UserCount: 5000
    ////                            },
    ////                            {
    ////                                EventCount: 2000,
    ////                                UserCount: 200
    ////                            }
    ////                          ]
    ////                },
    ////               {
    ////                   Date: '2016-11-01',
    ////                   ClusterNum: 2,
    ////                   data:  [{
    ////                               EnventCount: 2500,
    ////                               UserCount: 280
    ////                           },
    ////                           {
    ////                               EnventCount: 900,
    ////                               UserCount: 360
    ////                           },
    ////                           {
    ////                               EnventCount: 3000,
    ////                               UserCount: 620
    ////                           }
    ////                   ]
    ////               },
    ////                {
    ////                    Date: '2016-11-01',
    ////                    ClusterNum: 3,
    ////                    data: [{
    ////                        EnventCount: 5500,
    ////                        UserCount: 220
    ////                    },
    ////                            {
    ////                                EnventCount: 7500,
    ////                                UserCount: 450
    ////                            },
    ////                            {
    ////                                EnventCount: 8000,
    ////                                UserCount: 200
    ////                            }
    ////                    ]
    ////                }
    //// ];


////var  donutDataList = [];
    ////for (var i = 0; i < data_first_day.length; i++) {
    ////    var dataset = data_first_day[i].data;

    ////    var temp = [];


    ////    for (var j = 0; j < dataset.length; j++) {
    ////        var donutData = {};
    ////        donutData.label = "EventCount : " + dataset[j].EnventCount;
    ////        donutData.value = dataset[j].UserCount;

    ////        temp.push(donutData);

    ////    }
    ////    donutDataList[i] = temp;

    ////}
    ////var data_week = [
    ////{
    ////    period: '2016-11-01',
    ////    Cluster1: 200,
    ////    Cluster2: 500,
    ////    Cluster3: 100
    ////},
    ////{
    ////    period: '2016-11-02',
    ////    Cluster1: 1200,
    ////    Cluster2: 1500,
    ////    Cluster3: 90
    ////},
    ////{
    ////    period: '2016-11-03',
    ////    Cluster1: 2200,
    ////    Cluster2: 1500,
    ////    Cluster3: 200
    ////},
    ////{
    ////    period: '2016-11-04',
    ////    Cluster1: 900,
    ////    Cluster2: 200,
    ////    Cluster3: 600
    ////},
    ////{
    ////    period: '2016-11-05',
    ////    Cluster1: 600,
    ////    Cluster2: 200,
    ////    Cluster3: 800
    ////}
    ////];
    //$.getJSON("/content/linechart.json", function(json) {
    //    //$('#area-chart-block').append(
    //    //              '<div class="col-lg-12">' +
    //    //                 '<div class="panel panel-default">' +
    //    //                     '<div class="panel-heading">' +
    //    //                         '<h3 class="panel-title">' +
    //    //                             '<i class="fa fa-bar-chart-o fa-fw"></i>' +
    //    //                         '</h3>' +
    //    //                     '</div>' +
    //    //                    '<div class="panel-body">' +
    //    //                         '<div id="morris-area-chart"' + '></div>' +
    //    //                     '</div>' +
    //    //                     '<div class="text-right">' +
    //    //                         '<a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>' +
    //    //                     '</div>' +
    //    //                 '</div>' +
    //    //             '</div>'
    //    //);

    //    //Morris.Area({
    //    //    element: 'morris-area-chart',
    //    //    data: json,
    //    //    xkey: 'period',
    //    //    ykeys: ['Cluster3', 'Cluster2','Cluster1' ],
    //    //    labels: [ 'Cluster3', 'Cluster2','Cluster1'],
    //    //    pointSize: 2,
    //    //    hideHover: 'auto',
    //    //    resize: true
    //    //});


    //    Morris.Line({
    //        element: 'line-example',
    //        data: [
    //          { y: '2016-10-27', Cluster1: 17934901, Cluster2: 1000000, Cluster3: 1175876},
    //          { y: '2016-10-28', Cluster1: 4613051,  Cluster2: 5326400, Cluster3: 676308},
    //          { y: '2016-10-29', Cluster1: 13430364,  Cluster2: 445880, Cluster3:1298149 },
    //          { y: '2016-10-30', Cluster1: 13515, Cluster2: 60000, Cluster3: 920 }

    //        ],
    //        xkey: 'y',
    //        ykeys: ['Cluster1', 'Cluster2', 'Cluster3'],
    //        labels: ['Cluster 1', 'Cluster 2', 'Cluster 3']
    //    });
    //});


}

//var total_step1_by_country = 163274;
//var total_step2_by_country = 138782;
//var total_drop_rate_by_country = '15%' ;


//var total_step1_by_device = 163274;
//var total_step2_by_device = 138782;
//var total_drop_rate_by_device = '15%';

//function drawFunnel(Data, Title, blockid) {
//    Highcharts.chart(blockid, {
//        chart: {
//            type: 'funnel',
//            marginRight: 100
//        },
//        title: {
//            text: Title,
//            x: -50
//        },
//        plotOptions: {
//            series: {
//                dataLabels: {
//                    enabled: true,
//                    format: '<b>{point.name}</b> ({point.y:,.0f})',
//                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
//                    softConnector: true
//                },
//                neckWidth: '30%',
//                neckHeight: '25%'
//            }
//        },
//        legend: {
//            enabled: false
//        },
//        series: [{
//            name: 'User Count',
//            data:
//             Data

//        }]
//    });
//}


//function getFunnel()
//{
    
//    var total_by_country = [['Step1', total_step1_by_country], ['Step2', total_step2_by_country]];
//    var country_1 = [['Step1', 133163], ['Step2', 131587]];
//    var country_2 = [['Step1', 24480], ['Step2', 23868]];
//    var country_3 = [['Step1', 5583], ['Step2', 5314]];
//    var country_4 = [['Step1', 29], ['Step2', 25]];
//    var country_5 = [['Step1', 19], ['Step2', 15]];
    
    
  


//    var total_by_device = [['Step1', 147029], ['Step2', 144308]];
//    var device_1 = [['Step1', 146955], ['Step2', 144248]];
//    var device_2 = [['Step1', 64], ['Step2', 55]];
//    var device_3 = [['Step1', 10], ['Step2', 5]];
    

//    if ($("input[name=Critiera]:checked").val() == 'byCountry') {
//        $('#funnel-total-block').empty();
//        $('#funnel-total-block').append('<div id="container_total" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>');

//        $('#funnel-block').empty();
//        $('#funnel-block').append(
            
//            '<div class="col-lg-6" id="container_1" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//            '<div class="col-lg-6" id="container_2" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//            '<div class="col-lg-6" id="container_3" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//            '<div class="col-lg-6" id="container_4" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//            '<div class="col-lg-6" id="container_5" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>');

//        drawFunnel(total_by_country, "Total User Count Trend: Drop Off: 15% ",container_total);
//        drawFunnel(country_1, "Cluster 1: Drop Off Rate: 0 ~ 2% ", container_1);
//        drawFunnel(country_2, "Cluster 2: Drop Off Rate: 2% ~ 4% ", container_2);
//        drawFunnel(country_3, "Cluster 3: Drop Off Rate: 4% ~ 10% ", container_3);
//        drawFunnel(country_4, "Cluster 4: Drop Off Rate: 11% ~ 15% ", container_4);
//        drawFunnel(country_5, "Cluster 5: Drop Off Rate: 20% ~ 30% ",container_5);

//    }
//    if ($("input[name=Critiera]:checked").val() == 'byDevice') {
//        $('#funnel-total-block').empty();
//        $('#funnel-total-block').append('<div id="container_total" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>');

//        $('#funnel-block').empty();
    
//        $('#funnel-block').append(
//    '<div class="col-lg-6" id="container_1" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//    '<div class="col-lg-6" id="container_2" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' +
//    '<div class="col-lg-6" id="container_3" style="min-width: 410px; max-width: 600px; height: 400px; margin: 0 auto"></div>' );

//        drawFunnel(total_by_device, "Total User Count Trend: Drop Off: 1.8% ", container_total);
//        drawFunnel(device_1, "Cluster 1: Drop Off Rate: 0 ~ 4% ", container_1);
//        drawFunnel(device_2, "Cluster 2: Drop Off Rate: 10% ~ 20% ", container_2);
//        drawFunnel(device_3, "Cluster 3: Drop Off Rate: 40% ~ 60% ", container_3);
//    }


//}