/**
 * Created by Araja Jyothi Babu on 22-May-16.
 */
/*
(function(factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'));
    } else {
        factory(jQuery);
    }

}(function($) {
    'use strict';
    var Retention = window.Retention || {};

    Retention = (function() {

        function Retention(element, options) {

        }
        return Retention;

    }());

    $.fn.retention = function() {

    };

}));*/



function getRows(data){
    var rows = [];
    var keys = Object.keys(data);
    var days = [];
    var percentDays = [];
    for(var key in keys){
        if(data.hasOwnProperty(keys[key])) {
            days = data[keys[key]];
            percentDays.push(keys[key]);
            for(var i = 0; i < days.length; i++){
                percentDays.push(i > 0 ? Math.round((days[i]/days[0] * 100) * 100) / 100 : days[i]);
            }
            rows.push(percentDays);
            percentDays = [];
        }
    }
    return rows;
}

function isValidHex(color){
    return /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(color);
}

function shadeColor(color, percent) { //#
    //color = isValidHex(color) ? color : "#3f83a3"; //handling null color;
   
    //percent = 1.0 - Math.ceil(percent / 10) / 10;
    //var f=parseInt(color.slice(1),16),t=percent<0?0:255,p=percent<0?percent*-1:percent,R=f>>16,G=f>>8&0x00FF,B=f&0x0000FF;
    
    //return "#" + (0x1000000 + (Math.round((t - R) * p) + R) * 0x10000 + (Math.round((t - G) * p) + G) * 0x100 + (Math.round((t - B) * p) + B)).toString(16).slice(1);
    i = percent * 10;
  
    var f =0;
    if (i < 550) {
        f = 2;
    }else if (i < 660) {
        f = 1;
    } else {
        f = 0;
    }
    i=percent;
    switch (f) {
    case 0:
        return "#00FF00" ;
    case 1:
        return "#FFFF00";
    case 2:
        return "#CC6600";
    }
}






function tooltipData(count, dayIndex){
    return  (count + "% of users were active after " + (dayIndex - 1)) + (dayIndex == 2 ? "day" : " days");
}


$.fn.Retention = function (options) {
    var atest = 1;
    var graphTitle = options.title || "Retention Graph";
    var data = options.data || null;

    var container = d3.select(this[0]).append("div")
        .attr("class", "box");

    var header = container.append("div")
        .attr("class", "box-header with-border");
    var title = header.append("p")
        .attr("class", "box-title")
        .text(graphTitle);
    var controls = header.append("div")
        .attr("class", "box-tools");
    var dateRange = controls.append("input")
        .attr("id", "date-range")
        .attr("type", "hidden"); //TODO: implement daterangepicker
  
    var body = container.append("div")
      ;

    var table = body.append("table")
        .attr("class", "table table-bordered text-center");

    var headData = ["Date \\ Days", "ActiveUsers", "day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10", "day11", "day12", "day13", "day14", "day15", "day16", "day17", "day18"];

    var tHead = table.append("thead")
        .append("tr")
        .attr("class", "retention-thead")
        .selectAll("td")
        .data(headData)
        .enter()
        .append("td")
        .attr("class", function (d, i) {
            if(i == 0)
                return "retention-date"
            else
                return "days"
        })
        .text(function (d) {
            return d;
        });

    var rowsData = getRows(data);

    var tBody = table.append("tbody");

    var rows = tBody.selectAll("tr")
        .data(rowsData).enter()
        .append("tr");

    var cells = rows.selectAll("td")
        .data(function (row, i) {
            return row;
        }).enter()
        .append("td")
        .attr("class", function (d, i) {
            if(i == 0)
                return "retention-date";
            else
                return "days";
        })
        .attr("style", function(d, i) {
            if (i > 1) 
            return "background-color :" + shadeColor("", d);
       
        })
        .append("div")
        .attr("data-toggle", "tooltip")
        .attr("title", function (d, i) {
            if(i != 0 && i != 1 && d != 0){
				//console.log(tooltipData(d, i));
			}
            return tooltipData(d, i);
        })
        .text(function (d, i) {
			var retention_rate = d + (i > 1 ? "%" : "");
			if(retention_rate == '0%' ||retention_rate == '60%'){
				return null;
			}
            return retention_rate;
        });

    $('[data-toggle="tooltip"]').tooltip(); //calling bootstrap tooltip

};