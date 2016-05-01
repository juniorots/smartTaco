var bancas = [
    ".stack", // cespe
    ".stackQuadrix",
    ".stackFcc",
    ".stackCesgranrio",
    ".stackExercito",
    ".stackMarinha",
    ".stackAeronautica"
];

function fecharOutrasCestas( banca ) {
    var closespeed = 300;
    
    for (i = 0; i < bancas.length; i++) {
        if ( (bancas[i] !== banca) && ( $(bancas[i]).find('ul').attr('class') === 'openStack' ) ) {
            $(bancas[i]).find('ul').removeClass('openStack').children('li').animate({top: '71px', left: '-10px'}, closespeed);
            
            if ( bancas[i] === '.stackFcc') {
                $(bancas[i]).find('ul').removeClass('openStack').children('li').animate({top: '72px', left: '-5px'}, closespeed);
                $(bancas[i]).find('ul > li > a > img').animate({width: '65px'}, 100);
            } else if ( bancas[i] === '.stackExercito' || bancas[i] === '.stackMarinha' || bancas[i] === '.stackAeronautica') {
                $(bancas[i]).find('ul').removeClass('openStack').children('li').animate({top: '72px', left: '-3px'}, closespeed);
                $(bancas[i]).find('ul > li > a > img').animate({width: '45px'}, 100);
            } else {
                // default case
                $(bancas[i]).find('ul > li > a > img').animate({width: '71px'}, 100);
            }
            $(bancas[i]).find('ul > li > a > span').animate({marginRight: '0px'});
        }
    }
}