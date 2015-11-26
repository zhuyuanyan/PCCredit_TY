// JavaScript Document
var base = {
    args : {
        sign : null,
        left : null,
        right : null,
        result : null,
        memory : null,
        isConcat : true,
        isOperator : false
    },
    input : function(btn) {
        var value = btn.value;
        if (/[0-9]/.test(value)) {
            if (document.getElementById("resultIpt").value.length >= 16
                    && base.args.isConcat) {
                return false;
            }
            document.getElementById("resultIpt").style.fontSize = "40";
            if ((base.args.sign == "=" || base.args.sign == null)
                    && !base.args.isConcat) {
                base.output(null, "");
            }
            if (base.args.isConcat) {
                if (document.getElementById("resultIpt").value == "0") {
                    document.getElementById("resultIpt").value = "";
                }
                base
                        .output(document.getElementById("resultIpt").value
                                + value,
                                document.getElementById("expressBox").innerHTML
                                        + value);
            } else {
                base
                        .output(value,
                                document.getElementById("expressBox").innerHTML
                                        + value);
            }
            base.args.isConcat = true;
            base.args.isOperator = false;
        }
        if (/\./.test(value)) {
            if (document.getElementById("resultIpt").value.length > 16
                    && base.args.isConcat) {
                return false;
            }
            if (document.getElementById("resultIpt").value.indexOf(".") != -1
                    && base.args.isConcat) {
                return false;
            }
            if (base.args.isConcat) {
                base
                        .output(document.getElementById("resultIpt").value
                                + value,
                                document.getElementById("expressBox").innerHTML
                                        + value);
            } else {
                base
                        .output("0" + value, document
                                .getElementById("expressBox").innerHTML
                                + value);
                base.args.isConcat = true;
            }
        }
        if (/[\+|\-|÷|×]/.test(value) && value.length < 2) {
            if (!base.args.isOperator) {
                base.set();
                base.args.result = base.calculate();
                if (base.args.result) {
                    base.output(base.args.result, document
                            .getElementById("expressBox").innerHTML
                            + "=" + base.args.result);
                    base.clear(true);
                    base.args.left = base.args.result;
                }
                base
                        .output(null,
                                document.getElementById("expressBox").innerHTML
                                        + value);
            } else {
                base.output(null,
                        document.getElementById("expressBox").html()
                                .substring(
                                        0,
                                        document.getElementById("expressBox")
                                                .html().length - 1)
                                + value);
            }
            base.args.sign = value;
            base.args.isConcat = false;
            base.args.isOperator = true;
        }
        if (/[\+\/-]/.test(value) && value.length > 2) {
            var result = document.getElementById("resultIpt").value;
            document.getElementById("expressBox").innerHTML = document
                    .getElementById("expressBox").innerHTML
                    .substr(
                            0,
                            (document.getElementById("expressBox").innerHTML.length - result.length));
            if (Number(result) != 0 && result != "") {
                result = -Number(result);
            }
            base.output(result, document.getElementById("expressBox").innerHTML
                    + result);
            base.set();
        }
        if (/=/.test(value)) {
            base.set();
            base.args.result = base.calculate();
            // if(/^\-?\d+(?=\.{0,1}\d+$|$)/.test(base.args.result)){//^[0-9]+.?[0-9]*$/
            base.output(base.args.result,
                    document.getElementById("expressBox").innerHTML + "="
                            + base.args.result);
            base.args.sign = value;
            base.clear(true);
            base.args.left = base.args.result;
            // }
            base.args.isConcat = false;
        }
        if (btn.maxLength >= 0 && (String(btn.maxLength).length == 1)) {
            switch (btn.maxLength) {
            case 5:
                base.save();
                break;
            case 1:
                var memory = base.get();
                if (memory == null) {
                    return false;
                }
                base.args.isConcat = false;
                base.output(memory,
                        document.getElementById("expressBox").innerHTML
                                + memory);
                break;
            case 2:
                base.saveAdd();
                break;
            case 3:
                base.saveMultiple();
                break;
            case 4:
                base.saveClear();
                break;
            case 8:
                base.remove();
                break;
            case 9:
                base.clear();
                break;
            }
        }
    },
    output : function(value, html) {
        if (value != null) {
            // if((value+"").length >
            // 16){document.getElementById("resultIpt").style.fontSize = "35";}
            if ((value + "").length > 16) {// $("resultInput").style.fontSize =
                                            // "35";
                if ((value + "").indexOf(".") != -1) {
                    var r = (value + "").split(".")[1];
                    var count = (value + "").length - 16;
                    if (r.length > count) {
                        r = r.substring(0, (r.length - count));
                        value = (value + "").split(".")[0] + "." + r;
                    } else {
                        document.getElementById("resultIpt").style.fontSize = "35";
                    }
                } else {
                    document.getElementById("resultIpt").style.fontSize = "35";
                }
            }
            document.getElementById("resultIpt").value = value;
        }
        if (html != null) {
            document.getElementById("expressBox").innerHTML = html;
        }
    },
    accurate : {
        add : function(num1, num2) {
            var index1 = 0, index2 = 0, index = 0;
            try {
                index1 = String(num1).split(".")[1].length
            } catch (e) {
                index1 = 0;
            }
            try {
                index2 = String(num2).split(".")[1].length
            } catch (e) {
                index2 = 0;
            }
            index = Math.pow(10, Math.max(index1, index2));
            return parseFloat((num1 * index + num2 * index) / index);
        },
        sub : function(num1, num2) {
            var index1 = 0, index2 = 0, index = 0;
            try {
                index1 = String(num1).split(".")[1].length
            } catch (e) {
                index1 = 0;
            }
            try {
                index2 = String(num2).split(".")[1].length
            } catch (e) {
                index2 = 0;
            }
            index = Math.pow(10, Math.max(index1, index2));
            return (num1 * index - num2 * index) / index;
        },
        mul : function(num1, num2) {
            var index = 0;
            try {
                index += String(num1).split(".")[1].length
            } catch (e) {
            }
            try {
                index += String(num2).split(".")[1].length
            } catch (e) {
            }
            return (Number(String(num1).replace(".", "")) * Number(String(num2)
                    .replace(".", "")))
                    / Math.pow(10, index);
        },
        div : function(num1, num2) {

            var index1 = 0, index2 = 0;
            try {
                index1 = String(num1).split(".")[1].length
            } catch (e) {
            }
            try {
                index2 = String(num2).split(".")[1].length
            } catch (e) {
            }
            // alert(index1 + " @@ " + index2);
            // return (Number(String(num1).replace(".","")) /
            // Number(String(num2).replace(".",""))) * Math.pow(10,(index2 -
            // index1));
            var a = Number(String(num1).replace(".", ""));
            var b = Number(String(num2).replace(".", ""));
            var c = Math.pow(10, (index2 - index1));
            var result = null;
            if ((a / b).toString().indexOf("e") != -1) {
                result = (a / b) * c;
            } else {
                result = base.accurate.mul((a / b), c);
            }
            return result;
        }
    },
    calculate : function() {
        if (base.args.left != null && base.args.sign != null
                && base.args.right != null) {
            switch (base.args.sign) {
            case "+":
                return base.accurate.add(Number(base.args.left),
                        Number(base.args.right));
                break;
            case "-":
                return base.accurate.sub(Number(base.args.left),
                        Number(base.args.right));
                break;
            case "×":
                return base.accurate.mul(Number(base.args.left),
                        Number(base.args.right));
                break;
            case "÷":
                return base.accurate.div(Number(base.args.left),
                        Number(base.args.right));
                break;
            }
        }
    },
    set : function() {
        var value = document.getElementById("resultIpt").value;
        if (base.args.sign == null) {
            // left
            base.args.left = value;
        } else {
            // right
            base.args.right = value;
        }
    },
    remove : function() {
        var html = document.getElementById("expressBox").innerHTML;
        var value = document.getElementById("resultIpt").value;
        if (html.length >= 1) {
            if (/[0-9|\.]/.test(html.substr(html.length - 1, 1))) {
                html = html.substring(0, html.length - 1);
                value = String(value).substring(0, value.length - 1);
                if (value == "") {
                    value = 0;
                }
                base.output(value, html);
                base.set();
            }
        }
    },
    clear : function(flag) {
        if (flag != true) {
            base.args.sign = null;
            base.args.left = null;
            base.args.right = null;
            base.args.result = null;
            // base.args.memory = null;
            document.getElementById("expressBox").innerHTML = "";
            document.getElementById("resultIpt").value = 0;
        } else if (flag == true) {
            base.args.sign = null;
            base.args.left = null;
            base.args.right = null;
        }
    },
    save : function() {
        base.args.memory = document.getElementById("resultIpt").value;
        base.args.isConcat = false;
    },
    get : function() {
        return (base.args.memory == null) ? 0 : base.args.memory;
    },
    saveAdd : function() {
        if (base.args.memory != null) {
            base.args.memory = base.accurate.add(Number(base.args.memory),
                    Number(document.getElementById("resultIpt").value));
        } else {
            base.save();
        }
    },
    saveMultiple : function() {
        if (base.args.memory != null) {
            base.args.memory = base.accurate.mul(Number(base.args.memory),
                    Number(document.getElementById("resultIpt").value));// base.args.memory
                                                                        // *
                                                                        // Number(document.getElementById("resultIpt").value);//
                                                                        // accurate
        } else {
            base.save();
        }
    },
    saveClear : function() {
        base.args.memory = null;
    },
    showStyle : function(btn) {
        btn.style.backgroundPosition = "0 0";
        try {
            clearTimeout(itv);
        } catch (e) {
        }
        var itv = setTimeout(function() {
            btn.style.backgroundPosition = "0px -44px";
        }, 100);
    }
}
my
        .loaded(function() {
            // tabs click
           
            // btns mousedown
            var btns = $t("INPUT").$css("btnCss");
            for ( var i = 0; i < btns.length; i++) {
                btns[i].bind("mousedown", function() {
                    var e = my.events.get();
                    var target = e.target;
                    target.style.backgroundPosition = "0 0";
                });
                btns[i].bind("mouseup", function() {
                    var e = my.events.get();
                    var target = e.target;
                    target.style.backgroundPosition = "0px -44px";
                });
                btns[i].bind("click", function() {
                    var e = my.events.get();
                    var target = e.target;
                    target.blur();
                    base.input(target);
                });
            }
            // keybroad
            my.events.bind(document, "keydown", function() {
                var e = my.events.get();
                var keyCode = e.keyCode;
                var btn = null;
                if (keyCode == 48 || keyCode == 96) {
                    btn = $("num0Btn");
                }
                if (keyCode == 49 || keyCode == 97) {
                    btn = $("num1Btn");
                }
                if (keyCode == 50 || keyCode == 98) {
                    btn = $("num2Btn");
                }
                if (keyCode == 51 || keyCode == 99) {
                    btn = $("num3Btn");
                }
                if (keyCode == 52 || keyCode == 100) {
                    btn = $("num4Btn");
                }
                if (keyCode == 53 || keyCode == 101) {
                    btn = $("num5Btn");
                }
                if (keyCode == 54 || keyCode == 102) {
                    btn = $("num6Btn");
                }
                if (keyCode == 55 || keyCode == 103) {
                    btn = $("num7Btn");
                }
                if (keyCode == 56 || keyCode == 104) {
                    btn = $("num8Btn");
                }
                if (keyCode == 57 || keyCode == 105) {
                    btn = $("num9Btn");
                }
                if (keyCode == 190 || keyCode == 110) {
                    btn = $("dotBtn");
                }
                if (keyCode == 109 || keyCode == 189) {
                    btn = $("subBtn");
                }
                if (keyCode == 111 || keyCode == 191) {
                    btn = $("divBtn");
                }
                if (keyCode == 13 || keyCode == 187) {
                    btn = $("equBtn");
                }
                if (keyCode == 107) {
                    btn = $("addBtn");
                }
                if (keyCode == 106) {
                    btn = $("mulBtn");
                }
                if (keyCode == 46) {
                    btn = $("removeBtn");
                }
                if (btn) {
                    btn.click();
                    base.showStyle(btn);
                }
            });
        });
