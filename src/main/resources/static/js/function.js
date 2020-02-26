series = function (servicedata) {
    var series=[];
    var ac=[];
    var k=1;
    for(var i=0;i<servicedata.length;i++){
        if(i == 0){
            ac.push(servicedata[i]);
        }
        else{

            if(servicedata[i].name == servicedata[i-1].name){
                ac.push(servicedata[i]);
                if(i == servicedata.length-1) {
                    var item={
                        name:ac[0].name,
                        type:'effectScatter',
                        coordinateSystem:'geo',
                        zlevel:2,
                        z:k,
                        rippleEffect: {
                            brushType: 'stroke'
                        },
                        label: {
                            emphasis: {
                                show: true,
                                position: 'right',
                                formatter: '{b}'
                            }
                        },
                        symbolSize: function (val) {
                            return val[2] / 10;
                        },
                        showEffectOn: 'render',
                        itemStyle: {
                            normal: {
                                color: '#46bee9'
                            }
                        },
                        data: ac

                    }
                    series.push(item);
                }
            }
            else {
                var item={
                    name:ac[0].name,
                    type:'effectScatter',
                    coordinateSystem:'geo',
                    zlevel:2,
                    z:k,
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    label: {
                        emphasis: {
                            show: true,
                            position: 'right',
                            formatter: '{b}'
                        }
                    },
                    symbolSize: function (val) {
                        return val[2] / 10;
                    },
                    showEffectOn: 'render',
                    itemStyle: {
                        normal: {
                            color: '#46bee9'
                        }
                    },
                    data: ac

                }
                series.push(item);
                ac=[];
                k++;
                ac.push(servicedata[i]);
                if(i == servicedata.length-1) {
                    var item = {
                        name: ac[0].name,
                        type: 'effectScatter',
                        coordinateSystem: 'geo',
                        zlevel: 2,
                        z: k,
                        rippleEffect: {
                            brushType: 'stroke'
                        },
                        label: {
                            emphasis: {
                                show: true,
                                position: 'right',
                                formatter: '{b}'
                            }
                        },
                        symbolSize: function (val) {
                            return val[2] / 10;
                        },
                        showEffectOn: 'render',
                        itemStyle: {
                            normal: {
                                color: '#46bee9'
                            }
                        },
                        data: ac

                    }
                    series.push(item);
                }
            }
        }
    }
    return series;
},
lengend = function (servicedata) {

    var ac = [];
    var data = [];
    for (var i = 0; i < servicedata.length; i++) {
        if (i == 0) {
            ac.push(servicedata[i]);
        }
        else {
            if (servicedata[i].name == servicedata[i - 1].name) {
                ac.push(servicedata[i]);
                if(i == servicedata.length-1){
                    var item = {
                        name: ac[0].name,
                        icon: "circle",
                        textStyle: {
                            color: "#a9d6fa"
                        }

                    }
                    data.push(item);
                    ac.push(servicedata[i]);
                }
            }
            else {
                var item = {
                    name: ac[0].name,
                    icon: "circle",
                    textStyle: {
                        color: "#a9d6fa"
                    }

                }
                data.push(item);
                ac = [];
                ac.push(servicedata[i]);
                if(i == servicedata.length-1){
                    var item = {
                        name: ac[0].name,
                        icon: "circle",
                        textStyle: {
                            color: "#a9d6fa"
                        }

                    }
                    data.push(item);
                    ac.push(servicedata[i]);
                }
            }
        }
    }
    return data;
}