function setSecond(value){
    var val = value;
    var sec = document.getElementById('country');
    if(val == "贵阳地区"){  //贵阳
        var sec = document.getElementById('country');
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("云岩区","云岩区");
        sec.options[2] = new Option("花溪区","花溪区");
        sec.options[3] = new Option("乌当区","乌当区");
        sec.options[4] = new Option("白云区","白云区");
        sec.options[5] = new Option("观山湖区","观山湖区");
        sec.options[6] = new Option("开阳县","开阳县");
        sec.options[7] = new Option("息烽县","息烽县");
        sec.options[8] = new Option("修文县","修文县");
        sec.options[9] = new Option("清镇市","清镇市");
        sec.options[10] = new Option("南明区","南明区");
    }else if (val == "六盘水地区"){   //六盘水
        var sec = document.getElementById('country');
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("六枝特区","六枝特区");
        sec.options[2] = new Option("水城县","水城县");
        sec.options[3] = new Option("盘州市","盘州市");
        sec.options[4] = new Option("钟山区","钟山区");

    } else if (val == "遵义市"){   //遵义
        var sec = document.getElementById('country');
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("汇川区","汇川区");
        sec.options[2] = new Option("播州区","播州区");
        sec.options[3] = new Option("桐梓县","桐梓县");
        sec.options[4] = new Option("绥阳县","绥阳县");
        sec.options[5] = new Option("正安县","正安县");
        sec.options[6] = new Option("道真仡佬族苗族自治县","道真仡佬族苗族自治县");
        sec.options[7] = new Option("务川仡佬族苗族自治县","务川仡佬族苗族自治县");
        sec.options[8] = new Option("凤冈县","凤冈县");
        sec.options[9] = new Option("湄潭县","湄潭县");
        sec.options[10] = new Option("余庆县","余庆县");
        sec.options[11] = new Option("习水县","习水县");
        sec.options[12] = new Option("赤水市","赤水市");
        sec.options[13] = new Option("仁怀市","仁怀市");
        sec.options[14] = new Option("红花岗区","红花岗区");

    }else if (val == "安顺地区"){   //安顺
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("平坝区","平坝区");
        sec.options[2] = new Option("普定县","普定县");
        sec.options[3] = new Option("镇宁布依族苗族自治县","镇宁布依族苗族自治县");
        sec.options[4] = new Option("关岭布依族苗族自治县","关岭布依族苗族自治县");
        sec.options[5] = new Option("紫云苗族布依族自治县","紫云苗族布依族自治县");
        sec.options[6] = new Option("西秀区","西秀区");

    }
    else if (val == "毕节地区"){   //毕节
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("大方县","大方县");
        sec.options[2] = new Option("黔西县","黔西县");
        sec.options[3] = new Option("金沙县","金沙县");
        sec.options[4] = new Option("织金县","织金县");
        sec.options[5] = new Option("纳雍县","纳雍县");
        sec.options[6] = new Option("威宁彝族回族苗族自治县","威宁彝族回族苗族自治县");
        sec.options[7] = new Option("赫章县","赫章县");
        sec.options[8] = new Option("七星关区","七星关区");

    }
    else if (val == "铜仁地区"){   //铜仁
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("万山区","万山区");
        sec.options[2] = new Option("江口县","江口县");
        sec.options[3] = new Option("玉屏侗族自治县","玉屏侗族自治县");
        sec.options[4] = new Option("石阡县","石阡县");
        sec.options[5] = new Option("思南县","思南县");
        sec.options[6] = new Option("印江土家族苗族自治县","印江土家族苗族自治县");
        sec.options[7] = new Option("德江县","德江县");
        sec.options[8] = new Option("沿河土家族自治县","沿河土家族自治县");
        sec.options[9] = new Option("松桃苗族自治县","松桃苗族自治县");
        sec.options[10] = new Option("碧江区","碧江区");

    }
    else if (val == "黔西南布依族苗族自治州"){   //黔西南布依族苗族自治州
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("兴仁市","兴仁市");
        sec.options[2] = new Option("普安县","普安县");
        sec.options[3] = new Option("晴隆县","晴隆县");
        sec.options[4] = new Option("贞丰县","贞丰县");
        sec.options[5] = new Option("望谟县","望谟县");
        sec.options[6] = new Option("册亨县","册亨县");
        sec.options[7] = new Option("安龙县","安龙县");
        sec.options[8] = new Option("兴义市","兴义市");

    } else if (val == "黔东南苗族侗族自治州"){   //黔东南苗族侗族自治州
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("黄平县","黄平县");
        sec.options[2] = new Option("施秉县","施秉县");
        sec.options[3] = new Option("三穗县","三穗县");
        sec.options[4] = new Option("镇远县","镇远县");
        sec.options[5] = new Option("岑巩县","岑巩县");
        sec.options[6] = new Option("天柱县","天柱县");
        sec.options[7] = new Option("锦屏县","锦屏县");
        sec.options[8] = new Option("剑河县","剑河县");
        sec.options[9] = new Option("台江县","台江县");
        sec.options[10] = new Option("黎平县","黎平县");
        sec.options[11] = new Option("榕江县","榕江县");
        sec.options[12] = new Option("从江县","从江县");
        sec.options[13] = new Option("雷山县","雷山县");
        sec.options[14] = new Option("麻江县","麻江县");
        sec.options[15] = new Option("丹寨县","丹寨县");
        sec.options[16] = new Option("凯里市","凯里市");

    }else if (val == "黔南布依族苗族自治州"){   //黔南布依族苗族自治州
        sec.options.length=0; //清空二级菜单项
        sec.options[0] = new Option("所有地区","all");
        sec.options[1] = new Option("福泉市","福泉市");
        sec.options[2] = new Option("荔波县","荔波县");
        sec.options[3] = new Option("贵定县","贵定县");
        sec.options[4] = new Option("瓮安县","瓮安县");
        sec.options[5] = new Option("独山县","独山县");
        sec.options[6] = new Option("平塘县","平塘县");
        sec.options[7] = new Option("罗甸县","罗甸县");
        sec.options[8] = new Option("长顺县","长顺县");
        sec.options[9] = new Option("龙里县","龙里县");
        sec.options[10] = new Option("惠水县","惠水县");
        sec.options[11] = new Option("三都水族自治县","三都水族自治县");
        sec.options[12] = new Option("都匀市","都匀市");

    }
}
function  setkind(value) {
    $.ajax({
        contentType:"application/json",
        type:"GET",
        url:"/selectkind/"+value,
        dataType:"json",
        success:function (data) {
            var sec = document.getElementById('Fungi');
            sec.options.length = 0;
            if(data.length != 0) {
                sec.options[0] = new Option("所有种类", "allkind");
                sec.options[1] = new Option("30种重点调查物种","30")
                if(value == 3){
                    for (var i = 0; i < data.length; i++) {
                        sec.options[i + 2] = new Option(data[i].genus1, data[i].genus1);
                    }
                }else {
                    for (var i = 0; i < data.length; i++) {
                        sec.options[i + 2] = new Option(data[i].shorttext1567761455594, data[i].shorttext1567761455594);

                    }
                }
            }

        }
    })
}