function showjsq() {
    if (document.getElementById('baseMainPanel').style.display == 'block') {
        document.getElementById('baseMainPanel').style.display = 'none'
    } else
        document.getElementById('baseMainPanel').style.display = 'block'

}

var rDrag = {

    o : null,

    init : function(o) {
        o.onmousedown = this.start;
    },
    start : function(e) {
        var o;
        e = rDrag.fixEvent(e);
        e.preventDefault && e.preventDefault();
        rDrag.o = o = this;
        o.x = e.clientX - rDrag.o.offsetLeft;
        o.y = e.clientY - rDrag.o.offsetTop;
        document.onmousemove = rDrag.move;
        document.onmouseup = rDrag.end;
    },
    move : function(e) {
        e = rDrag.fixEvent(e);
        var oLeft, oTop;
        oLeft = e.clientX - rDrag.o.x;
        oTop = e.clientY - rDrag.o.y;
        rDrag.o.style.left = oLeft + 'px';
        rDrag.o.style.top = oTop + 'px';
    },
    end : function(e) {
        e = rDrag.fixEvent(e);
        rDrag.o = document.onmousemove = document.onmouseup = null;
    },
    fixEvent : function(e) {
        if (!e) {
            e = window.event;
            e.target = e.srcElement;
            e.layerX = e.offsetX;
            e.layerY = e.offsetY;
        }
        return e;
    }
}
