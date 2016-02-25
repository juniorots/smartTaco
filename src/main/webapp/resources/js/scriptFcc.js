/*
 * Customizando Menus existentes na pagina principal
 */
$(function () { 
    // Stack initialize
    var openspeed = 300;
    var closespeed = 300;
    
    $('.stackFcc>img').click(function () {
        if ( $('.stackFcc').find('ul').attr('class') !== 'openStack') {
            var vertical = 0;
            var horizontal = 0;
            var $el=$(this);
            $el.next().children().each(function(){
                $(this).animate({top: '-' + vertical + 'px', left: horizontal + 'px'}, openspeed);
                vertical = vertical + 55;
                horizontal = (horizontal+.75)*2;
            });
            $el.next().animate({top: '403px', left: '448px'}, openspeed).addClass('openStack')
                .find('li a>img').animate({width: '45px', marginLeft: '0px'}, openspeed);
            $el.animate({paddingTop: '0'});
            fecharOutrasCestas('.stackFcc');
        } else {
            //reverse above
            var $el=$(this);
            $el.next().removeClass('openStack').children('li').animate({top: '72px', left: '-107px'}, closespeed);
            $el.next().find('li a>img').animate({width: '65px', marginLeft: '102'}, closespeed);
        }
    });
     
    // Stacks additional animation
    $('.stackFcc li a').hover(function(){
       if ( $('.stackFcc').find('ul').attr('class') === 'openStack') {
            $("img",this).animate({width: '55px'}, 100);
            $("span",this).animate({marginRight: '30px'});
        }
    },function(){
        if ( $('.stackFcc').find('ul').attr('class') === 'openStack') {
            $("img",this).animate({width: '45px'}, 100);
            $("span",this).animate({marginRight: '0px'});
        }
    });
});