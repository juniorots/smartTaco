/*
 * Customizando Menus existentes na pagina principal
 */
$(function () { 
    // Stack initialize
    var openspeed = 300;
    var closespeed = 300;
    
    $('.stackCesgranrio>img').click(function () {
        if ( $('.stackCesgranrio').find('ul').attr('class') !== 'openStack') {
            var vertical = 0;
            var horizontal = 0;
            var $el=$(this);
            $el.next().children().each(function(){
                $(this).animate({top: '-' + vertical + 'px', left: horizontal + 'px'}, openspeed);
                vertical = vertical + 55;
                horizontal = (horizontal+.75)*2;
            });
            $el.next().animate({top: '403px', left: '598px'}, openspeed).addClass('openStack')
                .find('li a>img').animate({width: '60px', marginLeft: '0px'}, openspeed);
            $el.animate({paddingTop: '0'});
            fecharOutrasCestas('.stackCesgranrio');
        } else {
            //reverse above
            var $el=$(this);
            $el.next().removeClass('openStack').children('li').animate({top: '72px', left: '-112px'}, closespeed);
            $el.next().find('li a>img').animate({width: '71px', marginLeft: '102'}, closespeed);
        }
    });
     
    // Stacks additional animation
    $('.stackCesgranrio li a').hover(function(){
       if ( $('.stackCesgranrio').find('ul').attr('class') === 'openStack') {
            $("img",this).animate({width: '71px'}, 100);
            $("span",this).animate({marginRight: '30px'});
        }
    },function(){
        if ( $('.stackCesgranrio').find('ul').attr('class') === 'openStack') {
            $("img",this).animate({width: '60px'}, 100);
            $("span",this).animate({marginRight: '0px'});
        }
    });
});