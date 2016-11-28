$(function () {
	$('#aa').hide();
	$('#bb').hide();
    Highcharts.setOptions({
        lang: {
            rangeSelectorZoom: ''
        }
    });
    var symbol = $('#symbol').val();
    var a = $('#b').val();
//    var b = $('#e').val();
//    console.log(a+'---'+b)
//    a = '2000-01-06';
//    b = '2000-02-26';
//    
//    $('#bb').datebox('setValue',a);
//    $('#aa').datebox('setValue',b);
//    var begin = $('#bb').datebox('getValue');
//	var end = $('#aa').datebox('getValue');
	
    $.getJSON(urls['msUrl']+'stockMain/showChart.do?symbol='+symbol+'&begin='+a, function (result) {
    	var data = result.data;
        var ohlc = [],
            volume = [],
            dataLength = data.length,
            // set the allowed units for data grouping
            groupingUnits = [[
                'week',                         // unit name
                [1]                             // allowed multiples
            ], [
                'month',
                [1, 2, 3, 4, 6]
            ]],
            i = 0;
        for (i; i < dataLength; i += 1) {
            ohlc.push([
                data[i].day, // the date
                data[i].open, // open
                data[i].max, // high
                data[i].min, // low
                data[i].close, // close
                data[i].increase
            ]);
            volume.push([
                data[i].day, // the date
                data[i].volume // the volume
            ]);
        }
        // create the chart
        $('#container').highcharts('StockChart', {
            rangeSelector: {
                selected: 1,
                inputDateFormat: '%Y-%m-%d'
            },
            title: {
                text: data[0].symbol
            },
            xAxis: {
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%m-%d',
                    week: '%m-%d',
                    month: '%y-%m',
                    year: '%Y'
                }
            },
            yAxis: [{
                labels: {
                    align: 'right',
                    x: -3
                },
                title: {
                    text: '股价'
                },
                height: '70%',
                lineWidth: 2
            }, {
                labels: {
                    align: 'right',
                    x: -3
                },
                title: {
                    text: '成交量'
                },
                top: '75%',
                height: '35%',
                offset: 0,
                lineWidth: 2
            }],
            series: [{
                type: 'candlestick',
                name: 'AAPL',
                data: ohlc,
                dataGrouping: {
                    units: groupingUnits
                }
            }, {
                type: 'column',
                name: 'Volume',
                data: volume,
                yAxis: 1,
                dataGrouping: {
                    units: groupingUnits
                }
            }]
        });
    });
});
