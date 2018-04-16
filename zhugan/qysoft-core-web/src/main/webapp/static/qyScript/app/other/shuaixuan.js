// ----------------------------------------------//
//                                               //
//                唐明明☀20150724                //
//                                               //
// ----------------------------------------------//
$(document).ready(function(){
    $(".screening>ul").click(function(){
        $("html,body").animate({scrollTop: $(".screening").offset().top}, 0);
        // $("html,body").addClass("allscr");
        if ($('.grade-eject,.Category-eject,.Sort-eject,.Class-eject').hasClass('grade-w-roll')) {
            $("body,html").css({"overflow":"hidden"}); 
        } else {
            $("body,html").css({"overflow":"visible"}); 
        }

    });
});

$(document).ready(function(){
    $(".mask").click(function(){
        $(this).parent().removeClass('grade-w-roll');
        if ($('.grade-eject,.Category-eject,.Sort-eject,.Class-eject').hasClass('grade-w-roll')) {
            $("body,html").css({"overflow":"hidden"}); 
        } else {
            $("body,html").css({"overflow":"visible"}); 
        }
    });
});
//Regional开始
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')) {
            $('.grade-eject').removeClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
        } else {
            $('.grade-eject').addClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-down').addClass('aui-icon-top');
        }
    });
});

$(document).ready(function(){
    $(".grade-eject .mask").click(function(){
        $(".Regional").find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
    });
});

//Fangs开始
$(document).ready(function(){
    $(".Fangs").click(function(){
        if ($('.Fangs-eject').hasClass('grade-w-roll')) {
            $('.Fangs-eject').removeClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
        } else {
            $('.Fangs-eject').addClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-down').addClass('aui-icon-top');
        }
    });
});

$(document).ready(function(){
    $(".Fangs-eject .mask").click(function(){
        $(".Fangs").find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
    });
});


//Brand开始

$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')) {
            $('.Category-eject').removeClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
        } else {
            $('.Category-eject').addClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-down').addClass('aui-icon-top');
        }
    });
});

$(document).ready(function(){
    $(".Category-eject .mask").click(function(){
        $(".Brand").find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
    });
});
//Sort开始

$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')) {
            $('.Sort-eject').removeClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
        } else {
            $('.Sort-eject').addClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-down').addClass('aui-icon-top');
        }
    });
});

$(document).ready(function(){
    $(".Sort-eject .mask").click(function(){
        $(".Sort").find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
    });
});
//Class开始

$(document).ready(function(){
    $(".Class").click(function(){
        if ($('.Class-eject').hasClass('grade-w-roll')) {
            $('.Class-eject').removeClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
        } else {
            $('.Class-eject').addClass('grade-w-roll');
            $(this).find('i').removeClass('aui-icon-down').addClass('aui-icon-top');
        }
    });
});

$(document).ready(function(){
    $(".Class-eject .mask").click(function(){
        $(".Class").find('i').removeClass('aui-icon-top').addClass('aui-icon-down');
    });
});
//判断页面是否有弹出

$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Class-eject').hasClass('grade-w-roll')){
            $('.Class-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Fangs-eject').hasClass('grade-w-roll')){
            $('.Fangs-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Class-eject').hasClass('grade-w-roll')){
            $('.Class-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Fangs-eject').hasClass('grade-w-roll')){
            $('.Fangs-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Class-eject').hasClass('grade-w-roll')){
            $('.Class-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Fangs-eject').hasClass('grade-w-roll')){
            $('.Fangs-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Class").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Class").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Class").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Class").click(function(){
        if ($('.Fangs-eject').hasClass('grade-w-roll')){
            $('.Fangs-eject').removeClass('grade-w-roll');
        };
    });
});

$(document).ready(function(){
    $(".Fangs").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Fangs").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Fangs").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Fangs").click(function(){
        if ($('.Class-eject').hasClass('grade-w-roll')){
            $('.Class-eject').removeClass('grade-w-roll');
        };
    });
});
//js点击事件监听开始
function grade1(wbj){
    var arr = document.getElementById("gradew").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    wbj.style.background = "#eee"
}

function gradet(tbj){
    var arr = document.getElementById("gradet").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    tbj.style.background = "#fff"
}

function grades(sbj){
    var arr = document.getElementById("grades").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}

function Categorytw(wbj){
    var arr = document.getElementById("Categorytw").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    wbj.style.background = "#eee"
}

function Categoryt(tbj){
    var arr = document.getElementById("Categoryt").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    tbj.style.background = "#fff"
}

function Categorys(sbj){
    var arr = document.getElementById("Categorys").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}

function Sorts(sbj){
    var arr = document.getElementById("Sort-Sort").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}
function Class(sbj){
    var arr = document.getElementById("Class-Class").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}
function Class(sbj){
    var arr = document.getElementById("Fangs-Fangs").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}